package com.sanamo.h0M3.api;

import org.bukkit.Location;

import java.util.Objects;

public final class LocationUtil {

    // Prevent instantiation
    private LocationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static double distance(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null || !Objects.equals(loc1.getWorld(), loc2.getWorld())) {
            return -1;
        }
        return loc1.distance(loc2);
    }

    public static String format(Location location) {
        if (location == null) {
            return "null";
        }
        return String.format("%s: %.2f, %.2f, %.2f",
                Objects.requireNonNull(location.getWorld()).getName(),
                location.getX(),
                location.getY(),
                location.getZ());
    }
}