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
