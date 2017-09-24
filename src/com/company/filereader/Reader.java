package com.company.filereader;

import com.company.threadboss.ThreadBoss;

import java.util.List;

public interface Reader {
    List<String> readLine();

    void closeResources();

    void setResource(String resource);

    void setThreadBoss(ThreadBoss threadBoss);
}
