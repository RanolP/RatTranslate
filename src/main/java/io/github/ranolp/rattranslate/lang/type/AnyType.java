package io.github.ranolp.rattranslate.lang.type;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.lang.Variable;
import io.github.ranolp.rattranslate.lang.VariableFormatter;
import io.github.ranolp.rattranslate.lang.VariableType;

import java.util.Objects;

public class AnyType implements VariableType {
  private final VariableFormatter FORMATTER = new DefaultFormatter();

  private AnyType() {
  }

  public static AnyType getInstance() {
    return SingletonHolder.INSTANCE;
  }

  @Override
  public String getName() {
    return "any";
  }

  @Override
  public boolean isSupported(VariableType type) {
    return true;
  }

  @Override
  public VariableFormatter getDefaultFormatter(Locale locale) {
    return FORMATTER;
  }

  private static final class SingletonHolder {
    private static final AnyType INSTANCE = new AnyType();
  }

  private class DefaultFormatter implements VariableFormatter {
    @Override
    public VariableType getSupportedType() {
      return AnyType.getInstance();
    }

    @Override
    public String format(Variable variable) {
      return Objects.toString(variable.getValue());
    }
  }
}
