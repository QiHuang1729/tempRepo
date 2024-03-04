import java.util.List;		// used by expression evaluator


// don't focus on minutae like this. You can spend 10 min
				// debating on which design is the cleanest using all the 
				// stylistic explanations, rationales, reasons, etc. your
				// various teachers has shown to you, and absolutely no one
				// will pay attention to it. 

/**
 *	This class outputs the result of correctly entered arithmetic 
 * 	expressions.
 *
 *	@author	Qi Huang
 *	@since	2/27/2024
 */
public class SimpleCalc {
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack

	// constructor	
	public SimpleCalc() {
		valueStack = new ArrayStack();
	}
	
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		String input = "";
		List<String> tokens = null;
		while (!input.equals("q")) {
			input = Prompt.getString("");
			if (input.equals("h")) {
				printHelp();
			} else {
				tokens = tokenizeExpression(input);
				evaluateExpression(tokens);
			}
		}
	}
	
	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) {
		double value = 0;
		String theToken = "";
		for (int i = 0; i < tokens.length; i++) {
			theToken = tokens.get(i);
			/* if you have a number, just push the number into the stack
			 * for this algorithm */
			if ('0' < theToken.charAt(0) && theToken.charAt(0) < '9') {
				valueStack.push(Double.parseDouble(theToken));
			} else {
				/* if you have a + or - and you see a + or -, evaluate the
				 * previous + or -. If you see anything else of a higher
				 * precedence, also evaluate it. If its an exponent, you 
				 * should continue checking until you don't have one anymore, 
				 * then evaluate whatever is left. Then add your + or - to the stack
				 * if you have a * or / and you see a - or +, you can put your 
				 * operation in the stack. If you see something of a higher
				 * precedence, evaluate it. If it's an exponent, keep 
				 * going to the left, since its right-associative. 
				 * If the precedence is lower, you can move on to the 
				 * next operation.
				 * if you have a ) after any operation, do the operation
				 * you saw
				 * continue doing operations until you find a (, then stop.
				 * if you see a + after a /, do the / first. 
				 * if you have a (, just continue along, since you can't
				 * do anything
				 * if you have a ^, always move on since you don't know 
				 * if the next operation is an ^. Then you will hit the 
				 * end, a right parenthesis, or a lower precedence 
				 * operation, in which case you should just evaluate it.
				 */
				
				String prevOperator = operatorStack.peek();
				if (hasPrecedence(theToken, prevOperator)) {
					double num2 = valueStack.pop();
					double num1 = valueStack.pop();
					operatorStack.pop();
					if (prevOperator.equals("+")) {
						valueStack.add(num2 + num1);
					} else if (prevOperator.equals("-") {
						valueStack.add(num2 - num1);
					} else if (prevOperator.equals("*")) {
						valueStack.add(num2 * num1);
					} else if (prevOperator.equals("/")) {
						valueStack.add(num2 / num1);
					} else if (prevOperator.equals("^") {
						// could be cleaner?
						while (prevOperator.equals("^")) {
							num2 = Math.pow(num1, num2);
							prevOperator = operatorStack.pop();
							if (prevOperator.equals("^")) {
								num1 = valueStack.pop();
							}
						}
						valueStack.add(num2);
					}
				} else {
					
				}
			}
		}
		
		return value;
	}
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	
	// it should work. Maybe you are using the operators backwards or 
	// interpreting it wrong (interpreting the expression or individual
	// operation wrong)
}
