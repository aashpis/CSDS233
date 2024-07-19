# CSDS 233 Programming Assignment 3
**Instructions:** Please make sure to write well-documented code using good variable names. Make sure to do proper input validation and error handling.

If working as a group, only one solution is needed unless otherwise specified. Each person in each group must commit and push their own work. You will not get credit for work committed/pushed by someone else even if done by you. Each person is expected to do an approximately equal share of the work, as shown by the git logs. **If we do not see evidence of equal contribution from the logs for someone, their individual grade will be reduced.** Please comment the last commit with "FINAL COMMIT" and **enter the final commit ID in canvas by the due date.**

All classes in this assignment should be in the package __prog3__. You __may__ use non-primitive Java data types for any part of this assignment. Max points: 100.

In this assignment you will generate a random text based on a provided source. Implement a class WriterBot. Your class should have a main method that takes the following four command line arguments. 

1.	A non-negative integer $k$.
2.	A non-negative integer $length$.
3.	The name of an input text file ___source___ that contains more than $k$ characters.
4.	The name of an output file ___result___. 

Your program should validate the inputs properly. If any of the command line arguments are invalid, your program should write an informative error message to System.err and terminate. Otherwise, your program should analyze the ___source___ text. First, convert the text into lowercase. Then in a suitable data structure that you choose, your program should store the probabilities of each character coming after all possible length $k$ character sequences in the source text. For example, if $k=2$ your data structure should contain the probability that the character ‘c’ will follow the string “ab” (assuming “abc” appears in the source). Similarly, if $k=3$, it will contain the probability that the character ‘d’ will follow the string “abc”, and so on. To calculate these probabilities, find every occurrence of each string of length $k$, and find what characters follow it and their frequencies. Then set the probabilities to be proportional to the frequencies. 


Once the source has been processed, you will use these probabilities to repeatedly choose the next character of the text, up to the given length. This will let your program generate a text that “looks like” the source, but is random. This is essentially the way generators like ChatGPT work, except the probability models are much more sophisticated.


For example, suppose that $k = 2$ and the source file contains

```the thrown rock then flew that way, through the thin feather pillow. ```

Here is how the first three characters might be chosen:
A $k$-character seed is chosen at random from the source to become the initial seed. Here $k=2$ so let's suppose that “th” is chosen. The first character must be chosen based on the probability that it follows the seed (currently “th”) in the source. The source contains eight occurrences of “th”, followed by __e__ (4), __r__ (2), __a__ (1), __i__ (1). Thus, the next character must be chosen so that there is a $\frac{4}{8}$ chance that an ‘e’ will be chosen, a $\frac{2}{8}$ chance that an ‘r’ will be chosen, etc. Suppose ‘e’ is chosen.

The next character must be chosen based on the probability that it follows the seed (currently “he”) in the source. The source contains three occurrences of “he”, followed by __space__ (1), __n__ (1) and __r__ (1). So each has probability $\frac{1}{3}$ to be chosen. Let's suppose that we choose an ‘r’.

The next character must be chosen based on the probability that it follows the seed (currently “er”) in the source. The source contains only one occurrence of “er”, and it is followed by a space. Thus, the next character must be a space. 

So the general strategy is to pick $k$ consecutive characters at random from source and use them as the initial seed. Your program should then write $length$ characters to result. Each of these additional characters should be chosen based on the current seed and the associated probabilities. 

If your program ever gets into a situation in which there are no characters to choose from (which can happen if the only occurrence of the current seed is at the exact end of the source), your program should print a period, pick a new random seed and continue generation.

When the generation is complete, before writing to the result file, output a message to the terminal stating how long the generation process took.

By increasing $k$, you can make the generated text increasingly realistic, although the cost of storing the probabilities grows quickly. This is the importance of the data structure you use, and different data structures may allow you to more efficiently reach higher values of $k$. You can experiment with different data structures, and also read about and implement data structures not discussed in class that may be well suited to this problem. Particularly good choices and implementations of such data structures may receive extra credit (up to 20 points at our discretion). If you implement or use different data structures, create a command line option to specify the data structure to use, and document the choice in a README.md. You may need good intermediate data structures to store the source and search through it as you create the probabilities as well.

The following resources can be helpful:

1.	java.util.Random to generate random integers in a specified range. 
2.	java.io.FileWriter object is useful for writing strings and characters to a file. 
3.	java.io.FileReader object is useful for reading one character at a time from a file. 
4.	java.lang.StringBuffer or StringBuilder object is useful for efficiently composing characters into a string. 
5.	[Project Gutenberg](https://www.gutenberg.org/) maintains a huge library of public domain books that you can use as source texts. Try generating texts in the style of a specific author!

Feel free to create other helper classes as needed besides WriterBot.

Your project must contain a README documenting: (1) the overall structure of your project (what classes you used and you broke down the given tasks) and (2) several trial runs with different sources and k values and the resulting output (both text and time). Include the sources you used in your repository. Note that we will test your code with other sources. 
