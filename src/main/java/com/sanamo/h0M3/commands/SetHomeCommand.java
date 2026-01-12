package com.sanamo.h0M3.commands;

import com.sanamo.h0M3.api.LocationUtil;
import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.api.command.annotations.PlayerOnly;
import com.sanamo.h0M3.api.sound.SoundUtil;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

@PlayerOnly
public class SetHomeCommand extends CoreCommand {
    private final HomeManager homeManager;

    public SetHomeCommand(HomeManager homeManager) {
        super(
                "sethome",
                "Sets a new home for the player",
                "/sethome <name>"
        );
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onExecute(CommandContext context) {
        // Ensure they entered a name
        Player player = context.getPlayer();
        Location location = player.getLocation();
        if (!context.hasArgs()) {
            player.sendMessage(ChatFormat.error(this.getUsage()));
            return true;
        }

        // Get & validate name
        String name = context.getArg(0);
        if (homeManager.isValidHomeName(name)) {
            player.sendMessage(ChatFormat.error("Please enter a home name between 3 and 16 characters"));
            return true;
        }

        // Ensure no color codes in it
        if (homeManager.homeNameHasColor(name)) {
            player.sendMessage(ChatFormat.error("Home names cannot contain color codes"));
            return true;
        }

        // Ensure they have not reached the limit
        if (homeManager.getHomeCount(player.getUniqueId()) >= HomeManager.MAX_NUM_HOMES) {
            player.sendMessage(ChatFormat.error("You already have the max number of homes"));
            return true;
        }

        // Home already exists, just update location
        if (homeManager.exists(player.getUniqueId(), name)) {
            Home home = homeManager.getHome(player.getUniqueId(), name);
            home.setLocation(player.getLocation());
            homeManager.update(home);

            // Home does not exist, create a new one and add it
        } else {
            String id = "home_" + player.getUniqueId() + "_" + UUID.randomUUID();

            Home home = new Home(
                    id,
                    player,
                    name,
                    new ArrayList<>(),
                    Material.CHEST,
                    location
            );

            homeManager.addHome(player.getUniqueId(), home);
        }

        // Confirmation
        SoundUtil.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.sendMessage(ChatFormat.info("Set your home " + name + " at " + LocationUtil.format(location)));

        return true;
    }
}