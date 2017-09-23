package com.company.report;

import java.util.HashMap;

public class ReportBuilder {
    HashMap<String, Integer> counter = new HashMap<>();

    public synchronized void addWord(String word) {
        Integer integer = counter.get(word);
        if (integer == null) {
            counter.put(word, 1);
        } else {
            counter.put(word, integer + 1);
        }
        System.out.println(counter);
    }
}
