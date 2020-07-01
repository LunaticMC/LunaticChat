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
        message = message.replace("\\","\\\\");
        message = message.replace("\"", "\\\"");
        String parsed = PlaceholderAPI.setPlaceholders(sender, template).replace("{MESSAGE}", message);
        return ComponentSerializer.parse(parsed);
    }
}
