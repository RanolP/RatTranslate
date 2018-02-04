package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.config.Configuration;
import io.github.ranolp.rattranslate.util.Util;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;
import java.util.Set;

public class BukkitConfiguration extends Configuration {
    private ConfigurationSection configuration;

    public BukkitConfiguration(ConfigurationSection configuration) {
        this.configuration = configuration;
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.ofNullable(configuration.get(key, null));
    }

    @Override
    public Optional<Configuration> getSection(String key) {
        return get(key).flatMap(Util.cast(ConfigurationSection.class)).map(BukkitConfiguration::new);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return configuration.getKeys(deep);
    }
}
