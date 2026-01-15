package com.sanamo.h0M3.commands.homecommand;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.CommandContext;
import com.sanamo.h0M3.api.command.CoreCommand;
import com.sanamo.h0M3.api.command.SubCommand;
import com.sanamo.h0M3.api.command.annotations.PlayerOnly;
import com.sanamo.h0M3.commands.homecommand.subcommands.*;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@PlayerOnly
public class HomeCommand extends CoreCommand {

    private final HomeManager homeManager;

    public HomeCommand(HomeManager homeManager) {
        super(
                "home",
                "Teleports a plyer to their home",
                "/home [name|tp|rename|delete|setmaterial|setlore|edit|info|invite|uninvite|accept|visit]"
        );
        this.homeManager = homeManager;
        registerSubCommands();
    }

    private void registerSubCommands() {
        addSubCommand(new AcceptSubCommand(homeManager));
        addSubCommand(new DeleteSubCommand(homeManager));
        addSubCommand(new EditSubCommand(homeManager));
        addSubCommand(new InfoSubCommand(homeManager));
        addSubCommand(new InviteSubCommand(homeManager));
        addSubCommand(new MoveSubCommand(homeManager));
        addSubCommand(new RemovePlayerSubCommand(homeManager));
        addSubCommand(new RenameSubCommand(homeManager));
        addSubCommand(new SetLoreSubCommand(homeManager));
        addSubCommand(new SetMaterialSubCommand(homeManager));
        addSubCommand(new SetSubCommand(homeManager));
        addSubCommand(new TpSubCommand(homeManager));
        addSubCommand(new UnInviteSubCommand(homeManager));
        addSubCommand(new VisitSubCommand(homeManager));
    }

    @Override
    protected boolean onExecute(CommandContext context) {
        // Ensure they entered a name
        Player player = context.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!context.hasArgs()) {
            player.sendMessage(ChatFormat.error(this.getUsage()));
            return true;
        }
        String name = context.getArg(0);

        // Ensure no color codes in it
        if (homeManager.homeNameHasColor(name)) {
            player.sendMessage(ChatFormat.error("Home names cannot contain color codes"));
            return true;
        }

        // Confirm name
        if (!homeManager.exists(uuid, name)) {
            player.sendMessage(ChatFormat.error("You do not have a home by the name of " + name));
            return true;
        }


        // Get home & teleport
        Home home = homeManager.getHome(uuid, name);
        if (home == null) {
            player.sendMessage(ChatFormat.error("I failed to get the home by the name of " + name));
            return true;
        }
        home.teleport();
        homeManager.update(home);

        return true;
    }

    @Override
    protected List<String> onTabComplete(CommandContext context) {
        if (context.getArgCount() == 1) {
            String partial = context.getArg(0).toLowerCase();
            List<String> matches = new ArrayList<>();

            // Subcommands
            for (SubCommand sub : getSubCommands()) {
                if (sub.getName().startsWith(partial)) {
                    matches.add(sub.getName());
                }
            }

            // Homes
            Map<String, Home> homes = homeManager.getHomes(context.getPlayer().getUniqueId());
            if (homes != null) {
                for (Home home : homes.values()) {
                    if (home.getDisplayName().toLowerCase().startsWith(partial)) {
                        matches.add(home.getDisplayName());
                    }
                }
            }

            return matches;
        }
        return new ArrayList<>();
    }
}