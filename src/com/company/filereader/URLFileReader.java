package com.company.filereader;

import com.company.threadboss.ThreadBoss;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class URLFileReader extends AbstractReader {

    private static final Logger logger = Logger.getLogger(URLFileReader.class);

    public URLFileReader() {
    }

    public URLFileReader(String resource, ThreadBoss threadBoss) {
        this.resource = resource;
        this.threadBoss = threadBoss;
    }

    @Override
    public List<String> readLine() {
        if (bufferedReader == null) {
            try {
                InputStream inputStream = new URL(resource).openStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return Collections.emptyList();
            }
        }
        return read();
    }
}
