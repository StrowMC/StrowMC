/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:11
 */

package fr.strow.persistence.dao.sql;

import fr.strow.persistence.beans.EconomyBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EconomySqlDao extends SqlDao {

    public EconomySqlDao(SQLAccess access) {
        super(access);
    }

    public EconomyBean loadEconomy(UUID uuid) {
        EconomyBean bean = null;

        try (Connection connection = access.getConnection()) {
            final String SQL = "SELECT * FROM players WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int coins = resultSet.getInt("coins");

                        bean = new EconomyBean(uuid, coins);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveEconomy(EconomyBean bean) {
        try (Connection connection = access.getConnection()) {
            final String SQL = "UPDATE players SET coins = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getUuid().toString());
                statement.setInt(2, bean.getCoins());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
