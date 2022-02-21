
/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. [Wali Khan, 2308097]
*/
import java.util.Stack;
public class WordSplitter {
    static Trie t = new Trie();

    public static void main(String[] args) {
        // Consider using the following if you haven't done Trie.java yet:
        // HashSet<String> dictionary;

        // your implementation goes here
        t.insertDictionary(args[0]); // should be words.txt or whichever file you want to put in to the trie
        System.out.println(findSplit(args[1])); // takes input and finds splitting
    }

    public static String findSplit(String x) {
        String result = "";// this is where we store the resulting string
        String s = "" + x.charAt(0);    // this is the current substirng of x 
        int j = 0; // this is the last index of a word to inspect
        for (int i = 0; i < x.length(); i++) {  
            s = x.substring(j, i + 1);  // substring adds one letter at a time to see if the resulting word is in the trie 
            if (t.contains(s)) {
                if (!checkPre(x, i, j)) {   // if the word is a prefix of another word later down the String x nothing is updated
                    result = result + s + " ";
                    j = i + 1;
                  
                } else if (checkOverlap(x, j)) {    // if the word found has overlapping letters with other words in the String x 
                    result = result + s + " ";  //the result should then add String s since if we were to keep going it would take up letters of another word
                    j = i + 1;  //updating j so that the String s has no letters it doesnt need anymore 
                } else {
                }
            }
        }
        if (t.contains(s)) {    // at the very end the last word should have been in the trie if not there is no split found
            return result;
        } else {
            return "No splitting found";
        }
    }

    public static boolean checkPre(String x, int i, int start) {    // this method checks if a word is the prefix of another word in String x
        int p = i + 1;
        String s = x.substring(start, p);
        String y = "";
        int j = i + 1;
        while (j < x.length()) {
            y = x.substring(p, j + 1);
            if (t.contains(s + y)) {
                return true;
            } else {
                j++;
            }
        }
        return false;
    }

    public static boolean checkOverlap(String x, int start) {   // this method checks if two adjeacent words in String x has overlapping letters 
        Stack<String> w = new Stack<String>(); // words added just normally
        Stack<String> z = new Stack<String>(); // words added using checkPre
        String s = x.substring(start, start + 1);
        int j = start;
        int p = start;
        while (p < x.length()) {// this is where we add all the words found without checking to see if a word is the prefix of another word
            s = x.substring(j, p + 1);
            if (t.contains(s)) {
                w.push(s);
                s = "";
                j = p + 1;
                p++;
            } else {
                p++;
            }
        }
        j = start;
        p = start;
        while (p < x.length()) {    // this is where we add all the words found with checking to see if a word is the prefix of another word
            s = x.substring(j, p + 1);
            if (t.contains(s) && p < x.length() - 1) {
                if (!checkPre(x, p + 1, j)) {
                    z.push(s);
                    s = "";
                    j = p + 1;
                    p++;
                } else {
                    p++;
                }
            } else {
                p++;
            }
        }
        if (z.isEmpty()) { 
            return false;
        } else {
            while(!w.isEmpty()){ 
                if(z.isEmpty()){
                    return false;
                }
                if(z.size() < w.size()){    // detects an overlapped word 
                    return true;
                }
                if(z.pop().length() < w.pop().length()){ // detects an overlapped word 
                    return true;
                } 
            } 
            return false;
        }

    }
}
