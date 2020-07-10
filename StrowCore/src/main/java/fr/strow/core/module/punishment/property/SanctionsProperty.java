package fr.strow.core.module.punishment.property;

import fr.strow.api.game.moderation.Sanction;
import fr.strow.api.game.moderation.Sanctions;

import java.util.List;

public class SanctionsProperty implements Sanctions {

    private List<BanImplementationProperty> bans;
    private List<MuteImplementationProperty> mutes;
    private List<KickProperty> kicks;

    @Override
    public <T extends Sanction<T>> T get(Class<T> sanction) {
        return null;
    }
}
