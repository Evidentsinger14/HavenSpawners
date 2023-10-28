package dev.Ev1dent.HavenSpawners.Events;

import dev.Ev1dent.HavenSpawners.HSMain;
import dev.Ev1dent.HavenSpawners.Utilities.CustomItems;
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

public class BlockBreak implements Listener {
    Utils Utils = new Utils();

    private FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (canBreakAdmin(player)) return; // Allow mods to break while in GMC + Crouching
        if (event.isCancelled()) return; // should respect claims, regions and any other protected areas

        Block block = event.getBlock();
        if (block.getType().equals(Material.SPAWNER)) {
            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            EntityType entityType = spawner.getSpawnedType();

            if (!canMine(player, entityType)) {
                Utils.sendMessage(player, Config().getString("messages.cannotMine"));
            }
            if(hasToken(player)){
                removeToken(player);
                giveSpawner(entityType);
            }
        }
    }

    private boolean canBreakAdmin(Player player){
        return player.getGameMode().equals(GameMode.CREATIVE)
                && player.isSneaking();
    }

    private boolean canMine(Player player, EntityType entityType) {
        return player.hasPermission("havenspawners.spawners.mine")
                || Config().getList("disabled-spawners").contains(entityType.toString());
    }

    private void giveSpawner(EntityType entityType){
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        ItemMeta meta = spawner.getItemMeta();
        String parsedName = Config().getString("spawnerName").replace("{MOB}", String.valueOf(entityType));
        meta.displayName(Utils.mmDeserialize(parsedName));
        spawner.setItemMeta(meta);
    }

    private boolean hasToken(Player player){
        return player.getInventory().containsAtLeast(CustomItems.spawnerToken(), 1);
    }

    private void removeToken(Player player){
        Utils.sendMessage(player, "<red>Remove conduit here. (not done yet)");
    }

}
