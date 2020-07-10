/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:45
 */

package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.PlayerBean;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PlayersBridge extends AbstractBridge {

    @Inject
    public PlayersBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }

    public void loadPlayers() {
        Map<UUID, PlayerBean> players = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.PLAYERS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        String name = resultSet.getString("name");
                        int roleId = resultSet.getInt("role_id");
                        int coins = resultSet.getInt("coins");

                        PlayerBean player = new PlayerBean(uuid, name, roleId, coins);
                        players.put(uuid, player);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, PlayerBean> player : players.entrySet()) {
                jedis.hset(Tables.PLAYERS, player.getKey().toString(), gson.toJson(player.getValue()));
            }
        }
    }

    public void unloadPlayers() {
        Map<UUID, PlayerBean> players;

        try (Jedis jedis = redisAccess.getResource()) {
            players = jedis.hgetAll(Tables.PLAYERS)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            player ->
                                    UUID.fromString(player.getKey()),
                            player ->
                                    gson.fromJson(player.getValue(), PlayerBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.PLAYERS + " SET name = ?, role_id = ?, coins = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, PlayerBean> player : players.entrySet()) {
                    UUID uuid = player.getKey();
                    PlayerBean bean = player.getValue();

                    statement.setString(1, bean.getName());
                    statement.setInt(2, bean.getRoleId());
                    statement.setInt(3, bean.getCoins());
                    statement.setString(4, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertPlayer(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertPlayer(Connection connection, PlayerBean bean) throws SQLException {
        final String SQL = "INSERT INTO " + Tables.PLAYERS + " (uuid, name, role_id, coins) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setString(2, bean.getName());
            statement.setInt(3, bean.getRoleId());
            statement.setInt(4, bean.getCoins());

            statement.executeUpdate();
        }
    }
}
