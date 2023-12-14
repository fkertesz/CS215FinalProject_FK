import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
/**
 * This class creates a Matrix object and includes general
 * methods a user may need when dealing with introductory
 * and advanced linear algebra. Vector is a child class.
 * Matrix is implemented in the Application for MAX.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */

public class Matrix implements LinearAlgebraicElement<BigDecimal>
{
	protected int row;//variable represents the number of rows in the matrix
	protected int col;//variable represents the number of columns in the matrix
	protected BigDecimal [][] m;//array contains the entries of the matrix
	
	/*
	 * Empty-argument constructor that instantiates the matrix
	 * dimensions (row x col) to 1 x 1, and instantiates the
	 * array with those dimensions.
	 */
	Matrix()
	{
		row = 1;
		col = 1;
		m = new BigDecimal [row][col];
	}//end constructor
	
	/**
	 * Preferred-argument constructor that instantiates the
	 * dimensions of the matrix to the preferred row and col
	 * integer variable, and instantiates the array dimensions to
	 * those same values.
	 * @param row the preferred row dimension for the matrix
	 * @param col the preferred column dimension for the matrix
	 */
	Matrix(int row, int col)
	{
		this.row = row;
		this.col = col;
		m = new BigDecimal [row][col];
	}//end constructor
	
	/**
	 * Preferred-argument constructor that instantiates the
	 * matrix with a 2d array
	 * @param m the preferred array of entries
	 */
	Matrix(BigDecimal[][] m)
	{
		row = m.length;
		col = m[0].length;
		this.m = m;
	}

	/**
	 * @return the row dimension of the matrix
	 */
	public int getRow()
	{
		return row;
	}//end getRow

	/**
	 * @return the column dimension of the matrix
	 */
	public int getCol() {
		return col;
	}//end getCol
	
	/**
	 * Getter for a particular entry in the matrix in the
	 * rth row and cth column. The entry numbering corresponds
	 * to array denotation (0 to n for n+1 entries) instead of
	 * the typical matrix denotation(1 to n+1 for n+1 entries).
	 * @param r the row number of the entry
	 * @param c the column number of the entry
	 * @return current entry at r,c
	 */
	public BigDecimal getEntry(int r, int c)
	{
		return m[r][c];
	}//end getEntry
	
	/**
	 * Setter for a particular entry in the matrix in the
	 * rth row and cth column.
	 * @param r the row number of the entry
	 * @param c the column number of the entry
	 * @param entry the value that is set in the entry
	 */
	public void setEntry(int r, int c, BigDecimal entry)
	{
		m[r][c] = entry;
	}//end setEntry
	
	/**
	 * @return current array containing entries of matrix
	 */
	public BigDecimal[][] getMatrix()
	{
		return m;
	}//end getMatrix
	
	/**
	 * This method creates the transpose (matrix with switched
	 * rows and columns) of the matrix and returns it. It does not
	 * affect the matrix stored in the object.
	 * @return transpose of original matrix
	 */
	public Matrix transpose()
	{
		Matrix tM = new Matrix(col, row);//(t)ranspose (M)atrix
		
		//iterates through original matrix
		for(int r = 0; r < row; r++)
		{
			for(int c = 0; c < col; c++)
			{
				//switches the rows and columns of the original
				//matrix and sets it as the entry of the transpose
				tM.setEntry(c, r, m[r][c]);
				
			}//end for c
			
		}//end for r
		
		return tM;
		
	}//end transpose
	
	/**
	 * multiplies matrix by scalar
	 * @param scalar to multiply by
	 * @return scaled matrix
	 */
	public Matrix scalarMultiply(BigDecimal scalar) {
	    BigDecimal[][] result = new BigDecimal[row][col];

	    //multiply every entry of matrix by scalar
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	            result[i][j] = m[i][j].multiply(scalar);
	        }
	    }

	    return new Matrix(result);
	}//end scalarMultiply
	
	/**
	 * This method multiplies two matrices and returns their product.
	 * The left matrix is this matrix and the right matrix is the
	 * inputed one (order matters with matrix multiplication).
	 * @param n the right matrix
	 * @return the product of the two matrices
	 */
	public Matrix multiply(Matrix n)
	{
		if(col != n.getRow())
		{
			throw new UnsupportedOperationException("Matrices not compatible.");
		}

		else {
			//product matrix with dimensions left row x right column
			Matrix prod = new Matrix(row, n.getCol());

			//iterates through each row of left matrix
			for(int leftR = 0; leftR < row; leftR++)
			{
				//iterates through each column of right matrix
				for(int rightC = 0; rightC < n.getCol(); rightC++)
				{
					//restarts the sum for each entry in the product
					BigDecimal sum = new BigDecimal(0);

					//iterates through each common dimension of the two matrices
					for(int comD = 0; comD < col; comD++)
					{
						sum = sum.add(m[leftR][comD].multiply(n.getEntry(comD, rightC)));
					}//end for comD

					//sets entry corresponding to left row, right column
					//to the sum
					prod.setEntry(leftR, rightC, sum);

				}//end for rightC
			}//end leftR
			return prod;
		}
	}//end multiply

	/**
	 * Checks if matrix spans Rn 
	 * @return true if matrix spans Rn, false if matrix does not span Rn
	 */
	public boolean spansRn()
	{
		boolean span = true;
		
		//automatically does not span Rn if cols less than row
		if(row > col)
			span = false;
		
		//if col space has row number of cols, it spans Rn. if not, then no.
		else		
			span = (createColSpace().getCol() == row);
		
		return span;
	}//end spansRn
	
	/**
	 * Checks if matrix is orthogonal.
	 * @return true if matrix is orthogonal, false if matrix is not orthogonal
	 */
	public boolean isOrtho() //every vector orthogonal
	{
		boolean ortho = true;
		
		//if invertible, check if transpose equals inverse
		if(invertible())
			ortho = transpose().equals(inverse());
		//if not invertible, automatically not ortho
		else
			ortho = false;
		
		return ortho;
		
	}//end isOrtho
	
	/**
	 * Checks if matrix is orthonormal.
	 * @return true if matrix is orthonormal, false if matrix is not orthonormal.
	 */
	public boolean isOrthonormal()
	{
		boolean normal = true;
		
		//returns if matrix ortho
		boolean ortho = isOrtho();
		
		//checks if matrix normal
		for(int i = 0; i < col; i++)
		{
			//checks if each column normal
			BigDecimal[][] vecArray = new BigDecimal[row][1];

			for(int k = 0; k < row; k++)
			{
				vecArray[k][0] = m[k][i];
			}

			Vector vec = new Vector(vecArray);
			
			//if a column not normal, breaks loop and returns false
			if(vec.dotProduct(vec).subtract(new BigDecimal(1)).abs().compareTo(new BigDecimal("1e-10")) > 0)
			{
				normal = false;
				i = col;
			}

		}
		
		return ortho&&normal;
	}//end isOrthonormal
	
	/**
	 * Creates null space of matrix.
	 * @return null space in matrix form
	 */
	public Matrix createNulSpace()
	{
		BigDecimal[][] nulBasis;

		//if matrix not linearly independent, create null space
		if(!linearlyIndep())
		{
			Matrix redM = this.rowReduce();//reduced echelon form of matrix

			int[] pCol = new int[col];//pivot columns
			int[] nonPCol = new int[col];//NON-pivot columns
			int pDim = 0;//# of pivots
			int nonPDim = 0;//# of free variables
			for(int c = 0; c < col; c++)//fill arrays with -1's
			{
				pCol[c] = -1;
				nonPCol[c] = -1;
			}

			//finds pivot columns
			for(int r = 0; r < row; r++)
			{
				boolean go = true;
				int i = 0;
				while(go && i < col	)
				{
					if(redM.getEntry(r, i).abs().compareTo(new BigDecimal("1e-10")) > 0)
					{
						go = false;
						pCol[r] = i; //# of column with pPosition

					}//if entry
					i++;
				}//while go
			}//for r

			//if a column is not a pivot column, it's a non-pivot column
			for(int j = 0; j < col; j++)
			{
				if(pCol[pDim]!=j)
				{
					nonPCol[nonPDim] =j;
					nonPDim++;
				}

				else
					pDim++;
			}
			
			nulBasis = new BigDecimal[col][nonPDim];

			int p = 0; //iterator for pivots
			
			//sets entry of null space accordingly
			for(int nRow = 0; nRow < col; nRow++)
			{
				for(int nCol = 0; nCol < nonPDim; nCol++)
				{
					//entry is placeholder of x sub j, then entry is 1
					if(nonPCol[nCol] == nRow)
					{
						nulBasis[nRow][nCol] = new BigDecimal(1);
					}
					//entry is placeholder of another free variable, then entry is 0
					else if(searchArray(nonPCol, nRow) != -1)
					{
						nulBasis[nRow][nCol] = new BigDecimal(0);
					}
					
					//entry is not a free variable, then entry is -1*entry of pivot
					else
					{
						if(redM.getEntry(p, nonPCol[nCol]).abs().compareTo(new BigDecimal("1e-10")) <= 0)
							nulBasis[nRow][nCol] = new BigDecimal(0);
						else
							nulBasis[nRow][nCol] = redM.getEntry(p ,nonPCol[nCol]).multiply(new BigDecimal(-1));
					}
				}//for nCol
				
				//if row was a pivot variable, add one for next pivot row
				if(searchArray(pCol, nRow) != -1)
					p++;
			}//for nRow
		}
		
		//if matrix linearly indep, nul space is zero-vector
		else
		{
			nulBasis = new BigDecimal[col][1];
			for(int i = 0; i < col; i ++)
				nulBasis[i][0] = new BigDecimal(0); // Matrix with zero vector

		}
		
		//NUL BASIS EMPTY
		Matrix nulMatrix = new Matrix(nulBasis);
		
		return nulMatrix;
	}//end createNulSpace
	
	/**
	 * Create row space of matrix
	 * @return row space in matrix form
	 */
	public Matrix createRowSpace()
	{
		return this.transpose().createColSpace();
	}//end createRowSpace
	
	//pivot column
	public Matrix createColSpace()
	{
		Matrix temp = this.rowReduce();//reduced echelon form of matrix
		
		int[] pCol = new int[col];//pivot columns
		int pDim = 0;
		for(int c = 0; c < col; c++)//fill array with -1's
		{
			pCol[c] = -1;
		}
		
		// find pivot columns
	    for (int r = 0; r < row; r++) {
	        for (int c = 0; c < col; c++) {
	            if (temp.getEntry(r, c).abs().compareTo(new BigDecimal("1e-10")) > 0) {
	                pCol[r] = c; // store the pivot column index
	                pDim++;
	                break; // ,ove to the next row after finding the pivot column in this row
	            }
	        }
	    }
		
		BigDecimal[][] colBasis = new BigDecimal[row][pDim];
		
		//if matrix entry is in pivot columns, set array entry to it
		for(int c2 = 0; c2 < col; c2++)
		{
			if(pCol[c2] != -1)
			{
				for(int r2 = 0; r2<row; r2++)
					colBasis[r2][c2] = getEntry(r2, pCol[c2]);
			}
		}
			
		
		return new Matrix(colBasis);
	}//end createColSpace
	
	/**
	 * Creates eigen space of matrix.
	 * @return eigen space basis in matrix form
	 */
	public Matrix eigenSpace()
	{
		Matrix eSpace;
		//not square matrix does not have eigen space
		if (row != col)
        {
            throw new UnsupportedOperationException("Eigenspace does not exist.");
        }
		//3x3+ matrices are too big
		else if(row > 2)
		{
			throw new UnsupportedOperationException("Matrix is too big.");
		}
		//if matrix 2x2
		else if(row == 2)
		{
			eSpace = new Matrix(2,2);
			
			//copies matrix, subtracts eigenvalues on diagonals and
			//gets null space
			
			BigDecimal[] eValues = eigenValues();
			Matrix temp1 = this.copy();
			BigDecimal n00_0 = new BigDecimal(m[0][0].toString());
			n00_0 = n00_0.subtract(eValues[0]);
			BigDecimal n11_0 = new BigDecimal(m[1][1].toString());
			n11_0 = n11_0.subtract(eValues[0]);
			
			temp1.setEntry(0, 0, n00_0);
			temp1.setEntry(1, 1, n11_0);
			eSpace.setEntry(0, 0, temp1.createNulSpace().getEntry(0, 0));
			eSpace.setEntry(1, 0, temp1.createNulSpace().getEntry(1, 0));
						
			//eigen values aren't equal, then continue
			if(!eValues[0].equals(eValues[1]))
			{
				Matrix temp2 = this.copy();
				BigDecimal n00_1 = m[0][0];
				n00_1 = n00_1.subtract(eValues[1]);
				BigDecimal n11_1 = m[1][1];
				n11_1 = n11_1.subtract(eValues[1]);

				temp2.setEntry(0, 0, n00_1);
				temp2.setEntry(1, 1, n11_1);
				eSpace.setEntry(0, 1, temp2.createNulSpace().getEntry(0, 0));
				eSpace.setEntry(1, 1, temp2.createNulSpace().getEntry(1, 0));
			}
			
			//eigen values are the same, then the other vector is the second
			//column of the null space
			else
			{
				eSpace.setEntry(0, 1, temp1.createNulSpace().getEntry(0, 1));
				eSpace.setEntry(1, 1, temp1.createNulSpace().getEntry(1, 1));
			}
			
		}
		// if matrix 1x1
		else if( row ==1)
		{
			BigDecimal[][] e = {{m[0][0]}};
			
			eSpace = new Matrix(e);
		}
		//i
		else
		{
			throw new UnsupportedOperationException("Eigenspace does not exist.");
		}
		
		return eSpace;
	}//end eigenSpace
	
	/**
	 * Calculates eigenvalues and returns them in an array
	 * @return array of eigenvalues
	 */
	public BigDecimal[] eigenValues()
	{
		BigDecimal[] eValues;
		
		//no eigenspace if not square matrix
		if (row != col)
        {
            throw new UnsupportedOperationException("Eigenspace does not exist.");
        }
		
		//matrices greater than 2x2 are too complicated
		else if(row > 2)
		{
			throw new UnsupportedOperationException("Matrix is too big.");
		}
		
		//matrix is 2x2, uses quadratic formula
		else if(row == 2)
		{
			eValues = new BigDecimal[2];
			
			//the entries for simplicity
			BigDecimal a00 = m[0][0];
			BigDecimal a01 = m[0][1];
			BigDecimal a10 = m[1][0];
			BigDecimal a11 = m[1][1];
			
			//first eigenvalue
			BigDecimal pt1_0 = a00.multiply(a11).subtract(a01.multiply(a10)).multiply(new BigDecimal(4));
						
			BigDecimal pt2_0 = a00.add(a11).pow(2).subtract(pt1_0).sqrt(new MathContext(15, RoundingMode.HALF_UP));
						
			eValues[0] = a00.add(a11).add(pt2_0).multiply(new BigDecimal(0.5));
						
			//second eigenvalue
			BigDecimal pt1_1 = a00.multiply(a11).subtract(a01.multiply(a10)).multiply(new BigDecimal(4));
						
			BigDecimal pt2_1 = a00.add(a11).pow(2).subtract(pt1_1).sqrt(new MathContext(15, RoundingMode.HALF_UP));
						
			eValues[1] = a00.add(a11).subtract(pt2_1).multiply(new BigDecimal(0.5));
		}
		
		//matrix is 1x1, single eigenvalue is the entry
		else if(row == 1)
		{
			eValues = new BigDecimal[1];
			
			eValues[0] = m[0][0];
		}
		
		else
		{
			throw new UnsupportedOperationException("Eigenspace does not exist.");
		}
		
		return eValues;
	}

	/**
	 * Returns the reduced echelon form of the matrix. The original
	 * matrix isn't changed.
	 * @return matrix in reduced-echelon form
	 */
	public Matrix rowReduce()
	{
        BigDecimal[][] temp = copy().getMatrix();

        for (int pRow = 0, pCol = 0; pRow < row && pCol < col; pCol++) {
            int pRowIndex = pRow;
            while (pRowIndex < row && temp[pRowIndex][pCol].compareTo(BigDecimal.ZERO) == 0) {
                pRowIndex++;
            }

            if (pRowIndex == row) {
                continue;
            }

            BigDecimal[] tempRow = temp[pRow];
            temp[pRow] = temp[pRowIndex];
            temp[pRowIndex] = tempRow;

            BigDecimal pivotValue = temp[pRow][pCol];
            if (pivotValue.abs().compareTo(new BigDecimal("1e-10")) > 0) {
                // divide the entire row by the pivotValue
                for (int c = 0; c < col; c++) {
                    temp[pRow][c] = temp[pRow][c].divide(pivotValue, 15, RoundingMode.HALF_UP);
                }

                for (int r = 0; r < row; r++) {
                    if (r != pRow) {
                        BigDecimal factor = temp[r][pCol];
                        for (int c = 0; c < col; c++) {
                            // subtract the factor times the entire row from the current row
                            temp[r][c] = temp[r][c].subtract(factor.multiply(temp[pRow][c]));
                        }
                    }
                }
            }

            pRow++;
        }

        // Create and return the reduced matrix
        Matrix reducedMatrix = new Matrix(temp);

        return reducedMatrix;
    }//end rowReduce

	/**
	 * Determines if the matrix is linearly independent
	 * @return true if linearly independent, false if linearly dependent
	 */
	public boolean linearlyIndep()
	{
		boolean indep = true;
		
		//if matrix not invertible, check
		if(!invertible())
		{
			//if colspace not equal to original matrix, not linearly indep
			if(!createColSpace().equals(this))
			{
				indep = false;
			}
		}
		//if matrix invertible, it's lin indep, so no checking required
		
		return indep;
	}//end linearly indep
	
	/**
	 * Checks if a matrix is invertible
	 * @return true if invertible, false if not invertible
	 */
	public boolean invertible()
	{
		boolean inv = true;
		
		//if square matrix, check if rowreduced is the identity matrix
		if(row == col)
		{
			Matrix temp = this.rowReduce();
		
			for(int c = 0; c < col; c++)
			{
				for(int r = 0; r < row; r++)
				{
					if(r == c)
					{
						//if diagonal entry not 1, return false and break loop
						if(temp.getEntry(r, c).subtract(new BigDecimal(1)).abs().compareTo(new BigDecimal("1e-10")) > 0)
						{
							inv = false;
							c = col;
							r = row;
						}
					}
					
					else
					{
						//if non-diagonal entry not 0, return false and break loop
						if(temp.getEntry(r, c).abs().compareTo(new BigDecimal("1e-10")) > 0)
						{
							inv = false;
							c = col;
							r = row;
						}
					}
				}
			}
		}
		
		//if matrix not square, return false
		else
			inv = false;
					
		return inv;
	}//end invertible
	
	/**
	 * Calculates the determinant of the matrix
	 * @return determinant
	 */
    public BigDecimal determinant()
    {
    	//no determinant if matrix not square
        if (row != col)
        {
            throw new UnsupportedOperationException("Determinant does not exist.");
        }

        //1x1 matrix
        else if (row == 1)
        {
            return m[0][0];
        }

        //2x2 matrix
        else if (row == 2)
        {
            // Determinant of a 2x2 matrix
            return m[0][0].multiply(m[1][1]).subtract(m[0][1].multiply(m[1][0]));
        }
        //bigger than 2x2
        else
        {
        	BigDecimal det = new BigDecimal(0);

        	for (int i = 0; i < col; i++)
        	{
        		//signs according to indexes
        		BigDecimal sign;
        		if (i % 2 == 0)
        		{
        			sign = new BigDecimal(1);
        		}
        		else
        		{
        			sign = new BigDecimal(-1);
        		}
        		//recursively uses this function for determinant of a smaller matrix
        		BigDecimal subDet = m[0][i].multiply(getMinor(0, i).determinant());
        		det = det.add(sign.multiply(subDet));
        	}

        	return det;
        }
    }//end determinant

    /**
     * Private method the determinant and inverse methods use to get
     * a portion of a matrix
     * @param rowToRemove
     * @param colToRemove
     * @return submatrix
     */
    private Matrix getMinor(int rowToRemove, int colToRemove)
    {
        BigDecimal[][] minorMat = new BigDecimal[row - 1][col - 1];

        //copies matrix except for the row and column to be removed
        for (int i = 0, newR = 0; i < row; i++)
        {
            if (i == rowToRemove)
            {
                continue; // skip the row to be removed
            }

            for (int j = 0, newC = 0; j < col; j++)
            {
                if (j == colToRemove)
                	{
                    continue; // skip the column to be removed
                }

                minorMat[newR][newC] = m[i][j];
                newC++;
            }

            newR++;
        }

        return new Matrix(minorMat);
    }//end getMinor
	
    /**
     * Creates the inverse matrix if matrix is invertible
     * @return inverse matrix
     */
    public Matrix inverse() {
       //if not invertible, no inverse
    	if (!invertible()) {
            throw new UnsupportedOperationException("Inverse does not exist.");
        }
    	
    	else
    	{
        BigDecimal det = determinant();

        BigDecimal detInverse = BigDecimal.ONE.divide(det, 15, RoundingMode.HALF_UP);

        // Create the inverse matrix directly using the cofactor formula
        BigDecimal[][] inverseMatrix = new BigDecimal[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                BigDecimal sign = ((i + j) % 2 == 0) ? BigDecimal.ONE : new BigDecimal(-1);
                BigDecimal subDet = getMinor(i, j).determinant();
                inverseMatrix[i][j] = sign.multiply(subDet).multiply(detInverse);
            }
        }

        // Transpose the matrix to get the final result
        return new Matrix(inverseMatrix).transpose();
    	}
    }
	
    /**
     * Determines if two matrices are equal
     * @param n, other matrix
     * @return true if they're equal, false if not
     */
	public boolean equals(Matrix n)
	{
		boolean equal = true;
		
		if(row == n.getRow() && col == n.getCol())
		{
			for(int c = 0; c < col; c++)
			{
				for(int r = 0; r < row; r++)
				{
					//if two compared entries aren't equal, break loop and return false
					if(m[r][c].subtract(n.getEntry(r, c)).abs().compareTo(new BigDecimal("1e-10")) > 0)
					{
						equal = false;
						c = col;
						r = row;
					}
				}
			}
		}
		//automatically not equal if they don't have the same dimensions
		else
			equal = false;
		
		return equal;
	}
	
	/*
	 * Prints out the matrix on the system's default
	 * outlet.
	 */
    @SuppressWarnings("deprecation")
	public void printMatrix() {
        StringBuilder sb = new StringBuilder();

        // for loops iterate through matrix
        for (int r = 0; r < row; r++) {
            sb.append("[");
            for (int c = 0; c < col; c++) {
                BigDecimal formattedValue = m[r][c].setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
                sb.append(formattedValue.toString());
                if (c + 1 != col)
                    sb.append(" ");
            }// end for c
            sb.append("]\n");
        }// end for r
        System.out.print(sb.toString());
    }// end printMatrix
	
	/**
	 * Array searching algorithm for searching for bases. The result will be
	 * the index of the target or negative if target can't be located.
	 * @param array
	 * @param target
	 * @return
	 */
	private int searchArray(int[] array, int target)
	{
		int result = -1;
		for(int i = 0; i < array.length; i++)
		{
			if(target == array[i])
				result = i;
		}
		
		return result;
	}
	
	/**
	 * Prints an array to the system's default outlet.
	 * @param array
	 */
	public void printArray(BigDecimal[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		// for loops iterate through matrix
		for (int i = 0; i < array.length; i++) {

			sb.append(array[i].toString());
			if (i + 1 != array.length)
				sb.append(",");

		}// end for r
		sb.append("]\n");
		System.out.print(sb.toString());
	}// end 
	
	/**
	 * Creates a copy of the matrix
	 * @return copy of matrix
	 */
	public Matrix copy() {
	    BigDecimal[][] copyArray = new BigDecimal[row][col];
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	            copyArray[i][j] = m[i][j];
	        }
	    }
	    return new Matrix(copyArray);
	}
}//end class