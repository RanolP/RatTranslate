package io.github.ranolp.rattranslate.util;

/**
 * <a href="https://github.com/sindresorhus/figures">Figures</a> port
 */
public enum Figures {
    TICK("√", "✔"),
    CROSS("×", "✖"),
    STAR("*", "★"),
    SQUARE("█", "▇"),
    SQUARE_SMALL("[ ]", "◻"),
    SQUARE_SMALL_FILLED("[█]", "◼"),
    PLAY("►", "▶"),
    CIRCLE("( )", "◯"),
    CIRCLE_FILLED("(*)", "◉"),
    CIRCLE_DOTTED("( )", "◌"),
    CIRCLE_DOUBLE("( )", "◎"),
    CIRCLE_CIRCLE("(○)", "ⓞ"),
    CIRCLE_CROSS("(×)", "ⓧ"),
    CIRCLE_PIPE("(│)", "Ⓘ"),
    CIRCLE_QUESTION_MARK("(?)", "?⃝"),
    BULLET("*", "●"),
    DOT(".", "․"),
    LINE("─", "─"),
    ELLIPSIS("...", "…"),
    POINTER(">", "❯"),
    POINTER_SMALL("»", "›"),
    INFO("i", "ℹ"),
    WARNING("‼", "⚠"),
    HAMBURGER("≡", "☰"),
    SMILEY("☺", "㋡"),
    MUSTACHE("┌─┐", "෴"),
    HEART("♥"),
    ARROW_UP("↑"),
    ARROW_DOWN("↓"),
    ARROW_LEFT("←"),
    ARROW_RIGHT("→"),
    RADIO_ON("(*)", "◉"),
    RADIO_OFF("( )", "◯"),
    CHECKBOX_ON("[×]", "☒"),
    CHECKBOX_OFF("[ ]", "☐"),
    CHECKBOX_CIRCLE_ON("(×)", "ⓧ"),
    CHECKBOX_CIRCLE_OFF("( )", "Ⓘ"),
    // The main one doesn't look that good on Ubuntu
    QUESTION_MARK_PREFIX("？", OsUtil.isUnix() ? "?" : "?⃝");

    private final String fallback;
    private final String unicode;

    Figures(String unicode) {
        this.fallback = unicode;
        this.unicode = unicode;
    }

    Figures(String fallback, String unicode) {
        this.fallback = fallback;
        this.unicode = unicode;
    }

    public String getFallback() {
        return fallback;
    }

    public String getUnicode() {
        return unicode;
    }

    public String getSupported() {
        return OsUtil.isWindows() ? fallback : unicode;
    }

    @Override
    public String toString() {
        return getSupported();
    }
}
