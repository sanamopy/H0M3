package com.sanamo.h0M3.commands.adminhomecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class ListSubCommand extends SubCommand {

    private final HomeManager homeManager;

    public ListSubCommand(HomeManager homeManager) {
        super(
                "list",
                "Lists all homes for a player",
                "/adminhome list <player>",
                "h0M3.admin.use",
                context -> {
                    return true;
                }
        );
        this.homeManager = homeManager;
    }
}