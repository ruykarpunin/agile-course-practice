package ru.unn.agile.Huffman.Model;


import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class HuffmanTests {


    @Test
    public void freqMapTest() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("Geewillickersg");
        assertEquals(new Integer(1), freqMap.get('G'));
        assertEquals(new Integer(3), freqMap.get('e'));
        assertEquals(new Integer(1), freqMap.get('w'));
        assertEquals(new Integer(2), freqMap.get('i'));
        assertEquals(new Integer(2), freqMap.get('l'));
        assertEquals(new Integer(1), freqMap.get('c'));
        assertEquals(new Integer(1), freqMap.get('k'));
        assertEquals(new Integer(1), freqMap.get('r'));
        assertEquals(new Integer(1), freqMap.get('s'));
        assertEquals(new Integer(1), freqMap.get('g'));
    }

    @Test
    public void freqMapTest2() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("Galifinacis");
        assertEquals(new Integer(1), freqMap.get('G'));
        assertEquals(new Integer(2), freqMap.get('a'));
        assertEquals(new Integer(1), freqMap.get('l'));
        assertEquals(new Integer(3), freqMap.get('i'));
        assertEquals(new Integer(1), freqMap.get('f'));
        assertEquals(new Integer(1), freqMap.get('n'));
        assertEquals(new Integer(1), freqMap.get('c'));
        assertEquals(new Integer(1), freqMap.get('s'));
    }


    @Test
    public void treeTest1() {
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        freqMap.put('b', 1);
        freqMap.put('p', 1);
        freqMap.put('j', 3);
        freqMap.put('u', 5);
        freqMap.put(' ', 12);
        freqMap.put('`', 2);
        freqMap.put('m', 2);
        freqMap.put('r', 5);
        freqMap.put('a', 4);
        freqMap = partOfTreeTest1(freqMap);
    }

    public Map<Character, Integer> partOfTreeTest1(final Map<Character, Integer> pFreqMap) {
        Map<Character, Integer> freqMap = pFreqMap;
        freqMap.put('i', 4);
        freqMap.put('o', 3);
        freqMap.put('d', 3);
        freqMap.put('e', 8);
        freqMap.put('l', 6);
        freqMap.put('s', 6);
        Node tree = Huffman.buildHuffmanTree(freqMap);
        sop("");
        sop("");
        return freqMap;
    }

    @Test
    public void encodingMap1() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("Galifinacis");
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);

        for (Map.Entry<Character, EncodedString> entry : encMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + encString(entry.getValue()));
        }
    }

    @Test
    public void encode1() {
        Map<Character, Integer> freqMap2 = Huffman.buildFrequencyMap("I'm Yaroslav Karpunin");
        Node tree2 = Huffman.buildHuffmanTree(freqMap2);
        Map<Character, EncodedString> encMap2 = Huffman.buildEncodingMap(tree2);
        for (Map.Entry<Character, EncodedString> entry2 : encMap2.entrySet()) {
            System.out.println(entry2.getKey() + ": " + encString(entry2.getValue()));
        }
        EncodedString string2 = Huffman.encode(encMap2, "I'm Yaroslav Karpunin");

        System.out.println("Encoded String: " + Math.ceil(string2.length() / 8) + " bytes");
        System.out.println("Normal String: " + "I'm Yaroslav Karpunin".length() + " bytes");
        System.out.println(encString(string2));

    }

    @Test
    public void decode1() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("I'm Yaroslav Karpunin");
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);
        EncodedString enc = Huffman.encode(encMap, "I'm Yaroslav Karpunin");

        assertEquals("I'm Yaroslav Karpunin", Huffman.decode(tree, enc));
    }

    @Test
    public void decode2() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap(
                "Polly Wolly Snack Pack Pocket");
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);
        EncodedString enc = Huffman.encode(encMap, "Polly Wolly Snack Pack Pocket");

        assertEquals("Polly Wolly Snack Pack Pocket", Huffman.decode(tree, enc));
    }

    @Test
    public void test1() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("aaaaa");
        assertEquals(new Integer(5), freqMap.get('a'));
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);
        EncodedString zero = new EncodedString();
        zero.zero();
        EncodedString enc = Huffman.encode(encMap, "aaaaa");
        assertEquals(5, enc.length());
        assertEquals("aaaaa", Huffman.decode(tree, enc));
    }

    @Test
    public void randomTests() {
        Map<Character, Integer> freqMap;
        Node tree;
        Map<Character, EncodedString> encMap;
        EncodedString enc;
        String message;
        StringBuilder builder;
        for (int i = 0; i < 1000; i++) {
            builder = randomTestsPart2();
            message = builder.toString();
            freqMap = Huffman.buildFrequencyMap(message);
            tree = Huffman.buildHuffmanTree(freqMap);
            encMap = Huffman.buildEncodingMap(tree);
            enc = Huffman.encode(encMap, message);
            assertTrue(randomTestsPart3(i, message, tree, enc));
        }
    }

    private static StringBuilder randomTestsPart2() {
        StringBuilder builder = new StringBuilder();
        Random rand;
        rand = new Random();
        for (int j = 0; j < 10000; j++) {
        builder.append((char) (rand.nextInt(527) + 133));
            }
        return builder;
    }

    private  static  boolean randomTestsPart3(final int i, final String message, final Node tree,
                                              final EncodedString enc) {
        if (i % 50 == 0) {
            System.out.println("Test " + i + " of 10000");
            System.out.println(message);
        }
        boolean passed = message.equals(Huffman.decode(tree, enc));
        if (!passed) {
            System.out.println("FAILED: Message was: " + message);
        }
        return  passed;
    }

    private static String encString(final EncodedString str) {
        Iterator<Byte> iter = str.iterator();
        String result = "";
        while (iter.hasNext()) {
            result += iter.next();
        }
        return result;
    }


    public static void betterPrint(final Node root) {
        if (null != root) {
            LinkedList<Node> list1 = new LinkedList<Node>();
            list1.add(root);
            betterPrint(list1, new LinkedList<Node>(), "");
        }
    }

    private static LinkedList<Node> betterPrint(final LinkedList<Node> lList,
                                                final LinkedList<Node> lList2,
                                                final String lResult) {
        LinkedList<Node> list = lList;
        LinkedList<Node> list2 = lList2;
        String result = lResult;
        if (!list.isEmpty()) {
            Node curr = list.remove();
            result += "  " + curr.getCharacter() + ":" + curr.getFrequency() + "  ";
            list2 = betterPrintPart2(list2, curr);
            list2 = betterPrint(list, list2, result);
        } else if (!list2.isEmpty()) {
            result += "\n";
            list = new LinkedList<>();
            list2 = betterPrint(list2, list, result);
        } else {
            betterPrintPart3(result);
        }
        return list2;
    }
    private static  LinkedList<Node> betterPrintPart2(final LinkedList<Node> lList2,
                                                      final Node curr) {
        LinkedList<Node> list2 = lList2;
        if (null != curr.getLeft()) {
            list2.add(curr.getLeft());
        }
        if (null != curr.getRight()) {
            list2.add(curr.getRight());
        }
        return list2;
    }
    private static void betterPrintPart3(final String lResult) {
        String result = lResult;
        String[] lines = result.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = "";
            for (int j = 0; j < (lines[lines.length - 1].length() - lines[i].length()) / 2;
                 j++) {
                line += " ";
            }
            line += "  " + lines[i] + "  ";
            sop(line);
        }
    }

    private static void sop(final Object o) {
        System.out.println(o.toString());
    }

    private static void print(final Node n, final String depth) {
        String depthIncrementer = "   ";
        System.out.println(depth + n.getCharacter() + ":" + n.getFrequency() + depth);
        if (!(n.getLeft() == null && n.getRight() == null)) {
            if (n.getLeft() != null) {
                print(n.getLeft(), depth + depthIncrementer);
            } else {
                System.out.println(depth + depthIncrementer + "**");
            }
            if (n.getRight() != null) {
                print(n.getRight(), depth + depthIncrementer);
            } else  {
                System.out.println(depth + depthIncrementer + "**");
            }
        }
    }

}
