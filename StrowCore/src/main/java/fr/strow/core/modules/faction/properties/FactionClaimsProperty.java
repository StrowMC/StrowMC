package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionClaims;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionClaimBean;
import fr.strow.persistence.dao.factions.FactionClaimsDao;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionClaimsProperty extends ImplementationProperty implements FactionClaims {

    private final FactionClaimsDao factionClaimsDao;

    private List<Chunk> claims;

    @Inject
    public FactionClaimsProperty(FactionClaimsDao factionClaimsDao) {
        this.factionClaimsDao = factionClaimsDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        if (factionClaimsDao.hasClaims(factionUuid)) {
            claims = new ArrayList<>();
            List<FactionClaimBean> beans = factionClaimsDao.loadFactionClaims(factionUuid);

            for (FactionClaimBean bean : beans) {
                String world = bean.getWorld();
                int x = bean.getX();
                int z = bean.getZ();

                Chunk chunk = Bukkit.getWorld(world).getChunkAt(x, z);
                claims.add(chunk);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(UUID factionUuid) {
        List<FactionClaimBean> beans = new ArrayList<>();

        for (Chunk chunk : claims) {
            String world = chunk.getWorld().getName();
            int x = chunk.getX();
            int z = chunk.getZ();

            FactionClaimBean bean = new FactionClaimBean(factionUuid, world, x, z);
            beans.add(bean);
        }

        factionClaimsDao.saveFactionClaims(beans);
    }

    @Override
    public List<Chunk> getClaims() {
        return claims;
    }

    @Override
    public boolean contains(Chunk chunk) {
        return claims.contains(chunk);
    }
}
