package com.SaaranshXd.discordbridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
    }

    @Override
    public void onDisable() {
        getLogger().info("DiscordBridge disabled!");
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

    // /dbridge reload command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("dbridge")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                loadConfigValues();
                sender.sendMessage(ChatColor.GREEN + "DiscordBridge config reloaded!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Usage: /dbridge reload");
            return true;
        }
        return false;
    }
}
// ur done

    // /dbridge reload command
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("dbridge")) {
        if (args.length == 1 && args[0].equalsIgnoreCase("lol")) {
            // Call event repeatedly every 20 ticks (1 second)
            BukkitTask task = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                @Override
                public void run() {
                    // Create and call your custom event
                    UrDoneEvent event = new UrDoneEvent();
                    Bukkit.getPluginManager().callEvent(event);
                }
            }, 0L, 20L); // 0 delay, repeat every 20 ticks
            
            // Store task to cancel later if needed
            // cancelTask = task;
            
            sender.sendMessage(ChatColor.GREEN + "ur done");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Usage: /dbridge lol");
        return true;
    }
    return false;
}
