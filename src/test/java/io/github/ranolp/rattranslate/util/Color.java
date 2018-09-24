package io.github.ranolp.rattranslate.util;

public enum Color {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    WHITE(37);
    private static final String PREFIX = "\u001b[";
    private static final String SUFFIX = "m";
    public static final String RESET = PREFIX + 0 + SUFFIX;
    public static final String BOLD = PREFIX + 1 + SUFFIX;
    public static final String ITALIC = PREFIX + 3 + SUFFIX;
    public static final String UNDERLINE = PREFIX + 4 + SUFFIX;
    public static final String REVERSED = PREFIX + 7 + SUFFIX;
    private int id;

    Color(int id) {
        this.id = id;
    }

    public String foreground() {
        return PREFIX + id + SUFFIX;
    }

    public String brightForeground() {
        return PREFIX + id + ";1" + SUFFIX;
    }

    public String background() {
        return PREFIX + (10 + id) + ";1" + SUFFIX;
    }

    public String brightBackground() {
        return PREFIX + (10 + id) + ";1" + SUFFIX;
    }
}
