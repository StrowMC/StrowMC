package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.*;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
                        String prefix = resultSet.getString("prefix");
                        UUID leaderUuid = UUID.fromString(resultSet.getString("leader_uuid"));
                        String description = resultSet.getString("description");
                        int points = resultSet.getInt("points");

                        FactionBean faction = new FactionBean(uuid, name, prefix, leaderUuid, description, points);
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

        loadChests();
        loadClaims();
        loadHomes();
        loadWarps();
    }

    private void loadChests() {
        Map<UUID, FactionChestBean> chests = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTION_CHESTS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));
                        InputStream content = resultSet.getAsciiStream("content");

                        FactionChestBean chest = new FactionChestBean(factionUuid, content);
                        chests.put(factionUuid, chest);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, FactionChestBean> chest : chests.entrySet()) {
                jedis.hset(Tables.FACTION_CHESTS, chest.getKey().toString(), gson.toJson(chest.getValue()));
            }
        }
    }

    private void loadClaims() {
        Map<UUID, List<FactionClaimBean>> claims = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTION_CLAIMS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));
                        String world = resultSet.getString("world");
                        int x = resultSet.getInt("x");
                        int z = resultSet.getInt("z");

                        FactionClaimBean claim = new FactionClaimBean(factionUuid, world, x, z);

                        if (!claims.containsKey(factionUuid)) {
                            claims.put(factionUuid, new ArrayList<>());
                        }

                        claims.get(factionUuid).add(claim);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, List<FactionClaimBean>> claim : claims.entrySet()) {
                jedis.hset(Tables.FACTION_CLAIMS, claim.getKey().toString(), gson.toJson(claim.getValue()));
            }
        }
    }

    private void loadHomes() {
        Map<UUID, FactionHomeBean> homes = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTION_HOMES;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));
                        int locationId = resultSet.getInt("location_id");

                        FactionHomeBean home = new FactionHomeBean(factionUuid, locationId);
                        homes.put(factionUuid, home);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, FactionHomeBean> home : homes.entrySet()) {
                jedis.hset(Tables.FACTION_CHESTS, home.getKey().toString(), gson.toJson(home.getValue()));
            }
        }
    }

    private void loadWarps() {
        Map<UUID, List<FactionWarpBean>> warps = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTION_WARPS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));
                        String name = resultSet.getString("name");
                        int locationId = resultSet.getInt("location_id");

                        FactionWarpBean warp = new FactionWarpBean(factionUuid, name, locationId);

                        if (!warps.containsKey(factionUuid)) {
                            warps.put(factionUuid, new ArrayList<>());
                        }

                        warps.get(factionUuid).add(warp);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, List<FactionWarpBean>> warp : warps.entrySet()) {
                jedis.hset(Tables.FACTION_WARPS, warp.getKey().toString(), gson.toJson(warp.getValue()));
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
            final String SQL = "UPDATE " + Tables.FACTIONS + " SET name = ?, prefix = ?, leader_uuid = ?, description = ?, points = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, FactionBean> faction : factions.entrySet()) {
                    UUID uuid = faction.getKey();
                    FactionBean bean = faction.getValue();

                    statement.setString(1, bean.getName());
                    statement.setString(2, bean.getPrefix());
                    statement.setString(3, bean.getLeaderUuid().toString());
                    statement.setString(4, bean.getDescription());
                    statement.setInt(5, bean.getPoints());
                    statement.setString(6, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertFaction(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        unloadChests();
        unloadClaims();
        unloadHomes();
        unloadWarps();
    }

    private void insertFaction(Connection connection, FactionBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.FACTIONS + " (uuid, name, prefix, leader_uuid, description, points) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setString(2, bean.getName());
            statement.setString(3, bean.getPrefix());
            statement.setString(4, bean.getLeaderUuid().toString());
            statement.setString(5, bean.getDescription());
            statement.setInt(6, bean.getPoints());

            statement.executeUpdate();
        }
    }

    private void unloadChests() {
        Map<UUID, FactionChestBean> chests;

        try (Jedis jedis = redisAccess.getResource()) {
            chests = jedis.hgetAll(Tables.FACTION_CHESTS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            chest ->
                                    UUID.fromString(chest.getKey()),
                            chest ->
                                    gson.fromJson(chest.getValue(), FactionChestBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.FACTION_CHESTS + " SET content = ? WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, FactionChestBean> chest : chests.entrySet()) {
                    UUID uuid = chest.getKey();
                    FactionChestBean bean = chest.getValue();

                    statement.setString(1, bean.getFactionUuid().toString());
                    statement.setString(2, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertChest(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertChest(Connection connection, FactionChestBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.FACTION_CHESTS + " (faction_uuid, content) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getFactionUuid().toString());
            statement.setAsciiStream(2, bean.getContent());

            statement.executeUpdate();
        }
    }

    private void unloadClaims() {
        Map<UUID, List<FactionClaimBean>> claims;
        Type type = new TypeToken<List<FactionClaimBean>>() {}.getType();

        try (Jedis jedis = redisAccess.getResource()) {
            claims = jedis.hgetAll(Tables.FACTION_CLAIMS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            claim ->
                                    UUID.fromString(claim.getKey()),
                            claim ->
                                    gson.fromJson(claim.getValue(), type)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.FACTION_CLAIMS + " SET world = ?, x = ?, z = ? WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, List<FactionClaimBean>> claim : claims.entrySet()) {
                    UUID uuid = claim.getKey();

                    for (FactionClaimBean bean : claim.getValue()) {
                        statement.setString(1, bean.getWorld());
                        statement.setInt(2, bean.getX());
                        statement.setInt(3, bean.getZ());
                        statement.setString(4, uuid.toString());

                        if (statement.executeUpdate() == 0) {
                            insertClaim(connection, bean);
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertClaim(Connection connection, FactionClaimBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.FACTION_CLAIMS + " (faction_uuid, x, z) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getFactionUuid().toString());
            statement.setInt(2, bean.getX());
            statement.setInt(3, bean.getZ());

            statement.executeUpdate();
        }
    }

    private void unloadHomes() {
        Map<UUID, FactionHomeBean> homes;

        try (Jedis jedis = redisAccess.getResource()) {
            homes = jedis.hgetAll(Tables.FACTION_HOMES)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            chest ->
                                    UUID.fromString(chest.getKey()),
                            chest ->
                                    gson.fromJson(chest.getValue(), FactionHomeBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.FACTION_HOMES + " SET location_id = ? WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, FactionHomeBean> home : homes.entrySet()) {
                    UUID uuid = home.getKey();
                    FactionHomeBean bean = home.getValue();

                    statement.setString(1, bean.getFactionUuid().toString());
                    statement.setString(2, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertHome(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertHome(Connection connection, FactionHomeBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.FACTION_HOMES + " (faction_uuid, location_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getFactionUuid().toString());
            statement.setInt(2, bean.getLocationId());

            statement.executeUpdate();
        }
    }

    private void unloadWarps() {
        Map<UUID, List<FactionWarpBean>> warps;
        Type type = new TypeToken<List<FactionWarpBean>>() {}.getType();

        try (Jedis jedis = redisAccess.getResource()) {
            warps = jedis.hgetAll(Tables.FACTION_WARPS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            inventory ->
                                    UUID.fromString(inventory.getKey()),
                            inventory ->
                                    gson.fromJson(inventory.getValue(), type)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.FACTION_WARPS + " SET name = ?, location_id = ? WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, List<FactionWarpBean>> warp : warps.entrySet()) {
                    UUID uuid = warp.getKey();

                    for (FactionWarpBean bean : warp.getValue()) {
                        statement.setString(1, bean.getFactionUuid().toString());
                        statement.setString(2, bean.getName());
                        statement.setString(3, uuid.toString());

                        if (statement.executeUpdate() == 0) {
                            insertWarp(connection, bean);
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertWarp(Connection connection, FactionWarpBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.FACTION_WARPS + " (faction_uuid, name, location_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getFactionUuid().toString());
            statement.setString(2, bean.getName());
            statement.setInt(3, bean.getLocationId());

            statement.executeUpdate();
        }
    }
}
