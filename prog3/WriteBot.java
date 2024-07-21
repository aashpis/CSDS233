package prog3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.lang.StringBuffer;
import java.util.Set;

public class WriteBot {

    
    public HashMap<String, HashMap<Character, Integer>> analyzeSource(File source) {
        //nested hashmap holds char occurences 
        HashMap<String, HashMap<Character,Integer>> pMap = new HashMap<>();
        
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
            System.exit(1);
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

            // adds new innerMap, adds current nextchar if substring is not in pMap
            if (!pMap.containsKey(substring)) {
                HashMap<Character, Integer> innerMap = new HashMap<>();
                pMap.put(substring, innerMap);
                pMap.get(substring).put(nextChar,1);
            } else {
                // get innerMap at substring, get nextChar value, increment by 1 
                HashMap<Character,Integer> innerMap = pMap.get(substring);
                if(!innerMap.containsKey(nextChar)){
                    innerMap.put(nextChar,1);
                } else {
                    int occurences = innerMap.get(nextChar);
                    innerMap.replace(nextChar, occurences++);
                }
            }
        }
        return pMap;
    }

    /**
     * 
     * @param probabilityMap Hashmap of k length substrings and following char frequencey
     * @param textLength length of text to generate
     * @return generated text to process
     */
    public String generateText(HashMap<String, HashMap<Character, Integer>> probabilityMap, int textLength) {

        long startTime = System.currentTimeMillis(); // save start time to calc runtime of method
        
        int length = textLength;
        HashMap<String, HashMap<Character, Integer>> pMap = probabilityMap;

        StringBuilder result = new StringBuilder(); // stores generated text

        Random random = new Random(); // use for random ints
        String textSeed; // records current text seed to get next generated char
        // put all pMap keys in a ArrayList
        // TODO: using an array uses too much memory and limits the upper bounds of k and length
        List<String> keys = new ArrayList<>(pMap.keySet());
        // select a random substring key
        textSeed = keys.get(random.nextInt(keys.size()));
        result.append(textSeed);

        while (result.length() < length) {

            // get map of next character occurences at index of current textSeed
            HashMap<Character,Integer> pChar = pMap.get(textSeed);
            // holds next character to be appended
            char nextChar = '\0';
            
            //calculate total occurences
            int totalOccurrences = 0;             
            Collection<Integer> charCounts = pChar.values();
            for(Integer count : charCounts)
                totalOccurrences += count;
            // select random char by subtracting count from random number
            // char is selected if randomNum is <= 0
            Set<Character> charSet = pChar.keySet();    
            int randomNum = random.nextInt(totalOccurrences);    
            for (Character key: charSet){
                randomNum -= pChar.get(key);
                if (randomNum <= 0){
                    nextChar = key;                    
                }
            }

            result.append(nextChar);
            // create new seed with nextChar
            textSeed = textSeed.substring(1) + nextChar;
        }

        // prints time to generate text
        System.out.println(
                "Time taken to generate text: "
                        + (System.currentTimeMillis() - startTime)
                        + " milliseconds");

        return result.toString();

    }
    
    /**
     * writes a string of text to a file
     * 
     * @param text
     * @param fileName
     */
    public void writeToFile(String text, String fileName) throws IOException{
        try{
        // Create a BufferedWriter object using a FileWriter
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)); 
        // Write text to the file
        writer.write(text);
        writer.close();
        System.out.println(fileName + " has been created.");
        } catch (IOException e) {
            System.err.println("error ");
        }
            
    }
    private static int k = 0;
    private static int length = 0;
    private static String result;
    
    public static void main(String[] args){
        
        
        if (args.length != 4) {
            System.out.println("Usage: java WriterBot <int k> <int length> <String source> <String result>");
            return;
        }

        // Validate the first two arg as positive integers
        try {
            k = Integer.parseInt(args[0]);
            length = Integer.parseInt(args[1]);

            if (k < 1 || length < 1) {
                System.err.println("k and length must be positive integers.");
                return;
            }

        } catch (NumberFormatException e) {
            System.err.println("k and length must be positive integers.");
            return;
        }


        String sourceFilePath = "prog3/sourcetexts/" + args[2];

        // checks if file exists
        File source = new File(sourceFilePath);
        if (!source.exists() || !source.isFile()){
            System.err.println("File does not exist");
            return;
        }

        // format output name
        result = args[3];
        if(!result.substring(result.length()-4).equals(".txt")){
            result = args[3]+ "_model-"+ args[2].substring(0,args[2].length()-4) +".txt";
        }
        

        WriteBot bot = new WriteBot();
        try {
            bot.writeToFile(bot.generateText(bot.analyzeSource(source), length), result);
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + e.getMessage());
        }
        
      
      }

}


