/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 14:59
 */

package fr.strow.persistence.dao.factions.player;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.profile.FactionClaimerBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionClaimerDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionClaimerDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionClaimerBean loadFactionClaimer(UUID uuid) {
        FactionClaimerBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT claimer FROM faction_profiles WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        boolean claimer = resultSet.getBoolean("claimer");

                        bean = new FactionClaimerBean(uuid, claimer);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionClaimer(FactionClaimerBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE faction_profiles SET claimer = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setBoolean(1, bean.isClaimer());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
