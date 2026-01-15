package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class AcceptSubCommand extends SubCommand {

    public AcceptSubCommand(HomeManager homeManager) {
        super(
                "accept",
                "Accepts an invite to a player's home",
                "/home accept <home> <player>",
                "h0M3.share",
                context -> {
                    return true;
                }
        );
    }
}