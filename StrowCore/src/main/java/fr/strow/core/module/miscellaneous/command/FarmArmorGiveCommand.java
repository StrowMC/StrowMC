package fr.strow.core.module.miscellaneous.command;

import com.google.inject.Inject;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Hokkaydo on 11-07-2020.
 */
public class FarmArmorGiveCommand extends EvolvedCommand {

    private static final Parameter<Player> playerParameter = new PlayerParameter();

    private static final ItemStack HELMET = new ItemStack(Material.LEATHER_HELMET);
    private static final ItemStack CHEST_PLATE = new ItemStack(Material.LEATHER_CHESTPLATE);
    private static final ItemStack LEGGINGS = new ItemStack(Material.LEATHER_LEGGINGS);
    private static final ItemStack BOOTS = new ItemStack(Material.LEATHER_BOOTS);
    public static final ItemStack[] FARM_ARMOR = new ItemStack[]{
            HELMET,
            CHEST_PLATE,
            LEGGINGS,
            BOOTS
    };

    static {
        ItemMeta helmetMeta = HELMET.getItemMeta();
        helmetMeta.setDisplayName(MiscellaneousConfiguration.FARMHELMET_NAME);
        helmetMeta.addEnchant(Enchantment.ARROW_FIRE, 50, true);
        helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        HELMET.setItemMeta(helmetMeta);

        ItemMeta chestplateMeta = CHEST_PLATE.getItemMeta();
        chestplateMeta.setDisplayName(MiscellaneousConfiguration.FARMCHESTPLATE_NAME);
        chestplateMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        chestplateMeta.addEnchant(Enchantment.ARROW_FIRE, 50, true);
        CHEST_PLATE.setItemMeta(chestplateMeta);

        ItemMeta leggingsMeta = LEGGINGS.getItemMeta();
        leggingsMeta.setDisplayName(MiscellaneousConfiguration.FARMLEGGINGS_NAME);
        leggingsMeta.addEnchant(Enchantment.ARROW_FIRE, 50, true);
        leggingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        LEGGINGS.setItemMeta(leggingsMeta);

        ItemMeta bootsMeta = BOOTS.getItemMeta();
        bootsMeta.setDisplayName(MiscellaneousConfiguration.FARMBOOTS_NAME);
        bootsMeta.addEnchant(Enchantment.ARROW_FIRE, 50, true);
        bootsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        BOOTS.setItemMeta(bootsMeta);
    }

    @Inject
    public FarmArmorGiveCommand() {
        super(
                CommandDescription
                        .builder()
                        .withPermission("*")
                        .withDescription("Permet de donner l'armure de farm à un joueur")
                        .withName("armuregive")
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

        for (Map.Entry<Integer, ItemStack> entry : player.getInventory().addItem(HELMET, CHEST_PLATE, LEGGINGS, BOOTS).entrySet()) {
            player.getWorld().dropItemNaturally(player.getLocation(), entry.getValue());
        }
        ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ARROW_HIT, 10, 10);
    }
}
