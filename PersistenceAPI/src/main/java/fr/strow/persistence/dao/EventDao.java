package fr.strow.persistence.dao;

import com.google.inject.Inject;
import fr.strow.persistence.beans.EventParticipantBean;
import fr.strow.persistence.data.sql.SQLAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class EventDao {

    private final SQLAccess sqlAccess;

    @Inject
    public EventDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }

    public EventParticipantBean loadEventParticipation(UUID uuid) {
        EventParticipantBean bean = null;

        try (Connection connection = sqlAccess.getConnection()) {
            final String SQL = "SELECT * FROM event_participants WHERE uuid = ?";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {

            }
        } catch (SQLException exception) {
        }
    }
}
