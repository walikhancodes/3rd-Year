/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. [Wali Khan, 2308097]
*/
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Trie {
    public static class TrieNode {
        // each child of a TrieNode corresponds to a certain letter
        private HashMap<Character, TrieNode> charMap = new HashMap<>();
        //boolean  to indicate the end of a word
        private boolean isLeaf;
        //methods insert and contains
        public void insert(String s) {
            if (charMap.containsKey(s.charAt(0))) {
                return;
            } else {
                TrieNode newNode = new TrieNode();
                charMap.put(s.charAt(0), newNode);
            }
        }
        public boolean contains(String s) {
            if(charMap.containsKey(s.charAt(0))){
                return true;
            } else {
                return false;
            }
        }
        // extra protected methods give accress to charMap. update isLeaf
        protected HashMap<Character, TrieNode> getChildren() {
            return charMap;
        }
        protected void setWordEnd(boolean x) {
            isLeaf = x;
        }

    } // end of TrieNode class

    public TrieNode root = new TrieNode();

    public void insert(String s) {
        // your implementation goes here
        TrieNode current = root;
        for (char c : s.toCharArray()) {
            String result = "";
            current.insert(result + c);
            current = current.getChildren().get(c);
        }
        current.setWordEnd(true);
    }

    public boolean contains(String s) {
        // your implementation goes here
        // warning: tries normally need the list of words to be
        // prefix-free
        TrieNode current = root;
        String input;
        for (char c : s.toCharArray()) {
            input = "";
            if (current.contains(input + c)){
                current = current.getChildren().get(c);
            } else {
                return false;
            }
        }
        if(current.isLeaf){
            return true;
        } else {
            return false;
        }
    }

    // given a file with one word per line, insert all the words in the
    // file into the trie
    public void insertDictionary(String filename) {
        // your implementation goes here
        // StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                insert(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

} // end of Trie class