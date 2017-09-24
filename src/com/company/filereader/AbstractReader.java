package com.company.filereader;

import com.company.threadboss.ThreadBoss;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractReader implements Reader {

    private static final Logger logger = Logger.getLogger(AbstractReader.class);

    protected String resource;
    protected BufferedReader bufferedReader = null;
    protected ThreadBoss threadBoss;

    protected List<String> read() {
        List<String> result = new ArrayList<>();
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
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
                logger.info("STOPPED BY WORD: \"" + word + "\"");
                logger.info("STOPPED BY RESOURCE NAME: \"" + resource + "\"");
                logger.info("STOPPED FROM THREAD ID: " + thread.getId());
                logger.info("STOPPED FROM THREAD NAME: " + thread.getName());
                threadBoss.requestExit();
                return result;
            }
            if (!TextHelper.containOnlyNumbers(word)) {
                result.add(word);
            }
        }
        return result;
    }

    @Override
    public void closeResources() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setThreadBoss(ThreadBoss threadBoss) {
        this.threadBoss = threadBoss;
    }
}
