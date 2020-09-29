package fr.strow.core.modules.faction.properties;

import com.google.inject.Inject;
import fr.strow.api.game.faction.FactionClaims;
import fr.strow.api.property.EmptyPropertyFactory;
import fr.strow.api.property.ImplementationProperty;
import fr.strow.persistence.beans.factions.FactionClaimBean;
import fr.strow.persistence.dao.factions.FactionClaimsDao;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.*;

public class FactionClaimsProperty implements FactionClaims, ImplementationProperty<FactionClaims> {

    private final FactionClaimsDao factionClaimsDao;

    private UUID factionUuid;
    private List<Chunk> claims;

    @Inject
    public FactionClaimsProperty(FactionClaimsDao factionClaimsDao) {
        this.factionClaimsDao = factionClaimsDao;
    }

    @Override
    public boolean load(UUID factionUuid) {
        this.factionUuid = factionUuid;

        if (factionClaimsDao.hasFactionClaims(factionUuid)) {
            List<FactionClaimBean> beans = factionClaimsDao.loadFactionClaims(factionUuid);

            for (FactionClaimBean bean : beans) {
                String world = bean.getWorld();
                int x = bean.getX();
                int z = bean.getZ();

                Chunk claim = Bukkit.getWorld(world).getChunkAt(x, z);
                claims.add(claim);
            }

            return true;
        } else {
            return false;
        }
    }

    /*@Override
    public void save(UUID factionUuid) {
        List<FactionClaimBean> beans = new ArrayList<>();

        for (Chunk claim : claims) {
            FactionClaimBean bean = createBean(claim);
            beans.add(bean);
        }

        factionClaimsDao.saveFactionClaims(beans);
    }*/

    @Override
    public void addClaim(Chunk claim) {
        if (!containsClaim(claim)) {
            FactionClaimBean bean = createBean(claim);
            factionClaimsDao.insertFactionClaim(bean);
        }
    }

    @Override
    public void removeClaim(Chunk claim) {
        if (containsClaim(claim)) {
            int id = claims.indexOf(claim);

            FactionClaimBean bean = createBean(id, claim);
            factionClaimsDao.deleteFactionClaim(bean);

            claims.remove(claim);
        }
    }

    @Override
    public boolean containsClaim(Chunk claim) {
        return claims.contains(claim);
    }

    @Override
    public void onRegister(UUID factionUuid, EmptyPropertyFactory factory) {
        claims = new ArrayList<>();
    }

    @Override
    public void onUnregister(UUID factionUuid) {
        factionClaimsDao.deleteFactionClaims(factionUuid);
    }

    private FactionClaimBean createBean(Chunk claim) {
        return createBean(-1, claim);
    }

    private FactionClaimBean createBean(int id, Chunk claim) {
        String world = claim.getWorld().getName();
        int x = claim.getX();
        int z = claim.getZ();

        return new FactionClaimBean(id, factionUuid, world, x, z);
    }
}
