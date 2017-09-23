package com.company.filereader;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexTest {

    @Test
    public void containOnlyNumberTest() {
        assertTrue(TextHelper.containOnlyNumbers("123"));
        assertFalse(TextHelper.containOnlyNumbers("A23"));
    }

    @Test
    public void containLatinChars() {
        assertTrue(TextHelper.containCyrillicOrDigit("привет"));
        assertTrue(TextHelper.containCyrillicOrDigit("при3ет"));
        assertFalse(TextHelper.containCyrillicOrDigit("при3еt"));
        assertFalse(TextHelper.containCyrillicOrDigit("hello"));
    }

    @Test
    public void isTxtFile() {
        assertTrue(TextHelper.isTxtFile("test.txt"));
        assertFalse(TextHelper.isTxtFile("ya.ru"));
    }

    @Test
    public void isURL() {
        assertTrue(TextHelper.isURL("https://raw.githubusercontent.com/SergeyShkuratko/LabOne/master/1.txt"));
        assertFalse(TextHelper.isURL("test.txt"));
    }
}