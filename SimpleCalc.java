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
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
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
	 
	// rewrite this part and think it through!!
	public void runCalc() {
		String input = "";
		List<String> tokens = null;
		input = Prompt.getString("");
		
		while (!input.equals("q")) {
			input = Prompt.getString("");
			if (input.equals("h")) {
				printHelp();
			} else {
				tokens = utils.tokenizeExpression(input);
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
	
	// add operator check to it
	public double evaluateExpression(List<String> tokens) {
		String theToken = null;
		String prevOperator = null;
		double num1 = 0.0;
		double num2 = 0.0;
		
		for (int i = 0; i < tokens.size(); i++) {
			theToken = tokens.get(i);
			if (Character.isDigit(theToken.charAt(0))) {
				valueStack.push(Double.parseDouble(theToken));
			} else {
				if (theToken.equals("(")) {
					operatorStack.push(theToken);
				} else if (theToken.equals(")")) {
					prevOperator = operatorStack.peek();
					while (!prevOperator.equals("(")) {
						calculate();
						prevOperator = operatorStack.peek();
					}
				} else {
					prevOperator = operatorStack.peek();
					while (!operatorStack.isEmpty()
								&& hasPrecedence(theToken, prevOperator)) {
						calculate();
						if (!operatorStack.isEmpty())
							prevOperator = operatorStack.peek();
					}
					operatorStack.push(theToken);
				}		
			}
		}
		
		while (!operatorStack.isEmpty()) {
			calculate();
		}
		
		return valueStack.pop();
	}
	
	/** calculates the expression using the popped values from valueStack
	 * and the popped operator from operatorStack */
	private void calculate() {
		double num1 = 0.0;
		double num2 = 0.0;
		String prevOperator = null;
		
		// the first value popped comes second in the 
		// expression, and the second value comes first 
		num2 = valueStack.pop();
		num1 = valueStack.pop();
		prevOperator = operatorStack.pop();
		
		// cases on the operator (hardcoded :/)
		if (prevOperator.equals("+")) {
			valueStack.push(num1 + num2);
		} else if (prevOperator.equals("-")) {
			valueStack.push(num1 - num2);
		} else if (prevOperator.equals("*")) {
			valueStack.push(num1 * num2);
		} else if (prevOperator.equals("/")) {
			valueStack.push(num1 / num2);
		} else if (prevOperator.equals("^")) {
			valueStack.push(Math.pow(num1, num2));
		}
	}
	
	/*
	/**
	 * Determines whether a string is a number
	 
	private boolean isNumber(String str) {
		boolean isNumber = true;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (! Character.isDigit(str.charAt(i)) && str.charAt(i) != '.') {
				isNumber = false;
			}
		}
	}
	*/
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *		Special case: hasPrecedence("^", "^") returns false
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
