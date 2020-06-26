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
