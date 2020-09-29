/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 01/07/2020 19:56
 */

package fr.strow.persistence.beans.factions.profile;

import fr.strow.persistence.utils.Permission;
import fr.strow.persistence.utils.PermissionsProvider;

import java.util.Map;

public class FactionPermissionsBean {

    @Permission("faction.autoclaim")
    private final boolean factionAutoClaim;

    @Permission("faction.claim")
    private final boolean factionClaim;

    @Permission("faction.demote")
    private final boolean factionDemote;

    @Permission("faction.description")
    private final boolean factionDescription;

    @Permission("faction.disband")
    private final boolean factionDisband;

    @Permission("faction.invite")
    private final boolean factionInvite;

    @Permission("faction.kick")
    private final boolean factionKick;

    @Permission("faction.promote")
    private final boolean factionPromote;

    @Permission("faction.sethome")
    private final boolean factionSetHome;

    @Permission("faction.unclaim")
    private final boolean factionUnClaim;

    @Permission("faction.unclaimall")
    private final boolean factionUnClaimAll;

    public FactionPermissionsBean(boolean factionAutoClaim, boolean factionClaim, boolean factionDemote, boolean factionDescription, boolean factionDisband, boolean factionInvite, boolean factionKick, boolean factionPromote, boolean factionSetHome, boolean factionUnClaim, boolean factionUnClaimAll) {
        this.factionAutoClaim = factionAutoClaim;
        this.factionClaim = factionClaim;
        this.factionDemote = factionDemote;
        this.factionDescription = factionDescription;
        this.factionDisband = factionDisband;
        this.factionInvite = factionInvite;
        this.factionKick = factionKick;
        this.factionPromote = factionPromote;
        this.factionSetHome = factionSetHome;
        this.factionUnClaim = factionUnClaim;
        this.factionUnClaimAll = factionUnClaimAll;
    }

    public Map<String, Boolean> getPermissions() {
        return PermissionsProvider.getPermissions(this);
    }
}
