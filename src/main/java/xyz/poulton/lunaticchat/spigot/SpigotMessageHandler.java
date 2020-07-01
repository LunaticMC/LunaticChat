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
