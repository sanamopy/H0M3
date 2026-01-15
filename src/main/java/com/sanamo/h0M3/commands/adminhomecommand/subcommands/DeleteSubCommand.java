package com.sanamo.h0M3.commands.adminhomecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class DeleteSubCommand extends SubCommand {

    private final HomeManager homeManager;

    public DeleteSubCommand(HomeManager homeManager) {
        super(
                "delete",
                "Deletes a home for a player",
                "/adminhome delete <player> <home>",
                "h0M3.admin.delete",
                context -> {
                    return true;
                }
        );
        this.homeManager = homeManager;
    }
}