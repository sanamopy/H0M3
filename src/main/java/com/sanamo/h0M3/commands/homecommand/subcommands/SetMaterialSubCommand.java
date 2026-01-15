package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SetMaterialSubCommand extends SubCommand {

    public SetMaterialSubCommand(HomeManager homeManager) {
        final String usage = "/home setmaterial <home> <material>";
        super(
                "setmaterial",
                "Sets the material of a player's home",
                usage,
                "h0M3.edit",
                context -> {
                    // Ensure they entered the correct number of arguments
                    Player player = context.getPlayer();
                    if (!context.hasArgs() || context.getArgCount() == 1) {
                        player.sendMessage(ChatFormat.error(usage));
                        return true;
                    }

                    // Get arguments
                    String homeName = context.getArg(0);
                    String materialName = context.getArg(1);

                    // Grab home & confirm
                    Home home = homeManager.getHome(player.getUniqueId(), homeName);
                    if (home == null) {
                        player.sendMessage(ChatFormat.error("You do not have a home by that name"));
                        return true;
                    }

                    Material oldMaterial = home.getMaterial();

                    // Confirm material
                    Material material = Material.getMaterial(materialName);
                    if (material == null) {
                        player.sendMessage(ChatFormat.error("Failed to find a material by that name"));
                        return true;
                    }

                    // Update material
                    home.setMaterial(material);
                    homeManager.update(home);
                    player.sendMessage(ChatFormat.info("Successfully updated your home's material (" + oldMaterial.name() + " → " + material.name() + ")"));

                    return true;
                },
                "changematerial"
        );
    }
}