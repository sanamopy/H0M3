package com.sanamo.h0M3.api.gui.listener;

import com.sanamo.h0M3.H0M3;
import com.sanamo.h0M3.api.gui.GUI;
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

        GUI gui = H0M3.getInstance().getGuiManager().getOpenGUI(player);

        if (gui != null) {
            gui.handleClose(event);
            H0M3.getInstance().getGuiManager().unregisterOpenGUI(player);
        }
    }
}
