package com.sanamo.h0M3.api.gui.listener;

import com.sanamo.h0M3.H0M3;
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
        if (H0M3.getInstance().getGuiManager().getOpenGUI(player) != null) {
            event.setCancelled(true);
        }
    }
}