package ru.unn.agile.Huffman.viewmodel;

import ru.unn.agile.Huffman.model.Huffman;

public class ViewModel {
    private String HuffmanString;
    private String result;
    private String status;
    private boolean isCalculateButtonEnabled;

    public ViewModel() {
        HuffmanString = "";
        result = "";
        status = Status.WAITING;

        isCalculateButtonEnabled = false;
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

        Map<Character, Integer> freqMap2 = Huffman.buildFrequencyMap(HuffmanString);
        Node tree2 = Huffman.buildHuffmanTree(freqMap2);
        Map<Character, EncodedString> encMap2 = Huffman.buildEncodingMap(tree2);
        for (Map.Entry<Character, EncodedString> entry2 : encMap2.entrySet()) {
            System.out.println(entry2.getKey() + ": " + encString(entry2.getValue()));
        }
        EncodedString string2 = Huffman.encode(encMap2, HuffmanString);

        result = string2 +"";
        status = Status.SUCCESS;
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
    }

    public final class Status {
        public static final String WAITING = "Please provide input data";
        public static final String READY = "Press button or Enter";
        public static final String SUCCESS = "Success";

        private Status() { }
    }
}
