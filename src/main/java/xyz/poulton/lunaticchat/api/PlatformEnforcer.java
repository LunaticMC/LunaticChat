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

public class PlatformEnforcer {
    public static void enforceBukkit() {
        try {
            Class.forName("org.bukkit.Bukkit");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("This method can only be called from Bukkit");
        }
    }

    public static void enforceBungee() {
        try {
            Class.forName("net.md_5.bungee.api.plugin.Plugin");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("This method can only be called from BungeeCord");
        }
    }
}
