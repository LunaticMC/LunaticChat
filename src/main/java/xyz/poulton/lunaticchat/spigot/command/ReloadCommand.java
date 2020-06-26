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