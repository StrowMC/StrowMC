package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionPrefixBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FactionPrefixDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionPrefixDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public FactionPrefixBean loadFactionPrefix(UUID uuid) {
        FactionPrefixBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT prefix FROM factions WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String prefix = resultSet.getString("prefix");

                        bean = new FactionPrefixBean(uuid, prefix);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveFactionPrefix(FactionPrefixBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE factions SET prefix = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getPrefix());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
