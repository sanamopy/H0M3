package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

public class TpSubCommand extends SubCommand {

    public TpSubCommand(HomeManager homeManager) {
        final String usage = "/home tp <home>";
        super(
                "tp",
                "Teleports to a player's home",
                usage,
                "h0M3.use",
                context -> {
                    // Ensure they entered an argument
                    Player player = context.getPlayer();
                    if (!context.hasArgs()) {
                        player.sendMessage(ChatFormat.error(usage));
                        return true;
                    }

                    // Get argument
                    String homeName = context.getArg(0);

                    // Get home & confirm
                    Home home = homeManager.getHome(player.getUniqueId(), homeName);
                    if (home == null) {
                        player.sendMessage(ChatFormat.error("You do not have a home by that name"));
                        return true;
                    }

                    // Ensure location is not null (this should never happen)
                    if (home.getLocation() == null) {
                        player.sendMessage(ChatFormat.error("There is no location on this home. Please delete it and reset it"));
                        return true;
                    }

                    // Teleport to the home
                    home.teleport();
                    homeManager.update(home);

                    return true;
                },
                "teleport"
        );
    }
}
