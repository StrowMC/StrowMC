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
import fr.strow.persistence.beans.moderation.BanBean;
import fr.strow.persistence.beans.moderation.MuteBean;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SanctionsBridge  {

    /*@Inject
    public SanctionsBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }

    public void loadSanctions() {
       loadBans();
       loadMutes();
    }

    private void loadBans() {
        Map<UUID, List<BanBean>> bans = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.BANS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        String reason = resultSet.getString("reason");
                        UUID sanctionerUuid = UUID.fromString(resultSet.getString("sanctioner_uuid"));
                        Timestamp startingTimestamp = resultSet.getTimestamp("starting_timestamp");
                        Timestamp endingTimestamp = resultSet.getTimestamp("ending_timestamp");

                        BanBean ban = new BanBean(uuid, reason, sanctionerUuid, startingTimestamp, endingTimestamp);

                        if (!bans.containsKey(uuid)) {
                            bans.put(uuid, new ArrayList<>());
                        }

                        bans.get(uuid).add(ban);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, List<BanBean>> ban : bans.entrySet()) {
                jedis.hset(Tables.BANS, ban.getKey().toString(), gson.toJson(ban.getValue()));
            }
        }
    }

    private void loadMutes() {
        Map<UUID, List<MuteBean>> mutes = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.MUTES;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        String reason = resultSet.getString("reason");
                        Timestamp startingTimestamp = resultSet.getTimestamp("starting_timestamp");
                        Timestamp endingTimestamp = resultSet.getTimestamp("ending_timestamp");
                        UUID sanctionerUuid = UUID.fromString(resultSet.getString("sanctioner_uuid"));

                        MuteBean mute = new MuteBean(uuid, reason, sanctionerUuid, startingTimestamp, endingTimestamp);

                        if (!mutes.containsKey(uuid)) {
                            mutes.put(uuid, new ArrayList<>());
                        }

                        mutes.get(uuid).add(mute);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, List<MuteBean>> mute : mutes.entrySet()) {
                jedis.hset(Tables.MUTES, mute.getKey().toString(), gson.toJson(mute.getValue()));
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
            bans = jedis.hgetAll(Tables.BANS)
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
            final String SQL = "UPDATE " + Tables.BANS + " SET reason = ?, starting_timestamp = ?, ending_timestamp = ?, sanctioner_uuid = ? WHERE uuid = ?";

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
        final String SQL = "INSERT INTO " + Tables.BANS + " (uuid, reason, sanctioner_uuid, starting_timestamp, ending_timestamp) VALUES (?, ?, ?, ?, ?)";

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
            mutes = jedis.hgetAll(Tables.MUTES)
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
            final String SQL = "UPDATE " + Tables.MUTES + " SET reason = ?, starting_timestamp = ?, ending_timestamp = ?, sanctioner_uuid = ? WHERE uuid = ?";

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
        final String SQL = "INSERT INTO " + Tables.MUTES +" (uuid, reason, sanctioner_uuid, starting_timestamp, ending_timestamp) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setString(2, bean.getReason());
            statement.setString(3, bean.getSanctionerUuid().toString());
            statement.setTimestamp(4, bean.getStartingTimestamp());
            statement.setTimestamp(5, bean.getEndingTimestamp());
        }
    }*/
}
