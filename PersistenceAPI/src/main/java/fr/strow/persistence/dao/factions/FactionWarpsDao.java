package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionWarpBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionWarpsDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionWarpsDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean hasFactionWarps(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM faction_warps WHERE faction_uuid = ?";

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

    public List<FactionWarpBean> loadFactionWarps(UUID factionUuid) {
        List<FactionWarpBean> beans = new ArrayList<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM faction_warps WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int locationId = resultSet.getInt("location_id");

                        FactionWarpBean bean = new FactionWarpBean(factionUuid, name, locationId);
                        beans.add(bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return beans;
    }

    public void insertFactionWarp(FactionWarpBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO faction_warps (faction_uuid, name, location_id) VALUE ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.toString());
                statement.setString(2, bean.getName());
                statement.setInt(3, bean.getLocationId());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFactionWarp(UUID factionUuid, String name) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM faction_warps WHERE faction_uuid = ? AND name = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());
                statement.setString(2, name);

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFactionWarps(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM faction_warps WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
