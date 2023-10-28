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

public class CommandGiveSpawner implements CommandExecutor {

    Utils Utils = new Utils();
    private FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!sender.hasPermission("havenspawners.command.giveconduit")){
            sender.sendMessage(Utils.mmDeserialize(Config().getString("messages.noPerms")));
            return true;
        }
        if(args.length < 2){
            sender.sendMessage(Utils.mmDeserialize(Config().getString("messages.usageFormat")));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        String mobType = args[1].toUpperCase();
        if(player == null){
            sender.sendMessage(Utils.mmDeserialize(Config().getString("messages.invalidPlayer")));
            return true;
            }

        if(!Config().getList("enabled-spawners").contains(mobType)){
            Utils.sendMessage(player, Config().getString("messages.invalidMob"));
            return true;
        }
        ItemStack spawner = CustomItems.spawnerBlock(mobType);
        HashMap<Integer, ItemStack> hashMap = player.getInventory().addItem(spawner);
            Utils.sendMessage(player, Config().getString("messages.spawnerReceived"));
            // If inventory is full, drop at player
            if (!hashMap.isEmpty()) {
                player.getWorld().dropItem(player.getLocation(), spawner);
            }
            return true;
        }

    }