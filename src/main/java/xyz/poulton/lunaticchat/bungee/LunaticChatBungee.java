package xyz.poulton.lunaticchat.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class LunaticChatBungee extends Plugin {
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new BungeeMessageHandler(this));

    }
    @Override
    public void onDisable() {

    }
}
