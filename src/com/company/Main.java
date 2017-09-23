package com.company;

import com.company.report.ReportBuilder;
import com.company.threadboss.ThreadBoss;

public class Main {
    public static void main(String[] args) {
        ReportBuilder rb = new ReportBuilder();
        ThreadBoss tb = new ThreadBoss(args.length, rb, args);
        tb.start();
    }
}
