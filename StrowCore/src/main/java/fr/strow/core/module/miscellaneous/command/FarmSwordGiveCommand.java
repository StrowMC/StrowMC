package fr.strow.core.module.miscellaneous.command;

import fr.strow.core.module.miscellaneous.command.parameter.PlayerParameter;
import fr.strow.core.module.miscellaneous.configuration.MiscellaneousConfiguration;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Hokkaydo on 12-07-2020.
 */
public class FarmSwordGiveCommand extends EvolvedCommand {

    private static final Parameter<Player> playerParameter = new PlayerParameter();

    private static final ItemStack FARM_SWORD = new ItemStack(Material.DIAMOND_SWORD);

    static {
        ItemMeta farmSwordMeta = FARM_SWORD.getItemMeta();
        farmSwordMeta.addEnchant(Enchantment.DURABILITY, 4, true);
        farmSwordMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 4, true);
        farmSwordMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 5, true);
        farmSwordMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, true);
        farmSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);

        farmSwordMeta.setLore(MiscellaneousConfiguration.FARMSWORD_LORE);
        farmSwordMeta.setDisplayName(MiscellaneousConfiguration.FARMSWORD_NAME);

        FARM_SWORD.setItemMeta(farmSwordMeta);
    }

    public FarmSwordGiveCommand() {
        super(
                CommandDescription
                        .builder()
                        .withName("farmgive")
                        .withDescription("Permet de donner l'épée de farm à un joueur")
                        .withPermission("*")
                        .build()
        );
        define();
    }

    @Override
    protected void define() {
        addParam(playerParameter, false);
    }

    @Override
    protected void execute(CommandSender sender) {
        Optional<Player> opt = readOptionalArg();
        if (sender instanceof ConsoleCommandSender && !opt.isPresent()) {
            sender.sendMessage("§cVeuillez préciser un joueur");
            return;
        }
        Player player = opt.orElse((Player) sender);

        for (Map.Entry<Integer, ItemStack> entry : player.getInventory().addItem(FARM_SWORD).entrySet()) {
            player.getWorld().dropItemNaturally(player.getLocation(), entry.getValue());
        }
        ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ARROW_HIT, 10, 10);
    }
}
