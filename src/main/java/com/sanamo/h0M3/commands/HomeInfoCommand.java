package com.sanamo.h0M3.commands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HomeInfoCommand extends CoreCommand {

    private final HomeManager homeManager;

    public HomeInfoCommand(HomeManager homeManager) {
        super(
                "homeinfo",
                "Sends information on a player's home",
                "/homeinfo <home>"
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

        Home home = homeManager.getHome(playerUUID, homeName);
        homeManager.sendInfo(home, player);

        return true;
    }
}
