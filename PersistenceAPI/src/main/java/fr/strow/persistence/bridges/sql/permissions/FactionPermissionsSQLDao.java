package fr.strow.persistence.bridges.sql.permissions;

import com.google.inject.Inject;
import fr.strow.persistence.Tables;
import fr.strow.persistence.beans.factions.profile.FactionPermissionsBean;
import fr.strow.persistence.bridges.sql.AbstractSQLDao;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FactionPermissionsSQLDao extends AbstractSQLDao {

    @Inject
    public FactionPermissionsSQLDao(SQLAccess sqlAccess) {
        super(sqlAccess);
    }

    public Map<Integer, FactionPermissionsBean> loadPermissions() {
        Map<Integer, FactionPermissionsBean> beans = new HashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM " + Tables.FACTION_PERMISSIONS;

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int roleId = resultSet.getInt("role_id");
                        boolean factionDisband = resultSet.getBoolean("faction_disband");

                        FactionPermissionsBean bean = new FactionPermissionsBean(factionDisband);
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
