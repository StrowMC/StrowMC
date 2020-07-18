/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:47
 */

package fr.strow.persistence.bridges;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.beans.factions.FactionMemberBean;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.sql.SQLAccess;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class FactionProfilesBridge extends AbstractBridge {

    @Inject
    public FactionProfilesBridge(SQLAccess sqlAccess, RedisAccess redisAccess, Gson gson) {
        super(sqlAccess, redisAccess, gson);
    }

    public void loadFactionProfiles() {
        Map<UUID, FactionProfileBean> profiles = new HashMap<>();
        Map<UUID, List<FactionMemberBean>> members = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTION_PROFILES;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                        UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));
                        int roleId = resultSet.getInt("role_id");
                        int power = resultSet.getInt("power");
                        boolean claimer = resultSet.getBoolean("claimer");

                        if (!members.containsKey(factionUuid)) {
                            members.put(factionUuid, new ArrayList<>());
                        }

                        FactionMemberBean member = new FactionMemberBean(uuid, factionUuid);
                        members.get(factionUuid).add(member);

                        FactionProfileBean profile = new FactionProfileBean(uuid, factionUuid, roleId, power, claimer);
                        profiles.put(uuid, profile);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try (Jedis jedis = redisAccess.getResource()) {
            for (Map.Entry<UUID, FactionProfileBean> profile : profiles.entrySet()) {
                UUID uuid = profile.getKey();

                jedis.hset(Tables.FACTION_PROFILES, uuid.toString(), gson.toJson(profile.getValue()));
            }

            for (Map.Entry<UUID, List<FactionMemberBean>> member : members.entrySet()) {
                UUID factionUuid = member.getKey();
                jedis.hset(Tables.FACTION_MEMBERS, factionUuid.toString(), gson.toJson(member.getValue()));
            }
        }
    }

    public void unloadFactionProfiles() {
        Map<UUID, FactionProfileBean> profiles;

        try (Jedis jedis = redisAccess.getResource()) {
            profiles = jedis.hgetAll(Tables.FACTION_PROFILES)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            profile ->
                                    UUID.fromString(profile.getKey()),
                            profile ->
                                    gson.fromJson(profile.getValue(), FactionProfileBean.class)
                    ));
        }

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE " + Tables.FACTION_PROFILES + " SET faction_uuid = ?, role_id = ?, power = ?, claimer = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (Map.Entry<UUID, FactionProfileBean> profile : profiles.entrySet()) {
                    UUID uuid = profile.getKey();
                    FactionProfileBean bean = profile.getValue();

                    statement.setString(1, bean.getFactionUuid().toString());
                    statement.setInt(2, bean.getRoleId());
                    statement.setInt(3, bean.getPower());
                    statement.setBoolean(4, bean.isClaimer());
                    statement.setString(5, uuid.toString());

                    if (statement.executeUpdate() == 0) {
                        insertProfile(connection, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertProfile(Connection connection, FactionProfileBean bean) throws SQLException {
        final String SQL = "INSERT INTO faction_profiles (uuid, faction_uuid, role_id, power, claimer) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, bean.getUuid().toString());
            statement.setString(2, bean.getFactionUuid().toString());
            statement.setInt(3, bean.getRoleId());
            statement.setInt(4, bean.getPower());
            statement.setBoolean(5, bean.isClaimer());

            statement.executeUpdate();
        }
    }
}
