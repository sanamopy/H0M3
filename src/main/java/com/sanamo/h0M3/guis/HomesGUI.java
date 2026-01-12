package com.sanamo.h0M3.guis;

import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.chat.ColorUtil;
import com.sanamo.h0M3.api.gui.GUI;
import com.sanamo.h0M3.api.item.ItemBuilder;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.managers.TeleportManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomesGUI extends GUI {

    private final HomeManager homeManager;
    private final Player player;

    public HomesGUI(HomeManager homeManager, Player player) {
        super("homes_gui",
                ColorUtil.translate(
                        "Homes ("
                                + homeManager.getHomeCount(player.getUniqueId())
                                + ")"),
                calculateSize(homeManager, player)
        );
        this.homeManager = homeManager;
        this.player = player;
        build();
    }

    private static int calculateSize(HomeManager homeManager, Player player) {
        int homeCount = homeManager.getHomeCount(player.getUniqueId());
        if (homeCount == 0) {
            return 9;
        }

        int rows = Math.min(6, (int) Math.ceil(homeCount / 9.0));
        return Math.max(9, rows * 9);
    }

    public void build() {
        Map<String, Home> homes = homeManager.getHomes(player.getUniqueId());

        if (homes.isEmpty()) {
            setItem(4, new ItemBuilder(Material.BARRIER)
                    .name(ColorUtil.translate("&cNo Homes"))
                    .lore(ColorUtil.translate("&7You have no homes created yet."))
                    .build());
            return;
        }

        int slot = 0;
        for (Home home : homes.values()) {
            if (slot >= getSize()) break;

            // Build lore
            List<String> lore = home.getLore();
            List<String> displayLore = new ArrayList<>();

            // Add home lore if it exists
            if (lore != null && !lore.isEmpty()) {
                for (String line : lore) {
                    displayLore.add(ColorUtil.translate(line));
                }
                displayLore.add(""); // Empty line separator
            }

            // Add instruction lines
            displayLore.add(ColorUtil.translate("&eLeft-Click &7to teleport"));
            displayLore.add(ColorUtil.translate("&eRight-Click &7to manage"));

            // Create item
            ItemStack item = new ItemBuilder(home.getMaterial())
                    .name(ColorUtil.translate("&6" + home.getDisplayName()))
                    .lore(displayLore)
                    .build();

            final String homeName = home.getDisplayName();
            setItem(slot, item);
            setClickHandler(slot, event -> handleClick(event, homeName));
            slot++;
        }
    }

    private void handleClick(InventoryClickEvent event, String homeName) {
        if (!(event.getWhoClicked() instanceof Player clicker)) {
            return;
        }

        ClickType clickType = event.getClick();

        // Right-click to manage
        if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
            Home home = homeManager.getHome(player.getUniqueId(), homeName);
            ManageHomeGUI gui = new ManageHomeGUI(homeManager, home, player);
            gui.open(player);
        }

        // Left-click to tp
        else if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            Home home = homeManager.getHome(player.getUniqueId(), homeName);
            clicker.closeInventory();
            if (!TeleportManager.teleportToLocation(clicker, home.getLocation())) {
                clicker.sendMessage(ChatFormat.error("Failed to teleport! The warp location may be invalid."));
            }
        }
    }
}