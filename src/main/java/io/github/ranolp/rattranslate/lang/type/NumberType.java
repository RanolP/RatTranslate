package io.github.ranolp.rattranslate.lang.type;

import io.github.ranolp.rattranslate.Locale;
import io.github.ranolp.rattranslate.lang.Variable;
import io.github.ranolp.rattranslate.lang.VariableFormatter;
import io.github.ranolp.rattranslate.lang.VariableType;

public class NumberType implements VariableType {
  private final VariableFormatter FORMATTER = new DefaultFormatter();

  private NumberType() {
  }

  public static NumberType getInstance() {
    return SingletonHolder.INSTANCE;
  }

  @Override
  public String getName() {
    return "number";
  }

  @Override
  public VariableFormatter getDefaultFormatter(Locale locale) {
    return FORMATTER;
  }

  private static final class SingletonHolder {
    private static final NumberType INSTANCE = new NumberType();
  }

  private class DefaultFormatter implements VariableFormatter {
    @Override
    public VariableType getSupportedType() {
      return NumberType.getInstance();
    }

    @Override
    public String format(Variable variable) {
      Object value = variable.getValue();
      if (!(value instanceof Number)) {
        return "NaN";
      }
      return value.toString();
    }
  }
}
