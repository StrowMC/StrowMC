package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.beans.EventParticipantBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EventDao {

    private final SQLAccess sqlAccess;

    @Inject
    public EventDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public boolean isParticipant(UUID uuid) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT 1 FROM event_participants WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public EventParticipantBean loadEventParticipation(UUID uuid) {
        EventParticipantBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM event_participants WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, uuid.toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    int eventId = resultSet.getInt("event_id");

                    bean = new EventParticipantBean(uuid, eventId);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return bean;
    }

    /*public void saveEventParticipation(EventParticipantBean bean) {
        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = ""
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }*/
}
