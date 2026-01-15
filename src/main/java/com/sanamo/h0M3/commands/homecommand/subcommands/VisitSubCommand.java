package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class VisitSubCommand extends SubCommand {

    public VisitSubCommand(HomeManager homeManager) {
        super(
                "visit",
                "Visit another player's home",
                "/home visit <home> <player>",
                "h0M3.share",
                context -> {
                    return true;
                }
        );
    }
}