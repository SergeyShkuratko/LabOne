package com.company.filereader;


import com.company.threadboss.ThreadBoss;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class LocalFileReader extends AbstractReader {

    public LocalFileReader() {
    }

    public LocalFileReader(String resource, ThreadBoss threadBoss) {
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
}