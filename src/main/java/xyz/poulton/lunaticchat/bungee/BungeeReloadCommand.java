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

package xyz.poulton.lunaticchat.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.ChatColor;

public class BungeeReloadCommand extends Command {
    private LunaticChatBungee plugin;
    public BungeeReloadCommand(LunaticChatBungee pl) {
        super("brch");
        plugin = pl;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("lunaticchat.reload")) {
            sender.sendMessage(ChatColor.RED + "No permission");
        } else {
            plugin.initFilter();
            sender.sendMessage(ChatColor.GREEN + "Chat reloaded");
        }
    }
}
