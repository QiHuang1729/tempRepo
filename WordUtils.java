
/**
 *	Provides utilities for word games:
 *	1. finds all words in the dictionary that match a list of letters
 *	2. prints an array of words to the screen in tabular format
 *	3. finds the word from an array of words with the highest score
 *	4. calculates the score of a word according to a table
 *
 *	Uses the FileUtils and Prompt classes.
 *	
 *	@author Qi Huang
 *	@since	October 19, 2023
 */
 
public class WordUtils
{
	private String[] words;		// the dictionary of words
	
	// File containing dictionary of almost 100,000 words.
	private final String WORD_FILE = "wordList.txt";
	
	/* Constructor */
	public WordUtils() 
	{ 
		words = new words[100000];
	}
	
	/**	Load all of the dictionary from a file into words array. */
	private void loadWords () 
	{ 
		Scanner input = FileUtils.openToRead(WORD_FILE);
		int index = 0;
		while (input.hasNext())
		{
			words[index] = input.next();
			index++;
		}
	}
	
	/**	Find all words that can be formed by a list of letters.
	 *  @param letters	string containing list of letters
	 *  @return			array of strings with all words found.
	 */
	public String [] findAllWords (String letters)
	{
		String [] wordsFound = new String[words.length];
		for (int i = 0; i < words.length; i++) 
		{
			String word = words[i];
			if (isWordMatch(word, letters)) 
			{
				wordsFound[i] = word;
			}
		}
		
		for (int i = 0; i < wordsFound.length; i++)
		{
			String word = words
		}
		return wordsFound;
	}
	
	/**
	 *  Decides if a word matches a group of letters.
	 *
	 *  @param word  The word to test.
	 *  @param letters  A string of letters to compare
	 *  @return  true if the word matches the letters, false otherwise
	 */
	public boolean isWordMatch (String word, String letters) {
		for (int a = 0; a < word.length(); a++) 
		{
			char c = word.charAt(a);
			if (letters.indexOf(c) > -1) 
				letters = letters.substring(0, letters.indexOf(c)) +
							letters.substring(letters.indexOf(c) + 1);
			else
				return false;
		}
		return true;
	}
	
	/**	Print the words found to the screen.
	 *  @param words	array containing the words to be printed
	 */
	public void printWords (String [] wordList) 
	{
		System.out.println();
		int i = 0;
		while (i < wordList.length && wordList[i] != null) 
		{
			System.out.printf("%-15s", wordList[i]);
			if ((i + 1) % 5 == 0) System.out.println();
			i++;
		}
		System.out.println();
	}
	
	/**	Finds the highest scoring word according to a score table.
	 *
	 *  @param word  		An array of words to check
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return   			The word with the highest score
	 */
	public String bestWord (String [] wordList, int [] scoreTable)
	{
		
		return "";
	}
	
	/**	Calculates the score of one word according to a score table.
	 *
	 *  @param word			The word to score
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return				The integer score of the word
	 */
	public int getScore (String word, int [] scoreTable)
	{
		return 0;
	}
	
	/***************************************************************/
	/************************** Testing ****************************/
	/***************************************************************/
	public static void main (String [] args)
	{
		WordUtils wu = new WordUtils();
		wu.run();
	}
	
	public void run() {
		String letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
		String [] word = findAllWords(letters);
		System.out.println();
		printWords(word);
		
		// Score table in alphabetic order according to Scrabble
		int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
		String best = bestWord(word,scoreTable);
		System.out.println("\nHighest scoring word: " + best + "\nScore = " 
							+ getScore(best, scoreTable) + "\n");
	}
}
