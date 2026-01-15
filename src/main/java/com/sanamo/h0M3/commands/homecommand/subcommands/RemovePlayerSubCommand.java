package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class RemovePlayerSubCommand extends SubCommand {

    public RemovePlayerSubCommand(HomeManager homeManager) {
        super(
                "removeplayer",
                "Removes another player from a player's home",
                "/home removeplayer <home> <player>",
                "h0M3.share",
                context -> {
                    return true;
                },
                "unadd"
        );
    }
}