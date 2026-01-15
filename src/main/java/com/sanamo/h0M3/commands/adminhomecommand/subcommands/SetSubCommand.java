package com.sanamo.h0M3.commands.adminhomecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class SetSubCommand extends SubCommand {

    private final HomeManager homeManager;

    public SetSubCommand(HomeManager homeManager) {
        super(
                "set",
                "Sets a new home for a player",
                "/adminhome set <player> <home>",
                "h0M3.admin.set",
                context -> {
                    return true;
                }
        );
        this.homeManager = homeManager;
    }
}