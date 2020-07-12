package fr.strow.proxy;

import fr.strow.proxy.command.SlotsCommand;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by Hokkaydo on 12-07-2020.
 */
public class StrowProxy extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new SlotsCommand());
    }
}
