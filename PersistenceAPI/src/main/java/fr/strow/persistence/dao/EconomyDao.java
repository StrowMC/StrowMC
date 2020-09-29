/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 15:00
 */

package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.beans.EconomyBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EconomyDao {

    private final SQLAccess sqlAccess;

    @Inject
    public EconomyDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public EconomyBean loadEconomy(UUID uuid) {
        EconomyBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT coins FROM players WHERE uuid = ?";

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
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE players SET coins = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, bean.getCoins());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Map<String, Integer> getRichestPlayers(int n) {
        Map<String, Integer> richestPlayers = new LinkedHashMap<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT pseudo, coins FROM players ORDER BY coins DESC LIMIT ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, n);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String pseudo = resultSet.getString("pseudo");
                        int coins = resultSet.getInt("coins");

                        richestPlayers.put(pseudo, coins);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return richestPlayers;
    }
}
