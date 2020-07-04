package fr.strow.core.module.mine.command;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import fr.strow.core.module.mine.MineModule;
import fr.strow.core.module.mine.command.parameter.MineNameParameter;
import fr.strow.core.module.mine.util.Mine;
import fr.strow.core.utils.Cuboid;
import me.choukas.commands.EvolvedCommand;
import me.choukas.commands.api.CommandDescription;
import me.choukas.commands.api.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Hokkaydo on 17-06-2020.
 */
public class MineCreateCommand extends EvolvedCommand {

    private static final Parameter<String> NAME = new MineNameParameter();

    private final WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

    public MineCreateCommand() {
        super(CommandDescription.builder()
                .withName("create")
                .withPermission("strow.mine")
                .withDescription("Permet de créer une mine")
                .build());
        define();
    }

    @Override
    protected void define() {
        addParam(NAME, true);
    }

    @Override
    protected void execute(CommandSender sender) {
        Player player = (Player)sender;
        String name = readArg();
        try {
            Region selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getLocation().getWorld()));
            if(selection == null) {
                sender.sendMessage("§cVeuillez définir une zone");
                return;
            }
            MineModule.registerMine(new Mine(new Cuboid(asLocation(player.getLocation().getWorld(), selection.getMinimumPoint()),
                                asLocation(player.getLocation().getWorld(), selection.getMaximumPoint())), name));
        } catch (IncompleteRegionException e) {
            sender.sendMessage("§cRégion incomplète");
        }
    }

    private Location asLocation(org.bukkit.World world, BlockVector3 blockVector3){
        return new Location(world, blockVector3.getX(), blockVector3.getY(), blockVector3.getZ());
    }
}
