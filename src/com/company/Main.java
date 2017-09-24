package com.company;

import com.company.report.DataStorage;
import com.company.threadboss.ThreadBoss;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        DataStorage rb = new DataStorage();


        ThreadBoss tb = new ThreadBoss(rb, args);
        tb.start();
    }
}
