package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.lang.type.AnyType;
import io.github.ranolp.rattranslate.lang.type.NumberType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Storage {
  private static Map<String, VariableType> types = new HashMap<>();
  private static Map<VariableType, Set<VariableFormatter>> formatters = new HashMap<>();

  static {
    addType(NumberType.getInstance());
  }

  public static void addType(VariableType type) {
    types.put(type.getName(), type);
  }

  static VariableType getType(String name) {
    name = name == null ? null : name.toLowerCase();
    return types.containsKey(name) ? types.get(name) : AnyType.getInstance();
  }

  public static void addFormatter(VariableFormatter formatter) {
    formatters.computeIfAbsent(formatter.getSupportedType(), it -> new HashSet<>()).add(formatter);
  }
}
