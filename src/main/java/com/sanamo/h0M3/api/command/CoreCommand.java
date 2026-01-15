package com.sanamo.h0M3.api.command;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.command.annotations.CommandPermission;
import com.sanamo.h0M3.api.command.annotations.PlayerOnly;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CoreCommand {

    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;
    private String permission;
    private boolean playerOnly;
    private final List<SubCommand> subCommands = new ArrayList<>();

    public CoreCommand(String name, String description, String usage, String... aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = new ArrayList<>();
        for (String alias : aliases) {
            this.aliases.add(alias.toLowerCase());
        }

        // Check for annotations
        CommandPermission permAnnotation = getClass().getAnnotation(CommandPermission.class);
        if (permAnnotation != null) {
            this.permission = permAnnotation.value();
        }

        PlayerOnly playerOnlyAnnotation = getClass().getAnnotation(PlayerOnly.class);
        if (playerOnlyAnnotation != null) {
            this.playerOnly = true;
        }
    }

    public boolean execute(CommandContext context) {
        CommandSender sender = context.getSender();

        // Player-only check
        if (playerOnly && !(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        // Permission check
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(ChatFormat.error("You do not have permission to use this command."));
            return true;
        }

        // ---- SUBCOMMAND DISPATCH ----
        if (context.hasArgs()) {
            String subName = context.getArg(0);

            for (SubCommand sub : subCommands) {
                if (sub.matches(subName)) {
                    CommandContext subContext = context.shift(1);
                    return sub.execute(subContext);
                }
            }
        }

        // ---- FALLBACK TO MAIN COMMAND ----
        try {
            return onExecute(context);
        } catch (Exception e) {
            sender.sendMessage(ChatFormat.error("An error occurred while executing this command."));
            e.printStackTrace();
            return true;
        }
    }

    public List<String> tabComplete(CommandContext context) {
        return onTabComplete(context);
    }

    protected abstract boolean onExecute(CommandContext context);

    protected List<String> onTabComplete(CommandContext context) {
        return new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return "Usage: " + usage;
    }

    public List<String> getAliases() {
        return new ArrayList<>(aliases);
    }

    public boolean matches(String command) {
        if (command == null) {
            return true;
        }
        String lowerCommand = command.toLowerCase();
        return name.equalsIgnoreCase(lowerCommand) || aliases.contains(lowerCommand);
    }

    public void addSubCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }
}