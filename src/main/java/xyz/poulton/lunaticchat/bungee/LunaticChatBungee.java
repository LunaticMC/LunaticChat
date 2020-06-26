package xyz.poulton.lunaticchat.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class LunaticChatBungee extends Plugin {
    private ChatFilter filter;
    @Override
    public void onEnable() {
        getProxy().registerChannel("BungeeCord");
        filter = new ChatFilter(loadFilterPatterns());
        getProxy().getPluginManager().registerListener(this, new BungeeMessageHandler(this));

    }

    public ChatFilter getFilter() {
        return filter;
    }

    public Pattern[] loadFilterPatterns() {
        try {
            if (!getDataFolder().exists()) getDataFolder().mkdir();

            File configFile = new File(getDataFolder(), "config.yml");
            if (configFile.createNewFile()) {
                InputStream defaultCfg = getResourceAsStream("bungeeconfig.yml");
                byte[] buffer = new byte[defaultCfg.available()];
                defaultCfg.read(buffer);
                new FileOutputStream(configFile).write(buffer);
                defaultCfg.close();
            }

            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            ArrayList<Pattern> exps = new ArrayList<>();
            HashMap<String, String> charsets = new HashMap<>();
            for (String set : config.getSection("charsets").getKeys()) {
                charsets.put(set, config.getString("charsets." + set));
            }
            for (String regex : config.getStringList("filter")) {
                for (String set : charsets.keySet()) {
                    regex = regex.replace(set, charsets.get(set));
                }
                exps.add(Pattern.compile(regex));
            }
            return exps.toArray(new Pattern[0]);

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void onDisable() {

    }
}
