package com.company.filereader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHelper {

    private static String NUMBER_REGEXP = "\\d+";
    private static String CYRILLIC_AND_DIGIT_CHARS_REGEXP = "[А-Яа-я0-9]+";

    public static String keepOnlyLatinCyrillicAndDigitChars(String input) {
        return input.replaceAll("[^0-9а-яА-Яa-zA-Z]", " ");
    }

    public static boolean containCyrillicOrDigit(String string) {
        return checkRegExp(string, CYRILLIC_AND_DIGIT_CHARS_REGEXP);
    }

    public static boolean containOnlyNumbers(String string) {
        return checkRegExp(string, NUMBER_REGEXP);
    }

    public static boolean isTxtFile(String resource) {
        return checkRegExp(resource, ".*\\.txt");
    }

    public static boolean isURL(String resource) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return checkRegExp(resource, regex);
    }

    private static boolean checkRegExp(String string, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
