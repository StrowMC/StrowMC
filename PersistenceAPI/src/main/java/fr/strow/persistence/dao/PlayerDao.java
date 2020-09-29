package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerDao {

    private final SQLAccess sqlAccess;

    @Inject
    public PlayerDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean playerExists(String name) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM players WHERE name = ?";

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

    public boolean playerNameExists(UUID uuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM players WHERE uuid = ?";

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

    public UUID getUUIDFromName(String name) {
        UUID uuid = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT uuid FROM players WHERE name = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, name);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        uuid = UUID.fromString(resultSet.getString("uuid"));
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return uuid;
    }

    public UUID getUUIDFromNickname(String nickname) {
        UUID uuid = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT uuid FROM players WHERE nickname = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, nickname);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        uuid = UUID.fromString(resultSet.getString("uuid"));
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return uuid;
    }

    public void createPlayer(String name, UUID uuid, String nickname, int roleId, int coins) {
       try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO players (name, uuid, nickname, role_id, coins) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, name);
                statement.setString(2, uuid.toString());
                statement.setString(3, nickname);
                statement.setInt(4, roleId);
                statement.setInt(5, coins);

                statement.executeUpdate();
            }
       } catch (SQLException exception) {
           exception.printStackTrace();
       }
    }
}
