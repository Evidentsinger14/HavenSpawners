package dev.Ev1dent.HavenSpawners.Events;

import dev.Ev1dent.HavenSpawners.HSMain;
import dev.Ev1dent.HavenSpawners.Utilities.CustomItems;
import dev.Ev1dent.HavenSpawners.Utilities.Utils;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlace implements Listener {

    Utils Utils = new Utils();

    private FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.isCancelled()) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(isToken(item)){
            event.setCancelled(true);
            Utils.sendMessage(player, Config().getString("messages.cannotPlace"));
        }

        if(isSpawner(item)){
            String[] rawName = item.getItemMeta().getDisplayName().split(" ", 2);
            String name = rawName[0].replaceAll("(?i)[§&][0-9A-FK-ORX]", "");
            CreatureSpawner spawner = (CreatureSpawner) event.getBlockPlaced().getState();
            setSpawner(spawner, name);
            Utils.sendMessage(player, Config().getString("messages.spawnerPlaced").replace("{0}", name));
        }


    }
    private boolean isToken(ItemStack item){
        return item == CustomItems.spawnerToken();
    }

    private boolean isSpawner(ItemStack item){
        return item.getType() == Material.SPAWNER;
    }
    private void setSpawner(CreatureSpawner spawner, String name){
        try{
            spawner.setSpawnedType(EntityType.valueOf(name.toUpperCase()));
        }
        catch (Exception err){
            Utils.LogWarn("Unknown Spawner placed. Defaulting to PIG");
            spawner.setSpawnedType(EntityType.PIG);
        }
        spawner.update();
    }
}