import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.math.BigDecimal;
/**
 * This class creates a Function object and includes methods necessary for
 * the FunctionMatrix class. FunctionMatrix is used in the Application for MAX.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */
public class Function
{
	private String fString; //function represented as a string
	private String[] infix; //function in infix form in an array
	private String[] postfix; //function in postfix form in an array

	/**
	 * Preferred-argument constructor takes in a String and converts it to
	 * postfix for evaluation purposes.
	 * @param fString
	 */
	public Function(String fString)
	{
		this.fString = fString;
		stringToInfArray();
		infixToPostfix();
	}

	public Function getFunction()
	{
		return this;
	}

	/**
	 * Evaluates the function at a certain x-value by replacing x with the value
	 * and evaluating the postfix expression.
	 * @param value
	 * @return evaluated value
	 */
	public BigDecimal evaluateAt(BigDecimal value)
	{
		String xValue = value.toString();
		String[] postfixWithX = new String[postfix.length];
		
		//replace x with x-value
		for(int pf = 0; pf < postfix.length; pf++)
		{
			if(postfix[pf].equals("x"))
				postfixWithX[pf] = xValue;
			else
				postfixWithX[pf] = postfix[pf];
		}
		
		//evaluate postfix form where x=value
		BigDecimal result = new BigDecimal(0);
		Stack<BigDecimal> stack = new Stack<BigDecimal>();
		
		for(int i = 0; i<postfix.length; i++)
		{
			//push numbers onto stack
			if(postfixWithX[i].matches("\\d+"))
			{
				stack.push(new BigDecimal(postfixWithX[i]));
			}
			//if an operator is the next element, operate on last two numbers on stack
			//and add the result to the stack
			else
			{
				if(postfixWithX[i].equals("+"))
				{
					BigDecimal second = stack.pop();
					BigDecimal first = stack.pop();
					BigDecimal subResult = first.add(second);
					stack.push(subResult);
				}
				else if(postfixWithX[i].equals("-"))
				{
					BigDecimal second = stack.pop();
					BigDecimal first = stack.pop();
					BigDecimal subResult = first.subtract(second);
					stack.push(subResult);
				}
				else if(postfixWithX[i].equals("*"))
				{
					BigDecimal second = stack.pop();
					BigDecimal first = stack.pop();
					BigDecimal subResult = first.multiply(second);
					stack.push(subResult);
				}
				else if(postfixWithX[i].equals("/"))
				{
					BigDecimal second = stack.pop();
					BigDecimal first = stack.pop();
					BigDecimal subResult = first.divide(second);
					stack.push(subResult);
				}
				else if(postfixWithX[i].equals("^"))
				{
					int second = stack.pop().intValue();
					BigDecimal first = stack.pop();
					BigDecimal subResult = first.pow(second);
					stack.push(subResult);
				}
			}
		}
		result = stack.pop();
				
		return result;
	}

	public String toString()
	{
		return fString;
	}

	/**
	 * Private method for converting from infix array to postfix array.
	 * Only used in constructor.
	 */
	private void infixToPostfix()
	{
		Stack<String> stack = new Stack<String>();
		String[] postfixLong = new String[infix.length];
		
		int postf = 0;//traverses postfix
		for(int inf = 0; inf<infix.length; inf++)
		{
			//next entry in infix is x or a number, add to array.
			//if last entry, pop stack till empty.
			if(infix[inf].equals("x") || infix[inf].matches("\\d+"))
			{
				postfixLong[postf] = infix[inf];
				postf++;
				
				if(inf == infix.length-1)
				{
					while(!stack.empty())
					{
						postfixLong[postf] = stack.pop();
						postf++;
					}
				}
			}
			
			//next entry in infix is ^, push onto stack
			else if(infix[inf].equals("^"))
			{
				stack.push(infix[inf]);
			}
			
			//next entry in infix is * or /, pop stack if peek is a higher priority
			//operation, push operator on stack
			else if(infix[inf].equals("*") || infix[inf].equals("/"))
			{
				if(!stack.empty())
				{
					if(stack.peek().equals("^"))
					{
						postfixLong[postf] = stack.pop();
						postf++;
					}
				}
				stack.push(infix[inf]);
			}
			
			//next entry in infix is + or -, pop stack if peek is a higher priority
			//operation, push operator on stack
			else if(infix[inf].equals("+") || infix[inf].equals("-"))
			{
				if(!stack.empty())
				{
					if(stack.peek().equals("^") || stack.peek().equals("*")
							|| stack.peek().equals("/"))
					{
						postfixLong[postf] = stack.pop();
						postf++;
					}
				}
				stack.push(infix[inf]);
			}
			
			//next entry in infix is (, push onto stack
			else if(infix[inf].equals("("))
			{
				stack.push(infix[inf]);
			}
			
			//next entry in infix is ), pop stack until "("
			else if(infix[inf].equals(")"))
			{
				while(!stack.peek().equals("("))
				{
					postfixLong[postf] = stack.pop();
					postf++;
				}
				
				stack.pop();
			}
		}
		
		//copy array up to postf index to remove null entries
		postfix = Arrays.copyOfRange(postfixLong, 0, postf);
		
	}

	/**
	 * Private method for converting from string to infix array.
	 * Only used in constructor.
	 */
	private void stringToInfArray()
	{
		// Split the string based on operators, parentheses, and exponents
		String[] splitNonOpWithSpaces = fString.split("[+\\-*/^()]|(?<=\\d)(?=x)|(?<=x)(?=\\d)|(?<=\\d)(?=\\()|(?<=\\))(?=\\d)");

		// Create a list to store non-empty substrings
		List<String> substringsList = new ArrayList<String>();

		// Add non-empty substrings to the list
		for (String substring : splitNonOpWithSpaces) {
			if (!substring.trim().isEmpty()) {
				substringsList.add(substring.trim());
			}
		}

		// Convert the list to an array - without the spaces
		String[] splitNonOp = substringsList.toArray(new String[0]);

		
		// Split the string without 'x' and digits
        String[] splitOpShortWithSpaces = fString.split("[x0-9]");

        // Remove empty strings from the splitOpShort array
        List<String> splitOpList = new ArrayList<>();
        for (String s : splitOpShortWithSpaces) {
            if (!s.isEmpty()) {
                splitOpList.add(s);
            }
        }
        String[] splitOpShort = splitOpList.toArray(new String[0]);
        
		String[] splitOp = new String[fString.length() - splitNonOp.length];
		//Split up adjacent operands from parentheses
		int realLength = 0;
		for(int shortL = 0; shortL < splitOpShort.length; shortL++)
		{
			if(splitOpShort[shortL].length() == 1)
			{
				splitOp[realLength] = splitOpShort[shortL];
				realLength++;
			}
			else
			{
				for(int i = 0; i < splitOpShort[shortL].length(); i++)
				{
					splitOp[realLength] = "" + splitOpShort[shortL].charAt(i);
					realLength++;
				}
			}

		}//end for shortL

		//put non-operands and operands in the infix array in the right order, all split up
		infix = new String[splitOp.length + splitNonOp.length];
		int nonOp = 0;//traverses splitNonOp
		int op = 0;//traverses splitOp
		int inf = 0;//traverses resulting array
		int s = 0; //traverses string function
		while(inf < infix.length)
		{
			//char in string is an operand
			if(op<splitOp.length)
			{
				if(fString.charAt(s) == splitOp[op].charAt(0))

				{
					infix[inf] = splitOp[op];
					s++;//adds length (1) of operand string
					op++;//+1 to op trav. index
				}
				
				else
				{
					infix[inf] = splitNonOp[nonOp];
					s += splitNonOp[nonOp].length();//adds length of non-operand string
					nonOp++;//+1 to nonOp trav. index
					
				}
			}
			//char in string is non-operand
			else
			{
				infix[inf] = splitNonOp[nonOp];
				s += splitNonOp[nonOp].length();//adds length of non-operand string
				nonOp++;//+1 to nonOp trav. index
			}
			inf++;
		}
	}//end StringToInfArray
	
	/**
	 * Prints array on default system outlet.
	 * @param array to be printed
	 */
	public void printArray(String[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		// for loops iterate through matrix
		for (int i = 0; i < array.length; i++) {

			sb.append(array[i]);
			if (i + 1 != array.length)
				sb.append(",");

		}// end for r
		sb.append("]\n");
		System.out.print(sb.toString());
	}// end printArray
}//end class
