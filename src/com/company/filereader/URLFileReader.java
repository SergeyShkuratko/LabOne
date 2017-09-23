package com.company.filereader;

import com.company.threadboss.ThreadBoss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class URLFileReader extends AbstractReader {

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
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
        return read();
    }
}
