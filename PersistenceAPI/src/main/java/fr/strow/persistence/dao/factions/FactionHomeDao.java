package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionHomeBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionHomeDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionHomeDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean hasFactionHome(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM faction_homes WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public FactionHomeBean loadFactionHome(UUID factionUuid) {
        FactionHomeBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM faction_homes WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int locationId = resultSet.getInt("location_id");

                        bean = new FactionHomeBean(factionUuid, locationId);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void insertFactionHome(FactionHomeBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO faction_homes (faction_uuid, location_id) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getFactionUuid().toString());
                statement.setInt(2, bean.getLocationId());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFactionHome(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM faction_homes WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
