package io.github.ranolp.rattranslate.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class Util {
  private Util() {
    throw new UnsupportedOperationException("You cannot instantiate Util");
  }

  public static <T> Function<Object, Optional<T>> cast(Class<T> clazz) {
    return new CastingFunction<>(clazz);
  }

  private static class CastingFunction<T> implements Function<Object, Optional<T>> {
    private Class<T> clazz;

    public CastingFunction(Class<T> clazz) {
      this.clazz = Objects.requireNonNull(clazz, "class");
    }

    @Override
    public Optional<T> apply(Object o) {
      if (clazz.isInstance(o)) {
        return Optional.of(clazz.cast(o));
      }
      return Optional.empty();
    }
  }
}
