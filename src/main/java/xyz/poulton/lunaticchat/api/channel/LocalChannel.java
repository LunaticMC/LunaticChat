package xyz.poulton.lunaticchat.api.channel;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.poulton.lunaticchat.api.format.ChatFormat;
import xyz.poulton.lunaticchat.bungee.LunaticChatBungee;

import java.util.ArrayList;

public class LocalChannel extends Channel {

    public LocalChannel(ChatFormat format) {
        super(format);
    }
    public LocalChannel() {
        super();
    }

    @Override
    public String getName() {
        return "local";
    }
    public String[] getAliases() {
        return new String[] {"l", "server"};
    }

    @Override
    public ProxiedPlayer[] getTargets(LunaticChatBungee pl, ProxiedPlayer sender) {
        super.getTargets(pl, sender);
        sender.getServer().getInfo().getName();
        ArrayList<ProxiedPlayer> players = new ArrayList<>();
        pl.getProxy().getPlayers().forEach(p -> {
            if (p.getServer().getInfo().getName().equals(sender.getServer().getInfo().getName())) players.add(p);
        });
        return players.toArray(new ProxiedPlayer[0]);
    }
}
