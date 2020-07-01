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
import xyz.poulton.lunaticchat.spigot.LunaticChatSpigot;


public class ReloadCommand implements CommandExecutor {
    private final LunaticChatSpigot plugin;
    public ReloadCommand(LunaticChatSpigot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("lunaticchat.reload")) {
            try {
                plugin.loadFormats();
                sender.sendMessage(ChatColor.GREEN + "Chat reloaded");
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "An error occurred whilst reloading the chat config. See the console for more info");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "No permission");
        }
        return true;
    }
}