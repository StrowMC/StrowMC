package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.LocationBean;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationsBridge extends AbstractBridge {

    @Inject
    public LocationsBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }

    public void loadLocations() {
        Map<Integer, LocationBean> locations = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.LOCATIONS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String world = resultSet.getString("world");
                        double x = resultSet.getDouble("x");
                        double y = resultSet.getDouble("y");
                        double z = resultSet.getDouble("z");
                        float yaw = resultSet.getFloat("yaw");
                        float pitch = resultSet.getFloat("pitch");

                        LocationBean location = new LocationBean(id, world, x, y, z, yaw, pitch);
                        locations.put(id, location);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<Integer, LocationBean> location : locations.entrySet()) {
                jedis.hset(Tables.LOCATIONS, String.valueOf(location.getKey()), gson.toJson(location.getValue()));
            }
        }
    }

    public void unloadLocations() {
        Map<Integer, LocationBean> locations;

        try (Jedis jedis = redisAccess.getResource()) {
            locations = jedis.hgetAll(Tables.LOCATIONS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            location ->
                                    Integer.parseInt(location.getKey()),
                            location ->
                                    gson.fromJson(location.getValue(), LocationBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.LOCATIONS + " SET world = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<Integer, LocationBean> location : locations.entrySet()) {
                    int id = location.getKey();
                    LocationBean bean = location.getValue();

                    statement.setString(1, bean.getWorld());
                    statement.setDouble(2, bean.getX());
                    statement.setDouble(3, bean.getY());
                    statement.setDouble(4, bean.getZ());
                    statement.setDouble(5, bean.getYaw());
                    statement.setDouble(6, bean.getPitch());
                    statement.setInt(7, id);

                    if (statement.executeUpdate() == 0) {
                        insertLocation(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertLocation(Connection connection, LocationBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.LOCATIONS + " (world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getWorld());
            statement.setDouble(2, bean.getX());
            statement.setDouble(3, bean.getY());
            statement.setDouble(4, bean.getZ());
            statement.setDouble(5, bean.getYaw());
            statement.setDouble(6, bean.getPitch());

            statement.executeUpdate();
        }
    }
}
