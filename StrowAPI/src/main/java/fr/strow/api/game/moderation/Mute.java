package fr.strow.api.game.moderation;

import java.sql.Timestamp;
import java.util.UUID;

public interface Mute {

    String getReason();

    UUID getSanctionerUuid();

    Timestamp getStartingTimestamp();

    Timestamp getEndingTimestamp();
}
