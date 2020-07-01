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

package xyz.poulton.lunaticchat.api;

import net.md_5.bungee.api.chat.BaseComponent;

public class ComponentUtils {
    public static String componentToPlain(BaseComponent[] in) {
        StringBuilder out = new StringBuilder();
        for (BaseComponent component : in) {
            out.append(component.toPlainText());
        }
        return out.toString();
    }

    public static String componentToLegacy(BaseComponent[] in) {
        StringBuilder out = new StringBuilder();
        for (BaseComponent component : in) {
            out.append(component.toLegacyText());
        }
        return out.toString();
    }
}
