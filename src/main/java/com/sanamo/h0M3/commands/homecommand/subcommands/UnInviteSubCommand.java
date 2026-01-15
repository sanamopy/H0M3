package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class UnInviteSubCommand extends SubCommand {

    public UnInviteSubCommand(HomeManager homeManager) {
        super(
                "uninvite",
                "Revoke an invite to a player's home",
                "/home uninvite <home> <player>",
                "h0M3.share",
                context -> {
                    return true;
                },
                "revokeinvite"
        );
    }
}