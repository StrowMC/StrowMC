/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 15:24
 */

package fr.strow.persistence.bridges.sql.permissions;

import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.permissions.ProxyPermissionsBean;
import fr.strow.persistence.bridges.sql.AbstractSQLDao;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProxyPermissionsSQLDao extends AbstractSQLDao {

    @Inject
    public ProxyPermissionsSQLDao(SQLAccess sqlAccess) {
        super(sqlAccess);
    }

    public Map<Integer, ProxyPermissionsBean> loadPermissions() {
        Map<Integer, ProxyPermissionsBean> beans = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.PROXY_PERMISSIONS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int roleId = resultSet.getInt("role_id");
                        boolean proxyJoin = resultSet.getBoolean("proxy_join");

                        ProxyPermissionsBean bean = new ProxyPermissionsBean(roleId, proxyJoin);
                        beans.put(roleId, bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return beans;
    }
}
