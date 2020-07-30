//    Copyright © Lucy Poulton 2020.
//    This file is part of LunaticChat.
//
//    LunaticChat is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    LunaticChat is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with LunaticChat. If not, see <https://www.gnu.org/licenses/>.

package xyz.poulton.lunaticchat.spigot;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.poulton.lunaticchat.api.channel.Channel;
import xyz.poulton.lunaticchat.api.format.ChatFormat;
import xyz.poulton.lunaticchat.api.format.PrivateFormat;
import xyz.poulton.lunaticchat.spigot.SpigotMessageHandler;
import xyz.poulton.lunaticchat.spigot.channel.ChannelHandler;
import xyz.poulton.lunaticchat.spigot.command.ChannelCommand;
import xyz.poulton.lunaticchat.spigot.command.MessageCommand;
import xyz.poulton.lunaticchat.spigot.command.ReloadCommand;
import xyz.poulton.lunaticchat.spigot.command.ReplyCommand;

public final class LunaticChatSpigot extends JavaPlugin implements Listener {
    private final ChannelHandler handler = new ChannelHandler();
    final String prefixes = "#";

    public LunaticChatSpigot() {
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new SpigotMessageHandler(this));
        this.saveDefaultConfig();
        this.loadFormats();
        this.getCommand("channel").setExecutor(new ChannelCommand(this));
        this.getCommand("reloadchat").setExecutor(new ReloadCommand(this));
        this.getCommand("reloadchat").setExecutor(new ReloadCommand(this));
    }


    public void loadFormats() {
        this.reloadConfig();
        ChatFormat localFormat = new ChatFormat((String[])this.getConfig().getStringList("channelFormats.local").toArray(new String[0]));
        ChatFormat globalFormat = new ChatFormat((String[])this.getConfig().getStringList("channelFormats.global").toArray(new String[0]));
        ChatFormat staffFormat = new ChatFormat((String[])this.getConfig().getStringList("channelFormats.staff").toArray(new String[0]));
        this.handler.initFormats(localFormat, globalFormat, staffFormat);
        PrivateFormat privateFormat = new PrivateFormat((String[])this.getConfig().getStringList("privateMessage").toArray(new String[0]));
        this.getCommand("message").setExecutor(new MessageCommand(this, privateFormat));
        this.getCommand("reply").setExecutor(new ReplyCommand(this, privateFormat));
    }

    public void onDisable() {
    }

    public ChannelHandler getChannelHandler() {
        return this.handler;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void on(AsyncPlayerChatEvent e) {
        if (e.getPlayer().hasPermission("lunaticchat.format")) {
            e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        }
        Player player = (Player) e.getPlayer();
        if (e.getMessage().startsWith(this.prefixes)) {
            if (player.hasPermission("lunaticchat.staff")) {
                String originalChannel = String.valueOf(this.handler.getPlayerChannel(e.getPlayer()));
                this.getChannelHandler().setPlayerChannel(player, "staff");
                e.setMessage(e.getMessage().replace(this.prefixes, ""));
                e.setCancelled(true);
                Channel channel = this.handler.getPlayerChannel(e.getPlayer());
                channel.sendMessage(e.getPlayer(), e.getMessage(), this);
                this.handler.setPlayerChannel(player, originalChannel);
                return;

            }
        }

        e.setCancelled(true);
        Channel channel = this.handler.getPlayerChannel(e.getPlayer());
        channel.sendMessage(e.getPlayer(), e.getMessage(), this);
    }
}

