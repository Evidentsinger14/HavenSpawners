package dev.Ev1dent.HavenSpawners.Commands;

import dev.Ev1dent.HavenSpawners.HSMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import dev.Ev1dent.HavenSpawners.Utilities.Utils;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandHavenSpawners implements CommandExecutor {

    Utils Utils = new Utils();

    private FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("havenspawners.havenspawners")){
            sender.sendMessage(Utils.mmDeserialize(Config().getString("messages.noPerms")));
        }

        if(args.length == 0){
            sender.sendMessage(Utils.mmDeserialize("            <gold><bold>HavenSpawners            "));
            sender.sendMessage(Utils.mmDeserialize("<strikethrough>                                       "));
            sender.sendMessage(Utils.mmDeserialize("<yellow>This server is running <gold><underline>HavenSpawners <green>v" + HSMain.plugin.getDescription().getVersion()));
            sender.sendMessage(Utils.mmDeserialize("<yellow>- <gold>Bukkit Version: " + Bukkit.getVersion()));
            return true;
        }

        switch (args[0].toLowerCase()){
            case "reload":
                HSMain.plugin.saveDefaultConfig();
                HSMain.plugin.reloadConfig();
                sender.sendMessage(Utils.mmDeserialize("<green>Config reloaded"));
                HSMain.plugin.addTabCompletion();
                break;

            case "version":
                sender.sendMessage("v" + HSMain.plugin.getDescription().getVersion());
                break;

            default:
                sender.sendMessage("/<command> [reload/version]");
                break;
        }
        return true;
    }
}
