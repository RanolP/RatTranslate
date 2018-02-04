package io.github.ranolp.rattranslate.lang;

public interface VariableFormatter {
    VariableType getSupportedType();

    String format(Variable variable);
}
