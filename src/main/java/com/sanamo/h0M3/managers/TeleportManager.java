package com.sanamo.h0M3.managers;

import com.sanamo.h0M3.H0M3;
import com.sanamo.h0M3.api.chat.ChatFormat;
import com.sanamo.h0M3.api.particles.ParticleUtil;
import com.sanamo.h0M3.api.sound.SoundUtil;
import com.sanamo.h0M3.api.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class TeleportManager {
    private static final int TELEPORT_TIME = 3;
    private static final boolean teleportDelay = false;
    private static final HashMap<UUID, UUID> playerTeleports = new HashMap<>();
    private static final HashMap<UUID, Location> locationTeleports = new HashMap<>();
    private static final HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    public static void teleportToLocation(Player player, Location toLocation) {
        locationTeleports.put(player.getUniqueId(), toLocation);
        startTeleportCountdown(player, toLocation, null);
    }

    private static void startTeleportCountdown(Player player, Location toLocation, Player target) {
        if (teleportDelay) {
            BukkitTask task = new BukkitRunnable() {
                int timeLeft = TELEPORT_TIME;

                @Override
                public void run() {
                    if (timeLeft <= 0) {
                        doTeleport(player, toLocation, target);
                        this.cancel();
                        return;
                    }
                    player.sendMessage(ChatFormat.info("You will be teleported in " + timeLeft + "..."));
                    ParticleUtil.spawnCircle(player.getLocation(), 1, Particle.DRIPPING_LAVA, 40);
                    SoundUtil.play(player, Sound.BLOCK_NOTE_BLOCK_BELL);
                    timeLeft -= 1;
                }
            }.runTaskTimer(H0M3.getInstance(), 0L, 20L);
            tasks.put(player.getUniqueId(), task);
        } else {
            doTeleport(player, toLocation, target);
        }
    }

    private static void doTeleport(Player player, Location toLocation, Player target) {
        player.teleport(toLocation);
        SoundUtil.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        ParticleUtil.spawnCircle(player.getLocation(), 1, Particle.HAPPY_VILLAGER, 40);
        if (target == null) {
            player.sendMessage(ChatFormat.info("You have been teleported to " + LocationUtil.format(toLocation)));
        } else {
            player.sendMessage(ChatFormat.info("You have been teleported to " + target.getName()));
        }
        removeTeleport(player.getUniqueId());
    }

    public static void playerMoved(Player player) {
        tasks.get(player.getUniqueId()).cancel();
        removeTeleport(player.getUniqueId());
        player.sendMessage(ChatFormat.error("You moved! Cancelling your teleportation request."));
    }

    public static boolean isPlayerTeleporting(Player player) {
        return tasks.containsKey(player.getUniqueId());
    }

    private static void removeTeleport(UUID uuid) {
        playerTeleports.remove(uuid);
        locationTeleports.remove(uuid);
        tasks.remove(uuid);
    }
}