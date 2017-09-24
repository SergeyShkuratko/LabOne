package com.company;

import com.company.report.DataStorage;
import com.company.threadboss.ThreadBoss;

public class Main {
    public static void main(String[] args) {
        DataStorage rb = new DataStorage();
        ThreadBoss tb = new ThreadBoss(rb, args);
        tb.start();
    }
}
