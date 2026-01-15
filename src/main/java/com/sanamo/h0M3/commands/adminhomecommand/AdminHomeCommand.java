package com.sanamo.h0M3.commands.adminhomecommand;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.managers.HomeManager;
import org.bukkit.entity.Player;

public class AdminHomeCommand extends CoreCommand {

    private final HomeManager homeManager;

    public AdminHomeCommand(HomeManager homeManager) {
        super(
                "adminhome",
                "Manages all homes via an admin GUI",
                "/adminhome [set|tp|info|reload|delete|list]"
        );
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onExecute(CommandContext context) {
        Player player = context.getPlayer();
        player.sendMessage(ChatFormat.warning("TODO: Admin Home Command"));
        return true;
    }
}
