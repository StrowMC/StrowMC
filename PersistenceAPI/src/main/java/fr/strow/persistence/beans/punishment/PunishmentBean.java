package fr.strow.persistence.beans.punishment;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class PunishmentBean {

    private final Timestamp startTimestamp;
    private final Timestamp endTimestamp;
    private final String reason;
    private final UUID moderator;
    private final Type type;

    public PunishmentBean(Timestamp startTimestamp, Timestamp endTimestamp, String reason, Player moderator, Type type){
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.reason = reason;
        this.moderator = moderator.getUniqueId();
        this.type = type;
    }

    public Timestamp getStartTimestamp(){
        return  startTimestamp;
    }

    public Timestamp getEndTimestamp(){
        return  endTimestamp;
    }

    public String getReason(){
        return reason;
    }

    public Player getModerator(){
        return Bukkit.getPlayer(moderator);
    }

    public Type getType(){
        return type;
    }

    enum Type{
        BAN, MUTE, TEMPBAN, TEMPMUTE
    }

}
