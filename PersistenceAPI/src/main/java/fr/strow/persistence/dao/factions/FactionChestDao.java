package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionChestBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionChestDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionChestDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean hasFactionChest(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM faction_chests WHERE faction_uuid = ?";

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

    public FactionChestBean loadFactionChest(UUID factionUuid) {
        FactionChestBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM faction_chests WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        InputStream content = resultSet.getAsciiStream("content");

                        bean = new FactionChestBean(factionUuid, content);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionChest(FactionChestBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE faction_chests SET content = ? WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setAsciiStream(1, bean.getContent());
                statement.setString(2, bean.getFactionUuid().toString());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void insertFactionChest(FactionChestBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO faction_chests (faction_uuid, content) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getFactionUuid().toString());
                statement.setAsciiStream(2, bean.getContent());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFactionChest(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM faction_chests WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
