package com.sanamo.h0M3.commands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

public class RenameHomeCommand extends CoreCommand {

    private final HomeManager homeManager;

    public RenameHomeCommand(HomeManager homeManager) {
        super(
                "renamehome",
                "Renames a player's home",
                "/renamehome <name> <new_name>",
                ""
        );
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onExecute(CommandContext context) {

        Player player = context.getPlayer();
        if (!context.hasArgs() || context.getArgCount() == 1) {
            player.sendMessage(ChatFormat.error(this.getUsage()));
            return true;
        }

        String homeName = context.getArg(0);
        String newName = context.getArg(1);

        // Get the home
        Home home = homeManager.getHome(player.getUniqueId(), homeName);
        if (home == null) {
            player.sendMessage(ChatFormat.error("There is no home by this name"));
            return true;
        }

        if (homeManager.homeNameHasColor(newName)) {
            player.sendMessage(ChatFormat.error("Home names cannot contain color codes"));
            return true;
        }

        if (homeManager.exists(player.getUniqueId(), newName)) {
            player.sendMessage(ChatFormat.error("A home with this name already exists"));
            return true;
        }

        if (homeManager.isValidHomeName(newName)) {
            player.sendMessage(ChatFormat.error("Please enter a home name between 3 and 16 characters"));
            return true;
        }

        // Update home name
        home.setDisplayName(newName);
        homeManager.update(home);
        player.sendMessage(ChatFormat.info("Successfully changed your home's name (" + homeName + " → " + newName + ")"));

        return true;
    }
}
