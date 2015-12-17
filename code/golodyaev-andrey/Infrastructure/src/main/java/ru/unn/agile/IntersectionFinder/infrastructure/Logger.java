package ru.unn.agile.IntersectionFinder.infrastructure;

import ru.unn.agile.IntersectionFinder.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Logger implements ILogger {
    private static final String DATE_FORMAT = "dd.MM.yy HH:mm:ss";
    private final BufferedWriter writer;
    private final String logFileName;

    public Logger(final String logFileName) {
        this.logFileName = logFileName;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.writer = writer;
    }

    private static String getTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    @Override
    public void log(final String message) {
        try {
            writer.write(getTime() + " > " + message);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(logFileName));
            String logLine = reader.readLine();
            while (logLine != null) {
                log.add(logLine);
                logLine = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }
}
