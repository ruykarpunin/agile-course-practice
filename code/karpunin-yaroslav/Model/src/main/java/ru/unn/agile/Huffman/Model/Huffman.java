package ru.unn.agile.Huffman.Model;

import java.util.*;

public final class Huffman {
    private Huffman() {
    }
    /**
     * Builds a frequency map of characters for the given string.
     *
     * This should just be the count of each character.
     *
     * @param s
     * @return
     */
    public static Map<Character, Integer> buildFrequencyMap(final String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        Integer freq;
        for (Character c : s.toCharArray()) {
            freq = freqMap.get(c);
            if (null == freq) {
                freq = 1;
            } else {
                freq = ++freq;
            }
            freqMap.put(c, freq);
        }

        return freqMap;
    }

    /**
     * Build the Huffman tree using the frequencies given.
     *
     * The frequency map will not necessarily come from the buildFrequencyMap() method.
     *
     * @param freq
     * @return
     */
    public static Node buildHuffmanTree(final Map<Character, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        if (pq.size() == 1) {
            pq.add(new Node(pq.poll(), new Node((char) 0, 0)));
        } else {
            while (pq.size() > 1) {
                pq.add(new Node(pq.poll(), pq.poll()));
            }
        }
        return pq.poll();
    }

     /**
      * Traverse the tree and extract the encoding for each character in the tree
      *
      * The tree provided will be a valid huffman tree,
     * but may not come from the buildHuffmanTree() method.
      *
      * @param tree
      * @return
      */
     public static Map<Character, EncodedString> buildEncodingMap(final Node tree) {
        return buildEncodingMap(tree, new EncodedString()
                , new HashMap<Character, EncodedString>());
     }

     private static Map<Character, EncodedString>
     buildEncodingMap(final Node tree, final EncodedString soFar,
                      final Map<Character, EncodedString> encMap) {
         if (tree.getCharacter() != 0) {
             EncodedString str = new EncodedString();
             str.concat(soFar);
             encMap.put(tree.getCharacter(), str);
        }
         if (null != tree.getLeft()) {
             EncodedString str = new EncodedString();
             str.concat(soFar);
             str.zero();
             buildEncodingMap(tree.getLeft(), str, encMap);
        }
         if (null != tree.getRight()) {
             EncodedString str = new EncodedString();
             str.concat(soFar);
             str.one();
             buildEncodingMap(tree.getRight(), str, encMap);
        }
         return encMap;
     }

    /**
     * Encode each character in the string using the map provided.
     *
     * If a character in the string doesn't exist in the map ignore it.
     *
     * The encoding map may not necessarily come from the buildEncodingMap() method,
     * but will be correct
     * for the tree given to decode() when decoding this method's output.
     *
     * @param encodingMap
     * @param s
     * @return
     */
    public static EncodedString encode(final Map<Character, EncodedString> encodingMap,
                                       final String s) {
        EncodedString str = new EncodedString();
        for (char c : s.toCharArray()) {
            if (null != encodingMap.get(c)) {
                str.concat(encodingMap.get(c));
            }
        }
        return str;
    }
    /**
     * Decode the encoded string using the tree provided.
     *
     * The encoded string may not necessarily come from encode,
     * but will be a valid string for the given tree.
     *
     * (tip: use StringBuilder to make this method faster -- concatenating strings is SLOW)
     *
     * @param tree
     * @param es
     * @return
     */
    public static String decode(final Node tree, final EncodedString es) {
        Iterator<Byte> iter = es.iterator();
        StringBuilder builder = new StringBuilder();
        Byte next;
        Node curr = tree;
        while (iter.hasNext()) {
            next = iter.next();
            curr = next == 0 ? curr.getLeft() : curr.getRight();
            if (null != curr && curr.getCharacter() != 0) {
                builder.append(curr.getCharacter());
                curr = tree;
            }
        }

        return builder.toString();
    }
}
