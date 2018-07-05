package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.util.Util;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;
import java.util.Set;

public class BukkitConfiguration {
    private ConfigurationSection configuration;

    public BukkitConfiguration(ConfigurationSection configuration) {
        this.configuration = configuration;
    }

    public Optional<Boolean> getBoolean(String key) {
        return get(key).flatMap(Util.cast(Boolean.class));
    }

    public Optional<Character> getCharacter(String key) {
        return get(key).flatMap(Util.cast(Character.class));
    }

    public Optional<Integer> getInt(String key) {
        return get(key).flatMap(Util.cast(Integer.class));
    }

    public Optional<Long> getLong(String key) {
        return get(key).flatMap(Util.cast(Long.class));
    }

    public Optional<Double> getDouble(String key) {
        return get(key).flatMap(Util.cast(Double.class));
    }

    public Optional<String> getString(String key) {
        return get(key).flatMap(Util.cast(String.class));
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(configuration.get(key, null));
    }

    public Optional<BukkitConfiguration> getSection(String key) {
        return get(key).flatMap(Util.cast(ConfigurationSection.class)).map(BukkitConfiguration::new);
    }

    public Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }
}
