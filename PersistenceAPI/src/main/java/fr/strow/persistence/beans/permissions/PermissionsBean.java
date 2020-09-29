/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 23:37
 */

package fr.strow.persistence.beans.permissions;

import java.util.HashMap;
import java.util.Map;

public class PermissionsBean {

    private final ProxyPermissionsBean proxyPermissionsBean;

    public PermissionsBean(ProxyPermissionsBean proxyPermissionsBean) {
        this.proxyPermissionsBean = proxyPermissionsBean;
    }

    public Map<String, Boolean> getPermissions() {
        Map<String, Boolean> permissions = new HashMap<>();

        permissions.putAll(proxyPermissionsBean.getPermissions());

        return permissions;
    }
}
