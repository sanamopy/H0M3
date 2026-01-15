package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.api.util.LocationUtil;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MoveSubCommand extends SubCommand {

    public MoveSubCommand(HomeManager homeManager) {
        final String usage = "/home move <home>";
        super(
                "move",
                "Moves a player's home",
                usage,
                "h0M3.edit",
                context -> {
                    Player player = context.getPlayer();
                    if (!context.hasArgs()) {
                        player.sendMessage(ChatFormat.error(usage));
                        return true;
                    }

                    String homeName = context.getArg(0);
                    UUID playerUUID = player.getUniqueId();

                    if (!homeManager.exists(playerUUID, homeName)) {
                        player.sendMessage(ChatFormat.error("You do not have a home by that name"));
                        return true;
                    }

                    Home home = homeManager.getHome(playerUUID, homeName);
                    Location oldLocation = home.getLocation();
                    Location newLocation = player.getLocation();
                    home.setLocation(newLocation);
                    homeManager.update(home);

                    player.sendMessage(ChatFormat.info("Successfully updated your home's location (" + LocationUtil.format(oldLocation) + " → " + LocationUtil.format(newLocation)));

                    return true;
                }
        );
    }
}