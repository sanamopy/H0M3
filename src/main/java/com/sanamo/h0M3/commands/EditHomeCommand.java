package com.sanamo.h0M3.commands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.guis.ManageHomeGUI;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EditHomeCommand extends CoreCommand {

    private final HomeManager homeManager;

    public EditHomeCommand(HomeManager homeManager) {
        super(
                "edithome",
                "Edits a player's home information",
                "/edithome <home>"
        );
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onExecute(CommandContext context) {

        Player player = context.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (!context.hasArgs()) {
            player.sendMessage(ChatFormat.error(this.getUsage()));
            return true;
        }

        String homeName = context.getArg(0);
        if (!homeManager.exists(playerUUID, homeName)) {
            player.sendMessage(ChatFormat.error("You do not have a home by that name"));
            return true;
        }

        Home home = homeManager.getHome(player.getUniqueId(), homeName);
        if (home == null) {
            player.sendMessage(ChatFormat.error("Home result is null"));
            return true;
        }

        ManageHomeGUI manageHomeGUI = new ManageHomeGUI(homeManager, home, player);
        manageHomeGUI.open(player);

        return true;
    }
}
