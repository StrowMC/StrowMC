package fr.strow.persistence.beans;

import java.util.UUID;

public class NicknameBean {

    private final UUID uuid;
    private final String nickname;

    public NicknameBean(UUID uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }
}
