package fr.strow.persistence.beans;

import java.util.UUID;

public class EventParticipantBean {

    private final UUID uuid;
    private final int eventId;

    public EventParticipantBean(UUID uuid, int eventId) {
        this.uuid = uuid;
        this.eventId = eventId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getEventId() {
        return eventId;
    }
}
