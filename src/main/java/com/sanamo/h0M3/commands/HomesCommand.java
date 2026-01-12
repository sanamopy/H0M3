package com.sanamo.h0M3.commands;

import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.api.command.annotations.PlayerOnly;
import com.sanamo.h0M3.guis.HomesGUI;
import com.sanamo.h0M3.managers.HomeManager;
import org.bukkit.entity.Player;

@PlayerOnly()
public class HomesCommand extends CoreCommand {

    HomeManager homeManager;

    public HomesCommand(HomeManager homeManager) {
        super(
                "homes",
                "Show all of the player's homes",
                "/homes"
        );
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onExecute(CommandContext context) {

        Player player = context.getPlayer();
        HomesGUI homesGUI = new HomesGUI(homeManager, player);
        homesGUI.open(player);

        return true;
    }
}
