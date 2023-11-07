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
	 *				returns { "Goodnight" "moon" "goodnight" "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String str) {
		// make the size of the array large to start
		String[] result = new String[10000];
		
		String token = "";
		int tokenNum = 0;
		int strIndex = 0;
		char ch = ' ';
		char nextChar = ' ';
		
		// remember this edge case
		while (strIndex < str.length()) {
			// reset token
			token = "";
			ch = str.charAt(strIndex);
			if (ch == '<') {
				token = assembleTag(str, strIndex);
			} else if (Character.isLetter(ch)) {
				token = assembleWord(str, strIndex);
			} else if (isPunctuation(ch)) {
				if (ch == '-') {
					if (strIndex + 1 < str.length()) {
						nextChar = str.charAt(strIndex + 1);
						if (Character.isDigit(nextChar))
							token = assembleNumber(str, strIndex);
						else if (nextChar == '.' && strIndex + 2 < str.length()) {
							nextChar = str.charAt(strIndex + 2);
							if (Character.isDigit(nextChar))
								token = assembleNumber(str, strIndex);
						} else {
							token = ch + "";
						}
					} else {
						token = ch + "";
					}
				} else if (ch == '.') {
					if (strIndex + 1 < str.length()) {
						nextChar = str.charAt(strIndex + 1);
						if (Character.isDigit(nextChar))
							token = assembleNumber(str, strIndex);
						else 
							token = ch + "";
					} else {
						token = ch + "";
					}
				} else {
					token = ch + "";
				}
			} else if (Character.isDigit(ch)) {
				token = assembleNumber(str, strIndex);
			}
			
			// token.equals("") also occurs when the token assembler returns a
			// "". 
			if (!token.equals("")) {
				result[tokenNum] = token;
				tokenNum++;
				strIndex += token.length();
			} else {
				strIndex++;
			}
		}
		
		result = trim(result, tokenNum);
		
		// return the correctly sized array
		return result;
	}
	
	/** 
	 * assembles an HTML tag token
	 */
	private String assembleTag(String str, int strIndex) {
		String tokenOut = "";
		char ch = ' ';
		char nextChar = '\0';
		
		// looks ahead first
		if (strIndex + 1 < str.length())
			nextChar = str.charAt(strIndex + 1);
			
		// repeatedly checks if the character ahead is not the end of the tag '>' 
		// also checks if nextChar is not reset to default value because the 
		// string has ended
		while (nextChar != '>' && nextChar != '\0') {
			// saves the current character, adds it, and increments index
			ch = str.charAt(strIndex);
			tokenOut += ch;
			strIndex++;
			
			// looks ahead again
			if (strIndex + 1 < str.length())
				nextChar = str.charAt(strIndex + 1);
			else
				nextChar = '\0';
		}
		// exits when the look ahead see an ending tag
		
		// saves the last character before '>'
		ch = str.charAt(strIndex);
		
		// if we aren't at the end of the string, then nextChar should be '>'
		tokenOut += "" + ch + nextChar;
		
		// if the nextChar is reset to default because we've reached the 
		// end of the string before the tag ends, then the method outputs
		// an empty string to signify an error.
		if (nextChar == '\0') {
			tokenOut == "";
		}
		
		return tokenOut;
	}
	
	/** 
	 * assembles a word token 
	 */
	private String assembleWord(String str, int strIndex) {
		String tokenOut = "";
		char ch = ' ';
		char nextChar = '\0';
		
		// look at the next char
		if (strIndex + 1 < str.length())
			nextChar = str.charAt(strIndex + 1);
		
		// condition: if nextChar is good...
		while ((Character.isLetter(nextChar) || nextChar == '-') && nextChar != '\0') {
			//... save the current char and add it to token
			ch = str.charAt(strIndex);
			tokenOut += ch;
			
			// increment strIndex and look at next char
			strIndex++;
			if (strIndex + 1 < str.length())
				nextChar = str.charAt(strIndex + 1);
			else 
				nextChar = '\0';
		}
		
		// if nextChar is not good exit out of the loop. Save the current char. 
		// If it's a letter then it will be saved. If it's a hyphen then it 
		// will not be added to token.
		ch = str.charAt(strIndex);
		if (ch != '-') {
			tokenOut += ch;
		}
		
		return tokenOut;
	}
	
	/** returns true if char ch is a punctuation and false otherwise */
	private boolean isPunctuation(char ch) {
		switch (ch) {
			case '.': case ',': case ';': case ':': case '(': case ')': case '?':
			case '!': case '=': case '&': case '~': case '+': case '-':
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Precondition: the characters in str at or after strIndex can form a valid
	 * number
	 * note that every number is in the form -[numbers].[numbers]e[-numbers],
	 * where the '-'s, the '.', and the stuff after the e (and including the e) 
	 * is optional. Note that a number can also start with '.' or '-.', since 
	 * some people write numbers that way. This helps us group the code 
	 * into chunks. 
	 */
	private String assembleNumber(String str, int strIndex) {
		String tokenOut = "";
		String subToken = "";
		char ch = ' ';
		char nextChar = '\0';
		
		// look at the current char
		ch = str.charAt(strIndex);
		
		// if current char is a negative sign...
		if (ch == '-') {
			// look ahead if possible
			if (strIndex + 1 < str.length()) {
				nextChar = str.charAt(strIndex + 1);
			}
			// if nextChar is a digit or it's a '.'
			if (Character.isDigit(nextChar) || nextChar == '.') {
				// concatenate current char to the token and increment the index
				tokenOut += ch;
				strIndex++;
				
				// look at the new current char
				ch = str.charAt(strIndex);
				
				// if it's a digit, assemble a sequence of numbers
				if (Character.isDigit(ch)) {
					subToken = assembleNumberSequence(str, strIndex);
					tokenOut += subToken;
					strIndex += subToken.length();
				}
			}
		} else if (Character.isDigit(ch)) {
			// if the current char is a digit, assemble a sequence of numbers
			subToken = assembleNumberSequence(str, strIndex);
			tokenOut += subToken;
			strIndex += subToken.length();
		}
		
		// now, check for the '.'
		if (strIndex < str.length()) {
			ch = str.charAt(strIndex);
			if (ch == '.') {
				if (strIndex + 1 < str.length()) {
					nextChar = str.charAt(strIndex + 1);
				}
				
				// if the nextChar is a digit, then the '.' is a valid part
				// of our number
				if (Character.isDigit(nextChar)) {
					// add the '.' to the token
					tokenOut += ch;
					strIndex++;
					
					// assemble number sequence.
					subToken = assembleNumberSequence(str, strIndex);
					tokenOut += subToken;
					strIndex += subToken.length();
				}
			} 
		}
		
		// checking for e
		if (strIndex < str.length()) {
			ch = str.charAt(strIndex);
			if (ch == 'e') {
				if (strIndex + 1 < str.length()) {
					nextChar = str.charAt(strIndex + 1);
				}
				if (nextChar == '-') {
					// if it is a '-' after the 'e'...
					if (strIndex + 2 < str.length()) {
						// we look ahead another character
						nextChar = str.charAt(strIndex + 2);
						
						// if there is a digit after '-', then 'e-...' is a 
						// valid part of our number
						if (Character.isDigit(nextChar)) {
							// save the 'e'
							tokenOut += ch;
							strIndex++; 
							
							// save the '-'
							ch = str.charAt(strIndex);
							tokenOut += ch;
							strIndex++;
							
							// save the sequence of numbers
							subToken = assembleNumberSequence(str, strIndex);
							tokenOut += subToken;
						}
					}
				} else if (Character.isDigit(nextChar)) {
					// if it is a digit after the 'e', then save the 'e'...
					tokenOut += ch;
					strIndex++;
					
					// ... and save the number sequence
					subToken = assembleNumberSequence(str, strIndex);
					tokenOut += subToken;
					strIndex += subToken.length();
				}
			}
		}
		
		// done with the entire token
		return tokenOut;
	}
	
	/** 
	 * Note: This assembles a POSITIVE integer
	 */
	private String assembleNumberSequence(String str, int strIndex) {
		String tokenOut = "";
		char ch = ' ';
		char nextChar = '\0';
		
		// save the current character, since it's guaranteed to be a part
		// of the number
		ch = str.charAt(strIndex);
		tokenOut += ch;
		
		// look ahead if possible
		if (strIndex + 1 < str.length()) {
			nextChar = str.charAt(strIndex + 1);
		}
		
		// repeatedly checks if the next character is a digit or if the next
		// character actually exists in the string
		while (Character.isDigit(nextChar) && strIndex + 1 < str.length()) {
			// increment index to read the next character separately from nextChar
			strIndex++;
			ch = str.charAt(strIndex);
			tokenOut += ch;
			
			// look ahead if possible
			if (strIndex + 1 < str.length()) {
				nextChar = str.charAt(strIndex + 1);
			}
		}
		
		return tokenOut;
	}
	
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
