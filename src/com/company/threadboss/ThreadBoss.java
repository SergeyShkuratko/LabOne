package com.company.threadboss;

import com.company.filereader.Reader;
import com.company.filereader.ReaderFactory;
import com.company.filereader.TextHelper;
import com.company.filereader.UnexpectedResourceTypeException;
import com.company.report.ReportBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ThreadBoss {

    private ReportBuilder rb;
    private String[] resources;
    private Set<Thread> threads = new HashSet<>();

    private volatile boolean exit = false;

    public void requestExit() {
        exit = true;
    }

    public ThreadBoss(ReportBuilder reportBuilder, String[] resources) {
        this.rb = reportBuilder;
        this.resources = resources;
    }

    public void start() {
        for (String resource : resources) {
            Thread thread = new Thread(() -> {
                String resourceType = resolveResourceType(resource);
                Supplier<ReaderFactory> readerFactory = ReaderFactory::new;
                Reader reader = readerFactory.get().getReader(resourceType);
                reader.setResource(resource);
                reader.setThreadBoss(this);
                while (!exit) {
                    List<String> strings = reader.readLine();
                    if (strings.size() == 0) {
                        break;
                    }
                    for (String string : strings) {
                        rb.addWord(string);
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    private String resolveResourceType(String resource) {
        if (TextHelper.isURL(resource)) {
            return "URL";
        }
        if (TextHelper.isTxtFile(resource)) {
            return "FILE";
        }
        throw new UnexpectedResourceTypeException("Resource " + resource + " has unexpected type");
    }
}
