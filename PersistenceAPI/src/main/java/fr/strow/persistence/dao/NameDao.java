package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.beans.NameBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NameDao {

    private final SQLAccess sqlAccess;

    @Inject
    public NameDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public NameBean loadName(UUID uuid) {
        NameBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT name FROM players WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");

                        bean = new NameBean(uuid, name);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }
}
