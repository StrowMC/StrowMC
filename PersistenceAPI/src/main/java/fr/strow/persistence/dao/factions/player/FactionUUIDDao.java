/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 15:00
 */

package fr.strow.persistence.dao.factions.player;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.profile.FactionUUIDBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionUUIDDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionUUIDDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionUUIDBean loadFactionUuid(UUID uuid) {
        FactionUUIDBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT faction_uuid FROM faction_profiles WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        UUID factionUuid = UUID.fromString(resultSet.getString("faction_uuid"));

                        bean = new FactionUUIDBean(uuid, factionUuid);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionUuid(FactionUUIDBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE faction_profiles SET faction_uuid = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getFactionUuid().toString());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
