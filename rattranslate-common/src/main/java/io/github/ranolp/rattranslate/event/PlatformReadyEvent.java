package io.github.ranolp.rattranslate.event;

import io.github.ranolp.rattranslate.abstraction.Platform;

public class PlatformReadyEvent extends Event {
    private final Platform platform;

    public PlatformReadyEvent(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }
}
