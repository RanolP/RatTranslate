package io.github.ranolp.rattranslate;

import io.github.ranolp.rattranslate.lang.FormattableText;
import io.github.ranolp.rattranslate.lang.Variable;
import org.junit.Test;

import java.util.Arrays;

public class FormatterTest {
    @Test
    public void testFormattableText() {
        FormattableText text = new FormattableText(
                "{} Your name is now \\{$0\\}, You changed name {times} times. Pay {change:money, number}$ to change nickname now.");

        System.out.println(text.format(
                Arrays.asList(Variable.ofAny("change", "name", "Ranol_"), Variable.ofNumber("change", "times", 4)),
                Locale.AMERICAN_ENGLISH));
    }
}
