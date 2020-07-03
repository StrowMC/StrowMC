/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 06/06/2020 21:24
 */

package fr.strow.persistence.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PermissionsProvider {

    public static ArrayList<String> getPermissions(Object permissionBean) {
        ArrayList<String> permissions = new ArrayList<>();

        for (Field field : permissionBean.getClass().getDeclaredFields()) {
            Permission permission = field.getAnnotation(Permission.class);

            if (permission != null) {
                // Field is annotated with @Permission
                field.setAccessible(true);

                try {
                    if (field.getBoolean(permissionBean)) {
                        permissions.add(permission.value());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return permissions;
    }
}
