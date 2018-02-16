package io.github.ranolp.rattranslate.config;

import io.github.ranolp.rattranslate.util.Util;

import java.util.Optional;
import java.util.Set;

public abstract class Configuration {
  public final Optional<Boolean> getBoolean(String key) {
    return get(key).flatMap(Util.cast(Boolean.class));
  }

  public final Optional<Character> getCharacter(String key) {
    return get(key).flatMap(Util.cast(Character.class));
  }

  public final Optional<Integer> getInt(String key) {
    return get(key).flatMap(Util.cast(Integer.class));
  }

  public final Optional<Long> getLong(String key) {
    return get(key).flatMap(Util.cast(Long.class));
  }

  public final Optional<Double> getDouble(String key) {
    return get(key).flatMap(Util.cast(Double.class));
  }

  public final Optional<String> getString(String key) {
    return get(key).flatMap(Util.cast(String.class));
  }

  public abstract Optional<Configuration> getSection(String key);

  public abstract Set<String> getKeys(boolean deep);

  public abstract Optional<Object> get(String key);
}
