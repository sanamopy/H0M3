package com.sanamo.h0M3.guis;

import com.sanamo.h0M3.H0M3;
import com.sanamo.h0M3.api.LocationUtil;
import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.chat.ColorUtil;
import com.sanamo.h0M3.api.gui.GUI;
import com.sanamo.h0M3.api.item.ItemBuilder;
import com.sanamo.h0M3.managers.HomeManager;
import com.sanamo.h0M3.models.Home;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageHomeGUI extends GUI {

    private final HomeManager homeManager;
    private final Home home;
    private final Player player;

    public ManageHomeGUI(HomeManager homeManager, Home home, Player player) {
        super("manage_home", "Home Manager", 27);
        this.homeManager = homeManager;
        this.home = home;
        this.player = player;
        build();
    }

    private void build() {
        // Change name item

        if (home == null) {
            ItemStack nullItem = new ItemBuilder(Material.BARRIER)
                    .name(ColorUtil.translate("&c&lERROR"))
                    .lore(List.of(
                            ColorUtil.translate("&7Sorry, but this home is corrupted,"),
                            ColorUtil.translate("&7invalid, or could not be found."),
                            ColorUtil.translate("&7Please try deleting the home with"),
                            ColorUtil.translate("&e/delhome &7and retry. Sorry!")
                    ))
                    .build();
            setItem(13, nullItem);
        }

        assert home != null;
        ItemStack changeNameItem = new ItemBuilder(Material.NAME_TAG)
                .name(ColorUtil.translate("&6Change Name"))
                .lore(List.of(
                        ColorUtil.translate("&eLeft-Click &7here to change"),
                        ColorUtil.translate("&7the display name of your home."),
                        " ",
                        ColorUtil.translate("&eCurrent: &7") + home.getDisplayName()
                ))
                .build();
        setItem(11, changeNameItem);
        setClickHandler(11, this::changeName);

        // Change material item
        ItemStack changeMaterialItem = new ItemBuilder(home.getMaterial())
                .name(ColorUtil.translate("&6Change Material"))
                .lore(List.of(
                        ColorUtil.translate("&eLeft-Click &7here to change"),
                        ColorUtil.translate("&7the material of your home."),
                        " ",
                        ColorUtil.translate("&eCurrent: &7") + home.getMaterial()
                ))
                .build();
        setItem(12, changeMaterialItem);
        setClickHandler(12, this::changeMaterial);

        // Change lore item
        List<String> lore = new ArrayList<>();

        lore.add(ColorUtil.translate("&eLeft-Click &7here to change"));
        lore.add(ColorUtil.translate("&7the lore of your home."));
        lore.add(" ");
        lore.add(ColorUtil.translate("&eCurrent:"));

        List<String> currentLore = home.getLore();

        if (currentLore == null || currentLore.isEmpty()) {
            lore.add(ColorUtil.translate("&7None"));
        } else {
            for (String line : currentLore) {
                lore.add(ColorUtil.translate("&8• &7" + line));
            }
        }

        ItemStack changeLoreItem = new ItemBuilder(Material.PAPER)
                .name(ColorUtil.translate("&6Change Lore"))
                .lore(lore)
                .build();

        setItem(13, changeLoreItem);
        setClickHandler(13, this::changeLore);

        // Change location item
        ItemStack changeLocationItem = new ItemBuilder(Material.MAP)
                .name(ColorUtil.translate("&6Change Location"))
                .lore(List.of(
                        ColorUtil.translate("&eLeft-Click &7here to change"),
                        ColorUtil.translate("&7the location of your home."),
                        " ",
                        ColorUtil.translate("&eCurrent: &7") + LocationUtil.format(home.getLocation())
                ))
                .build();
        setItem(14, changeLocationItem);
        setClickHandler(14, this::changeLocation);

        // Delete home item
        ItemStack deleteHomeItem = new ItemBuilder(Material.BARRIER)
                .name(ColorUtil.translate("&6Delete Home"))
                .lore(List.of(
                        ColorUtil.translate("&eLeft-Click &7here to permanently"),
                        ColorUtil.translate("&7delete your home."),
                        ColorUtil.translate("&7"),
                        ColorUtil.translate("&c&lWARNING: THIS IS IRREVERSIBLE!")
                ))
                .build();
        setItem(15, deleteHomeItem);
        setClickHandler(15, this::deleteHome);

        // Back button item
        ItemStack backButtonItem = new ItemBuilder(Material.ARROW)
                .name(ColorUtil.translate("&c&lGo Back"))
                .lore(List.of(
                        ColorUtil.translate("&7Left-Click here to go back"),
                        ColorUtil.translate("&7to the previous page")
                ))
                .build();
        setItem(18, backButtonItem);
        setClickHandler(18, this::backButton);
    }

    private void changeName() {
        player.closeInventory();
        H0M3.getInstance().getChatInputManager().awaitInput(
                player,
                "&ePlease type the new name for your home",
                input -> {
                    if (homeManager.isValidHomeName(input)) {
                        player.sendMessage(ChatFormat.error("That is not a valid home name"));
                    } else {
                        this.home.setDisplayName(input);
                        homeManager.update(home);
                        player.sendMessage(ChatFormat.info("Successfully updated your home's name"));
                    }
                    ManageHomeGUI manageHomeGUI = new ManageHomeGUI(homeManager, home, player);
                    manageHomeGUI.open(player);
                },
                () -> player.sendMessage(ChatFormat.error("Name input cancelled"))
        );
    }

    private void changeMaterial() {
        player.closeInventory();
        H0M3.getInstance().getChatInputManager().awaitInput(
                player,
                "&ePlease type the name of the new material for your home (EX: DIAMOND_BLOCK, GRASS, STONE, etc.)",
                input -> {
                    Material newMaterial = Material.getMaterial(input);
                    if (newMaterial == null) {
                        player.sendMessage(ChatFormat.error("Failed to grab the material by that name"));
                    } else {
                        this.home.setMaterial(newMaterial);
                        homeManager.update(home);
                        player.sendMessage(ChatFormat.info("Successfully updated your home's material"));
                    }
                    ManageHomeGUI manageHomeGUI = new ManageHomeGUI(homeManager, home, player);
                    manageHomeGUI.open(player);
                },
                () -> player.sendMessage(ChatFormat.error("Material input cancelled"))
        );
    }

    private void changeLore() {
        player.closeInventory();
        H0M3.getInstance().getChatInputManager().awaitInput(
                player,
                "&ePlease enter the new lore for your home (Use | to separate lines)",
                input -> {

                    if (input == null || input.trim().isEmpty()) {
                        player.sendMessage(ChatFormat.error("Lore cannot be empty"));
                        return;
                    }

                    List<String> lore = Arrays.stream(input.split("\\|"))
                            .map(String::trim)
                            .filter(line -> !line.isEmpty())
                            .toList();

                    if (lore.isEmpty()) {
                        player.sendMessage(ChatFormat.error("Lore must contain at least one line"));
                        return;
                    }

                    this.home.setLore(lore);
                    homeManager.update(home);
                    player.sendMessage(ChatFormat.info("Successfully updated your home's lore"));

                    ManageHomeGUI manageHomeGUI = new ManageHomeGUI(homeManager, home, player);
                    manageHomeGUI.open(player);
                },
                () -> player.sendMessage(ChatFormat.error("Lore input cancelled"))
        );

    }

    private void changeLocation() {
        this.home.setLocation(player.getLocation());
        homeManager.update(home);
        ManageHomeGUI manageHomeGUI = new ManageHomeGUI(homeManager, home, player);
        manageHomeGUI.open(player);
        player.sendMessage(ChatFormat.info("Successfully updated your home's location"));
    }

    private void deleteHome() {
        player.closeInventory();
        H0M3.getInstance().getChatInputManager().awaitInput(
                player,
                "&c&lWARNING: Are you sure you want to delete your home? This action is IRREVERSIBLE\n&7Type '&aConfirm&7' to confirm",
                input -> {
                    if (input.equalsIgnoreCase("confirm")) {
                        homeManager.deleteHome(player.getUniqueId(), home.getDisplayName());
                        player.sendMessage(ChatFormat.info("Successfully deleted your home"));
                        HomesGUI homesGUI = new HomesGUI(homeManager, player);
                        homesGUI.open(player);
                    } else {
                        player.sendMessage(ChatFormat.error("Home deletion cancelled"));
                        ManageHomeGUI manageHomeGUI = new ManageHomeGUI(homeManager, home, player);
                        manageHomeGUI.open(player);
                    }
                }, () -> player.sendMessage(ChatFormat.error("Home deletion cancelled"))
        );
    }

    private void backButton(InventoryClickEvent event) {
        player.closeInventory();
        HomesGUI homesGUI = new HomesGUI(homeManager, player);
        homesGUI.open(player);
    }
}