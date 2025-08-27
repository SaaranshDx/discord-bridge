package com.SaaranshXd.discordbridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.advancement.Advancement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordBridge extends JavaPlugin implements Listener {

    private String webhookUrl;
    private String consoleAvatar;

    @Override
    public void onEnable() {
        // Generate /plugins/DiscordBridge/config.yml if missing
        saveDefaultConfig();
        loadConfigValues();

        // Register events
        Bukkit.getPluginManager().registerEvents(this, this);

        getLogger().info("DiscordBridge enabled!");
        sendWebhook("[SERVER]", "\uD83D\uDFE2 Server is online!", true);
    }

    @Override
    public void onDisable() {
        getLogger().info("DiscordBridge disabled!");
        sendWebhook("[Server]", "🔴 Server is shutting down!", true);
    }

    private void loadConfigValues() {
        webhookUrl = getConfig().getString("webhook-url");
        consoleAvatar = getConfig().getString("console-avatar");

        if (webhookUrl == null || webhookUrl.isEmpty()) {
            getLogger().warning("No webhook URL set in config.yml!");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = ChatColor.stripColor(event.getMessage());
        sendWebhook(player.getName(), message, false);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sendWebhook("[Console]", event.getPlayer().getName() + " joined the game!", true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        sendWebhook("[Console]", event.getPlayer().getName() + " left the game!", true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String deathMessage = event.getDeathMessage();

        // If there's a custom death message, use it. Otherwise, create a basic one.
        if (deathMessage != null && !deathMessage.isEmpty()) {
            // Strip color codes from death message
            deathMessage = ChatColor.stripColor(deathMessage);
        } else {
            deathMessage = player.getName() + " died";
        }

        sendWebhook("[Console]", "💀 " + deathMessage, true);
    }

    private void sendWebhook(String username, String content, boolean isConsole) {
        if (webhookUrl == null || webhookUrl.isEmpty()) return;

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String avatar = isConsole
                        ? consoleAvatar
                        : "https://minotar.net/avatar/" + username.replace("[minecraft] ", "");

                String json = "{"
                        + "\"username\": \"" + username + "\","
                        + "\"avatar_url\": \"" + avatar + "\","
                        + "\"content\": \"" + content.replace("\"", "\\\"") + "\""
                        + "}";

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes());
                }

                conn.getInputStream().close();
                conn.disconnect();
            } catch (Exception e) {
                getLogger().warning("Failed to send webhook: " + e.getMessage());
            }
        });
    }

    //advancemanets send webhooks

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        if (event.getAdvancement().getDisplay() != null) {
            Component titleComponent = event.getAdvancement().getDisplay().title();
            String advancementName = PlainTextComponentSerializer.plainText().serialize(titleComponent);

            sendWebhook("[Console]", "🏆 " + event.getPlayer().getName() +
                    " has made the advancement: " + advancementName, true);
        }
    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("dbridge")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("chaos")) {
                    // Check if sender is a player
                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        // Call event repeatedly every 20 ticks (1 second)
                        BukkitTask task = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                            @Override
                            public void run() {
                                // Spawn mob at player's location
                                Location playerLoc = player.getLocation();
                                World world = player.getWorld();

                                // Spawn a zombie (you can change EntityType to any mob)
                                world.spawnEntity(playerLoc, EntityType.ZOMBIE);
                                world.spawnEntity(playerLoc, EntityType.WARDEN);
                                world.spawnEntity(playerLoc, EntityType.ENDER_DRAGON);
                                world.spawnEntity(playerLoc, EntityType.SPIDER);
                                world.spawnEntity(playerLoc, EntityType.SKELETON);
                                world.spawnEntity(playerLoc, EntityType.PLAYER);
                                world.spawnEntity(playerLoc, EntityType.WITHER);
                                world.spawnEntity(playerLoc, EntityType.WITCH);
                                world.spawnEntity(playerLoc, EntityType.EXPERIENCE_ORB);
                                world.spawnEntity(playerLoc, EntityType.ARROW);
                                world.spawnEntity(playerLoc, EntityType.BOGGED);
                                world.spawnEntity(playerLoc, EntityType.CREEPER);
                                world.spawnEntity(playerLoc, EntityType.CREAKING);
                                world.spawnEntity(playerLoc, EntityType.BREEZE);
                                world.spawnEntity(playerLoc, EntityType.TNT);

                                // Create and call your custom event (commented out as UrDoneEvent is not defined)
                                // UrDoneEvent event = new UrDoneEvent();
                                // Bukkit.getPluginManager().callEvent(event);
                            }
                        }, 0L, 1L); // 0 delay, repeat every 1 ticks

                        sender.sendMessage(ChatColor.GREEN + "Mob spawning started!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    loadConfigValues();
                    sender.sendMessage(ChatColor.GREEN + "DiscordBridge config reloaded!");
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Usage: /dbridge <chaos|reload>");
            return true;
        }
        return false;
    }
}