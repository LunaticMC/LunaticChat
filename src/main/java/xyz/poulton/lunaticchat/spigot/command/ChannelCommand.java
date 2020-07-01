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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.poulton.lunaticchat.spigot.LunaticChatSpigot;

import java.util.Arrays;
import java.util.List;

public class ChannelCommand implements CommandExecutor {
    private final LunaticChatSpigot plugin;
    final List<String> globalNames = Arrays.asList("global", "g", "network", "n");
    final List<String> localNames = Arrays.asList("local", "l", "server", "s");
    final List<String> staffNames = Arrays.asList("staff", "st", "s");

    public ChannelCommand(LunaticChatSpigot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player");
            return true;
        }
        if (args.length != 1) return false;
        if (localNames.contains(args[0])) {
            plugin.getChannelHandler().setPlayerChannel((Player) sender,"local");
            sender.sendMessage(ChatColor.GREEN + "Channel set to local");
        } else if (globalNames.contains(args[0])) {
            plugin.getChannelHandler().setPlayerChannel((Player) sender,"global");
            sender.sendMessage(ChatColor.GREEN + "Channel set to global");
        } else if (staffNames.contains(args[0])) {
            if (sender.hasPermission("lunaticchat.staff")) {
                plugin.getChannelHandler().setPlayerChannel((Player) sender,"staff");
                sender.sendMessage(ChatColor.GREEN + "Channel set to staff");
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this channel");
            }
        } else return false;
        return true;
    }
}
