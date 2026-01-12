package com.sanamo.h0M3.commands;

import com.sanamo.h0M3.api.LocationUtil;
import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MoveHomeCommand extends CoreCommand {

    private final HomeManager homeManager;

    public MoveHomeCommand(HomeManager homeManager) {
        super(
                "movehome",
                "Moves a home to the player's location",
                "/movehome <home>"
        );
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onExecute(CommandContext context) {

        Player player = context.getPlayer();
        if (!context.hasArgs()) {
            player.sendMessage(ChatFormat.error(this.getUsage()));
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

    @Override
    protected List<String> onTabComplete(CommandContext context) {
        Player player = context.getPlayer();

        if (context.getArgCount() == 1) {
            String partial = context.getArg(0);
            Map<String, Home> homes = homeManager.getHomes(player.getUniqueId());
            if (homes != null) {
                List<String> matches = new ArrayList<>();

                for (Home home : homes.values()) {
                    if (home.getDisplayName().toLowerCase().startsWith(partial)) {
                        matches.add(home.getDisplayName());
                    }
                }
                return matches;
            }
        }
        return new ArrayList<>();
    }
}
