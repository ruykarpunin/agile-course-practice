package ru.unn.agile.Huffman.Model;

import org.junit.Test;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

public class HuffmanTest {

    private final String lEASY = "ab", lONE_LETTER = "a", lDOUBLE = "aabb",
            lSENTENCE = "The quick brown fox trips over the lazy dog.",
            lCAPITALS = "aAaAaAaAaA",
            lSENTENCE2 = "This is a test sentence for Huffman's algorithm.";

    @Test
    public void testEasy() {
        Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(lEASY));
        assertEquals(lEASY, Huffman.decode(tree, Huffman.encode(
                Huffman.buildEncodingMap(tree), lEASY)));
    }

    @Test
    public void testOneLetter() {
        Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(lONE_LETTER));
        assertEquals(lONE_LETTER, Huffman.decode(tree, Huffman.encode(
                Huffman.buildEncodingMap(tree), lONE_LETTER)));
    }

    @Test
    public void testDouble() {
        Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(lDOUBLE));
        assertEquals(lDOUBLE, Huffman.decode(tree, Huffman.encode(
                Huffman.buildEncodingMap(tree), lDOUBLE)));
    }

    @Test
    public void testSentence() {
        Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(lSENTENCE));
        assertEquals(lSENTENCE, Huffman.decode(tree, Huffman.encode(
                Huffman.buildEncodingMap(tree), lSENTENCE)));
    }

    @Test
    public void testCapitals() {
        Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(lCAPITALS));
        assertEquals(lCAPITALS, Huffman.decode(tree, Huffman.encode(
                Huffman.buildEncodingMap(tree), lCAPITALS)));
    }

    @Test
    public void testSentence2() {
        Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(lSENTENCE2));
        assertEquals(lSENTENCE2, Huffman.decode(tree, Huffman.encode(
                Huffman.buildEncodingMap(tree), lSENTENCE2)));
    }
    @Test (timeout = 1000)
    public void testFrequencyTable1() {
        String s = "aaabcdzz";
        Map<Character, Integer> map = Huffman.buildFrequencyMap(s);
        assertEquals((Integer) 3, map.get('a'));
        assertEquals((Integer) 1, map.get('b'));
        assertEquals((Integer) 1, map.get('c'));
        assertEquals((Integer) 1, map.get('d'));
        assertEquals((Integer) 2, map.get('z'));
    }
    @Test (timeout = 1000)
    public void testBuildHuffmanTree1() {
        HashMap<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
        frequencyMap.put('a', 5);
        frequencyMap.put('b', 1);
        frequencyMap.put('c', 2);
        Node tree = Huffman.buildHuffmanTree(frequencyMap);
        assertEquals((Integer) 8, (Integer) tree.getFrequency());
        assertEquals((Integer) 3, (Integer) tree.getLeft().getFrequency());
        assertEquals((Integer) 5, (Integer) tree.getRight().getFrequency());
        assertEquals((Integer) 1, (Integer) tree.getLeft().getLeft().getFrequency());
        assertEquals((Integer) 2, (Integer) tree.getLeft().getRight().getFrequency());
    }
    @Test (timeout = 1000)
    public void testBuildEncodingMap1() {
        Node rrr = new Node('a', 5);
        Node rrl = new Node('b', 4);
        Node rr = new Node(rrl, rrr);
        Node rl = new Node('c', 3);
        Node r = new Node(rl, rr);
        Map<Character, EncodedString> encodingMap = Huffman.buildEncodingMap(r);

        assertEquals("0", HuffmanTest.encodedString(encodingMap.get('c')));
        assertEquals("10", HuffmanTest.encodedString(encodingMap.get('b')));
        assertEquals("11", HuffmanTest.encodedString(encodingMap.get('a')));
    }
    public static String encodedString(final EncodedString s) {
        String toReturn = "";
        Iterator<Byte> iterator = s.iterator();
        while (iterator.hasNext()) {
            if ((int) iterator.next() == 1) {
                toReturn += "1";
            } else {
                toReturn += "0";
            }
        }
        return toReturn;
    }
}
