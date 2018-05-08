// Yicheng Wang, CSE 143, Summer 2015, Section AD
// Programming Assignment #7: Huffman Coding (HuffmanNode)
// 08/13/2015
// Huffman Coding is an algorithm for compressing data to make a file 
// occupy a smaller amount of space
// This program constructs a single piece of a huffman code that is comparable by frequencies

public class HuffmanNode implements Comparable<HuffmanNode> {
   public char ch;
   public int frequency;
   public HuffmanNode left;
   public HuffmanNode right;
                
    // constructs a leaf node
   public HuffmanNode(char ch, int frequency) {
       this(ch, frequency, null, null);
   }
                        
    // constructs a branch node, left subtree, right subtree
   public HuffmanNode(char ch, int frequency, HuffmanNode left, HuffmanNode right) {
      this.ch = ch;
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   // implements the comparable interface and compareTo method to compare frequencies
   public int compareTo(HuffmanNode other) {
      return this.frequency - other.frequency;
   }
}