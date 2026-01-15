package com.sanamo.h0M3.commands.adminhomecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class ReloadSubCommand extends SubCommand {

    private final HomeManager homeManager;

    public ReloadSubCommand(HomeManager homeManager) {
        super(
                "reload",
                "Reloads the plugin configuration",
                "/adminhome reload",
                "h0M3.admin.reload",
                context -> {
                    return true;
                }
        );
        this.homeManager = homeManager;
    }
}