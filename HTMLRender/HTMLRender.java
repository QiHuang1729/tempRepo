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
	
	// the name of the HTML file
	private String fileName;
	
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
		
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
	}
	
	
	public static void main(String[] args) {
		HTMLRender hf = new HTMLRender(args);
		System.out.println("\"".length()); // length is one!
		//hf.run();
	}
	
	public void run() {		
		Scanner input = FileUtils.openToRead(fileName);
		tokenizeHTMLFile(input);
		
		/*
		// Sample renderings from HtmlPrinter class
		
		// Print plain text without line feed at end
		browser.print("First line");
		
		// Print line feed
		browser.println();
		
		// Print bold words and plain space without line feed at end
		browser.printBold("bold words");
		browser.print(" ");
		
		// Print italic words without line feed at end
		browser.printItalic("italic words");
		
		// Print horizontal rule across window (includes line feed before and after)
		browser.printHorizontalRule();
		
		// Print words, then line feed (printBreak)
		browser.print("A couple of words");
		browser.printBreak();
		browser.printBreak();
		
		// Print a double quote
		browser.print("\"");
		
		// Print Headings 1 through 6 (Largest to smallest)
		browser.printHeading1("Heading1");
		browser.printHeading2("Heading2");
		browser.printHeading3("Heading3");
		browser.printHeading4("Heading4");
		browser.printHeading5("Heading5");
		browser.printHeading6("Heading6");
		
		// Print pre-formatted text (optional)
		browser.printPreformattedText("Preformat Monospace\tfont");
		browser.printBreak();
		browser.print("The end");
		*/
	}
	
	private void tokenizeHTMLFile(Scanner input) {
		HTMLUtilities util = new HTMLUtilities();

		int index1 = 0;
		int index2 = 0;
		String line = "";
		String[] lineTokens = null;
		
		while (input.hasNext()) {
			index2 = 0;
			line = input.nextLine();
			lineTokens = util.tokenizeHTMLString(line);
			
			while (index2 < lineTokens.length) {
				tokens[index1] = lineTokens[index2];
				index1++;
				index2++;
			}
		}
	}
	
	private void printHTMLFile() {
		// a field holding the format of the text (bold, italic)
		int[] format;
		final int BOLD = 0; // index for BOLD
		final int ITALIC = 1; // index for ITALIC
		final int HEADING = 2; // index for headings
		final int TYPES = 3; // number of different types of formatting
		
		// Initialize Text Format
		format = new int[TYPES];
		format[BOLD] = format[ITALIC] = format[HEADING] = 0;
		
		int index = 0;
		String element = null;
		
		/*
		int length = 0;
		while (index < input.hasNext()) {
			element = tokens[index];
			if (element.charAt(0) == '<') {
				switch (element) {
					case "<b>":
						format[BOLD] = 1;
						break;
					case "<\b>":
						format[BOLD] = 0;
						break;
					case "<i>":
						format[ITALIC] = 1;
						break;
					case "<\i>":
						format[ITALIC] = 0;
						break;
					case "<h1>": case "<h2>": case "<h3>": case "<h4>":
					case "<h5>": case "<h6>":
						char size = element.charAt(element.indexOf("h") + 1);
						format[HEADING] = Integer.parseInt(size);
						break;
					case "<\h1>": case "<\h2>": case "<\h3>": case "<\h4>":
					case "<\h5>": case "<\h6>":
						format[HEADING] = 0;
						break;
					case "<p>": 
						browser.println();
						break;
					case "<\p>":
						browser.println();
						element = tokens[index - 1];
						if (!element.equals("<br>")) {
							element = token[index + 1];
							if (!element.equals("<p>")) {
								browser.println();
							}
						}
						break;
					case "<q>": case "<\q>": 
						length += print("\"");
				}
			} else {
				if (format[HEADING] > 0) {
					switch (format[HEADING]) {
						case 1:
							browser
					}
				}
			}
		}*/
	}
	
	private int print(String toPrint) {
		browser.print(toPrint);
		return toPrint.length();
	}
	/*
	private void applyTag() {
		
	}
	
	private void thing() {
		
	}*/
}
