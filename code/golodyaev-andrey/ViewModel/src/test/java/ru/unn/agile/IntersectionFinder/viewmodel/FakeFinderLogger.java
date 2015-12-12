package ru.unn.agile.IntersectionFinder.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeFinderLogger implements IFinderLogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public void log(final String message) {
        log.add(message);
    }
}
