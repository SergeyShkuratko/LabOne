package com.company.filereader;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileReaderImplTest extends FileReaderImpl {
    final private static String CONTAIN_ONLY_NUMBER_METHOD_NAME = "containOnlyNumbers";
    final private static String CONTAIN_LATIN_CHARS_METHOD_NAME = "containLatinChars";

    private FileReaderImpl fileReader;

    public FileReaderImplTest() {
        super();
    }

    @Before
    public void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void containOnlyNumberTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = fileReader.getClass().getDeclaredMethod(CONTAIN_ONLY_NUMBER_METHOD_NAME, String.class);
        method.setAccessible(true);
        boolean resultTrue = (boolean) method.invoke(fileReader, "123");
        boolean resultFalse = (boolean) method.invoke(fileReader, "A23");
        method.setAccessible(false);
        assertTrue(resultTrue);
        assertFalse(resultFalse);
    }

    @Test
    public void containLatinChars() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = fileReader.getClass().getDeclaredMethod(CONTAIN_LATIN_CHARS_METHOD_NAME, String.class);
        method.setAccessible(true);
        assertTrue((boolean) method.invoke(fileReader, "привет"));
        assertTrue((boolean) method.invoke(fileReader, "при3ет"));
        assertFalse((boolean) method.invoke(fileReader, "hello"));
        assertFalse((boolean) method.invoke(fileReader, "приbet"));
        method.setAccessible(false);
    }

}