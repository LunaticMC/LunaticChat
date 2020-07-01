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

package xyz.poulton.lunaticchat.api.channel;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.poulton.lunaticchat.api.PlatformEnforcer;
import xyz.poulton.lunaticchat.api.format.ChatFormat;
import xyz.poulton.lunaticchat.bungee.LunaticChatBungee;

import java.util.ArrayList;

public class StaffChannel extends Channel {

    public StaffChannel(ChatFormat format) {
        super(format);
    }
    public StaffChannel() {
        super();
    }

    @Override
    public String getName() {
        return "staff";
    }
    public String[] getAliases() {
        return new String[] {"st", "s"};
    }


    @Override
    public ProxiedPlayer[] getTargets(LunaticChatBungee pl, ProxiedPlayer sender) {
        super.getTargets(pl, sender);
        sender.getServer().getInfo().getName();
        ArrayList<ProxiedPlayer> players = new ArrayList<>();
        pl.getProxy().getPlayers().forEach(p -> {
            if (p.hasPermission("lunaticchat.staff")) players.add(p);
        });
        return players.toArray(new ProxiedPlayer[0]);
    }
}
