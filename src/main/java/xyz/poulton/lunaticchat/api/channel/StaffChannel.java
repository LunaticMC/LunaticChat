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
