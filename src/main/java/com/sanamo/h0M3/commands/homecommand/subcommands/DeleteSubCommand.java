package com.sanamo.h0M3.commands.homecommand.subcommands;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.api.sound.SoundUtil;
import com.sanamo.h0M3.managers.HomeManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DeleteSubCommand extends SubCommand {

    public DeleteSubCommand(HomeManager homeManager) {
        final String usage = "/home delete <home>";
        super(
                "delete",
                "Deletes a player's home",
                usage,
                "h0M3.delete",
                context -> {
                    Player player = context.getPlayer();
                    // Ensure they entered a name
                    if (!context.hasArgs()) {
                        player.sendMessage(ChatFormat.error(usage));
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
                },
                "remove"
        );
    }
}