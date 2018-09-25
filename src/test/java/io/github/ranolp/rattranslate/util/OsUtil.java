package io.github.ranolp.rattranslate.util;

public final class OsUtil {
    private static final OperatingSystem OS;

    static {
        OperatingSystem result = OperatingSystem.UNKNOWN;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac") || osName.contains("darwin")) {
            result = OperatingSystem.MAC;
        } else if (osName.contains("win")) {
            result = OperatingSystem.WINDOWS;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            result = OperatingSystem.UNIX;
        } else if (osName.contains("sunos")) {
            result = OperatingSystem.SOLARIS;
        }

        OS = result;
    }

    private OsUtil() {
        throw new UnsupportedOperationException("You cannot instantiate OsUtil");
    }

    public static boolean isWindows() {
        return OS == OperatingSystem.WINDOWS;
    }

    public static boolean isUnix() {
        return OS == OperatingSystem.UNIX;
    }

    public static boolean isMacOs() {
        return OS == OperatingSystem.MAC;
    }

    public static boolean isSolaris() {
        return OS == OperatingSystem.SOLARIS;
    }

    private enum OperatingSystem {
        WINDOWS,
        MAC,
        UNIX,
        SOLARIS,
        UNKNOWN
    }
}
