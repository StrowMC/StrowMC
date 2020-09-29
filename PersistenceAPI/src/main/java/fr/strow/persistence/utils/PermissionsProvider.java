/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 06/06/2020 21:24
 */

package fr.strow.persistence.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PermissionsProvider {

    public static Map<String, Boolean> getPermissions(Object permissionBean) {
        Map<String, Boolean> permissions = new HashMap<>();

        for (Field field : permissionBean.getClass().getDeclaredFields()) {
            Permission permission = field.getAnnotation(Permission.class);

            if (permission != null && field.getType() == boolean.class) {
                // Field is annotated with @Permission
                field.setAccessible(true);

                try {
                    permissions.put(permission.value(), field.getBoolean(permissionBean));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return permissions;
    }
}
