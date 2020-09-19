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

package xyz.poulton.lunaticchat.spigot.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.poulton.lunaticchat.api.encode.PrivateMessageEncoder;
import xyz.poulton.lunaticchat.api.format.PrivateFormat;
import xyz.poulton.lunaticchat.api.message.PrivateMessage;
import xyz.poulton.lunaticchat.spigot.LunaticChatSpigot;

public class MessageCommand implements CommandExecutor {
    private final LunaticChatSpigot plugin;
    private PrivateFormat format;

    public MessageCommand(LunaticChatSpigot plugin, PrivateFormat format) {
        this.plugin = plugin;
        this.format = format;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player");
            return true;
        }
        if (args.length < 2) return false;

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        PrivateMessage pm = new PrivateMessage();
        pm.target = args[0];
        pm.sender = ((Player) sender).getUniqueId();
        pm.message = format.resolve((Player)sender, args[0], message.toString());

        byte[] encoded = new PrivateMessageEncoder(pm).encodeMessage();
        ((Player) sender).sendPluginMessage(plugin, "BungeeCord", encoded);
        return true;
    }
}
