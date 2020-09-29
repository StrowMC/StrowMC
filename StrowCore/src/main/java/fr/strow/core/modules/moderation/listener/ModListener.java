package fr.strow.core.modules.moderation.listener;

import com.google.inject.Inject;
import fr.strow.api.StrowPlugin;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.services.Messaging;
import fr.strow.core.modules.moderation.ModerationModule;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Hokkaydo on 09-07-2020.
 */
public class ModListener implements Listener {

    private final PlayerManager playerManager;
    private final Messaging messaging;
    private final List<UUID> inInvseePlayers = new ArrayList<>();

    @Inject
    public ModListener(PlayerManager playerManager, Messaging messaging) {
        this.playerManager = playerManager;
        this.messaging = messaging;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR) return;
        if (!ModerationModule.isInModMode(e.getPlayer().getUniqueId())) return;
        if (e.getPlayer().getItemInHand().getType().equals(Material.INK_SACK)
                && e.getPlayer().getItemInHand().getData().getData() == 14
                && e.getPlayer().getItemInHand().hasItemMeta()
                && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§4Quitter le mode staff")) {

            ModerationModule.removeMod(e.getPlayer().getUniqueId());
            e.getPlayer().getActivePotionEffects().forEach(p -> e.getPlayer().removePotionEffect(p.getType()));
            Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(e.getPlayer()));
            messaging.sendMessage(playerManager.getPlayer(e.getPlayer().getUniqueId()), "%s §cTu n'est plus en mode staff", ModerationModule.PREFIX);
            e.getPlayer().getInventory().clear();
            e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
        }

        if (e.getPlayer().getItemInHand().getType().equals(Material.RECORD_3)
                && e.getPlayer().getItemInHand().hasItemMeta()
                && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§5Téléporation à un autre joueur aléatoire")) {

            Player player = new ArrayList<>(Bukkit.getOnlinePlayers()).get(new Random().nextInt(Bukkit.getOnlinePlayers().size()));
            e.getPlayer().teleport(player.getLocation());
            messaging.sendMessage(playerManager.getPlayer(e.getPlayer().getUniqueId()), "%s §cTu as été téléporté à %s.", ModerationModule.PREFIX, player.getName());
        }

        if (e.getPlayer().getItemInHand().getType().equals(Material.INK_SACK)
                && e.getPlayer().getItemInHand().hasItemMeta()
                && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§4Quitter le mode staff")) {

            if (e.getPlayer().getItemInHand().getData().getData() == 10) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(e.getPlayer()));
                        ItemStack invisibility = new ItemStack(Material.INK_SACK, 1, (short) 8);
                        ItemMeta invisibilityMeta = invisibility.getItemMeta();
                        invisibilityMeta.setDisplayName("§cInvisibilité");
                        invisibilityMeta.setLore(Collections.singletonList("§6Clic droit pour activer le vanish"));
                        invisibility.setItemMeta(invisibilityMeta);

                        e.getPlayer().getInventory().setItem(3, invisibility);
                        messaging.sendMessage(playerManager.getPlayer(e.getPlayer().getUniqueId()), "%s §cLe mode vanish à été activé, tu es maintenant invisible par tous les joueurs.", ModerationModule.PREFIX);
                    }
                }.runTaskLater(JavaPlugin.getPlugin(StrowPlugin.class), 1);
            } else if (e.getPlayer().getItemInHand().getData().getData() == 8) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(e.getPlayer()));
                        ItemStack invisibility = new ItemStack(Material.INK_SACK, 1, (short) 10);
                        ItemMeta invisibilityMeta = invisibility.getItemMeta();
                        invisibilityMeta.setDisplayName("§cInvisibilité");
                        invisibilityMeta.setLore(Collections.singletonList("§6Clic droit pour désactiver le vanish"));
                        invisibility.setItemMeta(invisibilityMeta);

                        e.getPlayer().getInventory().setItem(3, invisibility);
                        messaging.sendMessage(playerManager.getPlayer(e.getPlayer().getUniqueId()), "%s §cLe mode vanish à été désactivé, tu es maintenant visible par tous les joueurs.", ModerationModule.PREFIX);
                    }
                }.runTaskLater(JavaPlugin.getPlugin(StrowPlugin.class), 1);
            }
        }
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR) return;
        if (!ModerationModule.isInModMode(e.getPlayer().getUniqueId())) return;
        if (!(e.getRightClicked() instanceof Player)) return;
        Player target = (Player) e.getRightClicked();
        if (e.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)
                && e.getPlayer().getItemInHand().hasItemMeta()
                && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3Freeze un joueur")) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (ModerationModule.isFreezed(target.getUniqueId())) {
                        target.removePotionEffect(PotionEffectType.JUMP);
                        target.removePotionEffect(PotionEffectType.SLOW);
                        target.removePotionEffect(PotionEffectType.BLINDNESS);
                        messaging.sendMessage(playerManager.getPlayer(target.getUniqueId()), "%s §cTu as été unfreeze, tu peux donc continuer à jouer", ModerationModule.PREFIX);
                        messaging.sendMessage(playerManager.getPlayer(e.getPlayer().getUniqueId()), "%s §cTu as unfreeze le joueur %s", ModerationModule.PREFIX, target.getName());
                        ModerationModule.unfreeze(target.getUniqueId());
                    } else {
                        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999 * 24 * 60 * 60 * 20, 250, false, false));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999 * 24 * 60 * 60 * 20, 250, false, false));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999 * 24 * 60 * 60 * 20, 250, false, false));
                        messaging.sendMessage(playerManager.getPlayer(e.getPlayer().getUniqueId()), "%s, §cTu as freeze le joueur %s", ModerationModule.PREFIX, target.getName());
                        target.sendMessage(" ");
                        target.sendMessage(" ");
                        target.sendMessage("§4§l§m-----------------------------------------");
                        target.sendMessage(" ");
                        target.sendMessage("§cTu as été §4FREEZE §crejoins notre discord sous le même pseudonyme qu'en jeu " +
                                "et vient dans le salon §4Attente ScreenShare§c. " +
                                "§4Si tu te déconnectes, tu seras automatiquement banni"
                        );
                        target.sendMessage("§cNotre discord: §4https://discord.gg/StwtVQJ");
                        target.sendMessage(" ");
                        target.sendMessage("§4§l§m-----------------------------------------");
                        target.sendMessage(" ");
                        target.sendMessage(" ");
                        ModerationModule.freeze(target.getUniqueId());
                    }
                }
            }.runTaskLater(JavaPlugin.getPlugin(StrowPlugin.class), 3);
        }
        if (e.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)
                && e.getPlayer().getItemInHand().hasItemMeta()
                && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()
                && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§7Regarder l'inventaire")) {
            e.getPlayer().openInventory(target.getInventory());
            if (!e.getPlayer().isOp()) {
                inInvseePlayers.add(e.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (ModerationModule.isInModMode(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (ModerationModule.isInModMode(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (ModerationModule.isInModMode(e.getEntity().getUniqueId())) {
            e.setCancelled(true);
            ((Player) e.getEntity()).setFoodLevel(10);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (ModerationModule.isInModMode(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (ModerationModule.isInModMode(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (ModerationModule.isInModMode(e.getWhoClicked().getUniqueId())) {
            if (!e.getClickedInventory().equals(e.getWhoClicked().getInventory()) ||
                    inInvseePlayers.contains(e.getWhoClicked().getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        inInvseePlayers.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (ModerationModule.isFreezed(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
