package com.airgear.dto;

import lombok.Getter;

@Getter
public class CheckResult {
    private boolean containsForbiddenWords;
    private boolean containsPhoneNumber;
    private boolean containsCardNumber;

    public CheckResult(boolean containsForbiddenWords, boolean containsPhoneNumber, boolean containsCardNumber) {
        this.containsForbiddenWords = containsForbiddenWords;
        this.containsPhoneNumber = containsPhoneNumber;
        this.containsCardNumber = containsCardNumber;
    }

    public boolean isClean() {
        return !containsForbiddenWords && !containsPhoneNumber && !containsCardNumber;
    }

    @Override
    public String toString() {
        if (!containsForbiddenWords && !containsPhoneNumber && !containsCardNumber) {
            return "Text is clean";
        }
        return "Text contains: " +
                (containsForbiddenWords ? "forbidden words; " : "") +
                (containsPhoneNumber ? "phone number; " : "") +
                (containsCardNumber ? "card number; " : "");
    }
}
