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
