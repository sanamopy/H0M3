package com.sanamo.h0M3.models;

import com.sanamo.h0M3.managers.TeleportManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Home {
    private final Player owner;
    private final String id;
    private String displayName;
    private List<String> lore;
    private Material material;
    private Location location;

    public Home(String id, Player owner, String displayName, List<String> lore, Material material, Location location) {
        this.owner = owner;
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
        this.location = location;
        this.id = id;
    }

    public void teleport() {
        TeleportManager.teleportToLocation(owner, location);
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> newLore) {
        lore = newLore;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material newMaterial) {
        material = newMaterial;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location newLocation) {
        location = newLocation;

    }

    public Player getOwner() {
        return owner;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Home{" +
                "owner=" + (owner != null ? owner.getName() : "null") +
                ", id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", lore=" + (lore != null ? String.join(", ", lore) : "null") +
                ", material=" + (material != null ? material.toString() : "null") +
                ", location=" + (location != null ? location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() : "null") +
                '}';
    }
}