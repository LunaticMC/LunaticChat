// Copyright Lucy Poulton 2020. All rights reserved.
package xyz.poulton.lunaticchat.spigot;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.poulton.lunaticchat.api.channel.Channel;
import xyz.poulton.lunaticchat.api.format.ChatFormat;
import xyz.poulton.lunaticchat.spigot.channel.ChannelHandler;
import xyz.poulton.lunaticchat.spigot.command.ChannelCommand;
import xyz.poulton.lunaticchat.spigot.command.ReloadCommand;

public final class LunaticChatSpigot extends JavaPlugin implements Listener {
    private final ChannelHandler handler = new ChannelHandler();
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        saveDefaultConfig();
        loadFormats();

        this.getCommand("channel").setExecutor(new ChannelCommand(this));
        this.getCommand("reloadchat").setExecutor(new ReloadCommand(this));
    }

    public void loadFormats() {
        reloadConfig();
        ChatFormat localFormat = new ChatFormat(getConfig().getStringList("channelFormats.local").toArray(new String[0]));
        ChatFormat globalFormat = new ChatFormat(getConfig().getStringList("channelFormats.global").toArray(new String[0]));
        ChatFormat staffFormat = new ChatFormat(getConfig().getStringList("channelFormats.staff").toArray(new String[0]));
        handler.initFormats(localFormat, globalFormat, staffFormat);
    }

    @Override
    public void onDisable() {
    }

    public ChannelHandler getChannelHandler() { return handler; }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(AsyncPlayerChatEvent e) {
        if (e.getPlayer().hasPermission("lunaticchat.format"))
            e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        e.setCancelled(true);
        Channel channel = handler.getPlayerChannel(e.getPlayer());
        channel.sendMessage(e.getPlayer(), e.getMessage(), this);
    }
}
