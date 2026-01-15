package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class InviteSubCommand extends SubCommand {

    public InviteSubCommand(HomeManager homeManager) {
        super(
                "invite",
                "Invites another player to a player's home",
                "/home invite <home> <player>",
                "h0M3.share",
                context -> {
                    return true;
                },
                "add"
        );
    }
}