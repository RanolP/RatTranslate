package io.github.ranolp.rattranslate.util;

import java.util.Arrays;

public class ColorUtil {
    public static StyledText fgBright(Color color, Object... texts) {
        return new StyledText.Foreground(Arrays.asList(texts), color, true);
    }

    public static StyledText bgBright(Color color, Object... texts) {
        return new StyledText.Background(Arrays.asList(texts), color, true);
    }

    public static StyledText fg(Color color, Object... texts) {
        return new StyledText.Foreground(Arrays.asList(texts), color, false);
    }

    public static StyledText bg(Color color, Object... texts) {
        return new StyledText.Background(Arrays.asList(texts), color, false);
    }

    public static StyledText fgBlack(Object... texts) {
        return fg(Color.BLACK, texts);
    }

    public static StyledText fgRed(Object... texts) {
        return fg(Color.RED, texts);
    }

    public static StyledText fgGreen(Object... texts) {
        return fg(Color.GREEN, texts);
    }

    public static StyledText fgYellow(Object... texts) {
        return fg(Color.YELLOW, texts);
    }

    public static StyledText fgBlue(Object... texts) {
        return fg(Color.BLUE, texts);
    }

    public static StyledText fgMagenta(Object... texts) {
        return fg(Color.MAGENTA, texts);
    }

    public static StyledText fgCyan(Object... texts) {
        return fg(Color.CYAN, texts);
    }

    public static StyledText fgWhite(Object... texts) {
        return fg(Color.WHITE, texts);
    }


    public static StyledText fgBrightRed(Object... texts) {
        return fgBright(Color.RED, texts);
    }

    public static StyledText fgBrightGreen(Object... texts) {
        return fgBright(Color.GREEN, texts);
    }

    public static StyledText fgBrightYellow(Object... texts) {
        return fgBright(Color.YELLOW, texts);
    }

    public static StyledText fgBrightBlue(Object... texts) {
        return fgBright(Color.BLUE, texts);
    }

    public static StyledText fgBrightMagenta(Object... texts) {
        return fgBright(Color.MAGENTA, texts);
    }

    public static StyledText fgBrightCyan(Object... texts) {
        return fgBright(Color.CYAN, texts);
    }

    public static StyledText fgBrightWhite(Object... texts) {
        return fgBright(Color.WHITE, texts);
    }


    public static StyledText bgBlack(Object... texts) {
        return bg(Color.BLACK, texts);
    }

    public static StyledText bgRed(Object... texts) {
        return bg(Color.RED, texts);
    }

    public static StyledText bgGreen(Object... texts) {
        return bg(Color.GREEN, texts);
    }

    public static StyledText bgYellow(Object... texts) {
        return bg(Color.YELLOW, texts);
    }

    public static StyledText bgBlue(Object... texts) {
        return bg(Color.BLUE, texts);
    }

    public static StyledText bgMagenta(Object... texts) {
        return bg(Color.MAGENTA, texts);
    }

    public static StyledText bgCyan(Object... texts) {
        return bg(Color.CYAN, texts);
    }

    public static StyledText bgWhite(Object... texts) {
        return bg(Color.WHITE, texts);
    }

    public static StyledText bgBrightBlack(Object... texts) {
        return bgBright(Color.BLACK, texts);
    }

    public static StyledText bgBrightRed(Object... texts) {
        return bgBright(Color.RED, texts);
    }

    public static StyledText bgBrightGreen(Object... texts) {
        return bgBright(Color.GREEN, texts);
    }

    public static StyledText bgBrightYellow(Object... texts) {
        return bgBright(Color.YELLOW, texts);
    }

    public static StyledText bgBrightBlue(Object... texts) {
        return bgBright(Color.BLUE, texts);
    }

    public static StyledText bgBrightMagenta(Object... texts) {
        return bgBright(Color.MAGENTA, texts);
    }

    public static StyledText bgBrightCyan(Object... texts) {
        return bgBright(Color.CYAN, texts);
    }

    public static StyledText bgBrightWhite(Object... texts) {
        return bgBright(Color.WHITE, texts);
    }

    public static StyledText bold(Object... texts) {
        return new StyledText.Bold(Arrays.asList(texts));
    }

    public static StyledText italic(Object... texts) {
        return new StyledText.Italic(Arrays.asList(texts));
    }

    public static StyledText underline(Object... texts) {
        return new StyledText.Underline(Arrays.asList(texts));
    }

    public static StyledText reversed(Object... texts) {
        return new StyledText.Reversed(Arrays.asList(texts));
    }
}
