import java.math.BigDecimal;
import java.util.Scanner;
/**
 * Application of MAX, the user-friendly linear algebra tool. Can do
 * general methods a user may need when dealing with introductory and
 * advanced linear algebra. Uses classes Function, FunctionMatrix,
 * Matrix, PolyMatrix, Polynomial, and Vector.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */
public class Application
{
	private static boolean cont = true;//static variable for continuing the program
	private static boolean same = true;//static variable for using the same object
	
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("Welcome to MAX, the Matrix Algebra eXplorer.");
		System.out.println("(Type \"stop\" at any point if you'd like to stop.)");

		while(cont == true)
		{
			same = true; //reset same object
			System.out.println("\nMatrix or vector or function matrix?(m/v/f)");
			String mOrV = scan.nextLine();

			if(mOrV.equals("m"))
			{
				Matrix m = enterMatrix();
				System.out.println("This is your matrix: ");
				m.printMatrix();

				while(same)
				{
					System.out.println("\nWhat would you like to do? \n(Type \"list\" to see "
							+ "list of possible commands and \"new\" to do new object.)");

					String toDo = scan.nextLine();

					if(toDo.equals("list"))
					{
						System.out.println("row reduce, linearly independent, transpose, "
								+ "invertible, inverse, determinant, scalar multiply, multiply"
								+ ", \nspans Rn, orthogonal, orthonormal, null space, row "
								+ "space, column space, eigen space, eigen values ");
					}
					else if(toDo.equals("row reduce"))
					{
						System.out.println("\nRowreduced matrix:");
						m.rowReduce().printMatrix();
					}
					else if(toDo.equals("linearly independent"))
					{
						if(m.linearlyIndep())
							System.out.println("\nYour matrix is linearly independent.");
						else
							System.out.println("\nYour matrix is linearly dependent.");
					}
					else if(toDo.equals("transpose"))
					{
						System.out.println("\nTranspose:");
						m.transpose().printMatrix();
					}
					else if(toDo.equals("invertible"))
					{
						if(m.invertible())
							System.out.println("\nYour matrix is invertible.");
						else
							System.out.println("\nYour matrix is not invertible.");
					}
					else if(toDo.equals("inverse"))
					{
						try {
							Matrix inv = m.inverse();
							System.out.println("\nInverse:");
							inv.printMatrix();
						}
						catch(UnsupportedOperationException e)
						{
							System.out.println("\nInverse does not exist.");
						}
					}
					else if(toDo.equals("determinant"))
					{
						try {
							BigDecimal det = m.determinant();
							System.out.println("\nDeterminant: " + det.toString());
						}
						catch(UnsupportedOperationException e)
						{
							System.out.println("\nMatrix does not have determinant.");
						}
					}
					else if(toDo.equals("scalar multiply"))
					{
						System.out.println("Enter scalar: ");

						BigDecimal scalar = new BigDecimal(scan.nextLine());

						System.out.println("\nScaled matrix:");
						m.scalarMultiply(scalar).printMatrix();
					}
					else if(toDo.equals("multiply"))
					{
						try{
							Matrix n = enterMatrix();

							Matrix prod = m.multiply(n);

							System.out.println("\nProduct matrix:");

							prod.printMatrix();
						}
						catch(UnsupportedOperationException e)
						{
							System.out.println("\nMatrices aren't compatible. "
									+ "Original matrix has " + m.getCol() + " columns, so new "
									+ "matrix must have " + m.getCol() + " rows.");
						}
					}
					else if(toDo.equals("spans Rn"))
					{
						if(m.spansRn())
							System.out.println("\nYour matrix spans Rn.");
						else
							System.out.println("\nYour matrix does not span Rn.");
					}
					else if(toDo.equals("orthogonal"))
					{
						if(m.isOrtho())
							System.out.println("\nYour matrix is orthogonal.");
						else
							System.out.println("\nYour matrix is not orthogonal.");
					}
					else if(toDo.equals("orthonormal"))
					{
						if(m.isOrthonormal())
							System.out.println("\nYour matrix is orthonormal.");
						else
							System.out.println("\nYour matrix is not orthonormal.");
					}
					else if(toDo.equals("null space"))
					{
						System.out.println("\nNull space:");
						m.createNulSpace().printMatrix();
					}
					else if(toDo.equals("row space"))
					{
						System.out.println("\nRow space:");
						m.createRowSpace().printMatrix();
					}
					else if(toDo.equals("column space"))
					{
						System.out.println("\nColumn space:");
						m.createColSpace().printMatrix();
					}
					else if(toDo.equals("eigen space"))
					{
						try
						{
							System.out.println("\nEigen space:");
							m.eigenSpace().printMatrix();
						}
						catch(UnsupportedOperationException e)
						{
							System.out.println("\nMatrix does not have an eigenspace.");
						}
					}
					else if(toDo.equals("eigen values"))
					{
						try
						{
							System.out.println("\nEigen values:");
							m.printArray(m.eigenValues());
						}
						catch(UnsupportedOperationException e)
						{
							System.out.println("\nMatrix does not have eigen values.");
						}
					}
					else if(toDo.equals("new"))
						same = false;
					else if(toDo.equals("stop"))
						wantToStop();
				}
			}

			//VECTOR
			else if(mOrV.equals("v"))
			{
				System.out.println("\nWhat's the vector's size? (col)");

				String rowStr = scan.nextLine();

				int row = Integer.parseInt(rowStr);

				BigDecimal[][] array = new BigDecimal[row][1];

				System.out.println("Type in the vector in one line. Separate entries by spaces.");

				String entries = scan.nextLine();

				if(!entries.equals("stop"))
				{
					boolean tryAgain = true;
					while(tryAgain)
					{
						String[] stringEntries = entries.split(" ");
						if(stringEntries.length == row)
						{
							for(int j = 0; j < row; j++)
								array[j][0] = new BigDecimal(stringEntries[j]);
							tryAgain = false;
						}
						else
						{
							System.out.println("Number of entries were " + stringEntries.length +
									" instead of " + row + ". Try the vector again and continue.");
						}
					}
				}

				else
					wantToStop();

				System.out.println("This is your vector: ");
				Vector v = new Vector(array);
				v.printMatrix();

				while(same)
				{
					System.out.println("\nWhat would you like to do? \n(Type \"list\" to see "
							+ "list of possible commands and \"new\" to do new object.)");

					String toDo = scan.nextLine();

					if(toDo.equals("list"))
					{
						System.out.println("scalar multiply, multiply by matrix, normal, dot product, "
								+ "is in column space, is in row space, is in nul space.");
					}
					else if(toDo.equals("scalar multiply"))
					{
						System.out.println("Enter scalar: ");

						BigDecimal scalar = new BigDecimal(scan.nextLine());

						System.out.println("\nScaled vector:");

						v.scalarMultiply(scalar).printMatrix();
					}
					else if(toDo.equals("multiply by matrix"))
					{	
						try
						{
							Matrix n = enterMatrix();


							Matrix prod = v.multByM(n);

							System.out.println("\nProduct matrix: ");
							prod.printMatrix();
						}

						catch(UnsupportedOperationException e)
						{
							System.out.println("\nMatrix isn't compatible. "
									+ "Vector has " + v.getRow() + " rows, so new "
									+ "matrix should have " + v.getRow() + " columns.");
						}
					}
					else if(toDo.equals("normal"))
					{
						if(v.isOrthonormal())
							System.out.println("\n\nYour vector is normal.");
						else
							System.out.println("Your vector is not normal.");
					}
					else if(toDo.equals("dot product"))
					{
						BigDecimal[][] array3 = new BigDecimal[row][1];

						System.out.println("Type in the vector in one line. Separate entries by spaces.");

						String entries3 = scan.nextLine();

						if(!entries3.equals("stop"))
						{
							boolean tryAgain = true;
							while(tryAgain)
							{
								String[] stringEntries3 = entries3.split(" ");
								if(stringEntries3.length == row)
								{
									for(int j = 0; j < row; j++)
										array3[j][0] = new BigDecimal(stringEntries3[j]);
									tryAgain = false;
								}
								else
								{
									System.out.println("Number of entries were " + stringEntries3.length +
											" instead of " + row + ". Try the vector again and continue.");
								}
							}
						}

						else
							wantToStop();

						Vector w = new Vector(array3);
						System.out.println("\nDot product: " + v.dotProduct(w).toString());
					}
					else if(toDo.equals("is in column space"))
					{
						Matrix m = enterMatrix();
						if(v.isInColSpace(m))
							System.out.println("\nYour vector is in the column space.");
						else
							System.out.println("\nYour vector is not in the column space.");
					}
					else if(toDo.equals("is in row space"))
					{
						Matrix m = enterMatrix();
						if(v.isInRowSpace(m))
							System.out.println("\nYour vector is in the row space.");
						else
							System.out.println("\nYour vector is not in the row space.");	
					}
					else if(toDo.equals("is in nul space"))
					{
						Matrix m = enterMatrix();
						if(v.isInNulSpace(m))
							System.out.println("\nYour vector is in the nul space.");
						else
							System.out.println("\nYour vector is not in the nul space.");	
					}
					else if(toDo.equals("stop"))
						wantToStop();
					else if(toDo.equals("new"))
						same = false;
				}
			}

			//FunctionMatrix
			else if(mOrV.equals("f"))
			{
				System.out.println("Polynomials or functions? (p/f)");
				
				String pOrF = scan.nextLine();

				if(pOrF.equals("p"))
				{

					System.out.println("\nHow many polynomials in the matrix?");

					String colStr = scan.nextLine();

					int col = Integer.parseInt(colStr);

					System.out.println("Type in the polynomials' coefficients per line in this order: "
							+ "a*1+b*x+c*x^2");
					System.out.println("In this form, separated by spaces: a b c");

					Polynomial[][] polys = new Polynomial[1][col];

					for(int i = 0; i < col; i++)
					{
						String coeffStr = scan.nextLine();

						if(!coeffStr.equals("stop"))
						{
							String[] coeffEntries = coeffStr.split(" ");
							BigDecimal[] coeff = new BigDecimal[3];

							if(coeffEntries.length == 3)
							{
								for(int j = 0; j < 3; j++)
								{
									coeff[j] = new BigDecimal(coeffEntries[j]);
								}

								polys[0][i] = new Polynomial(coeff);
							}
							else
							{
								System.out.println("Number of entries were " + coeffEntries.length +
										" instead of 3. Try the vector again and continue.");
								i--;
							}
						}
						else
							wantToStop();
					}

					System.out.println("This is your function matrix: ");
					PolyMatrix f = new PolyMatrix(polys);
					f.printMatrix();

					while(same)
					{
						System.out.println("\nWhat would you like to do? \n(Type \"list\" to see "
								+ "list of possible commands and \"new\" to do new object.)");

						String toDo = scan.nextLine();

						if(toDo.equals("list"))
						{
							System.out.println("linearly independent, spans P2, null space, "
									+ "orthogonal points.");
						}
						else if(toDo.equals("linearly independent"))
						{
							if(f.linearlyIndep())
								System.out.println("\nYour function matrix is linearly independent.");
							else
								System.out.println("\nYour function matrix is linearly dependent.");
						}
						else if(toDo.equals("spans P2"))
						{
							if(f.spansP2())
								System.out.println("\nYour function matrix spans P2.");
							else
								System.out.println("\nYour function matrix does not span P2.");
						}
						else if(toDo.equals("null space"))
						{
							System.out.println("\nNull space:");
							f.createNulSpace().printMatrix();
						}
						else if(toDo.equals("orthogonal points"))
						{
							System.out.println("Define inner product space by points separated by "
									+ "spaces like a b c:");
							String iPStr = scan.nextLine();
							String[] iPEntries = iPStr.split(" ");
							BigDecimal[] iP = new BigDecimal[iPEntries.length];
							if(!iPStr.equals("stop"))
							{
								for(int i = 0; i < iPEntries.length; i++)
								{
									iP[i] = new BigDecimal(iPEntries[i]);
								}
							}
							else
								wantToStop();

							if(f.isOrtho(iP))
								System.out.println("\nYour function matrix is orthogonal in defined "
										+ "innerproduct space");
							else
								System.out.println("\nYour function matrix is not orthogonal in defined "
										+ "innerproduct space");


						}
						else if(toDo.equals("stop"))
							wantToStop();
						else if(toDo.equals("new"))
							same = false;

					}//while same
				}//if poly
				
				else if(pOrF.equals("f"))
				{
					System.out.println("How many functions?");
					
					String nOfFStr = scan.nextLine();
					
					int nOfF = Integer.parseInt(nOfFStr);
					
					Function[][] functionArray = new Function[1][nOfF];
					
					System.out.println("Type each function in separate lines. "
							+ "\nKeep in mind that trig functions, e, pi, etc. "
							+ "can't be used.");
					
					for(int i = 0; i < nOfF; i++)
					{
						functionArray[0][i] = new Function(scan.nextLine());
					}
					
					System.out.println("This is your function matrix: ");
					
					FunctionMatrix f = new FunctionMatrix(functionArray);
					
					f.printMatrix();
					
					while(same)
					{
						System.out.println("\nWhat would you like to do? \n(Type \"orthogonal points\""
								+ " or \"new\" to do new object.)");

						String toDo = scan.nextLine();

						if(toDo.equals("orthogonal points"))
						{
							System.out.println("Define inner product space by points separated by "
									+ "spaces like a b c:");
							String iPStr = scan.nextLine();
							String[] iPEntries = iPStr.split(" ");
							BigDecimal[] iP = new BigDecimal[iPEntries.length];
							if(!iPStr.equals("stop"))
							{
								for(int i = 0; i < iPEntries.length; i++)
								{
									iP[i] = new BigDecimal(iPEntries[i]);
								}
							}
							else
								wantToStop();

							if(f.isOrtho(iP))
								System.out.println("\nYour function matrix is orthogonal in defined "
										+ "innerproduct space");
							else
								System.out.println("\nYour function matrix is not orthogonal in defined "
										+ "innerproduct space");
						}
						else if(toDo.equals("stop"))
							wantToStop();
						else if(toDo.equals("new"))
							same = false;
						
					}//while same
					
				}//if function

			}//function matrix

			//STOP
			else if(mOrV.equals("stop"))
				wantToStop();
		}//while cont

		System.out.println("MAX stopped.");

		scan.close();

	}

	/**
	 * Static method for frequently appearing command. Method stops the program.
	 */
	public static void wantToStop()
	{
		same = false;
		cont = false;
	}

	/**
	 * Static method for frequently appearing command. Method intakes
	 * a matrix from the user and returns it as a Matrix object.
	 * @return matrix as a Matrix object
	 */
	public static Matrix enterMatrix()
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("\nWhat's the matrix's size? (rowxcol)");

		String rxc = scan.nextLine();

		String[] parts = rxc.split("x");

		int row = Integer.parseInt(parts[0]);
		int col = Integer.parseInt(parts[1]);

		BigDecimal[][] array = new BigDecimal[row][col];

		System.out.println("Type in the matrix by row. Separate row entries by spaces " 
				+ "and rows by starting a new line.");

		for(int i = 0; i < row; i++)
		{
			String rowOfEntries = scan.nextLine();
			if(!rowOfEntries.equals("stop"))
			{
				String[] stringEntries = rowOfEntries.split(" ");
				if(stringEntries.length == col)
				{
					for(int j = 0; j < col; j++)
						array[i][j] = new BigDecimal(stringEntries[j]);
				}
				else
				{
					System.out.println("Number of entries were " + stringEntries.length +
							" instead of " + col + ". Try the row again and continue.");
					i--;

				}
			}
			else
				wantToStop();
		}
		//scan.close();

		return new Matrix(array);
	}
}//end class
