package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionNameBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionNameDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionNameDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionNameBean loadFactionName(UUID uuid) {
        FactionNameBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT name FROM factions WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");

                        bean = new FactionNameBean(uuid, name);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionName(FactionNameBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE factions SET name = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getName());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
