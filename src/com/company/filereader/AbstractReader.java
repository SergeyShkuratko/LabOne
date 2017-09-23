package com.company.filereader;

import com.company.threadboss.ThreadBoss;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractReader implements Reader {

    protected String resource;
    protected BufferedReader bufferedReader = null;
    protected ThreadBoss threadBoss;

    protected List<String> read() {
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
        line = TextHelper.keepOnlyLatinCyrillicAndDigitChars(line);
        String[] split = line.split(" ");
        for (String word : split) {
            word = word.trim();
            if (Strings.isNullOrEmpty(word)) {
                continue;
            }
            if (!TextHelper.containCyrillicOrDigit(word)) {
                Thread thread = Thread.currentThread();
                System.out.println("STOPPED BY WORD: \"" + word + "\"");
                System.out.println("STOPPED BY RESOURCE NAME: \"" + resource + "\"");
                System.out.println("STOPPED FROM THREAD ID: " + thread.getId());
                System.out.println("STOPPED FROM THREAD NAME: " + thread.getName());
                threadBoss.requestExit();
                return result;
            }
            if (!TextHelper.containOnlyNumbers(word)) {
                result.add(word);
            }
        }
        return result;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setThreadBoss(ThreadBoss threadBoss) {
        this.threadBoss = threadBoss;
    }
}
