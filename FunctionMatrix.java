import java.math.BigDecimal;
/**
 * This class creates a FunctionMatrix object, a matrix with function columns, 
 * and includes general methods a user may need when dealing
 * with introductory and advanced linear algebra. FunctionMatrix is
 * implemented in the Application for MAX.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */
public class FunctionMatrix
{
	protected final int ROW = 1;//variable represents the number of rows in the matrix
	protected int col;//variable represents the number of columns in the matrix
	protected Function[][] f;
	
	/*
	 * Empty-argument constructor that instantiates the matrix
	 * dimensions (row x col) to 1 x 1, and instantiates the
	 * array with those dimensions.
	 */
	FunctionMatrix()
	{
		col = 1;
		f = new Function [1][col];
	}//end constructor
	
	/**
	 * Preferred-argument constructor that instantiates the
	 * dimensions of the matrix to the preferred row and col
	 * integer variable, and instantiates the array dimensions to
	 * those same values.
	 * @param row the preferred row dimension for the matrix
	 * @param col the preferred column dimension for the matrix
	 */
	FunctionMatrix(int row, int col)
	{
		this.col = col;
		f = new Function [1][col];
	}//end constructor
	
	/**
	 * Preferred-argument constructor that instantiates the
	 * array of functions in the matrix.
	 * @param p array of polynomials
	 */
	FunctionMatrix(Function[][] f)
	{
		col = f[0].length;
		this.f = f;
	}
	
	public int getRow()
	{
		return ROW;
	}

	public int getCol()
	{
		return col;
	}

	public Function getEntry(int r, int c)
	{
		return f[r][c];
	}

	public void setEntry(int r, int c, Function entry)
	{
		f[r][c] = entry;
	}

	public Function[][] getMatrix()
	{
		return f;
	}
	
	/**
	 * Checks if function matrix is orthogonal on a defined inner product space.
	 * @param points defining inner product space
	 * @return true if orthogonal, false if not
	 */
	public boolean isOrtho(BigDecimal[] points)
	{
		Matrix n = new Matrix(points.length, col);

		for(int r = 0; r < n.getRow(); r++)
		{
			for(int c = 0; c < col; c++)
			{
				n.setEntry(r, c, f[0][c].evaluateAt(points[r]));
			}
		}

		return n.isOrtho();
	}
	
	/**
	 * Prints matrix on default system outlet.
	 */
	public void printMatrix()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		// for loops iterate through matrix
		for (int c = 0; c < col; c++) 
		{
			sb.append(f[0][c].toString());
			if (c + 1 != col)
				sb.append(", ");
			
        }// end for c
		sb.append("]");
        System.out.print(sb.toString());
	}
}//end class
