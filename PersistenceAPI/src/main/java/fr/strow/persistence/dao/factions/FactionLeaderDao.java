package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionLeaderBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionLeaderDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionLeaderDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionLeaderBean loadFactionLeader(UUID uuid) {
        FactionLeaderBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT leader_uuid FROM factions WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        UUID leaderUuid = UUID.fromString(resultSet.getString("leader_uuid"));

                        bean = new FactionLeaderBean(uuid, leaderUuid);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionLeader(FactionLeaderBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE factions SET leader_uuid = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getLeaderUuid().toString());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
