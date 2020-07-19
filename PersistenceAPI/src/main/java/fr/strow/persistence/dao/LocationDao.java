package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.beans.LocationBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDao {

    private final SQLAccess sqlAccess;

    @Inject
    public LocationDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public LocationBean loadLocation(int id) {
        LocationBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM locations WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String world = resultSet.getString("world");
                        double x = resultSet.getDouble("x");
                        double y = resultSet.getDouble("y");
                        double z = resultSet.getDouble("z");
                        float yaw = resultSet.getFloat("yaw");
                        float pitch = resultSet.getFloat("pitch");

                        bean = new LocationBean(id, world, x, y, z, yaw, pitch);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveLocation(LocationBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE locations SET world = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getWorld());
                statement.setDouble(2, bean.getX());
                statement.setDouble(3, bean.getY());
                statement.setDouble(4, bean.getZ());
                statement.setDouble(5, bean.getYaw());
                statement.setDouble(6, bean.getPitch());
                statement.setInt(7, bean.getId());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int insertLocation(LocationBean bean) {
        int id = -1;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO locations (world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getWorld());
                statement.setDouble(2, bean.getX());
                statement.setDouble(3, bean.getY());
                statement.setDouble(4, bean.getZ());
                statement.setDouble(5, bean.getYaw());
                statement.setDouble(6, bean.getPitch());

                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return id;
    }

    public void deleteLocation(int id) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM locations WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, id);

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
