/**
 * This interface creates a basis for some linear algebraic elements of
 * a generic type. The methods included are the basic ones needed in most
 * implementations. Implementations are used in the Application for MAX.
 * 
 * @author Fanni Kertesz
 * @version 12/14/2023
 * Final Project
 * CS215-01
 */
public interface LinearAlgebraicElement<T>
{	
		public int getRow();

		public int getCol();
		
		public T getEntry(int r, int c);
		
		public void setEntry(int r, int c, T entry);
		
		public T[][] getMatrix();
		
		public boolean linearlyIndep();
		
		public Matrix createNulSpace();
		
		public void printMatrix();
}
