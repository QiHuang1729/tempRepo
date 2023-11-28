import java.util.Scanner;

/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author Qi Huang
 *	@version 1.0
 */
public class HTMLRender {
	
	// maximum line lengths for each format type
	private int MAX_LENGTH_H1 = 40;
	private int MAX_LENGTH_H2 = 50;
	private int MAX_LENGTH_H3 = 60;
	private int MAX_LENGTH_H4 = 80;
	private int MAX_LENGTH_H5 = 100;
	private int MAX_LENGTH_H6 = 120;
	private int MAX_LENGTH = 80;
	
	// the name of the HTML file
	private String fileName;
	
	// format of each token for printing
	private enum TokenFormat { PLAIN, BOLD, ITALIC, PREFORMAT, HEADING1, 
		HEADING2, HEADING3, HEADING4, HEADING5, HEADING6 };
	private TokenFormat format;
	
	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private final int TOKENS_SIZE = 100000;	// size of array
	
	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
	
	public HTMLRender(String[] args) {
		// Initialize html file name
		if (args.length > 0) {
			fileName = args[0];	
		} else {
			System.out.println("Usage: java HTMLRender <htmlFileName>");
		}
		
		// Initialize text format
		format = TokenFormat.PLAIN;
		
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
	}
	
	
	public static void main(String[] args) {
		HTMLRender hf = new HTMLRender(args);
		hf.run();
	}
	
	public void run() {		
		Scanner input = FileUtils.openToRead(fileName);
		tokenizeHTMLFile(input);
		printHTMLFile();
	}
	
	/** tokenizes the entire HTML file line by line and saves them into the
	 * String[] tokens. */
	private void tokenizeHTMLFile(Scanner input) {
		HTMLUtilities util = new HTMLUtilities();
		
		// index1 is for String[] tokens, and index2 is for the tokens in each line.
		int index1 = 0;
		int index2 = 0;
		String fileLine = "";
		String[] lineTokens = null;
		
		while (input.hasNext()) {
			// resets the line index after each line is saved
			index2 = 0;
			fileLine = input.nextLine();
			lineTokens = util.tokenizeHTMLString(fileLine);
			
			// saves each token in a line
			while (index2 < lineTokens.length) {
				tokens[index1] = lineTokens[index2];
				index1++;
				index2++;
			}
		}
	}
	
	/** Prints the HTML File. The program looks at each element one-by-one. 
	 *  If it's a tag, it changes the formatting and prints any necessary
	 *  characters or blank lines. If it's not a tag, then it is printed
	 *  based on the formatting. */
	private void printHTMLFile() {
		String element = null;
		int lineLength = 0;
		
		int index = 0;
		while (index < tokens.length && tokens[index] != null) {
			element = tokens[index];
			if (element.charAt(0) == '<') {
				element = element.toLowerCase();
				switch (element) {
					case "<b>":
						format = TokenFormat.BOLD;
						break;
						
					case "<i>":
						format = TokenFormat.ITALIC;
						break;
						
					case "<pre>":
						format = TokenFormat.PREFORMAT;
						break;
						
					case "<h1>":
						format = TokenFormat.HEADING1;
						browser.println();
						browser.println();
						lineLength = 0;
						break;
					
					case "<h2>":
						format = TokenFormat.HEADING2;
						browser.println();
						browser.println();
						lineLength = 0;
						break;
						
					case "<h3>":
						format = TokenFormat.HEADING3;
						browser.println();
						browser.println();
						lineLength = 0;
						break;
						
					case "<h4>":
						format = TokenFormat.HEADING4;
						browser.println();
						browser.println();
						lineLength = 0;
						break;
						
					case "<h5>":
						format = TokenFormat.HEADING5;
						browser.println();
						browser.println();
						lineLength = 0;
						break;
						
					case "<h6>":
						format = TokenFormat.HEADING6;
						browser.println();
						browser.println();
						lineLength = 0;
						break;
						
					case "</b>": case "</i>": case "</pre>":
						format = TokenFormat.PLAIN;
						break;
						
					case "</h1>": case "</h2>": case "</h3>": case "</h4>": 
					case "</h5>": case "</h6>":
						format = TokenFormat.PLAIN;
						break;
						
						
					case "<p>":
						browser.println();
						lineLength = 0;
						element = tokens[index - 1].toLowerCase();
						
						// if there is no newline before this in the form of
						// <br> or </p>, then we need two printlns to create 
						// a completely blank line.
						if (!element.equals("<br>") && 
											!element.equals("</p>")) {
							browser.println();
							lineLength = 0;
						}
						element = tokens[index].toLowerCase();
						break;
						
					case "</p>": 
						// first line printed
						browser.println();
						lineLength = 0;
						element = tokens[index - 1].toLowerCase();
						
						// if no <br> or <p> before, then we do another println
						// to create a completely blank line
						if (!element.equals("<br>") && 
											!element.equals("<p>")) {
							browser.println();
							lineLength = 0;
						}
						element = tokens[index].toLowerCase();
						break;
						
					case "<q>": 
						browser.print("\"");
						lineLength += "\"".length();
						break;
					
					case "</q>":
						browser.print("\" ");
						lineLength += "\" ".length();
						break;
						
					case "<hr>":
						browser.printHorizontalRule();
						lineLength = 0;
						break;
						
					case "<br>":
						browser.printBreak();
						lineLength = 0;
						break;
				}
			} else {
				switch (format) {
					case HEADING1:
						if (lineLength + element.length() > MAX_LENGTH_H1) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printHeading1(element);
						lineLength += element.length();
						
						if (index + 1 > tokens.length - 1 || 
										!isPunctuation(tokens[index + 1])) {
							browser.printHeading1(" ");
							lineLength++;
						}
						break;
						
					case HEADING2:
						if (lineLength + element.length() > MAX_LENGTH_H2) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printHeading2(element);
						lineLength += element.length();
						
						if (index + 1 > tokens.length - 1 || 
										!isPunctuation(tokens[index + 1])) {
							browser.printHeading2(" ");
							lineLength++;
						}
						break;
						
					case HEADING3:
						if (lineLength + element.length() > MAX_LENGTH_H3) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printHeading3(element);
						lineLength += element.length();
						
						if (index + 1 > tokens.length - 1 || 
										!isPunctuation(tokens[index + 1])) {
							browser.printHeading3(" ");
							lineLength++;
						}
						break;
						
					case HEADING4:
						if (lineLength + element.length() > MAX_LENGTH_H4) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printHeading4(element);
						lineLength += element.length();
						
						if (index + 1 > tokens.length - 1 || 
										!isPunctuation(tokens[index + 1])) {
							browser.printHeading4(" ");
							lineLength++;
						}
						break;
						
					case HEADING5:
						if (lineLength + element.length() > MAX_LENGTH_H5) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printHeading5(element);
						lineLength += element.length();
						
						if (index + 1 > tokens.length - 1 || 
										!isPunctuation(tokens[index + 1])) {
							browser.printHeading5(" ");
							lineLength++;
						}
						break;
						
					case HEADING6:
						if (lineLength + element.length() > MAX_LENGTH_H6) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printHeading6(element);
						lineLength += element.length();
						
						if (index + 1 > tokens.length - 1 || 
										!isPunctuation(tokens[index + 1])) {
							browser.printHeading6(" ");
							lineLength++;
						}
						break;
					
					case ITALIC:
						if (lineLength + element.length() > MAX_LENGTH) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printItalic(element);
						lineLength += element.length();
						
						if (index + 1 > tokens.length - 1 || 
											!isPunctuation(tokens[index + 1])) {
							browser.printItalic(" ");
							lineLength++;
						}
						break;
						
					case BOLD:
						if (lineLength + element.length() > MAX_LENGTH) {
							browser.println();
							lineLength = 0;
						}
						
						browser.printBold(element);
						lineLength += element.length();
						if (index + 1 > tokens.length - 1 || 
											!isPunctuation(tokens[index + 1])) {
							browser.printBold(" ");
							lineLength++;
						}
						break;
						
					case PLAIN:
						if (lineLength + element.length() > MAX_LENGTH) {
							browser.println();
							lineLength = 0;
						}
						
						browser.print(element);
						lineLength += element.length();
						if (index + 1 > tokens.length - 1 || 
											!isPunctuation(tokens[index + 1])) {
							browser.print(" ");
							lineLength++;
						}
						break;
						
					case PREFORMAT:
						browser.printPreformattedText(element);
				}
			}
			index++;
		}
	}
	
	/** Returns true if str is a punctuation and false otherwise */
	private boolean isPunctuation(String str) {
		boolean output = false;
		if (str.equalsIgnoreCase("</q>")) 
			output = true;
		else {
			if (str == null || str.length() != 1) {
				output = false;
			} else {
				char ch = str.charAt(0);
				switch (ch) {
					case '.': case ',': case ';': case ':': case '(': case ')': case '?':
					case '!': case '=': case '&': case '~': case '+': case '-':
						output = true;
						break;
					default:
						output = false;
						break;
				}
			}
		}
		return output;
	}
}
