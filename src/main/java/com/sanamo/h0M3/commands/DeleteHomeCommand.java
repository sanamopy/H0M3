package com.sanamo.h0M3.commands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.api.command.annotations.PlayerOnly;
import com.sanamo.h0M3.api.sound.SoundUtil;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@PlayerOnly
public class DeleteHomeCommand extends CoreCommand {

    private final HomeManager homeManager;

    public DeleteHomeCommand(HomeManager homeManager) {
        super(
                "deletehome",
                "Deletes a player's home",
                "/deletehome <home>",
                "delhome", "dh"
        );
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onExecute(CommandContext context) {
        Player player = context.getPlayer();
        // Ensure they entered a name
        if (!context.hasArgs()) {
            player.sendMessage(ChatFormat.error(this.getUsage()));
            return true;
        }

        // Validate home name
        String homeName = context.getArg(0);
        if (!homeManager.exists(player.getUniqueId(), homeName)) {
            player.sendMessage(ChatFormat.error("You do not have a home by the name of " + homeName));
            return true;
        }

        homeManager.deleteHome(player.getUniqueId(), homeName);
        player.sendMessage(ChatFormat.info("Your home " + homeName + " has been deleted"));
        SoundUtil.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandContext context) {
        Player player = context.getPlayer();

        if (context.getArgCount() == 1) {
            String partial = context.getArg(0, "").toLowerCase();
            Map<String, Home> homes = homeManager.getHomes(player.getUniqueId());
            if (homes != null) {
                List<String> matches = new ArrayList<>();

                for (Home home : homes.values()) {
                    if (home.getDisplayName().toLowerCase().startsWith(partial)) {
                        matches.add(home.getDisplayName());
                    }
                }

                return matches;
            }
        }
        return new ArrayList<>();
    }
}