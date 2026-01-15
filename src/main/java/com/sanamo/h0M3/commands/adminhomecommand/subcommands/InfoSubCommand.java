package com.sanamo.h0M3.commands.adminhomecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class InfoSubCommand extends SubCommand {

    private final HomeManager homeManager;

    public InfoSubCommand(HomeManager homeManager) {
        super(
                "info",
                "View the info for a player's home",
                "/adminhome info <player> <home>",
                "h0M3.admin.use",
                context -> {
                    return true;
                }
        );
        this.homeManager = homeManager;
    }
}