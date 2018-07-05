package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.abstraction.MessageReceiver;
import io.github.ranolp.rattranslate.command.LanguageCommand;
import io.github.ranolp.rattranslate.command.RatTranslateCommand;
import io.github.ranolp.rattranslate.lang.Lang;
import io.github.ranolp.rattranslate.lang.LangLoader;
import io.github.ranolp.rattranslate.lang.LangStorage;
import io.github.ranolp.rattranslate.lang.Variable;
import io.github.ranolp.rattranslate.translator.CompoundTranslator;
import io.github.ranolp.rattranslate.translator.NopTranslator;
import io.github.ranolp.rattranslate.translator.Translator;
import io.github.ranolp.rattranslate.translator.Translators;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class RatTranslate extends JavaPlugin implements MessageReceiver {
    private final File dataFile = new File(getDataFolder(), "data.yml");
    private CompoundTranslator translator;
    private Locale serverLocale = Locale.AMERICAN_ENGLISH;
    private LangStorage langStorage = new LangStorage();
    private BukkitConfiguration configurationSection;
    // todo: how to get?
    private boolean jsonMessageAvailable = true;
    private YamlConfiguration dataConfiguration;

    /**
     * Get the RatTranslate instance.
     *
     * @return the RatTranslate instance
     */
    public static RatTranslate getInstance() {
        return getPlugin(RatTranslate.class);
    }

    @Override
    public void onEnable() {
        // noinspection ResultOfMethodCallIgnored
        getDataFolder().mkdirs();

        saveDefaultConfig();

        for (Locale locale: Locale.values()) {
            if (getResource("lang_" + locale.getCode() + ".properties") != null) {
                if (!new File("lang_" + locale.getCode() + ".lang").exists()) {
                    saveResource("lang_" + locale.getCode() + ".properties", true);
                }
                registerLang(LangLoader.load(new File(getDataFolder(), "lang_" + locale.getCode() + ".properties"),
                        locale));
            }
        }

        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

        getCommand("rattranslate").setExecutor(new RatTranslateCommand());
        getCommand("language").setExecutor(new LanguageCommand());

        // ok, it's ready!
        reload();
        sendMessage(langStorage,
                "server.platform.ready",
                Variable.ofAny("platform", "version", getVersion()),
                Variable.ofAny("game", "version", getGameVersion()));
        if (isJsonMessageAvailable()) {
            sendMessage(langStorage, "server.json.ok");
        } else {
            sendMessage(langStorage, "server.json.no");
        }
    }

    @Override
    public void onDisable() {
        save();
        sendMessage(langStorage,
                "server.platform.disable",
                Variable.ofAny("platform", "version", getVersion()),
                Variable.ofAny("game", "version", getGameVersion()));
    }

    /**
     * Get the translator from which translate the sentences.
     *
     * @return the translator
     */
    public Translator getTranslator() {
        return translator;
    }

    public void reload() {
        reloadConfig();
        configurationSection = new BukkitConfiguration(getConfig());
        RatPlayer.PLAYER_MAP.clear();
        dataConfiguration = YamlConfiguration.loadConfiguration(dataFile);
        if (dataConfiguration.isList("players")) {
            // Deserialize on method call
            dataConfiguration.getList("players");
        }

        loadFromConfiguration(configurationSection);
    }

    public void registerLang(Lang lang) {
        langStorage.addLang(lang);
    }

    public LangStorage getLangStorage() {
        return langStorage;
    }

    public Locale getServerLocale() {
        return serverLocale;
    }

    private void loadFromConfiguration(BukkitConfiguration section) {
        List<Translator> translators = new ArrayList<>();
        serverLocale = section.getString("language").map(Locale::getByCode).orElse(Locale.AMERICAN_ENGLISH);
        section.getSection("translator").ifPresent(translatorSection -> {
            for (String key: translatorSection.getKeys(false)) {
                translatorSection.getSection(key).ifPresent(configuration -> {
                    if (configuration.getBoolean("use").orElse(true)) {
                        Translators.get(key).ifPresent(translator -> {
                            translator.applyConfiguration(configuration);
                            translators.add(translator);
                        });
                    }
                });
            }
        });
        if (translators.isEmpty()) {
            sendMessage(langStorage, "server.no-translator");
        }

        translators.add(NopTranslator.getInstance());

        translator = new CompoundTranslator(translators);
    }

    public boolean isJsonMessageAvailable() {
        return jsonMessageAvailable;
    }

    public String getVersion() {
        return getDescription().getVersion();
    }

    public String getGameVersion() {
        return Bukkit.getBukkitVersion();
    }

    @Override
    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public boolean canReceiveMessage() {
        return true;
    }

    public void save() {
        dataConfiguration.set("players", new ArrayList<>(RatPlayer.PLAYER_MAP.values()));
        try {
            dataConfiguration.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Locale getLocale() {
        return RatTranslate.getInstance().getServerLocale();
    }
}
