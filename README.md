# DiscordBridge

**A lightweight Minecraft server plugin that seamlessly connects your server chat with Discord using webhooks.**

[![GitHub](https://img.shields.io/badge/GitHub-SaarasnhDx-181717?style=for-the-badge&logo=github)](https://github.com/SaarasnhDx)
[![YouTube](https://img.shields.io/badge/YouTube-saaransh__Xd-FF0000?style=for-the-badge&logo=youtube)](https://youtube.com/@saaransh_Xd)
[![Discord](https://img.shields.io/badge/Broken_SMP-Join_Server-7289DA?style=for-the-badge&logo=discord)](https://discord.gg/mHHYRg6cS9)

---

## Features
- **Real-time Chat Sync**: All player messages are instantly sent to your Discord channel
- **Player Activity Notifications**: Get notified when players join or leave the server  
- **Death Messages**: Player death events are broadcasted to Discord with full details
- **Player Avatars**: Automatic player head integration using Minotar
- **Console Messages**: Server events appear with a custom console avatar
- **Easy Configuration**: Simple YAML config file setup
- **Reload Command**: Update settings without restarting your server
- **Minecraft Prefix**: All messages are tagged with `[minecraft]` for easy identification

## Quick Setup
1. **Install the plugin** in your `plugins` folder
2. **Start your server** to generate the config file
3. **Edit** `/plugins/DiscordBridge/config.yml`:
   ```yaml
   webhook-url: "https://discord.com/api/webhooks/YOUR_WEBHOOK_URL"
   console-avatar: "https://your-console-avatar-url.png"
   ```
4. **Use** `/dbridge reload` to apply changes

## Commands
- `/dbridge reload` - Reload the plugin configuration

## What Gets Sent to Discord
- **Player Chat**: `[minecraft] PlayerName: Hello world!`
- **Player Joins**: `[minecraft] [Console]: PlayerName joined the game!`
- **Player Leaves**: `[minecraft] [Console]: PlayerName left the game!`
- **Player Deaths**: `[minecraft] [Console]: PlayerName was slain by Zombie`

## Creating a Discord Webhook
1. Go to your Discord server settings
2. Navigate to Integrations → Webhooks
3. Click "New Webhook"
4. Choose your channel and copy the webhook URL
5. Paste it into your config.yml

## Requirements
- **Minecraft Server**: 1.8+ (Bukkit/Spigot/Paper)
- **Java**: 8 or higher
- **Discord Webhook**: Required for functionality

## Configuration
```yaml
# Discord webhook URL (required)
webhook-url: "https://discord.com/api/webhooks/YOUR_WEBHOOK_HERE"
# Avatar URL for console messages (optional)
console-avatar: "https://example.com/console-icon.png"
```

## Perfect For
- **Community Servers**: Keep Discord members connected to in-game chat
- **Staff Monitoring**: Track player activity from Discord  
- **Death Tracking**: Monitor player deaths and dangerous areas
- **Cross-Platform Communication**: Bridge the gap between Minecraft and Discord
- **Server Administration**: Monitor your server without being in-game

## Pro Tips
- Use a dedicated Discord channel for server chat to avoid spam
- Set up proper webhook permissions in your Discord server
- Consider using a custom console avatar that matches your server theme
- The plugin automatically handles message escaping and special characters
- **Want to see it in action?** Join the **Broken SMP** - one of the best Minecraft servers using this plugin!

## Privacy & Security
- No data is stored or logged by this plugin
- Only sends public chat messages and join/leave events
- Uses Discord's official webhook API
- All communication is encrypted via HTTPS

## 📞 Support & Community
Having issues? Check that your webhook URL is correct and that the Discord channel permissions allow webhook messages.

### 🌟 Connect with the Developer
- **GitHub**: [github.com/SaarasnhDx](https://github.com/SaarasnhDx) - Source code, issues, and contributions
- **YouTube**: [youtube.com/@saaransh_Xd](https://youtube.com/@saaransh_Xd) - Tutorials and Minecraft content
- **Discord**: [Join Broken SMP](https://discord.gg/mHHYRg6cS9) - Experience the plugin in action on the best Minecraft server!

---
*Made with ❤️ for the Minecraft community by [SaarasnhDx](https://github.com/SaarasnhDx)*
