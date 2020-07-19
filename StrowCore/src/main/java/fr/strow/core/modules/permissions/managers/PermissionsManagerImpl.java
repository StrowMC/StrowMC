package fr.strow.core.modules.permissions.managers;

import com.google.inject.Inject;
import fr.strow.api.game.faction.player.FactionGroup;
import fr.strow.api.game.faction.player.FactionProfile;
import fr.strow.api.game.permissions.Group;
import fr.strow.api.game.permissions.PermissionsManager;
import fr.strow.api.game.player.Name;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.game.player.StrowPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class PermissionsManagerImpl implements PermissionsManager {

    private static final Map<String, PermissionAttachment> attachments = new HashMap<>();

    private final JavaPlugin plugin;
    private final PlayerManager playerManager;

    @Inject
    public PermissionsManagerImpl(JavaPlugin plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    @Override
    public void loadPermissions(String name) {
        Player player = Bukkit.getPlayer(name);
        PermissionAttachment attachment = player.addAttachment(plugin);

        attachments.put(name, attachment);

        reloadPermissions(name);
    }

    @Override
    public void reloadPermissions(StrowPlayer player) {
        String name = player.getProperty(Name.class).getName();

        reloadPermissions(name);
    }

    @Override
    public void reloadPermissions(String name) {
        PermissionAttachment attachment = attachments.get(name);
        Player player = Bukkit.getPlayer(name);

        StrowPlayer strowPlayer = playerManager.getPlayer(player);
        Map<String, Boolean> permissions = strowPlayer.getProperty(Group.class).getPermissions();

        strowPlayer.getOptionalProperty(FactionProfile.class).ifPresent(
                factionProfile -> permissions.putAll(factionProfile.getProperty(FactionGroup.class).getPermissions())
        );

        for (Map.Entry<String, Boolean> permission : permissions.entrySet()) {
            attachment.setPermission(permission.getKey(), permission.getValue());
        }
    }

    @Override
    public void unloadPermissions(String name) {
        if (attachments.containsKey(name)) {
            Player player = Bukkit.getPlayer(name);

            PermissionAttachment attachment = attachments.get(name);
            player.removeAttachment(attachment);

            attachments.remove(name);
        }
    }
}
