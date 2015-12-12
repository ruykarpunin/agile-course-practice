package ru.unn.agile.IntersectionFinder.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class FinderLoggerTests {
    private static final String LOGFILENAME = "./FinderLoggerTests.log";
    private FinderLogger logger;

    @Before
    public void setUp() {
        logger = new FinderLogger(LOGFILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        assertTrue(new File(LOGFILENAME).isFile());
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test";

        logger.log(testMessage);

        String message = logger.getLog().get(0);
        assertTrue(message.indexOf(testMessage) > 0);
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String message1 = "Test 1";
        String message2 = "Test 2";

        logger.log(message1);
        logger.log(message2);

        List<String> messages = logger.getLog();
        int index1, index2;
        index1 = messages.get(0).indexOf(message1);
        index2 = messages.get(1).indexOf(message2);
        assertTrue(index1 > 0 && index2 > 0);
    }
}
