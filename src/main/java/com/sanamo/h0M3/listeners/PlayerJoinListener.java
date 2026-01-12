package com.sanamo.h0M3.listeners;

import com.sanamo.h0M3.managers.HomeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final HomeManager homeManager;

    public PlayerJoinListener(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        homeManager.loadHomes(event.getPlayer().getUniqueId());
        System.out.print("Loaded all of " + event.getPlayer() + "'s homes.");
    }
}
