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
