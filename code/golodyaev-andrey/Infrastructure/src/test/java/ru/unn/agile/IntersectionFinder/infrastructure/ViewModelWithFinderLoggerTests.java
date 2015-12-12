package ru.unn.agile.IntersectionFinder.infrastructure;

import ru.unn.agile.IntersectionFinder.viewmodel.ViewModel;
import ru.unn.agile.IntersectionFinder.viewmodel.ViewModelTests;

public class ViewModelWithFinderLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        FinderLogger logger = new FinderLogger("./FinderLoggerTests.log");
        super.setViewModel(new ViewModel(logger));
    }
}
