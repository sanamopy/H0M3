package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetLoreSubCommand extends SubCommand {

    public SetLoreSubCommand(HomeManager homeManager) {
        final String usage = "/home setlore <home> <lore>";
        super(
                "setlore",
                "Sets the lore of a player's home",
                usage,
                "h0M3.edit",
                context -> {
                    Player player = context.getPlayer();
                    if (!context.hasArgs() || context.getArgCount() == 1) {
                        player.sendMessage(ChatFormat.error(usage));
                        return true;
                    }

                    String homeName = context.getArg(0);
                    String newLore = context.getArg(1);

                    // Get the home
                    Home home = homeManager.getHome(player.getUniqueId(), homeName);
                    if (home == null) {
                        player.sendMessage(ChatFormat.error("There is no home by that name"));
                        return true;
                    }

                    List<String> lore = Arrays.stream(newLore.split("\\|"))
                            .map(String::trim)
                            .filter(line -> !line.isEmpty())
                            .toList();

                    if (lore.isEmpty()) {
                        player.sendMessage(ChatFormat.error("Lore must contain at least one line"));
                        return true;
                    }

                    home.setLore(lore);
                    homeManager.update(home);
                    player.sendMessage(ChatFormat.info("Successfully updated your home's lore to " + lore.size() + " lines"));

                    return true;
                },
                "changelore"
        );
    }
}