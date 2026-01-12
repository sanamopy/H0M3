package com.sanamo.h0M3.api.gui.listener;

import com.sanamo.h0M3.api.gui.GUI;
import com.sanamo.h0M3.api.gui.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        GUI gui = GUIManager.getInstance().getOpenGUI(player);

        if (gui != null) {
            gui.handleClose(event);
            GUIManager.getInstance().unregisterOpenGUI(player);
        }
    }
}
