import java.math.BigDecimal;
/**
 * This class creates a Vector object, a more specific Matrix, 
 * and includes general methods a user may need when dealing
 * with introductory and advanced linear algebra. Vector is
 * implemented in the Application for MAX.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */
public class Vector extends Matrix
{
	
	/*
	 * Empty-argument constructor that instantiates the vector
	 * dimension row to 1 using the super class's constructor.
	 */
	Vector()
	{
		super(1,1);
	}//end constructor
	
	/**
	 * Preferred-argument constructor that instantiates the vector
	 * dimension row to preferred row using the super class's
	 * constructor
	 * @param row the preferred row
	 */
	Vector(int row)
	{
		super(row, 1);
	}//end constructor
	
	Vector(BigDecimal[][] v)
	{
		super(v);
	}//end constructor
	
	/**
	 * @return row dimension
	 */
	public int getRow()
	{
		return row;
	}//end getRow
	
	/**
	 * Getter for a particular entry in the rth row.
	 * @param r the row number of the entry 
	 * @return the entry in row r
	 */
	public BigDecimal getEntry(int r)
	{
		return m[r][0];
	}//end getEntry
	
	/**
	 * Setter for a particular entry in the rth row.
	 * @param r the row number of the entry
	 * @param the entry in row r
	 */
	public void setEntry(int r, BigDecimal entry)
	{
		m[r][0] = entry;
	}//end setEntry
	
	/**
	 * Setter for all entries using a 1-d array of length
	 * corresponding to the vector row numbers and of type double.
	 * @param a the array corresponding to set entries
	 */
	public void setAllEntries(BigDecimal[] a)
	{
		for(int t = 0; t<row; t++)
		{
			m[t][0] = a[t];
		}//end for t
	}//end setAllEntries
	
	/**
	 * This method multiplies a matrix on the left with this vector
	 * on the right. This method is necessary regardless of the
	 * multiply method in the matrix class because this method's
	 * product is a vector and thus returns a Vector object
	 * instead of a Matrix object.
	 * @param m the left matrix
	 * @return the product vector
	 */
	public Vector multByM(Matrix m)
	{
		//product vector with row number same as left matrix
		Vector prod = new Vector(m.getRow());
		
		//iterates through each row of left matrix
		for(int leftR = 0; leftR < m.getRow(); leftR++)
		{
			//restarts the sum for each entry in the product
			BigDecimal sum = new BigDecimal(0);
			
			//iterates through each common dimensions of the matrix
			//and the vector
			for(int comD = 0; comD < row; comD++)
			{
				sum.add(m.getEntry(leftR, comD).multiply(getEntry(comD)));
			}//end for comD
			
			//sets entry corresponding to left row to the sum
			prod.setEntry(leftR, sum);
			
		}//end for leftR
		
		return prod;
	
	}//end multbyM
	
	/**
	 * This method calculates the dot-product of this vector
	 * and another vector. Order does not matter here.
	 * @param v the other vector
	 * @return the dot-product
	 */
	public BigDecimal dotProduct(Vector v)
	{
		BigDecimal sum = new BigDecimal(0);
		
		//iterates through each entry in the vectors
		for(int r = 0; r < row; r++)
		{
			sum = sum.add( m[r][0].multiply(v.getEntry(r)));
		}//end for r
		
		return sum;
	}//end dotProduct
	
	/**
	 * This method parameterizes the entries of this vector,
	 * and represents that parameterization as a matrix.
	 * The columns of represent the "construction" of each variable,
	 * and the rows represent each variable.
	 * @return the parameterization matrix
	 */
	public Matrix simpleParam()
	{
		//parameterization matrix with dimensions (row-1) x row
		Matrix p = new Matrix(row-1,row);
		boolean go = true;
		
		//This variable keeps track of which entry = 1 first.
		//The entry corresponding to i is the parameterized variable
		int i = -1;
		
		while(go)
		{
			i++;
			if(m[i][0].subtract(new BigDecimal(1)).abs().compareTo(new BigDecimal("1e-10")) <= 0)//checks if entry = 1
			{
				go = false;
			}//end if
		}//end while
		
		/*variable skips the param variable column when making
		 * up an identity matrix "besides" the param variable*/
		int altJ = 0;
		
		//iterates through the entries of the vector
		for(int j = 0; j < row; j++)
		{
			//if j = the param variable, so column represents
			//the param variable
			if(j == i)
			{
				int altQ = 0;//variable for skipping param variable
				for(int q = 0; q < row; q++)
				{
					
					/*Entry setting skips the row that would
					correspond to the param variable in the column
					that corresponds to the param variable,
					hence there not being an else block*/
					if(q != i)
					{
						p.setEntry(altQ, j, m[q][0].multiply(new BigDecimal(-1)));
						altQ++;
					}//end if
					
				}//end for q
				
			}//end if
			
			/*Creates the identity matrix wrapping around the
			 * one column that represents the param variable*/
			else
			{
				//iterates through rows
				for(int k = 0; k < row-1; k++)
				{
					//row number corresponding to next 1-diagonal
					if(k == altJ)
					{
						p.setEntry(k, j, new BigDecimal(1));
					}//end if
					
					else
					{
						p.setEntry(k, j, new BigDecimal(0));
					}//end else
					
				}//end for k
				
				altJ++;
			}//end else
			
		}//end for j
		
		return p;
	}//end simpleParam
	
	/**
	 * This method checks if two vectors have the same number of
	 * entries and the same values in corresponding entries,
	 * therefore equal.
	 * @param w the vector being compared to this vector
	 * @return true if equal, false if not equal
	 */
	public boolean equals(Vector w)
	{
		boolean b = false;
		
		//immediately rules out equality if entry numbers don't match
		if(row == w.getRow())
		{
			b = true;
			
			//iterates through each entry and checks if they are unequal
			for(int r = 0; r < row; r++)
			{
				if(m[r][0] != w.getEntry(r))
					b = false;
			}//end for r
			
			return b;
		}//end if
		
		else
			return b;
	}//end equalsTo
	
	/**
	 * Checks if vector is in the column space of the provided matrix
	 * @param matrix
	 * @return true if vector is in the column space, false if not
	 */
	public boolean isInColSpace(Matrix matrix)
	{
		return isInSpace(matrix.createColSpace());
	}
	
	/**
	 * Checks if vector is in the row space of the provided matrix
	 * @param matrix
	 * @return true if vector is in the row space, false if not
	 */
	public boolean isInRowSpace(Matrix matrix)
	{
		return isInSpace(matrix.createRowSpace());
	}
	
	/**
	 * Checks if vector is in the null space of the provided matrix
	 * @param matrix
	 * @return true if vector is in the null space, false if not
	 */
	public boolean isInNulSpace(Matrix matrix)
	{
		return isInSpace(matrix.createNulSpace());
	}
	
	/**
	 * Private method the "isInXSpace" methods use. It checks if augmented
	 * matrix with vector is consistent.
	 * @param matr, basis of space
	 * @return true if vector is in space, false if not
	 */
	private boolean isInSpace(Matrix matr)
	{
		boolean consistent = true;
		
		//matrix and vector are in same Rn
		if(matr.getRow() == row)
		{
			BigDecimal[][] augMat = new BigDecimal[matr.getRow()][matr.getCol()+1];
			
			//create augmented matrix
			for(int r = 0; r < augMat.length; r++)
			{
				for(int c = 0; c < augMat[0].length; c++)
				{
					if(c < matr.getCol())
					{
						augMat[r][c] = matr.getEntry(r, c);
					}
					else
					{
						augMat[r][c] = this.getEntry(r);
					}
				}
			}
			
			Matrix augMatrix = new Matrix(augMat);
			Matrix rrMatrix = augMatrix.rowReduce();
			boolean zeroEntry = false;//true if entry is 0, false if not
			boolean lastEntry = false;//true if entry is in last col, false if not
			for(int r2 = 0; r2 < rrMatrix.getRow(); r2++)
			{
				for(int c2 = 0; c2 < rrMatrix.getCol(); c2++)
				{
					zeroEntry = rrMatrix.getEntry(r2,c2).abs().compareTo(new BigDecimal("1e-10")) <= 0;
					lastEntry = c2 == rrMatrix.getCol() - 1;
					
					//entry is not zero and not last col, move onto next row
					if(!zeroEntry && !lastEntry)
						c2 = rrMatrix.getCol();
					//entry is not zero with previous entries zero and last col,
					//consistent is false and terminates loop
					else if(!zeroEntry && lastEntry)
					{
						consistent = false;
						r2 = rrMatrix.getRow();
						c2 = rrMatrix.getCol();
					}
					//entry is zero and not last col, it moves to next entry in row
					//OR entry is zero and last col, it moves onto next row or ends
				}
			}
			
		}
		
		//matrix and vector now in same Rn
		else
			consistent = false;
		
		return consistent;
	}
	
}//end class
