package dev.Ev1dent.HavenSpawners.Utilities;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {

    public Component mmParse(String message){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(message);
    }

    // Logging
    public void LogInfo(String log){
        Bukkit.getLogger().info(log);
    }

    public void LogWarn(String log){
        Bukkit.getLogger().warning(log);
    }

    public void LogSevere(String log){
        Bukkit.getLogger().severe(log);
    }

    public void sendMessage(Player player, String message){
        Audience adventurePlayer = (Audience) player;
        adventurePlayer.sendMessage(mmParse(message));
    }
}



