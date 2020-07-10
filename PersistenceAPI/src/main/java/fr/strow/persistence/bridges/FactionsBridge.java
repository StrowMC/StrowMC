package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.FactionBean;
import fr.strow.persistence.beans.factions.FactionInventoryBean;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FactionsBridge extends AbstractBridge {

    @Inject
    public FactionsBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }

    public void loadFactions() {
        Map<UUID, FactionBean> factions = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTIONS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        UUID leaderUuid = UUID.fromString(resultSet.getString("leader_uuid"));
                        int points = resultSet.getInt("points");

                        FactionBean faction = new FactionBean(uuid, name, description, leaderUuid, points);
                        factions.put(uuid, faction);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, FactionBean> faction : factions.entrySet()) {
                jedis.hset(Tables.FACTIONS, faction.getKey().toString(), gson.toJson(faction.getValue()));
            }
        }

        loadInventories();
    }

    private void loadInventories() {
        Map<UUID, FactionInventoryBean> inventories = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTION_INVENTORIES;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));
                        InputStream content = resultSet.getAsciiStream("content");

                        FactionInventoryBean inventory = new FactionInventoryBean(factionUuid, content);
                        inventories.put(factionUuid, inventory);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, FactionInventoryBean> inventory : inventories.entrySet()) {
                jedis.hset(Tables.FACTION_INVENTORIES, inventory.getKey().toString(), gson.toJson(inventory.getValue()));
            }
        }
    }

    public void unloadFactions() {
        Map<UUID, FactionBean> factions;

        try (Jedis jedis = redisAccess.getResource()) {
            factions = jedis.hgetAll(Tables.FACTIONS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            faction ->
                                    UUID.fromString(faction.getKey()),
                            faction ->
                                    gson.fromJson(faction.getValue(), FactionBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.FACTIONS + " SET name = ?, description = ?, leader_uuid = ?, points = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, FactionBean> faction : factions.entrySet()) {
                    UUID uuid = faction.getKey();
                    FactionBean bean = faction.getValue();

                    statement.setString(1, bean.getName());
                    statement.setString(2, bean.getDescription());
                    statement.setString(3, bean.getLeaderUuid().toString());
                    statement.setInt(4, bean.getPoints());
                    statement.setString(5, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertFaction(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        unloadInventories();
    }

    private void insertFaction(Connection connection, FactionBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.FACTIONS + " (uuid, name, description, leader_uuid, points) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setString(2, bean.getName());
            statement.setString(3, bean.getDescription());
            statement.setString(4, bean.getLeaderUuid().toString());
            statement.setInt(5, bean.getPoints());

            statement.executeUpdate();
        }
    }

    private void unloadInventories() {
        Map<UUID, FactionInventoryBean> inventories;

        try (Jedis jedis = redisAccess.getResource()) {
            inventories = jedis.hgetAll(Tables.FACTION_INVENTORIES)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            inventory ->
                                    UUID.fromString(inventory.getKey()),
                            inventory ->
                                    gson.fromJson(inventory.getValue(), FactionInventoryBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.FACTION_INVENTORIES + " SET content = ? WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, FactionInventoryBean> inventory : inventories.entrySet()) {
                    UUID uuid = inventory.getKey();
                    FactionInventoryBean bean = inventory.getValue();

                    statement.setString(1, bean.getUuid().toString());
                    statement.setString(2, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertInventory(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertInventory(Connection connection, FactionInventoryBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.FACTION_INVENTORIES + " (faction_uuid, content) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setAsciiStream(2, bean.getContent());

            statement.executeUpdate();
        }
    }
}
