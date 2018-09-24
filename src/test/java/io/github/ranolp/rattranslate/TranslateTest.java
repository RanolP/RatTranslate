package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.Translator;
import io.github.ranolp.rattranslate.util.StyledText;
import io.github.ranolp.rattranslate.util.Symbols;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;

import static io.github.ranolp.rattranslate.util.ColorUtil.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TranslateTest {
    private final String name;
    private final Translator translator;

    public TranslateTest(String name, Translator translator) {
        this.name = name;
        this.translator = translator;
    }

    private List<AbstractTestCase> testCases = Arrays.asList(
            new TestCase("Hello", Locale.AMERICAN_ENGLISH, Locale.KOREAN),
            new TestCase("Hello", Locale.AMERICAN_ENGLISH, Locale.JAPANESE),

            new TestCase("안녕", Locale.KOREAN, Locale.AMERICAN_ENGLISH),
            new TestCase("안녕", Locale.KOREAN, Locale.JAPANESE),

            new TestCase("初めまして。", Locale.JAPANESE, Locale.AMERICAN_ENGLISH),
            new TestCase("初めまして。", Locale.JAPANESE, Locale.KOREAN),

            new AutoTestCase("Hello", Locale.KOREAN), new AutoTestCase("Hello", Locale.JAPANESE),

            new AutoTestCase("初めまして。", Locale.AMERICAN_ENGLISH), new AutoTestCase("初めまして。", Locale.KOREAN),

            new AutoTestCase("안녕", Locale.AMERICAN_ENGLISH), new AutoTestCase("안녕", Locale.JAPANESE)
    );

    @Test
    public void test() {
        System.out.println(bgBlue(" ", name, " "));
        for (AbstractTestCase testCase : testCases) {
            System.out.println("   " + testCase.translate(translator));
        }
    }

    private abstract class AbstractTestCase {
        final String sentence;
        final Locale output;

        AbstractTestCase(String sentence, Locale output) {
            this.sentence = sentence;
            this.output = output;
        }

        public abstract StyledText translate(Translator translator);

        protected StyledText failure(String cause) {
            return fgRed(Symbols.cross, " ", cause);
        }

        protected StyledText success(Object... message) {
            Object[] real = new Object[message.length + 2];
            real[0] = Symbols.tick;
            real[1] = " ";
            System.arraycopy(message, 0, real, 2, message.length);
            return fgGreen(real);
        }
    }

    private final class TestCase extends AbstractTestCase {
        private final Locale input;

        TestCase(String sentence, Locale input, Locale output) {
            super(sentence, output);
            this.input = input;
        }

        @Override
        public StyledText translate(Translator translator) {
            if (!translator.isLocaleSupported(input)) {
                return failure(input.getCode() + " not supported.");
            }
            if (!translator.isLocaleSupported(output)) {
                return failure(output.getCode() + " not supported.");
            }

            String translated = translator.translate(sentence, input, output);

            if (translated == null || translated.isEmpty()) {
                return failure(
                        "Cannot translate " + sentence + "(" + input.getName() + ") to " + output.getName());
            }

            return fgGreen(Symbols.tick, " ", sentence, "(", input.getName(), ") ", Symbols.arrowRight, " ",
                    translated, "(", output.getName(), ")"
            );
        }
    }

    private final class AutoTestCase extends AbstractTestCase {

        AutoTestCase(String sentence, Locale output) {
            super(sentence, output);
        }

        @Override
        public StyledText translate(Translator translator) {
            if (!translator.isAutoSupported()) {
                return failure("Auto not supported.");
            }
            if (!translator.isLocaleSupported(output)) {
                return failure(output.getCode() + " not supported.");
            }
            String translated = translator.translateAuto(sentence, output);

            if (translated == null || translated.isEmpty()) {
                return failure("Cannot translate " + sentence + " to " + output.getName());
            }
            return fgGreen(Symbols.tick, " ", sentence, "(auto) ", Symbols.arrowRight, " ", translated,
                    "(" + output.getName() + ")"
            );
        }
    }
}
