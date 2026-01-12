package com.sanamo.h0M3.api.gui.listener;

import com.sanamo.h0M3.api.gui.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragListener implements Listener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        // Cancel drag events in GUIs
        if (GUIManager.getInstance().getOpenGUI(player) != null) {
            event.setCancelled(true);
        }
    }
}