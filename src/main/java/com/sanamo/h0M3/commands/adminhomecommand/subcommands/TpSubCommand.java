package com.sanamo.h0M3.commands.adminhomecommand.subcommands;

import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.managers.HomeManager;

public class TpSubCommand extends SubCommand {

    private final HomeManager homeManager;

    public TpSubCommand(HomeManager homeManager) {
        super(
                "tp",
                "Teleports to a player's home",
                "/adminhome tp <player> <home>",
                "h0M3.admin.tp",
                context -> {
                    return true;
                }
        );
        this.homeManager = homeManager;
    }
}