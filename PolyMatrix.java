import java.math.BigDecimal;
/**
 * This class creates a PolyMatrix object, a matrix with polynomial columns, 
 * and includes general methods a user may need when dealing
 * with introductory and advanced linear algebra. PolyMatrix is
 * implemented in the Application for MAX.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */
public class PolyMatrix implements LinearAlgebraicElement<Polynomial>
{
	protected final int ROW = 3;//variable represents the number of rows in the matrix
	protected int col;//variable represents the number of columns in the matrix
	protected Polynomial[][] p;//array contains the polynomials
	protected BigDecimal [][] m;//array contains the coefficients in order
	
	/*
	 * Empty-argument constructor that instantiates the matrix
	 * dimensions (row x col) to 1 x 1, and instantiates the
	 * array with those dimensions.
	 */
	PolyMatrix()
	{
		col = 1;
		p = new Polynomial [1][col];
		m = new BigDecimal [ROW][col];
	}//end constructor
	
	/**
	 * Preferred-argument constructor that instantiates the
	 * dimensions of the matrix to the preferred row and col
	 * integer variable, and instantiates the array dimensions to
	 * those same values.
	 * @param row the preferred row dimension for the matrix
	 * @param col the preferred column dimension for the matrix
	 */
	PolyMatrix(int row, int col)
	{
		this.col = col;
		p = new Polynomial [1][col];
		m = new BigDecimal [ROW][col];
	}//end constructor
	
	/**
	 * Preferred-argument constructor that instantiates the
	 * array of polynomials in the matrix.
	 * @param p array of polynomials
	 */
	PolyMatrix(Polynomial[][] p)
	{
		col = p[0].length;
		this.p = p;
		m = new BigDecimal[ROW][col];
		polyToRep();
	}
	
	@Override
	public int getRow()
	{
		return ROW;
	}

	@Override
	public int getCol()
	{
		return col;
	}

	@Override
	public Polynomial getEntry(int r, int c)
	{
		return p[r][c];
	}

	@Override
	public void setEntry(int r, int c, Polynomial entry)
	{
		p[r][c] = entry;
		polyToRep();
	}

	@Override
	public Polynomial[][] getMatrix()
	{
		return p;
	}
	
	public BigDecimal[][] getRepMatrix()
	{
		return m;
	}
	
	/**
	 * Checks if matrix is linearly independent.
	 * @return true if linearly independent, false if not
	 */
	public boolean linearlyIndep()
	{
		return (new Matrix(m)).linearlyIndep();
	}
	
	/**
	 * Checks if matrix spans P2, the space of polynomials of up to degree 2
	 * @return true if spans P2, false if not
	 */
	public boolean spansP2()
	{
		return (new Matrix(m)).spansRn();
	}
	
	/**
	 * Creates a null space of the matrix
	 * @return null space matrix
	 */
	public Matrix createNulSpace()
	{
		return (new Matrix(m)).createNulSpace();
	}
	
	/**
	 * Checks if the matrix is orthogonal with a defined inner product space.
	 * @param points to define inner product space
	 * @return true if orthogonal, false if not
	 */
	public boolean isOrtho(BigDecimal[] points)
	{
		Matrix n = new Matrix(points.length, col);
		
		for(int r = 0; r < n.getRow(); r++)
		{
			for(int c = 0; c < col; c++)
			{
				n.setEntry(r, c, p[0][c].evaluateAt(points[r]));
			}
		}
		
		return n.isOrtho();
	}
	
	/**
	 * Private method that initializes the coefficient matrix
	 */
	private void polyToRep()
	{
		for(int c = 0; c < col; c++)
		{
			for(int r = 0; r < ROW; r++)
			{
				m[r][c] = p[0][c].getCoefficients()[r];
			}
		}
	}

	/**
	 * Prints matrix to default system outlet.
	 */
	@Override
	public void printMatrix()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		// for loops iterate through matrix
		for (int c = 0; c < col; c++) 
		{
			sb.append(p[0][c].toString());
			if (c + 1 != col)
				sb.append(", ");
			
        }// end for c
		sb.append("]");
        System.out.print(sb.toString());
	}

}//end class
