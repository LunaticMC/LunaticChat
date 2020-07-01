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
        initFilter();
        getProxy().getPluginManager().registerListener(this, new BungeeMessageHandler(this));
        getProxy().getPluginManager().registerCommand(this, new BungeeReloadCommand(this));

    }

    public void initFilter() {
        filter = new ChatFilter(loadFilterPatterns());
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
                FileOutputStream out = new FileOutputStream(configFile);
                out.write(buffer);
                out.close();
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDisable() {

    }
}
