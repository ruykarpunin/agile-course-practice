package ru.unn.agile.IntersectionFinder.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class LoggerTests {
    private static final String LOG_FILE_NAME = "./LoggerTests.log";
    private Logger logger;

    @Before
    public void setUp() {
        logger = new Logger(LOG_FILE_NAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        assertTrue(new File(LOG_FILE_NAME).isFile());
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test";

        logger.log(testMessage);

        String message = logger.getLog().get(0);
        assertThat(message, containsString(testMessage));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String message1 = "Test 1";
        String message2 = "Test 2";

        logger.log(message1);
        logger.log(message2);

        List<String> messages = logger.getLog();
        assertThat(messages.get(0), containsString(message1));
        assertThat(messages.get(1), containsString(message2));
    }
}
