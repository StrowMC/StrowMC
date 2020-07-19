package fr.strow.persistence.dao.factions;

import com.google.inject.Inject;
import fr.strow.persistence.beans.factions.FactionMemberBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionMembersDao {

    private final SQLAccess sqlAccess;

    @Inject
    public FactionMembersDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public List<FactionMemberBean> loadMembers(UUID factionUuid) {
        List<FactionMemberBean> beans = new ArrayList<>();

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT uuid FROM faction_profiles WHERE faction_uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, factionUuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        UUID uuid = UUID.fromString(resultSet.getString("uuid"));

                        FactionMemberBean bean = new FactionMemberBean(uuid, factionUuid);
                        beans.add(bean);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return beans;
    }
}
