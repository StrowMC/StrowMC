package fr.strow.api.game.permissions;

import fr.strow.api.game.player.StrowPlayer;

public interface PermissionsManager {

    void loadPermissions(String name);

    void reloadPermissions(StrowPlayer player);

    void reloadPermissions(String name);

    void unloadPermissions(String name);
}
