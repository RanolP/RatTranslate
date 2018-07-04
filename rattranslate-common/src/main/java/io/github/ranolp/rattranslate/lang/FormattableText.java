package io.github.ranolp.rattranslate.lang;

import io.github.ranolp.rattranslate.Locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FormattableText {
  private List<Node> nodes = new ArrayList<>();

  public FormattableText(String rawText) {
    char[] chars = rawText.toCharArray();
    StringBuilder builder = new StringBuilder();
    boolean num = false;
    boolean escape = false;
    int braces = 0;
    String name = null;
    String type = null;
    String formatter = null;
    for (char c: chars) {
      if (escape) {
        escape = false;
        builder.append(c);
        continue;
      }
      if (braces > 0) {
        switch (c) {
          case ',':
            if (name == null) {
              name = builder.toString().trim();
              builder.setLength(0);
            } else if (type == null) {
              type = builder.toString().trim();
              builder.setLength(0);
            } else {
              builder.append(',');
            }
            continue;
          case '{':
            braces++;
            builder.append('{');
            continue;
          case '}':
            braces--;
            if (braces > 0) {
              builder.append('}');
            } else {
              if (name == null) {
                name = builder.toString().trim();
              } else if (type == null) {
                type = builder.toString().trim();
              } else {
                formatter = builder.toString().trim();
              }
              builder.setLength(0);
              if (name.isEmpty()) {
                builder.append("{}");
              } else {
                nodes.add(new FormatterVariableNode(name, type, formatter));
              }
              name = null;
              type = null;
              formatter = null;
            }
            continue;
        }
      }
      if (num) {
        if (Character.isDigit(c)) {
          builder.append(c);
          continue;
        } else if (builder.length() == 0) {
          builder.append('$');
          num = false;
        } else {
          nodes.add(new IndexedVariableNode(Integer.parseInt(builder.toString())));
          builder.setLength(0);
          num = false;
        }
      }
      switch (c) {
        case '$':
          if (builder.length() > 0) {
            nodes.add(new SimpleTextNode(builder.toString()));
            builder.setLength(0);
          }
          num = true;
          continue;
        case '{':
          if (builder.length() > 0) {
            nodes.add(new SimpleTextNode(builder.toString()));
            builder.setLength(0);
          }
          braces++;
          continue;
        case '\\':
          escape = true;
          continue;
      }
      builder.append(c);
    }
    if (num) {
      builder.append('$');
    }
    if (builder.length() > 0) {
      nodes.add(new SimpleTextNode(builder.toString()));
      builder.setLength(0);
    }
  }

  public String format(List<Variable> variables, Locale locale) {
    StringBuilder result = new StringBuilder();
    for (Node node: nodes) {
      result.append(node.format(variables, locale));
    }
    return result.toString();
  }

  private interface Node {
    String format(List<Variable> variables, Locale locale);
  }

  private final class SimpleTextNode implements Node {
    private String simpleText;

    private SimpleTextNode(String simpleText) {
      this.simpleText = simpleText;
    }

    @Override
    public String format(List<Variable> variables, Locale locale) {
      return simpleText;
    }
  }

  private final class IndexedVariableNode implements Node {
    private int index;

    private IndexedVariableNode(int index) {
      this.index = index;
    }

    @Override
    public String format(List<Variable> variables, Locale locale) {
      return index < variables.size() ? Objects.toString(variables.get(index).getValue()) : null;
    }
  }

  private final class FormatterVariableNode implements Node {
    private String name;
    private VariableType type;
    private String formatter;

    private FormatterVariableNode(String name, String type, String formatter) {
      this.name = name;
      this.type = Storage.getType(type);
      this.formatter = formatter;
    }

    @Override
    public String format(List<Variable> variables, Locale locale) {
      Variable variable = variables.stream()
          .filter(Variable::isNamed)
          .filter(v -> v.isNameEquals(name))
          .filter(v -> type == null || type.isSupported(v.getType()))
          .findFirst()
          .orElse(Variable.NOTHING);
      return (type != null ? type : variable.getType()).getDefaultFormatter(locale).format(variable);
    }
  }
}
