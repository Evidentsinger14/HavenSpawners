package dev.Ev1dent.HavenSpawners.Commands;

import dev.Ev1dent.HavenSpawners.HSMain;
import dev.Ev1dent.HavenSpawners.Utilities.CustomItems;
import dev.Ev1dent.HavenSpawners.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CommandGiveToken implements CommandExecutor {

    Utils Utils = new Utils();
    private FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!sender.hasPermission("havenspawners.command.givetoken")){
            sender.sendMessage(Utils.mmDeserialize(Config().getString("messages.noPerms")));
            return true;
        }

        ItemStack token = CustomItems.spawnerToken();

        if (args.length == 0 || Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(Utils.mmDeserialize(Config().getString("messages.invalidPlayer")));
            return true;
        } else {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            giveToken(targetPlayer, token);
        }
        return true;
    }
    public void giveToken(Player targetPlayer, ItemStack token){
        // tries adding the item to the players inventory
        HashMap<Integer, ItemStack> hashMap = targetPlayer.getInventory().addItem(token);
        Utils.sendMessage(targetPlayer, Config().getString("messages.tokenReceived"));
        // If inventory is full, drop at player
        if (!hashMap.isEmpty()) {
            targetPlayer.getWorld().dropItem(targetPlayer.getLocation(), token);
        }
    }

}
