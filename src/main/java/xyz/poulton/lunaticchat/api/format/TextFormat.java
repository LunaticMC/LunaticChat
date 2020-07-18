package xyz.poulton.lunaticchat.api.format;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

public abstract class TextFormat {
    protected String template;

    public TextFormat(String[] templates) {
        StringBuilder completeFormat = new StringBuilder("[");
        for (String original : templates) {
            if (original.startsWith("{") && original.endsWith("}")) completeFormat.append(original);
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

    protected String escape(String message) {
        message = message.replace("\\","\\\\");
        return message.replace("\"", "\\\"");
    }

    public BaseComponent[] resolve(Player sender, String message) {
        return resolve(sender, null, message);
    }
    public abstract BaseComponent[] resolve(Player sender, String target, String message);
}
