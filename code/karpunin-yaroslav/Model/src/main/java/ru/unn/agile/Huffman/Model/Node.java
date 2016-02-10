package ru.unn.agile.Huffman.Model;
public class Node implements Comparable<Node>, java.io.Serializable {
    private Integer timer = 0;
    private Integer time;
    private Integer frequency;
    private Character character;
    private Node left;
    private Node right;
    public Node getLeft() { return  left; }
    public Node getRight() { return  right; }
    public Node(final Character character, final Integer frequency) {
        this.time = timer++;
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
    public Node(final Node less, final Node more) {
        this.time = timer++;
        this.character = 0;
        this.frequency = less.frequency + more.frequency;
        this.left = less;
        this.right = more;
    }
    public Character getCharacter() { return character; }
    public void setCharacter(final Character value) { character = value; }
    public Integer getTime() { return time; }
    public void setTime(final Integer value) { time = value; }
    public Integer getTimer() { return timer; }
    public void setTimer(final Integer value) { timer = value; }
    public Integer getFrequency() { return frequency; }
    @Override
    public int compareTo(final Node that) {
        if (this.frequency == that.frequency) {
            if (this.character == that.character) {
                return this.time - that.time;
            } else {
                return this.character - that.character;
            }
        } else {
            return this.frequency - that.frequency;
        }
    }
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Node) {
            return ((Node) obj).time == time;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return time;
    }
}
