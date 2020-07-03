/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:12
 */

package fr.strow.persistence.dao.sql;

import fr.strow.persistence.beans.FactionProfileBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionProfileSqlDao extends SqlDao {

    public FactionProfileSqlDao(SQLAccess access) {
        super(access);
    }

    public FactionProfileBean loadFactionProfile(UUID uuid) {
        FactionProfileBean bean = null;

        try (Connection connection = access.getConnection()) {
            final String SQL = "SELECT * FROM faction_profiles WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));
                    int roleId = resultSet.getInt("role_id");
                    int power = resultSet.getInt("power");
                    boolean claimer = resultSet.getBoolean("claimer");

                    bean = new FactionProfileBean(uuid, factionUuid, roleId, power, claimer);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionProfile(FactionProfileBean bean) {
        try (Connection connection = access.getConnection()) {
            final String SQL = "UPDATE faction_profiles SET faction_uuid = ?, role_id = ?, power = ?, claimer = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getFactionUuid().toString());
                statement.setInt(2, bean.getRoleId());
                statement.setInt(3, bean.getPower());
                statement.setBoolean(4, bean.isClaimer());
                statement.setString(5, bean.getUuid().toString());

                int rows = statement.executeUpdate();

                if (rows == 0) {
                    createFactionProfile(bean);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void createFactionProfile(FactionProfileBean bean) {
        try (Connection connection = access.getConnection()) {
            final String SQL = "INSERT INTO faction_profiles (uuid, faction_uuid, role_id, power, claimer) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getUuid().toString());
                statement.setString(2, bean.getFactionUuid().toString());
                statement.setInt(3, bean.getRoleId());
                statement.setInt(4, bean.getPower());
                statement.setBoolean(5, bean.isClaimer());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
