package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.api.sound.SoundUtil;
import com.sanamo.h0M3.api.util.LocationUtil;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class SetSubCommand extends SubCommand {

    public SetSubCommand(HomeManager homeManager) {
        final String usage = "/home set <home>";
        super(
                "set",
                "Sets a new player's home",
                usage,
                "h0M3.set",
                context -> {
                    // Ensure they entered an argument
                    Player player = context.getPlayer();
                    if (!context.hasArgs()) {
                        player.sendMessage(ChatFormat.error(usage));
                        return true;
                    }

                    // Get argument
                    String homeName = context.getArg(0);
                    Location location = player.getLocation();

                    // See if a home by this name already exists
                    if (homeManager.exists(player.getUniqueId(), homeName)) {
                        // If it does, then just overwrite its location
                        Home home = homeManager.getHome(player.getUniqueId(), homeName);
                        Location oldLocation = home.getLocation();

                        home.setLocation(location);
                        homeManager.update(home);
                        player.sendMessage(ChatFormat.info("Successfully updated your home's location (" + LocationUtil.format(oldLocation) + " → " + LocationUtil.format(location) + ")"));
                        return true;
                    } else {
                        // Only do this if the home does not exist already
                        // Create a new home here
                        String id = "home_" + location + "_" + UUID.randomUUID();

                        Home home = new Home(
                                id,
                                player,
                                homeName,
                                new ArrayList<>(),
                                Material.CHEST,
                                location,
                                System.currentTimeMillis(),
                                0
                        );

                        homeManager.addHome(player.getUniqueId(), home);

                        // Confirmation
                        SoundUtil.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        player.sendMessage(ChatFormat.info("Set your home " + homeName + " at " + LocationUtil.format(location)));
                    }

                    return true;
                },
                "create"
        );
    }
}