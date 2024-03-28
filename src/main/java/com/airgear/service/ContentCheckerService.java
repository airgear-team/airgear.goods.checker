package com.airgear.service;

import com.airgear.dto.CheckResult;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContentCheckerService {
    private Set<String> forbiddenWords = new HashSet<>();
    private Pattern forbiddenWordsPattern;
    private final Pattern phoneNumberPattern = Pattern.compile("(\\+\\d{1,3}[- ]?)?\\d{10}");
    private final Pattern cardNumberPattern = Pattern.compile("\\b(?:\\d{4}[- ]?){3}(?:(\\d{4})|\\d{6})\\b");

    @PostConstruct
    public void init() {
        loadForbiddenWords();
        compileForbiddenWordsPattern();
    }

    private void loadForbiddenWords() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("forbidden_words.txt").getInputStream()))) {

            forbiddenWords = reader.lines().collect(Collectors.toSet());

        } catch (IOException e) {
            throw new RuntimeException("Failed to load forbidden words from file", e);
        }
    }

    private void compileForbiddenWordsPattern() {
        String pattern = forbiddenWords.stream()
                .map(word -> "\\b" + Pattern.quote(word) + "\\b")
                .collect(Collectors.joining("|"));
        forbiddenWordsPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
    }

    public CheckResult checkContent(String text) {
        return new CheckResult(
                containsForbiddenWords(text),
                containsPhoneNumber(text),
                containsCardNumber(text)
        );
    }

    private boolean containsForbiddenWords(String text) {
        if (forbiddenWordsPattern == null || forbiddenWordsPattern.pattern().isEmpty()) {
            return false;
        }
        var matcher = forbiddenWordsPattern.matcher(text.toLowerCase());
        return matcher.find();
    }

    private boolean containsPhoneNumber(String text) {
        var matcher = phoneNumberPattern.matcher(text);
        return matcher.find();
    }

    private boolean containsCardNumber(String text) {
        var matcher = cardNumberPattern.matcher(text);
        return matcher.find();
    }
}