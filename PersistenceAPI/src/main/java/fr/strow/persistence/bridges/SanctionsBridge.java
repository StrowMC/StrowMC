/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 03/07/2020 16:40
 */

package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.sanctions.BanBean;
import fr.strow.persistence.beans.sanctions.MuteBean;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SanctionsBridge extends AbstractBridge {

    @Inject
    public SanctionsBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }

    public void loadSanctions() {
       loadBans();
       loadMutes();
    }

    private void loadBans() {
        Map<UUID, BanBean> bans = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.BANNED_PLAYERS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        String reason = resultSet.getString("reason");
                        UUID sanctionerUuid = UUID.fromString(resultSet.getString("sanctioner_uuid"));
                        Timestamp startingTimestamp = resultSet.getTimestamp("starting_timestamp");
                        Timestamp endingTimestamp = resultSet.getTimestamp("ending_timestamp");

                        BanBean ban = new BanBean(uuid, reason, sanctionerUuid, startingTimestamp, endingTimestamp);
                        bans.put(uuid, ban);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, BanBean> ban : bans.entrySet()) {
                jedis.hset(Tables.BANNED_PLAYERS, ban.getKey().toString(), gson.toJson(ban.getValue()));
            }
        }
    }

    private void loadMutes() {
        Map<UUID, MuteBean> mutes = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.MUTED_PLAYERS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        String reason = resultSet.getString("reason");
                        Timestamp startingTimestamp = resultSet.getTimestamp("starting_timestamp");
                        Timestamp endingTimestamp = resultSet.getTimestamp("ending_timestamp");
                        UUID sanctionerUuid = UUID.fromString(resultSet.getString("sanctioner_uuid"));

                        MuteBean mute = new MuteBean(uuid, reason, sanctionerUuid, startingTimestamp, endingTimestamp);
                        mutes.put(uuid, mute);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, MuteBean> mute : mutes.entrySet()) {
                jedis.hset(Tables.MUTED_PLAYERS, mute.getKey().toString(), gson.toJson(mute.getValue()));
            }
        }
    }

    public void unloadSanctions() {
        unloadBans();
        unloadMutes();
    }

    private void unloadBans() {
        Map<UUID, BanBean> bans;

        try (Jedis jedis = redisAccess.getResource()) {
            bans = jedis.hgetAll(Tables.BANNED_PLAYERS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            ban ->
                                    UUID.fromString(ban.getKey()),
                            ban ->
                                    gson.fromJson(ban.getValue(), BanBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.BANNED_PLAYERS + " SET reason = ?, starting_timestamp = ?, ending_timestamp = ?, sanctioner_uuid = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, BanBean> ban : bans.entrySet()) {
                    UUID uuid = ban.getKey();
                    BanBean bean = ban.getValue();

                    statement.setString(1, bean.getReason());
                    statement.setString(2, bean.getSanctionerUuid().toString());
                    statement.setTimestamp(3, bean.getStartingTimestamp());
                    statement.setTimestamp(4, bean.getEndingTimestamp());
                    statement.setString(5, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertBan(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertBan(Connection connection, BanBean bean) throws SQLException {
        final String SQL = "INSERT INTO banned_players (uuid, reason, sanctioner_uuid, starting_timestamp, ending_timestamp) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setString(2, bean.getReason());
            statement.setString(3, bean.getSanctionerUuid().toString());
            statement.setTimestamp(4, bean.getStartingTimestamp());
            statement.setTimestamp(5, bean.getEndingTimestamp());
        }
    }

    private void unloadMutes() {
        Map<UUID, MuteBean> mutes;

        try (Jedis jedis = redisAccess.getResource()) {
            mutes = jedis.hgetAll(Tables.MUTED_PLAYERS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            mute ->
                                    UUID.fromString(mute.getKey()),
                            mute ->
                                    gson.fromJson(mute.getValue(), MuteBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.MUTED_PLAYERS + " SET reason = ?, starting_timestamp = ?, ending_timestamp = ?, sanctioner_uuid = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, MuteBean> mute : mutes.entrySet()) {
                    UUID uuid = mute.getKey();
                    MuteBean bean = mute.getValue();

                    statement.setString(1, bean.getReason());
                    statement.setString(2, bean.getSanctionerUuid().toString());
                    statement.setTimestamp(3, bean.getStartingTimestamp());
                    statement.setTimestamp(4, bean.getEndingTimestamp());
                    statement.setString(5, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertMute(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }

    private void insertMute(Connection connection, MuteBean bean) throws SQLException {
        final String SQL = "INSERT INTO muted_players (uuid, reason, sanctioner_uuid, starting_timestamp, ending_timestamp) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setString(2, bean.getReason());
            statement.setString(3, bean.getSanctionerUuid().toString());
            statement.setTimestamp(4, bean.getStartingTimestamp());
            statement.setTimestamp(5, bean.getEndingTimestamp());
        }
    }
}
