package io.github.ranolp.rattranslate;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import io.github.ranolp.rattranslate.abstraction.Platform;
import io.github.ranolp.rattranslate.command.Command;
import io.github.ranolp.rattranslate.command.LanguageCommand;
import io.github.ranolp.rattranslate.command.RatTranslateCommand;
import io.github.ranolp.rattranslate.config.Configuration;
import io.github.ranolp.rattranslate.event.command.CommandIssueEvent;
import io.github.ranolp.rattranslate.event.PlatformDisableEvent;
import io.github.ranolp.rattranslate.event.PlatformReadyEvent;
import io.github.ranolp.rattranslate.lang.Lang;
import io.github.ranolp.rattranslate.lang.LangStorage;
import io.github.ranolp.rattranslate.lang.Variable;
import io.github.ranolp.rattranslate.translator.CompoundTranslator;
import io.github.ranolp.rattranslate.translator.NopTranslator;
import io.github.ranolp.rattranslate.translator.Translator;
import io.github.ranolp.rattranslate.translator.Translators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatTranslate {
    // todo: load from config
    private CompoundTranslator translator;
    private Platform platform;
    private EventBus eventBus = new EventBus((exception, context) -> exception.printStackTrace());
    private LangStorage langStorage = new LangStorage();
    private Locale serverLocale = Locale.AMERICAN_ENGLISH;

    private Map<String, Command> commandMap = new HashMap<>();

    private static final class SingletonHolder {
        private static final RatTranslate INSTANCE = new RatTranslate();
    }

    {
        RatTranslateCommand ratTranslate = new RatTranslateCommand();
        commandMap.put("rattranslate", ratTranslate);
        commandMap.put("rattr", ratTranslate);
        commandMap.put("rtr", ratTranslate);

        LanguageCommand language = new LanguageCommand();
        commandMap.put("language", language);
        commandMap.put("lang", language);

        getEventBus().register(new ChatHandler());
        getEventBus().register(this);
    }

    /**
     * Get the RatTranslate instance.
     *
     * @return the RatTranslate instance
     */
    public static RatTranslate getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Get the translator from which translate the sentences.
     *
     * @return the translator
     */
    public Translator getTranslator() {
        return translator;
    }

    /**
     * Get the configuration.
     *
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return platform.getConfiguration();
    }

    /**
     * Get the platform.
     *
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }


    /**
     * Get the RatTranslate's event bus.
     *
     * @return the event bus
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    @Subscribe
    public void onPlatformReady(PlatformReadyEvent e) {
        if (this.platform != null) {
            // platform already initialized
            return;
        }
        this.platform = e.getPlatform();
        reload();
        platform.sendMessage(langStorage, "server.platform.ready",
                             Variable.ofAny("platform", "name", platform.getName()),
                             Variable.ofAny("platform", "version", platform.getVersion()),
                             Variable.ofAny("game", "version", platform.getGameVersion()));
        if (platform.isJsonMessageAvailable()) {
            platform.sendMessage(langStorage, "server.json.ok");
        } else {
            platform.sendMessage(langStorage, "server.json.no");
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onPlatformDisable(PlatformDisableEvent e) {
        platform.save();
        platform.sendMessage(langStorage, "server.platform.disable",
                             Variable.ofAny("platform", "name", platform.getName()),
                             Variable.ofAny("platform", "version", platform.getVersion()),
                             Variable.ofAny("game", "version", platform.getGameVersion()));
    }

    @Subscribe
    @AllowConcurrentEvents
    public void onCommandReceived(CommandIssueEvent e) {
        if (commandMap.containsKey(e.getLabel())) {
            commandMap.get(e.getLabel()).onCommand(e.getPlayer(), e.getLabel(), e.getArgs());
        }
    }

    public void reload() {
        platform.reload();
        loadFromConfiguration(platform.getConfiguration());
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

    private void loadFromConfiguration(Configuration section) {
        List<Translator> translators = new ArrayList<>();
        serverLocale = section.getString("language").map(Locale::getByCode).orElse(Locale.AMERICAN_ENGLISH);
        section.getSection("translator").ifPresent(translatorSection -> {
            for (String key : translatorSection.getKeys(false)) {
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
            platform.sendMessage(langStorage, "server.no-translator");
        }

        translators.add(NopTranslator.getInstance());

        translator = new CompoundTranslator(translators);
    }
}
