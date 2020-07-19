package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean factionExists(String name) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM factions WHERE name = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, name);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public boolean factionExists(UUID uuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM factions WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public void createFaction(UUID uuid, String name, String prefix, UUID leaderUuid, String description, int points) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO factions (uuid, name, prefix, leader_uuid, description, points) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());
                statement.setString(2, name);
                statement.setString(3, prefix);
                statement.setString(4, leaderUuid.toString());
                statement.setString(5, description);
                statement.setInt(6, points);

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFaction(UUID uuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM factions WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
