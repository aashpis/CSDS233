package prog3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.StringBuffer;

public class WriteBot {
    // substrings are keys to linkedlist are value
    // LinkedList contain all chars that follow substring
    // each Pnode, records char and its occurences
    private HashMap<String, LinkedList<Pnode>> pMap = new HashMap<>();

    public void analyzeSource(File source) {

        StringBuilder sourceText = new StringBuilder();

        // iterate through source text
        // check if source text input is valid
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sourceText.append(line.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading the source file: " + e.getMessage());
            System.exit(1);
        }

        // source must be longer than k arg
        String text = sourceText.toString();
        if (text.length() <= k) {
            System.err.println("The source file must contain more than " + k + " characters.");
            return;
        }

        // iterate through source
        // create substrings of k length
        // save char that follows substring
        for (int i = 0; i < text.length() - k; i++) { // stop iterating when no more substrings can be made
            String substring = text.substring(i, i + k);
            char nextChar = '\0';
            if (i + k < text.length()) {
                nextChar = text.charAt(i + k);
            }

            //adds new list and node if substring is not in pMap
            if (!pMap.containsKey(substring)) {
                LinkedList<Pnode> list = new LinkedList<>();
                list.add(new Pnode(nextChar));
                pMap.put(substring, list);
            // checks for char in list at substring index
            // increments Pnode if found, adds new Pnode with char if not.      
            } else {
                LinkedList<Pnode> list = pMap.get(substring);
                boolean found = false;
                for (Pnode node : list) {
                    if (node.getChar() == nextChar && !found) {
                        node.increment();
                        found = true;
                    }
                }
                if (!found) {
                    list.add(new Pnode(nextChar));
                }
            }
        }
    }

    public void generateText(){

        Random random = new Random(); 

        StringBuilder result = new StringBuilder(); // holds generated text
        String textSeed; // records current text seed

        // put all pMap keys in a ArrayList
        List<String> keys = new ArrayList<>(pMap.keySet());
        // select a random key
        textSeed = keys.get(random.nextInt(keys.size()));

        LinkedList<Pnode> pChar = pMap.get(textSeed);
        int pSum = 0;
        for (Pnode node : pChar){
            pSum += node.getCount();
        }

        


        }


    final static int k = 0;
    final static int length = 0;

    public static void main(String[] args){
        
        String result;
        
        if (args.length != 4) {
            System.out.println("Usage: java WriterBot <int k> <int length> <String source> <String result>");
            return;
        }

        // Validate the first two arg as positive integers
        try {
            WriteBot.k = Integer.parseInt(args[0]);
            WriteBot.length = Integer.parseInt(args[1]);

            if (k < 1 || length < 1) {
                System.err.println("k and length must be positive integers.");
                return;
            }

        } catch (NumberFormatException e) {
            System.err.println("k and length must be positive integers.");
            return;
        }

        
        // checks if file exists
        File source = new File(args[2])
        if (!source.exists() || !source.isFile()){
            System.err.println("File does not exist");
            return;
        }    

        
    

        
        
    }

}
