package io.github.ranolp.rattranslate.util;

import java.util.ArrayList;
import java.util.List;

public abstract class StyledText {
    private List<Object> texts;

    StyledText(List<Object> texts) {
        this.texts = new ArrayList<>(texts);
    }

    public String render() {
        return render(new RenderContext());
    }

    protected abstract void beforeRender(RenderContext context);

    private String render(RenderContext parentContext) {
        StringBuilder builder = new StringBuilder();
        RenderContext context = parentContext.copy();
        beforeRender(context);
        boolean resetRequired = false;
        if (context.bold) {
            builder.append(Color.BOLD);
            resetRequired = true;
        }
        if (context.italic) {
            builder.append(Color.ITALIC);
            resetRequired = true;
        }
        if (context.underline) {
            builder.append(Color.UNDERLINE);
            resetRequired = true;
        }
        if (context.reversed) {
            builder.append(Color.REVERSED);
            resetRequired = true;
        }

        if (context.foreground != null) {
            builder.append(
                    context.foregroundBright ? context.foreground.brightForeground() : context.foreground.foreground());
            resetRequired = true;
        }

        if (context.background != null) {
            builder.append(
                    context.backgroundBright ? context.background.brightBackground() : context.background.background());
            resetRequired = true;
        }

        for (Object object : texts) {
            if (object instanceof StyledText) {
                StyledText casted = ((StyledText) object);
                builder.append(casted.render(context));
            } else {
                builder.append(object);
            }
        }

        if (resetRequired) {
            builder.append(Color.RESET);
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return render();
    }

    static class Foreground extends StyledText {
        private final Color foreground;
        private final boolean bright;

        public Foreground(List<Object> texts, Color foreground, boolean bright) {
            super(texts);
            this.foreground = foreground;
            this.bright = bright;
        }

        @Override
        protected void beforeRender(RenderContext context) {
            context.foreground = foreground;
            context.foregroundBright = bright;
        }
    }

    static class Background extends StyledText {
        private final Color background;
        private final boolean bright;

        public Background(List<Object> texts, Color background, boolean bright) {
            super(texts);
            this.background = background;
            this.bright = bright;
        }

        @Override
        protected void beforeRender(RenderContext context) {
            context.background = background;
            context.backgroundBright = bright;
        }
    }

    static class Bold extends StyledText {

        public Bold(List<Object> texts) {
            super(texts);
        }

        @Override
        protected void beforeRender(RenderContext context) {
            context.bold = true;
        }
    }

    static class Italic extends StyledText {

        public Italic(List<Object> texts) {
            super(texts);
        }

        @Override
        protected void beforeRender(RenderContext context) {
            context.italic = true;
        }
    }

    static class Underline extends StyledText {

        public Underline(List<Object> texts) {
            super(texts);
        }

        @Override
        protected void beforeRender(RenderContext context) {
            context.underline = true;
        }
    }

    static class Reversed extends StyledText {

        public Reversed(List<Object> texts) {
            super(texts);
        }

        @Override
        protected void beforeRender(RenderContext context) {
            context.reversed = true;
        }
    }

    private class RenderContext {
        boolean bold = false;
        boolean italic = false;
        boolean underline = false;
        boolean reversed = false;
        Color foreground = null;
        boolean foregroundBright = false;
        Color background = null;
        boolean backgroundBright = false;

        public RenderContext copy() {
            RenderContext result = new RenderContext();
            result.bold = this.bold;
            result.italic = this.italic;
            result.underline = this.underline;
            result.reversed = this.reversed;
            result.foreground = this.foreground;
            result.foregroundBright = this.foregroundBright;
            result.background = this.background;
            result.backgroundBright = this.backgroundBright;
            return result;
        }
    }
}
