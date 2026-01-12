package com.sanamo.h0M3.api.gui.listener;

import com.sanamo.h0M3.api.gui.GUI;
import com.sanamo.h0M3.api.gui.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        GUI gui = GUIManager.getInstance().getOpenGUI(player);

        if (gui != null) {
            event.setCancelled(true);
            gui.handleClick(event);
        }
    }
}
