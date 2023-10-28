package dev.Ev1dent.HavenSpawners.Events;

import dev.Ev1dent.HavenSpawners.HSMain;
import dev.Ev1dent.HavenSpawners.Utilities.Utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class BlockBreak implements Listener {
    Utils Utils = new Utils();

    public FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(canBreakAdmin(player)) return; // Allow mods to break while in GMC + Crouching
        if(event.isCancelled()) return; // should respect claims, regions and any other protected areas

        Block block = event.getBlock();
        if(block.getType().equals(Material.SPAWNER)){
            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            EntityType entityType = spawner.getSpawnedType();

            if(!canMine(player, entityType)){
                Utils.sendMessage(player, Config().getString("messages.cannotMine"));
            }

            for (ItemStack item : player.getInventory().getContents()) {
                if (!item.getType().equals(Material.CONDUIT)) {
                    continue;
                }

                ItemMeta meta = item.getItemMeta();


                    ItemStack DroppedSpawner = new ItemStack(Material.SPAWNER);
                    String entity = Config().getString("spawnerName"), ET = entity.replace("{MOB}", String.valueOf(entityType));
                    ItemMeta SpawnerMeta = DroppedSpawner.getItemMeta();
                    SpawnerMeta.setDisplayName(Utils.Color(ET));
                    SpawnerMeta.setNa
                    DroppedSpawner.setItemMeta(SpawnerMeta);

                    player.getInventory().remove(item);
                    e.setExpToDrop(0);
                    HashMap<Integer, ItemStack> hashMap = player.getInventory().addItem(DroppedSpawner);
                    player.sendMessage(Utils.Color(Utils.Config().getString("Messages.Spawner-Received")));
                    if (!hashMap.isEmpty()) {
                        player.getWorld().dropItem(player.getLocation(), DroppedSpawner);
                    }
                    return;
                }
            }
        }

    public boolean canBreakAdmin(Player player){
        return player.getGameMode().equals(GameMode.CREATIVE)
                && player.isSneaking();
    }
    public boolean canMine(Player player, EntityType entityType) {
        return player.hasPermission("havenspawners.spawners.mine")
                || Config().getList("disabled-spawners").contains(entityType.toString());
    }

}
