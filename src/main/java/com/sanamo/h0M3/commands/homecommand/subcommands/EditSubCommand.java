package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.guis.ManageHomeGUI;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EditSubCommand extends SubCommand {

    public EditSubCommand(HomeManager homeManager) {
        final String usage = "/home edit <home>";
        super(
                "edit",
                "Edits a player's home",
                usage,
                "h0M3.edit",
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

                    Home home = homeManager.getHome(player.getUniqueId(), homeName);
                    if (home == null) {
                        player.sendMessage(ChatFormat.error("Home result is null"));
                        return true;
                    }

                    ManageHomeGUI manageHomeGUI = new ManageHomeGUI(homeManager, home, player);
                    manageHomeGUI.open(player);

                    return true;
                }
        );
    }
}