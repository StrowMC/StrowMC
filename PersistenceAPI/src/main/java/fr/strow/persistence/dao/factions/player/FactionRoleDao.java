/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 02/07/2020 21:12
 */

package fr.strow.persistence.dao.factions.player;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.profile.FactionRoleBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionRoleDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionRoleDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionRoleBean loadFactionRole(UUID uuid) {
        FactionRoleBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT role_id FROM faction_profiles WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int role = resultSet.getInt("role_id");

                        bean = new FactionRoleBean(uuid, role);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionRole(FactionRoleBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE faction_profiles SET role_id = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, bean.getRoleId());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
