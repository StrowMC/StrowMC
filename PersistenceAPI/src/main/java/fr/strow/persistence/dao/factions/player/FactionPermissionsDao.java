/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:59
 */

package fr.strow.persistence.dao.factions.player;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.profile.FactionPermissionsBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactionPermissionsDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionPermissionsDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionPermissionsBean loadFactionPermissions(int roleId) {
        FactionPermissionsBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM faction_permissions WHERE role_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, roleId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        boolean factionAutoClaim = resultSet.getBoolean("faction_autoclaim");
                        boolean factionClaim = resultSet.getBoolean("faction_claim");
                        boolean factionDemote = resultSet.getBoolean("faction_demote");
                        boolean factionDescription = resultSet.getBoolean("faction_description");
                        boolean factionDisband = resultSet.getBoolean("faction_disband");
                        boolean factionInvite = resultSet.getBoolean("faction_invite");
                        boolean factionKick = resultSet.getBoolean("faction_kick");
                        boolean factionPromote = resultSet.getBoolean("faction_promote");
                        boolean factionSetHome = resultSet.getBoolean("faction_sethome");
                        boolean factionUnClaim = resultSet.getBoolean("faction_unclaim");
                        boolean factionUnClaimAll = resultSet.getBoolean("faction_unclaimall");

                        bean = new FactionPermissionsBean(factionAutoClaim, factionClaim, factionDemote, factionDescription, factionDisband, factionInvite, factionKick, factionPromote, factionSetHome, factionUnClaim, factionUnClaimAll);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }
}
