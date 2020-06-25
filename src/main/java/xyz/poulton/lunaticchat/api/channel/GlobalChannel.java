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
