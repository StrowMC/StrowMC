package fr.strow.core.module.punishment.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

/**
 * Created by Hokkaydo on 07-07-2020.
 */
public class Punishment {

    private static int PUNISHMENT_ID = 0;

    private final int id;
    private final Type type;
    private final Timestamp start;
    private final Timestamp end;
    private final boolean definitive;
    private final UUID moderatorId;
    private final String moderatorName;
    private final String reason;

    public Punishment(Type type, Timestamp start, Timestamp end, boolean definitive, UUID moderatorId, String moderatorName, String reason) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.definitive = definitive;
        this.moderatorId = moderatorId;
        this.moderatorName = moderatorName;
        this.reason = reason;
        this.id = PUNISHMENT_ID++;
    }

    public Type getType() {
        return type;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public boolean isDefinitive() {
        return definitive;
    }

    public UUID getModeratorId() {
        return moderatorId;
    }

    public String getReason() {
        return reason;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return Instant.now().isAfter(end.toInstant());
    }

    public String getModeratorName() {
        return moderatorName;
    }

    public enum Type {
        BAN, MUTE
    }
}
