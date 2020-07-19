package fr.strow.persistence.dao.factions.player;

import com.google.inject.Inject;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionProfileDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionProfileDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean hasProfile(UUID uuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM faction_profiles WHERE uuid = ?";

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

    public void createProfile(UUID uuid, UUID factionUuid, int roleId, int power, boolean claimer) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO faction_profiles (uuid, faction_uuid, role_id, power, claimer) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());
                statement.setString(2, factionUuid.toString());
                statement.setInt(3, roleId);
                statement.setInt(4, power);
                statement.setBoolean(5, claimer);

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteProfile(UUID uuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM faction_profiles WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
