package io.github.ranolp.rattranslate.util;

import org.apache.commons.lang.CharRange;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Predict {
    private List<Character> charset = new ArrayList<>();
    private List<CharRange> ranges = new ArrayList<>();

    public Predict charset(char c, char... chars) {
        charset.add(c);
        for (char ch : chars) {
            charset.add(ch);
        }
        return this;
    }

    public Predict ranges(char start, char end) {
        ranges.add(new CharRange(start, end));
        return this;
    }

    public Predicate<Character> asPredicate() {
        return this::test;
    }

    public boolean test(char c) {
        return charset.contains(c) || ranges.parallelStream().anyMatch(range -> range.contains(c));
    }

    public float checkAll(String sentence) {
        float result = 100f;
        float decrease = 100f / sentence.length();
        for (char c : sentence.toCharArray()) {
            if (!test(c)) {
                result -= decrease;
            }
        }
        return result;
    }
}
