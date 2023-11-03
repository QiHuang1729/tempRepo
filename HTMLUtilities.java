/**
 *	Utilities for handling HTML
 *
 *	@author	Qi Huang
 *	@since	10/31/2023
 */
public class HTMLUtilities {

	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String str) {
		/** Mr. Greenstein says: "Do a character by character analysis. 
		 * don't do indexOf. You can do loops within loops. Routine
		 * for tag, routine for word, routine for punctuation or number
		 */
		/* for number, I think you can just use parseInt */
		// make the size of the array large to start
		String[] result = new String[10000];
		
		String token = "";
		int tokenNum = 0;
		int index = 0;
		char ch = ' ';
		while (index < str.length()) {
			ch = str.charAt(index);
			if (ch == '<') { // start of a tag token
				 // save the starting index into a variable to be used
				 // later
				 int startIndex = index;
				 
				 // increment index and store the next character
				 // while the character is not the end angle bracket '>'
				 do {
					index++; 
					ch = str.charAt(index);
				 } while (ch != '>');
				 
				 // saves the resulting token
				 token = str.substring(startIndex, index + 1);
				 result[tokenNum] = token;
				 tokenNum++; // number of tokens stored is increased.
				 
				 // increment index after we are done with a token
				 index++;
				 
			} else if (ch.isLetter()) {
				// save the starting index
				int startIndex = index;
				
				// increment index, store the next character
				// while the character is a letter or a hyphen surrounded
				// by letters
				do {
					index++;
					ch = str.charAt(index);
				} while (ch.isLetter() || 
					(ch == '-' && index < str.length() - 1 && 
						str.charAt(index + 1).isLetter()); 
					// the above works due to short-circuit eval
				token = str.substring(startIndex, index);
				result[tokenNum] = token;
				tokenNum++; 
				
			} else if (/* ... */) {
				
			} else {
				// not start of token, so just increment index
				index++;
			}
			
			/* if the first character after word token is a -, it could 
			 * be the start of a number */
			
		}
		
		result = trim(result, tokenNum);
		
		// return the correctly sized array
		return result;
	}
	
	/// bad code with indexOf
	/*  check for <, check for > from that index, create
		 * a substring with those two indices as the endpoints, 
		 * then put the substring into the array /
		int startIndex = -1;
		int endIndex = -1;
		int arrayIndex = 0;
		String token = "";
		
		startIndex = str.indexOf('<');
		while (startIndex != -1) {
			// find the endIndex
			/* if the startIndex is for a '<', then we should look 
			 * for the end of the HTML tag ('>')
			 * if the startIndex is for a sequence of characters, then
			 * we should look for the end of the sequence of characters,
			 * if the startIndex is for punctuation, we don't need to do 
			 * anything
			 * if the startIndex is for numbers, then we need to find
			 * the end of the number, which is the last number. Note that 
			 * the numbers should only contain one e. 
			 * Weird interactions with the e and the strings of words?
			/
			endIndex = str.indexOf('>', startIndex);
			
			// find the token
			token = str.substring(startIndex, endIndex + 1);
			
			// save in to the array
			result[arrayIndex] = token;
			arrayIndex++;
			
			// find the start index again
			/* find the next angle bracket or the next letter, which
			 * indicates the start of a sequence of letter. Then, look 
			 * for a non-hyphen non-alphabetic characters, or a hyphen
			 * that does not have an alphabetic character to the left of it
			
			int temp1 = str.indexOf('<', endIndex);
			int temp2 = nextLetterIndex(str, endIndex);
			//startIndex = str.indexOf('<', endIndex);
		}
	*/
	
	/// bad code that didn't follow assignment suggestions
	/*while (index < str.length()) {
			ch = str.charAt(index);
			if (ch == '<') { // start of a tag token
				 // save the starting index into a variable to be used
				 // later
				 int startIndex = index;
				 
				 // increment index and store the next character
				 // while the character is not the end angle bracket '>'
				 do {
					index++; 
					ch = str.charAt(index);
				 } while (ch != '>');
				 
				 // saves the resulting token
				 token = str.substring(startIndex, index + 1);
				 result[tokenNum] = token;
				 tokenNum++; // number of tokens stored is increased.
				 
				 // increment index after we are done with a token
				 index++;
				 
			} else if (ch.isLetter()) {
				// save the starting index
				int startIndex = index;
				
				// increment index, store the next character
				// while the character is a letter or a hyphen surrounded
				// by letters
				do {
					index++;
					ch = str.charAt(index);
				} while (ch.isLetter() || 
					(ch == '-' && index < str.length() - 1 && 
						str.charAt(index + 1).isLetter()); 
					// the above works due to short-circuit eval
				token = str.substring(startIndex, index);
				result[tokenNum] = token;
				tokenNum++; 
				
			} else if (/* ... ) {
				
			} else {
				// not start of token, so just increment index
				index++;
			}
			
			/* if the first character after word token is a -, it could 
			 * be the start of a number 
		}
	*/
	
	/** Trims an array of strings to a given length
	 * 
	 *  @param input		The input array to be trimmed
	 * 	@param newLength	The length of the trimmed array
	 * 	@return 			The trimmed output
	 */
	private String[] trim (String[] input, int newLength) 
	{
		String[] output = new String[newLength];
		for (int i = 0; i < output.length; i++)
		{
			output[i] = input[i];
		}
		return output;
	}
	
	private int nextLetterIndex(String str, int startIndex) {
		int i = startIndex;
		while (i < str.length()) {
			char ch = str.charAt(i);
			if (Character.isLetter(ch))
				return i;
			i++;
		}
		//return ???
	}
	
	/**
	 *	Print the tokens in the array to the screen
	 *	Precondition: All elements in the array are valid String objects.
	 *				(no nulls)
	 *	@param tokens		an array of String tokens
	 */
	public void printTokens(String[] tokens) {
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0) System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}
	
}
