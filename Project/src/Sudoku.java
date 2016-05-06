import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sudoku {
	int n, N, N2; //typically, n=3, N=9,N2=81
	Table dancingTable;
	public Sudoku(int n){
		// we initialize the dancing links structure
		/* we have N^3 lines and 3 N*N columns
		 * the first N lines correspond to the top left cell
		 * the following N lines correspond to cell (0, 1)
		 * 
		 * Concerning the columns, the first N*N columns correspond to elements
		 * appearances in each line of the Sudoku table (N elements x N lines)
		 * the following N*N lines correspond to the Sudoku columns and the last
		 * N*N lines to the Sudoku small squares
		 */
		this.n = n;
		this.N = n*n;
		this.N2 = N*N;
		int N3 = N*N2;
		boolean [][] table = new boolean[N3][3*N2];
		
		int i, j, k;
		for(k=0; k<N; k++){
			// we fill the table's lines with true
			for(i=0; i<N; i++){
				for(j=0; j<N; j++){
					table[k*N2+N*i+j][N*k+i] = true; // (i, j) is in the lines i
					table[k*N2+N*i+j][N2+N*k+j] = true; //(i, j) is in the column j
					table[k*N2+N*i+j][2*N2+N*k + n*i+j] = true; // (i, j) is in the subsquare n*i+j
				}				
			}			
		}
		dancingTable = new Table(table);
	}
		
	static int [][] readSudokuTable(){
        Scanner in;
		try {
			in = new Scanner(new File("input/inSudoku.txt"));
			int n = in.nextInt();
			int N = n*n;
	      	int [][] table = new int[N][N];
	      	int i, j;
	      	for (i=0; i<N; i++)
	      		for(j=0; j<N; j++)
	      			table[i][j] = in.nextInt();	      		
	        in.close();
	        return table;
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public void fillStartingCells(int [][] table){
		int N0 = table.length;
		assert (N ==N0);
		int i, j, k;
		for(i=0; i<N; i++){
			for(j=0; j<N; j++){
				k = table[i][j];
				if (k>0){
					// in our exact cover solution, there need to be the row k*N2+N*i+j
					// we assume that the input <table> has no collisions
					// otherwise this code would produce meaningless and very unexpected results
					Cell p = dancingTable.CellsOfM[k*N2+N*i+j][N*k+i];
					// we consider any of the cells in the row k*N2+N*i+j that contains true
					/*dancingTable.solutions.add(p);
					Cell q = p.R;
					while(q!=p){
						dancingTable.Cover(q.C);				
						q = q.R;
					}		*/			
					
				}				
			}			
		}
		
	}
	
	public static void main(String args[]){
		
		
		
	}
	
}
