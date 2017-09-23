package com.company.filereader;


import com.company.threadboss.ThreadBoss;
import com.google.common.base.Strings;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReaderImpl implements Reader {

    private String resource;

    private BufferedReader bufferedReader = null;
    private ThreadBoss threadBoss;
    private static String NUMBER_REGEXP = "\\d+";
    private static String CYRILLIC_AND_DIGIT_CHARS_REGEXP = "[А-Яа-я0-9]+";

    public FileReaderImpl() {
    }

    public FileReaderImpl(String resource, ThreadBoss threadBoss) {
        this.resource = resource;
        this.threadBoss = threadBoss;
    }

    @Override
    public List<String> readLine() {
        if (Strings.isNullOrEmpty(resource)) {
            throw new FileReadException("Target can not be null");
        }

        File f = new File(resource);
        if (!f.exists()) {
            throw new FileReadException("Can not found resource: " + resource);
        }

        if (!f.canRead()) {
            throw new FileReadException("Can not read resource: " + resource);
        }

        if (bufferedReader == null) {
            try {
                FileReader fileReader = new FileReader(resource);
                bufferedReader = new BufferedReader(fileReader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return read();
    }

    private List<String> read() {
        List<String> result = new ArrayList<>();

        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Strings.isNullOrEmpty(line)) {
            return Collections.emptyList();
        }

        line = line.replaceAll("[^0-9а-яА-Яa-zA-Z]", " ");
        String[] split = line.split(" ");
        for (String word : split) {
            word = word.trim();
            if (Strings.isNullOrEmpty(word)) {
                continue;
            }
            if (!containCyrillicOrDigit(word)) {
                Thread thread = Thread.currentThread();
                System.out.println("STOPPED BY WORD: \"" + word + "\"");
                System.out.println("STOPPED FROM THREAD ID: " + thread.getId());
                System.out.println("STOPPED FROM THREAD NAME: " + thread.getName());
                threadBoss.requestExit();
                return result;
            }
            if (!containOnlyNumbers(word)) {
                result.add(word);
            }
        }
        return result;
    }

    private boolean containCyrillicOrDigit(String splittedString) {
        Pattern pattern = Pattern.compile(CYRILLIC_AND_DIGIT_CHARS_REGEXP);
        Matcher matcher = pattern.matcher(splittedString);
        return matcher.matches();
    }

    private boolean containOnlyNumbers(String string) {
        Pattern pattern = Pattern.compile(NUMBER_REGEXP);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}