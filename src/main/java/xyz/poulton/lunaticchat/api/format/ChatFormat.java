package xyz.poulton.lunaticchat.api.format;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

public class ChatFormat {
    public String template;

    public ChatFormat(String[] templates) {
        StringBuilder completeFormat = new StringBuilder("[");
        for (String original : templates) {
            if (original.equals("{MESSAGE}")) completeFormat.append(original);
            else {
                ComponentSerializer.parse(original); // this will throw an exception if the formatting is bad
                if (original.startsWith("[")) {
                    original = original.substring(1, original.length() - 2);
                }
                completeFormat.append(original).append(",");
            }
        }
        completeFormat.setLength(completeFormat.length() - 1);
        completeFormat.append("]");
        template = completeFormat.toString();
    }

    public BaseComponent[] resolve(Player sender, String message) {
        String parsed = PlaceholderAPI.setPlaceholders(sender, template).replace("{MESSAGE}", message);
        return ComponentSerializer.parse(parsed);
    }

    public BaseComponent[] resolve(Player sender, BaseComponent[] message) {
        return resolve(sender, message);
    }
}
