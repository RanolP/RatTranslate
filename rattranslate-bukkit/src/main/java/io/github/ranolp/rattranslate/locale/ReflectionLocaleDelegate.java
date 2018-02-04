package io.github.ranolp.rattranslate.locale;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionLocaleDelegate implements LocaleDelegate {
    private Field locale;
    private Class<?> CraftPlayer;
    private Method getHandle;

    private ReflectionLocaleDelegate() {
    }

    private static final class SingletonHolder {
        private static final ReflectionLocaleDelegate INSTANCE = new ReflectionLocaleDelegate();
    }

    public static ReflectionLocaleDelegate getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String getLocale(Player player) {
        try {
            return (String) locale.get(getHandle.invoke(CraftPlayer.cast(player)));
        } catch (Exception ignore) {
            // unable to get locale
        }
        return null;
    }

    {
        String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
        try {
            locale = Class.forName("net.minecraft.server." + version + ".EntityPlayer").getDeclaredField("locale");
            locale.setAccessible(true);

            CraftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            getHandle = CraftPlayer.getMethod("getHandle");
        } catch (Exception exc) {
            // Incompatible minecraft version
        }
    }
}
