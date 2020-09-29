package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.beans.NicknameBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NicknameDao {

    private final SQLAccess sqlAccess;

    @Inject
    public NicknameDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public NicknameBean loadNickname(UUID uuid) {
        NicknameBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT nickname FROM players WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String nickname = resultSet.getString("nickname");

                        bean = new NicknameBean(uuid, nickname);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    public void saveNickname(NicknameBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "UPDATE players SET nickname = ? WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, bean.getNickname());
                statement.setString(2, bean.getUuid().toString());

                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
