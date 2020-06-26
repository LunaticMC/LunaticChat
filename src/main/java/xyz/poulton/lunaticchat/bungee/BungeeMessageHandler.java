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
import xyz.poulton.lunaticchat.api.encode.ReturnMessageEncoder;
import xyz.poulton.lunaticchat.api.message.ChatMessage;
import xyz.poulton.lunaticchat.api.message.ReturnMessage;

import static xyz.poulton.lunaticchat.api.ComponentUtils.componentToLegacy;
import static xyz.poulton.lunaticchat.api.ComponentUtils.componentToPlain;

public class BungeeMessageHandler implements Listener {
    private final GlobalChannel global = new GlobalChannel();
    private final LocalChannel local = new LocalChannel();
    private final StaffChannel staff = new StaffChannel();
    private ChatFilter filter;
    private final LunaticChatBungee plugin;

    public BungeeMessageHandler(LunaticChatBungee plugin) {
        this.plugin = plugin;
        filter = plugin.getFilter();
    }



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

            if (!filter.check(componentToPlain(parsedMessage.message))) {
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
        }
    }
}
