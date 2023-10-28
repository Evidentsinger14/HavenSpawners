package dev.Ev1dent.HavenSpawners.Utilities;

import dev.Ev1dent.HavenSpawners.HSMain;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItems {
    static Utils Utils = new Utils();

    private static FileConfiguration Config(){
        return HSMain.plugin.getConfig();
    }

    public static ItemStack spawnerToken() {
        Material token = Material.matchMaterial(Config().getString("tokenMaterial"));
        ItemStack spawnerToken = new ItemStack(token, 1);
        ItemMeta meta = spawnerToken.getItemMeta();
        meta.displayName(Utils.mmDeserialize(Config().getString("tokenName")));
        spawnerToken.setAmount(1);
        spawnerToken.setItemMeta(meta);
        return spawnerToken;
    }

    public static ItemStack spawnerBlock(String mobType){
        String spawnerName = Config().getString("spawnerName").replace("{MOB}", mobType);
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        ItemMeta meta = spawner.getItemMeta();
        meta.displayName(Utils.mmDeserialize(spawnerName));
        spawner.setItemMeta(meta);
        return spawner;
    }
}
