package com.company.threadboss;

import com.company.filereader.FileReaderImpl;
import com.company.filereader.Reader;
import com.company.report.ReportBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreadBoss {

    private int threadCount;
    private ReportBuilder rb;
    private String[] resources;
    private Set<Thread> threads = new HashSet<>();

    private volatile boolean exit = false;


    public void requestExit() {
        exit = true;
    }


    public ThreadBoss(int threadCount, ReportBuilder reportBuilder, String[] resources) {
        this.threadCount = threadCount;
        this.rb = reportBuilder;
        this.resources = resources;
    }

    public void start() {
        for (String resource : resources) {
            Thread thread = new Thread(() -> {
                Reader reader = new FileReaderImpl(resource, this);
                while (!exit) {
                    List<String> strings = reader.readLine();
                    if (strings.size() == 0) {
                        exit = true;
                    }
                    for (String string : strings) {
                        rb.addWord(string);
                    }
                }
//                return;
            });
            threads.add(thread);
            thread.start();
        }
    }
}
