package fr.strow.proxy.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Hokkaydo on 12-07-2020.
 */
public class SlotsCommand extends Command {

    public SlotsCommand() {
        super("slots", "*");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new ComponentBuilder("§cArgument manquant : servername -> lobby|faction").create());
            return;
        }
        if (args.length == 1) {
            sender.sendMessage(new ComponentBuilder("§cArgument manquant : slot amount").create());
            return;
        }
        String serverName = args[0];
        if (!serverName.equalsIgnoreCase("lobby") && !serverName.equalsIgnoreCase("faction")) {
            sender.sendMessage(new ComponentBuilder("§cArgument manquant : servername -> lobby|faction").create());
            return;
        }
        if (!isInteger(args[1])) {
            sender.sendMessage(new ComponentBuilder("§cArgument manquant : slot amount").create());
            return;
        }
        int slotAmount = Integer.parseInt(args[1]);


    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}
