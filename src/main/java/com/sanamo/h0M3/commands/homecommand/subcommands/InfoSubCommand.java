package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InfoSubCommand extends SubCommand {

    public InfoSubCommand(HomeManager homeManager) {
        final String usage = "/home info <home>";
        super(
                "info",
                "Information on a player's home",
                usage,
                "h0M3.use",
                context -> {
                    Player player = context.getPlayer();
                    UUID playerUUID = player.getUniqueId();
                    if (!context.hasArgs()) {
                        player.sendMessage(ChatFormat.error(usage));
                        return true;
                    }

                    String homeName = context.getArg(0);

                    if (!homeManager.exists(playerUUID, homeName)) {
                        player.sendMessage(ChatFormat.error("You do not have a home by that name"));
                        return true;
                    }

                    Home home = homeManager.getHome(playerUUID, homeName);
                    homeManager.sendInfo(home, player);

                    return true;
                }
        );
    }
}