package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionClaimBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionClaimsDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionClaimsDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean hasFactionClaims(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM faction_claims WHERE faction_uuid = ?";

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

    public List<FactionClaimBean> loadFactionClaims(UUID factionUuid) {
        List<FactionClaimBean> beans = new ArrayList<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM faction_claims WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String world = resultSet.getString("world");
                        int x = resultSet.getInt("x");
                        int z = resultSet.getInt("z");

                        FactionClaimBean bean = new FactionClaimBean(id, factionUuid, world, x, z);
                        beans.add(bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return beans;
    }

    public void saveFactionClaims(List<FactionClaimBean> beans) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE faction_claims SET world = ?, x = ?, z = ? WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (FactionClaimBean bean : beans) {
                    statement.setString(1, bean.getWorld());
                    statement.setInt(2, bean.getX());
                    statement.setInt(3, bean.getZ());
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void insertFactionClaim(FactionClaimBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "INSERT INTO faction_claims (faction_uuid) VALUE ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFactionClaim(FactionClaimBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM faction_claims WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFactionClaims(UUID factionUuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "DELETE FROM faction_claims WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean isFree(String world, int x, int z) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM faction_claims WHERE world = ? AND x = ? AND z = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, world);
                statement.setInt(2, x);
                statement.setInt(3, z);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
