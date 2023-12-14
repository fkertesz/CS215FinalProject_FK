import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
/**
 * This class creates a Polynomial object and includes methods necessary for
 * the PolyMatrix class. PolyMatrix is used in the Application for MAX.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */
public class Polynomial
{
    private BigDecimal[] coefficients = new BigDecimal[3];//1+t+t^2 order

    /**
     * Constructor that takes an array of coefficients in descending order
     * @param coefficients, array of coefficients ordered
     */
    public Polynomial(BigDecimal[] coefficients)
    {
        this.coefficients = coefficients;
    }

    /**
     * Method to evaluate the polynomial at a given x value
     * @param x, value to evaluate at
     * @return evaluated value
     */
    public BigDecimal evaluateAt(BigDecimal x)
    {
        BigDecimal result = new BigDecimal(0);

        for (int i = 0; i < 3; i++)
        {
            result = result.add(coefficients[i].multiply(x.pow(i)));
        }

        return result;
    }

    /**
     * Method to find x-intercepts (roots) of the polynomial.
     * @return
     */
    public BigDecimal[] zeros()
    {
    	BigDecimal[] roots = new BigDecimal[getDegree()];
        
    	if(getDegree() == 2)
    	{
    		//Quadratic formula
    		BigDecimal a = coefficients[2];
    		BigDecimal b = coefficients[1];
    		BigDecimal c = coefficients[0];
    		roots[0] = b.negate().add(b.pow(2).subtract(a.multiply(c).multiply(new BigDecimal(4)))
    				.sqrt(new MathContext(15, RoundingMode.HALF_UP))).divide(new BigDecimal(2))
    				.divide(a);
    		roots[1] = b.negate().subtract(b.pow(2).subtract(a.multiply(c).multiply(new BigDecimal(4)))
    				.sqrt(new MathContext(15, RoundingMode.HALF_UP))).divide(new BigDecimal(2))
    				.divide(a);
    	}
    	else if(getDegree() == 1)
    		roots[0] = coefficients[1].divide(coefficients[0]);
    	else
    		throw new UnsupportedOperationException("No zeros or infinite zeros.");
    	return roots;
    }

    /**
     * Method to get the degree of the polynomial, up to 2.
     * @return degree of the polynomial, 0-2
     */
    public int getDegree()
    {
    	if(!coefficients[2].equals(BigDecimal.ZERO))
    		return 2;
    	else if(!coefficients[1].equals(BigDecimal.ZERO))
    		return 1;
    	else
    		return 0;
    }

    /**
     * Method to get the coefficients of the polynomial
     * @return array of coefficients
     */
    public BigDecimal[] getCoefficients()
    {
        return coefficients;
    }

    @Override
    public String toString()
    {
        StringBuilder polynomialString = new StringBuilder();

        for (int i = 0; i < 3; i++) {

        	if (i != 0)
        	{
        		polynomialString.append(" + ");
        	}
        	polynomialString.append(coefficients[i].toString());
        	if (i != 0) {
        		polynomialString.append("x^").append(i);
        	}
        }

        return polynomialString.toString();
    }
}//end class
