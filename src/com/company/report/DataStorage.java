package com.company.report;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class DataStorage {
    private static final Logger logger = Logger.getLogger(DataStorage.class);
    private ConcurrentHashMap<String, Integer> storage = new ConcurrentHashMap<>();

    public void addWord(String word) {
        Integer integer = storage.get(word);
        if (integer == null) {
            storage.put(word, 1);
        } else {
            storage.put(word, integer + 1);
        }
        logger.info(storage);
    }
}
