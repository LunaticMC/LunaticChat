package xyz.poulton.lunaticchat.api.channel;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.OfflinePlayer;
import xyz.poulton.lunaticchat.api.PlatformEnforcer;
import xyz.poulton.lunaticchat.api.encode.ChatMessageEncoder;
import xyz.poulton.lunaticchat.api.format.ChatFormat;
import xyz.poulton.lunaticchat.api.message.ChatMessage;
import xyz.poulton.lunaticchat.bungee.LunaticChatBungee;
import xyz.poulton.lunaticchat.spigot.LunaticChatSpigot;

public abstract class Channel {
    protected ChatFormat format;
    public ChatFormat getFormat() {
        return format;
    }
    public abstract String getName();
    abstract String[] getAliases();

    public Channel() {
        PlatformEnforcer.enforceBungee();
    }

    public Channel(ChatFormat format) {
        PlatformEnforcer.enforceBukkit();
        this.format = format;
    }

    public ProxiedPlayer[] getTargets(LunaticChatBungee pl, ProxiedPlayer sender) {
        PlatformEnforcer.enforceBungee();
        return null;
    }

    public void sendMessage(OfflinePlayer sender, String message, LunaticChatSpigot pl) {
        PlatformEnforcer.enforceBukkit();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.message = format.resolve(sender.getPlayer(), message);
        chatMessage.channel = getName();
        chatMessage.sender = sender.getUniqueId();
        chatMessage.senderUsername = sender.getName();

        byte[] encoded = new ChatMessageEncoder(chatMessage).encodeMessage();
        sender.getPlayer().sendPluginMessage(pl, "BungeeCord", encoded);
    }
}
