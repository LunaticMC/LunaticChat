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

package xyz.poulton.lunaticchat.bungee;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.poulton.lunaticchat.api.channel.Channel;
import xyz.poulton.lunaticchat.api.channel.GlobalChannel;
import xyz.poulton.lunaticchat.api.channel.LocalChannel;
import xyz.poulton.lunaticchat.api.channel.StaffChannel;
import xyz.poulton.lunaticchat.api.encode.ChatMessageEncoder;
import xyz.poulton.lunaticchat.api.encode.PrivateMessageEncoder;
import xyz.poulton.lunaticchat.api.encode.ReplyMessageEncoder;
import xyz.poulton.lunaticchat.api.encode.ReturnMessageEncoder;
import xyz.poulton.lunaticchat.api.message.ChatMessage;
import xyz.poulton.lunaticchat.api.message.PrivateMessage;
import xyz.poulton.lunaticchat.api.message.ReplyMessage;
import xyz.poulton.lunaticchat.api.message.ReturnMessage;

import java.util.HashMap;
import java.util.UUID;

import static xyz.poulton.lunaticchat.api.ComponentUtils.componentToLegacy;
import static xyz.poulton.lunaticchat.api.ComponentUtils.componentToPlain;

public class BungeeMessageHandler implements Listener {
    private final GlobalChannel global = new GlobalChannel();
    private final LocalChannel local = new LocalChannel();
    private final StaffChannel staff = new StaffChannel();
    private final LunaticChatBungee plugin;

    public BungeeMessageHandler(LunaticChatBungee plugin) {
        this.plugin = plugin;
    }

    private HashMap<UUID, UUID> lastMessageMap = new HashMap<>();

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        if (!event.getTag().equals("BungeeCord")) return;
        if (!in.readUTF().equals("LunaticChat")) return;
        String action = in.readUTF();

        if (action.equals("Message")) {
            ChatMessage parsedMessage = new ChatMessageEncoder(null).decodeMessage(in);
            Channel channel;
            if (parsedMessage.channel.equals(staff.getName())) {
                channel = staff;
            } else if (parsedMessage.channel.equals(global.getName())) {
                channel = global;
            } else {
                channel = local;
            }

            if (!plugin.getFilter().check(componentToPlain(parsedMessage.message))) {
                ((ProxiedPlayer) event.getReceiver()).sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Please don't say that!"));
            } else {
                for (ProxiedPlayer p : channel.getTargets(plugin, (ProxiedPlayer) event.getReceiver())) {
                    p.sendMessage(ChatMessageType.CHAT, parsedMessage.message);
                }
                if (channel == local) {
                    ReturnMessage msg = new ReturnMessage();
                    msg.message = parsedMessage.message;
                    byte[] returnMessage = new ReturnMessageEncoder(msg).encodeMessage();
                    ((ProxiedPlayer) event.getReceiver()).getServer().getInfo().sendData("BungeeCord", returnMessage);
                } else {
                    plugin.getProxy().getLogger().info(componentToLegacy(parsedMessage.message));
                }
            }
        } else if (action.equals("Private")) {
            PrivateMessage parsedMessage = new PrivateMessageEncoder(null).decodeMessage(in);
            if (!plugin.getFilter().check(componentToPlain(parsedMessage.message))) {
                ((ProxiedPlayer) event.getReceiver()).sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Please don't say that!"));
            } else {
                ProxiedPlayer sender = plugin.getProxy().getPlayer(parsedMessage.sender);
                ProxiedPlayer target = plugin.getProxy().getPlayer(parsedMessage.target);
                if (target == null) {
                    ((ProxiedPlayer) event.getReceiver()).sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "That player isn't online."));
                    return;
                }
                sender.sendMessage(parsedMessage.message);
                target.sendMessage(parsedMessage.message);
                lastMessageMap.put(sender.getUniqueId(), target.getUniqueId());
                lastMessageMap.put(target.getUniqueId(), sender.getUniqueId());
                plugin.getProxy().getLogger().info(componentToLegacy(parsedMessage.message));
            }
        } else if (action.equals("Reply")) {
            ReplyMessage parsedMessage = new ReplyMessageEncoder(null).decodeMessage(in);
            UUID targetId = lastMessageMap.get(parsedMessage.sender);
            if (targetId == null) {
                ((ProxiedPlayer) event.getReceiver()).sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "You haven't been messaged recently."));
                return;
            }
            ProxiedPlayer sender = plugin.getProxy().getPlayer(parsedMessage.sender);
            ProxiedPlayer target = plugin.getProxy().getPlayer(targetId);

            if (target == null) {
                ((ProxiedPlayer) event.getReceiver()).sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "That player is no longer online."));
                return;
            }

            sender.sendMessage(parsedMessage.message);
            target.sendMessage(parsedMessage.message);
            lastMessageMap.put(sender.getUniqueId(), target.getUniqueId());
            lastMessageMap.put(target.getUniqueId(), sender.getUniqueId());
            plugin.getProxy().getLogger().info(componentToLegacy(parsedMessage.message));
        }
    }
}
