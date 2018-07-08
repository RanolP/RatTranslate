package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.translator.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class EnglishKoreanTranslateTest {
    private abstract class AbstractTestCase {
        protected final String sentence;
        protected final Locale output;

        public AbstractTestCase(String sentence, Locale output) {
            this.sentence = sentence;
            this.output = output;
        }

        public abstract String translate(Translator translator);
    }

    private final class TestCase extends AbstractTestCase {
        private final Locale input;

        public TestCase(String sentence, Locale output, Locale input) {
            super(sentence, output);
            this.input = input;
        }

        @Override
        public String translate(Translator translator) {
            if (!translator.isLocaleSupported(input)) {
                return input.getName() + "(" + input.getCode() + ") Not Supported";
            }
            if (!translator.isLocaleSupported(output)) {
                return output.getName() + "(" + output.getCode() + ") Not Supported";
            }
            return String.format(
                    "%s (%s→%s, original : %s)",
                    translator.translate(sentence, input, output),
                    input.getLanguageCode(),
                    output.getLanguageCode(),
                    sentence
            );
        }
    }

    private final class AutoTestCase extends AbstractTestCase {

        public AutoTestCase(String sentence, Locale output) {
            super(sentence, output);
        }

        @Override
        public String translate(Translator translator) {
            if (!translator.isAutoSupported()) {
                return "Auto Not Supported";
            }
            if (!translator.isLocaleSupported(output)) {
                return output.getName() + "(" + output.getCode() + ") Not Supported";
            }
            return String.format("%s (Auto, original : %s)", translator.translateAuto(sentence, output), sentence);
        }
    }

    private List<AbstractTestCase> testCases = Arrays.asList(
            new TestCase("Hello", Locale.KOREAN, Locale.AMERICAN_ENGLISH),
            new TestCase("안녕", Locale.AMERICAN_ENGLISH, Locale.KOREAN),

            new AutoTestCase("Hello", Locale.KOREAN),
            new AutoTestCase("안녕", Locale.AMERICAN_ENGLISH)
    );

    private void test(Translator translator) {
        String name = translator.getClass().getSimpleName();
        for (AbstractTestCase testCase : testCases) {
            System.out.println(name + ": " + testCase.translate(translator));
        }
    }

    @Test
    public void google() {
        test(GoogleApisTranslator.getInstance());
    }

    @Test
    public void papago() {
        test(PapagoTranslator.getInstance());
    }

    @Test
    public void kakao() {
        test(KakaoTranslator.getInstance());
    }

    @Test
    public void kakaoAutoable() {
        test(new AutoableTranslator(KakaoTranslator.getInstance()));
    }
}
