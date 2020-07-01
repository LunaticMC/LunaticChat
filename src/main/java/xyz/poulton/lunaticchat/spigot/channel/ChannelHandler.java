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

package xyz.poulton.lunaticchat.spigot.channel;

import org.bukkit.entity.Player;
import xyz.poulton.lunaticchat.api.channel.Channel;
import xyz.poulton.lunaticchat.api.channel.GlobalChannel;
import xyz.poulton.lunaticchat.api.channel.LocalChannel;
import xyz.poulton.lunaticchat.api.channel.StaffChannel;
import xyz.poulton.lunaticchat.api.format.ChatFormat;

import java.util.HashMap;
import java.util.UUID;

public class ChannelHandler {
    private LocalChannel localChannel;
    private GlobalChannel globalChannel;
    private StaffChannel staffChannel;

    private final HashMap<UUID, String> channelMap = new HashMap<UUID, String>();

    public void initFormats(ChatFormat localFormat, ChatFormat globalFormat, ChatFormat  staffFormat) {
        localChannel = new LocalChannel(localFormat);
        globalChannel = new GlobalChannel(globalFormat);
        staffChannel = new StaffChannel(staffFormat);
    }

    public Channel getPlayerChannel(Player player) {
        String channel = channelMap.get(player.getUniqueId());
        if (channel == null) return localChannel;
        else if (channel.equals(globalChannel.getName())) return globalChannel;
        else if (channel.equals(staffChannel.getName())) return staffChannel;
        return localChannel;
    }

    public void setPlayerChannel(Player player, String channel) {
        channelMap.remove(player.getUniqueId());
        if (channel.equals(globalChannel.getName())) channelMap.put(player.getUniqueId(), globalChannel.getName());
        else if (channel.equals(staffChannel.getName())) channelMap.put(player.getUniqueId(), staffChannel.getName());
        else channelMap.put(player.getUniqueId(), localChannel.getName());
    }
}
