/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:38
 */

package fr.strow.persistence.beans.permissions;

import fr.strow.persistence.utils.Permission;
import fr.strow.persistence.utils.PermissionsProvider;

import java.util.List;
import java.util.Map;

public class ProxyPermissionsBean {

    @Permission("perm")
    private final boolean perm;

    public ProxyPermissionsBean(boolean perm) {
        this.perm = perm;
    }

    public Map<String, Boolean> getPermissions() {
        return PermissionsProvider.getPermissions(this);
    }
}
