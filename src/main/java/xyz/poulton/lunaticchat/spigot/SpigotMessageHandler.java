package xyz.poulton.lunaticchat.spigot;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.poulton.lunaticchat.api.encode.ReturnMessageEncoder;
import xyz.poulton.lunaticchat.api.message.ReturnMessage;

import static xyz.poulton.lunaticchat.api.ComponentUtils.componentToLegacy;

public class SpigotMessageHandler implements PluginMessageListener {
    private LunaticChatSpigot plugin;
    public SpigotMessageHandler(LunaticChatSpigot plugin) {
        this.plugin = plugin;
    }



    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput( message );
        if (!in.readUTF().equals("LunaticChat")) return;
        String action = in.readUTF();
        if (action.equals("Return")) {
            ReturnMessage parsedMessage = new ReturnMessageEncoder(null).decodeMessage(in);
            plugin.getLogger().info(componentToLegacy(parsedMessage.message));
        }
    }
}
