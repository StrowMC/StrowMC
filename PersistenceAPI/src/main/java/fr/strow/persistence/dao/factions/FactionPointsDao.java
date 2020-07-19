package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionPointsBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionPointsDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionPointsDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionPointsBean loadFactionPoints(UUID uuid) {
        FactionPointsBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT points FROM factions WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int points = resultSet.getInt("points");

                        bean = new FactionPointsBean(uuid, points);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionPoints(FactionPointsBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE factions SET points = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, bean.getPoints());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
