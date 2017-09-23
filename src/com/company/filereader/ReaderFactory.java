package com.company.filereader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ReaderFactory {
    final static Map<String, Supplier<Reader>> map = new HashMap<>();

    static {
        map.put("URL", URLFileReader::new);
        map.put("FILE", LocalFileReader::new);
    }

    public Reader getReader(String readerType) {
        Supplier<Reader> reader = map.get(readerType.toUpperCase());
        if (reader != null) {
            return reader.get();
        }
        throw new IllegalArgumentException("No such reader " + readerType.toUpperCase());
    }
}
