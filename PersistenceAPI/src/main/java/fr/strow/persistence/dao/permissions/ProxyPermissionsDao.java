/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 15:24
 */

package fr.strow.persistence.dao.permissions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.permissions.ProxyPermissionsBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProxyPermissionsDao {

    private final SQLAccess sqlAccess;

    @Inject
    public ProxyPermissionsDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public ProxyPermissionsBean loadPermissions(int roleId) {
        ProxyPermissionsBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM proxy_permissions WHERE role_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, roleId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        boolean proxyJoin = resultSet.getBoolean("proxy_join");

                        bean = new ProxyPermissionsBean(proxyJoin);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }
}
