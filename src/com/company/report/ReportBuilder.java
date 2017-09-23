package com.company.report;

import java.util.HashMap;

public class ReportBuilder {
    final public HashMap<String, Integer> counter = new HashMap<>();

    public void addWord(String word) {
        synchronized (counter) {
            Integer integer = counter.get(word);
            if (integer == null) {
                counter.put(word, 1);
            } else {
                counter.put(word, integer + 1);
            }

            System.out.println(counter);
        }
    }
}
