package dev.Ev1dent.HavenSpawners.Events;

import dev.Ev1dent.HavenSpawners.HSMain;
import dev.Ev1dent.HavenSpawners.Utilities.CustomItems;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import dev.Ev1dent.HavenSpawners.Utilities.Utils;

public class AnvilRename implements Listener {

    Utils Utils = new Utils();

    private FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }

    @EventHandler
    public void onRename(InventoryClickEvent event){
        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getInventory().getType() == InventoryType.ANVIL){
            if(isSpawner(event) || isToken(event)){
                Player player = (Player) event.getWhoClicked();
                Utils.sendMessage(player, Config().getString("messages.cannotRename"));
                event.setCancelled(true);
            }
        }
    }
    private boolean isToken(InventoryClickEvent event){
        return event.getCurrentItem() == CustomItems.spawnerToken();
    }
    private boolean isSpawner(InventoryClickEvent event){
        return event.getCurrentItem().getType() == Material.SPAWNER;
    }

}
