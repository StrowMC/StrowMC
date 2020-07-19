/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:11
 */

package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.beans.RoleBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RoleDao {

    private final SQLAccess sqlAccess;

    @Inject
    public RoleDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public RoleBean loadRole(UUID uuid) {
        RoleBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT role_id FROM players WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int roleId = resultSet.getInt("role_id");

                        bean = new RoleBean(uuid, roleId);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveRole(RoleBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE players SET role_id = ? WHERE uuid = ?";

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
