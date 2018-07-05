package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.lang.type.AnyType;
import io.github.ranolp.rattranslate.lang.type.NumberType;

public class Variable {
    public static final Variable NOTHING = new Variable(null, null, null, AnyType.getInstance()) {
        @Override
        public String toString() {
            return "Variable(NOTHING)";
        }
    };
    private final String namespace;
    private final String name;
    private final Object value;
    private final VariableType variableType;

    private Variable(String namespace, String name, Object value, VariableType variableType) {
        this.namespace = namespace;
        this.name = name;
        this.value = value;
        this.variableType = variableType;
    }

    public static Variable ofNumber(String namespace, String name, Number value) {
        return new Variable(namespace, name, value, NumberType.getInstance());
    }

    public static Variable ofAny(String namespace, String name, Object value) {
        return new Variable(namespace, name, value, AnyType.getInstance());
    }

    public boolean isNamed() {
        return name != null;
    }

    public VariableType getType() {
        return variableType;
    }

    public String getName() {
        if (name == null) {
            return null;
        } else if (namespace != null) {
            return namespace + ":" + name;
        } else {
            return name;
        }
    }

    public boolean isNameEquals(String name) {
        if (name == null) {
            return false;
        } else if (name.indexOf(':') != -1) {
            return namespace != null && getName().equals(name);
        } else {
            return this.name.equals(name);
        }
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Variable(name=" + getName() + ", value=" + value + ", type=" + variableType.getName() + ")";
    }
}
