package ru.unn.agile.Huffman.viewmodel;

import ru.unn.agile.Huffman.Model.Huffman;
import ru.unn.agile.Huffman.Model.EncodedString;
import ru.unn.agile.Huffman.Model.Node;
import ru.unn.agile.Huffman.Model.HuffmanFileEncode;

import java.util.*;

public class ViewModel {
    private String HuffmanString;
    private String result;
    private String status;
    private boolean isCalculateButtonEnabled;
    private boolean isInputChanged;
    private ILogger logger;


    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        HuffmanString = "";
        result = "";
        status = Status.WAITING;
        isCalculateButtonEnabled = false;
        isInputChanged = true;
    }




    public void processKeyInTextField(final int keyCode) {
        parseInput();

        if (keyCode == KeyboardKeys.ENTER) {
            enterPressed();
        }
    }

    private void enterPressed() {

        if (isCalculateButtonEnabled()) {
            calculate();
        }
    }
    private void logInputParams() {
        if (!isInputChanged) {
            return;
        }

        logger.log(editingFinishedLogMessage());
        isInputChanged = false;
    }

    public void focusLost() {
        logInputParams();
    }
    private String editingFinishedLogMessage() {
        String message = LogMessages.EDITING_FINISHED
                + "Input argument is: "
                + HuffmanString ;


        return message;
    }
    public boolean isCalculateButtonEnabled() {
        return isCalculateButtonEnabled;
    }

    private boolean isInputAvailable() {
        return !HuffmanString.isEmpty();
    }

    private boolean parseInput() {
        isCalculateButtonEnabled = isInputAvailable();
        if (isCalculateButtonEnabled) {
            status = Status.READY;
        } else {
            status = Status.WAITING;
        }

        return isCalculateButtonEnabled;
    }

    public void calculate() {
        logger.log(calculateLogMessage());

        Map<Character, Integer> freqMap2 = Huffman.buildFrequencyMap(HuffmanString);
        Node tree2 = Huffman.buildHuffmanTree(freqMap2);
        Map<Character, EncodedString> encMap2 = Huffman.buildEncodingMap(tree2);
        for (Map.Entry<Character, EncodedString> entry2 : encMap2.entrySet()) {
            System.out.println(entry2.getKey() +"");
        }
        EncodedString string2 = Huffman.encode(encMap2, HuffmanString);

        result = string2 +"";
        status = Status.SUCCESS;
    }
    private String calculateLogMessage() {
        String message =
                LogMessages.CALCULATE_WAS_PRESSED + "Arguments"
                        + ": String = " + HuffmanString+ ".";

        return message;
    }
    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getHuffmanString() {
        return HuffmanString;
    }

    public void setHuffmanString(final String HuffmanString) {
        this.HuffmanString = HuffmanString;
        isInputChanged = true;
    }

    public final class Status {
        public static final String WAITING = "Please provide input data";
        public static final String READY = "Press button or Enter";
        public static final String SUCCESS = "Success";

        private Status() { }
    }
    public final class LogMessages {
        public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
        public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
        public static final String EDITING_FINISHED = "Updated input. ";

        private LogMessages() { }
    }
}
