import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.*;
import java.lang.*;

public class MM {

	private BufferedReader br;
	private int sum = 0;
	private final static String matrixA="MatrixA.txt"; 
	private final static String matrixB="MatrixB.txt";

	public static void main(String[] args) throws FileNotFoundException {
		new MM().MathMultiplicationValues(matrixA, matrixB);
		
	}



	private void MathMultiplicationValues(String mat1, String mat2) {
		try {
			br = new BufferedReader(new FileReader(mat1));			// to read input from file.
			
			String line;
			int mat1rows = 0, mat1cols = 0, mat2rows = 0, mat2cols = 0;
			while ((line = br.readLine()) != null) {
				mat1cols = line.split(" ").length + 1;
				mat1rows++;
			}
			br.close(); // To close file
			br = new BufferedReader(new FileReader(mat2)); // to read input from file.
			while ((line = br.readLine()) != null) {
				mat2cols = line.split(" ").length + 1;
				mat2rows++;
			}

			mat1cols = mat1cols-1;
			mat2cols = mat2cols-1;


			int[][] mat1vals = new int[mat1rows ][mat1cols ]; // Copies input fron MatrixA to an  2Dim array
			int[][] mat2vals = new int[mat2rows ][mat2cols ]; // Copies input fron MatrixB to an  2Dim array
			
			br.close();

			
			br = new BufferedReader(new FileReader(mat1));
			for (int i = 0; i < mat1rows; i++) {
				line = br.readLine();
				String[] colvals = line.split(" ");
				for (int j = 0; j < mat1cols; j++) {
					mat1vals[i][j] = Integer.parseInt(colvals[j]);
					
				}
				
			}
			

			br.close();

			br = new BufferedReader(new FileReader(mat2));
			for (int i = 0; i < mat2rows; i++) {
				line = br.readLine();
				String[] colvals = line.split(" ");
				for (int j = 0; j < mat2cols; j++) {
					mat2vals[i][j] = Integer.parseInt(colvals[j]);
					
				}
				

			}

			br.close();


			if ((mat1cols) == mat2rows) { 
				int[][] resltmat = new int[mat1rows][mat2cols];
				for (int i = 0; i < mat1rows; i++) { //Loop does matrix multiplication. 
					for (int j = 0; j < mat2cols; j++) {
						for (int k = 0; k < mat2rows; k++)
							sum = sum + mat1vals[i][k] * mat2vals[k][j];
						resltmat[i][j] = sum;
						sum = 0;
					}
				}

				final PrintWriter pw = new PrintWriter("matrixAnswer.txt"); //Creates a new file called Matrix Answer. 
				for (int i = 0; i < mat1rows; i++) 
				{
					
					for (int j = 0; j < mat2cols; j++) {
						pw.print(resltmat[i][j] + " "); // Writes the output to file the file called MatrixAnswer
						
					}
					pw.println();
					
				}
				pw.close();
			} else // If no of columns not equal to rows control passes to else block. 
				System.out.println("Multiplication of Matrix is not possible because columns are not equal to rows");

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
