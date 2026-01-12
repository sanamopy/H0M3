package com.sanamo.h0M3;

import com.sanamo.h0M3.api.command.CommandManager;
import com.sanamo.h0M3.api.gui.GUIManager;
import com.sanamo.h0M3.api.logging.CoreLogger;
import com.sanamo.h0M3.api.logging.LogLevel;
import com.sanamo.h0M3.commands.*;
import com.sanamo.h0M3.listeners.PlayerJoinListener;
import com.sanamo.h0M3.listeners.PlayerQuitListener;
import com.sanamo.h0M3.listeners.TeleportMoveListener;
import com.sanamo.h0M3.managers.ChatInputManager;
import com.sanamo.h0M3.managers.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class H0M3 extends JavaPlugin {

    private static H0M3 instance;
    private static CoreLogger logger;
    private HomeManager homeManager;
    private CommandManager commandManager;
    private ChatInputManager chatInputManager;
    private GUIManager guiManager;

    // Getters
    public static H0M3 getInstance() {
        return instance;
    }

    public static CoreLogger getLog() {
        return logger;
    }

    @Override
    public void onEnable() {
        instance = this;

        initLogger();
        registerManagers();
        registerCommands();
        registerListeners();
        loadHomes();

        logger.info(getDescription().getName() + "v" + getDescription().getVersion() + " has been enabled!");
    }

    private void initLogger() {
        logger = new CoreLogger(this);
        logger.setLevel(LogLevel.INFO);
    }

    private void registerManagers() {
        guiManager = new GUIManager(this);
        homeManager = new HomeManager(this);
        chatInputManager = new ChatInputManager(this);
        commandManager = new CommandManager(this);
    }

    private void registerCommands() {
        commandManager.registerCommand(new HomeCommand(homeManager));
        commandManager.registerCommand(new DeleteHomeCommand(homeManager));
        commandManager.registerCommand(new SetHomeCommand(homeManager));
        commandManager.registerCommand(new HomesCommand(homeManager));
        commandManager.registerCommand(new RenameHomeCommand(homeManager));
        commandManager.registerCommand(new MoveHomeCommand(homeManager));
        commandManager.registerCommand(new EditHomeCommand(homeManager));
        commandManager.registerCommand(new HomeInfoCommand(homeManager));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(homeManager), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(homeManager), this);
        Bukkit.getPluginManager().registerEvents(new TeleportMoveListener(), this);
    }

    private void loadHomes() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            homeManager.loadHomes(player.getUniqueId());
        }
    }

    /*
     * Cleanup
     */
    @Override
    public void onDisable() {
        unloadHomes();
        logger.close();
    }

    private void unloadHomes() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            homeManager.unloadHomes(player.getUniqueId());
        }
    }

    public ChatInputManager getChatInputManager() {
        return chatInputManager;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }
}
