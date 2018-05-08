// Yicheng Wang, CSE 143, Summer 2015, Section AD
// Programming Assignment #7: Huffman Coding
// 08/13/2015
// Huffman Coding is an algorithm for compressing data to make a file 
// occupy a smaller amount of space
// This program represents a "huffman code" which is used to compress data

import java.util.*;
import java.io.*;

public class HuffmanCode {
   private HuffmanNode root;
   
   // pre: take in an array of frequencies where 
   //      its position is the count of the character with ASCII value i
   // post: be able to initialize a new HuffmanCode object that is built up by frequencies
   public HuffmanCode(int[] frequencies) {
      // create a queue ordered by frequency
      Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < frequencies.length; i++) {
         if(frequencies[i] > 0) {
            HuffmanNode huff = new HuffmanNode((char)i, frequencies[i]);
            pq.add(huff);
         }
      }
      
      // create the HuffmanCode object by combining the elements in the queue
      while(pq.size() > 1) {
         HuffmanNode first = pq.remove();
         HuffmanNode second = pq.remove();
         int combineFreq = first.frequency + second.frequency;
         HuffmanNode combine = new HuffmanNode(' ', combineFreq, first, second);
         pq.add(combine);
      }
      root = pq.peek();
   }
   
   // pre: reading in a previously constructed code 
   // post: initialize a new HuffmanCode object
   public HuffmanCode(Scanner input) {
      while(input.hasNextLine()) {
         int ascii = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         root = HuffmanCode(root, input, ascii, code);
      }
   } 
   
   // pre: take in the current position, input file, each ascii and code read in the file
   // post: a private helper method that returns a constructed HuffmanCode object
   private HuffmanNode HuffmanCode(HuffmanNode curr, Scanner input, int ascii, String code) {
      if(code.length() == 0) {
         curr = new HuffmanNode((char)ascii, 0);
         
      } else {
         if(curr == null) {
            curr = new HuffmanNode((char)-1, 0, null, null);
         }  
         char bit = code.charAt(0);
         if(bit == '0') {
            curr.left = HuffmanCode(curr.left, input, ascii, code.substring(1));
         } else {
            curr.right = HuffmanCode(curr.right, input, ascii, code.substring(1));
         }
      }     
      return curr;
   }
   
   // pre: take in a output printstream
   // post: store the current huffman codes to the given output stream
   public void save(PrintStream output) {
      save(output, root, "");
   }
   
   // pre: take in a output stream, the current node, the string code to be printed
   // post: a private helper method that prints the code corresponding to characters
   private void save(PrintStream output, HuffmanNode current, String code) {
      if(current != null) {
         // the base case: print the code corresponding to the character
         if(current.left == null && current.right == null) {
            output.println((int)current.ch);
            output.println(code);
         
         // recursive case   
         } else {
            save(output, current.left, code + "0");
            save(output, current.right, code + "1"); 
         }
      }
   }
   
   // reading individual bits from the input stream, the code, 
   // and printing to the output stream as characters
   public void translate(BitInputStream input, PrintStream output) {
      HuffmanNode current = root;
      
      while(input.hasNextBit()) {
         // print the character if it reaches the leaf node
         if(current.left == null && current.right == null) {
            output.print(current.ch);
            current = root;
            
         // traverse the HuffmanCode object
         } else {
            if(input.nextBit() == 0) {
               current = current.left;            
            }  
            else {
               current = current.right;
            }
         }
      }
      
      // print the last character
      output.print(current.ch);
   } 
   
}