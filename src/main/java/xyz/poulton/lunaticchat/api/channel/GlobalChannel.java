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

import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.poulton.lunaticchat.api.format.ChatFormat;
import xyz.poulton.lunaticchat.bungee.LunaticChatBungee;

public class GlobalChannel extends Channel {

    public GlobalChannel(ChatFormat format) {
        super(format);
    }
    public GlobalChannel() {
        super();
    }

    @Override
    public String getName() {
        return "global";
    }
    public String[] getAliases() {
        return new String[] {"g", "network", "n"};
    }

    @Override
    public ProxiedPlayer[] getTargets(LunaticChatBungee pl, ProxiedPlayer sender) {
        super.getTargets(pl, sender);
        return pl.getProxy().getPlayers().toArray(new ProxiedPlayer[0]);
    }
}
