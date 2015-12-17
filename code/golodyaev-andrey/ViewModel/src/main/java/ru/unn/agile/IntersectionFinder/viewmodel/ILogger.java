package ru.unn.agile.IntersectionFinder.viewmodel;

import java.util.List;

public interface ILogger {
    void log(final String message);

    List<String> getLog();
}
