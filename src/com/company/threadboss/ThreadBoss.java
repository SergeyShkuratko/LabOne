package com.company.threadboss;

import com.company.filereader.Reader;
import com.company.filereader.ReaderFactory;
import com.company.filereader.TextHelper;
import com.company.filereader.UnexpectedResourceTypeException;
import com.company.report.DataStorage;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ThreadBoss {


    private final static Logger logger = Logger.getLogger(ThreadBoss.class);

    private DataStorage rb;
    private String[] resources;
    private volatile int finishedThreads = 0;

    private volatile boolean exit = false;

    public void requestExit() {
        logger.trace("Requested exit");
        exit = true;
    }

    public ThreadBoss(DataStorage dataStorage, String[] resources) {
        this.rb = dataStorage;
        this.resources = resources;
    }

    public void start() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (String resource : resources) {
            executorService.submit(() -> {
                logger.trace("Start new thread for resource: " + resource);
                String resourceType = resolveResourceType(resource);
                Supplier<ReaderFactory> readerFactory = ReaderFactory::new;
                Reader reader = readerFactory.get().getReader(resourceType);
                reader.setResource(resource);
                reader.setThreadBoss(this);
                while (true) {
                    List<String> strings = reader.readLine();
                    if (strings.size() == 0) {
                        finishedThreads++;
                        break;
                    }
                    for (String string : strings) {
                        rb.addWord(string);
                    }
                }
                reader.closeResources();
            });
        }

        while (true) {
            if (finishedThreads == resources.length) {
                break;
            }
        }
        executorService.shutdownNow();
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
