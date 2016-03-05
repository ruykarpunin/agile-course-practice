package ru.unn.agile.Huffman.infrastructure;

import ru.unn.agile.Huffman.viewmodel.ViewModel;
import ru.unn.agile.Huffman.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
