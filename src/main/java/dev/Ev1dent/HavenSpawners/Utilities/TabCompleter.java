package dev.Ev1dent.HavenSpawners.Utilities;

import dev.Ev1dent.HavenSpawners.HSMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private static final String[] hsArguments = { "reload", "version" };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();

        switch (command.getName().toLowerCase()) {
            case "givespawner": {
                switch (args.length) {
                    case 1 : {
                        return null;
                    }
                    case 2 : {
                        StringUtil.copyPartialMatches(args[1], HSMain.plugin.getConfig().getStringList("spawners-for-givespawner"), completions);
                        Collections.sort(completions);
                        return completions;
                    }
                }
            }

            case "havenspawners" :{
                StringUtil.copyPartialMatches(args[0], Arrays.asList(hsArguments), completions);
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}
