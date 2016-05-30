import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sudoku {
	static int n; //typically, n=3, N=9,N2=81
	static int N;
	static int N2;
	static int [][] originalTable;
	SudokuTable dancingTable;
	
	public Sudoku(){
		readSudokuTable();
		initializeEmptySudoku();
		fillStartingCells();
	}
	public void initializeEmptySudoku(){
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
		int N3 = N*N2;
		boolean [][] table = new boolean[N3][4*N2];
		
		int i, j, k;
		for(k=0; k<N; k++){
			// we fill the table's lines with true
			for(i=0; i<N; i++){
				for(j=0; j<N; j++){
					//System.out.println("i = "+i+" j= "+j);
					//System.out.println("i = "+i+" j= "+j);
					//System.out.println("k*N2+N*i+j = "+	(k*N2+N*i+j)+" 2*N2+N*k + n*(i/n)+(j/n) = "+(2*N2+N*k + n*(i/n)+(j/n)));
					
					table[k*N2+N*i+j][N*k+i] = true; // for (i, j) is in the lines i
					table[k*N2+N*i+j][N2+N*k+j] = true; //for (i, j) is in the column j
					table[k*N2+N*i+j][2*N2+N*k + n*(i/n)+(j/n)] = true; // for (i, j) is in the subsquare n*i+j
					table[k*N2+N*i+j][3*N2+N*i+j]=true; //means that the position (i, j) is filled				
				}				
			}			
		}
		dancingTable = new SudokuTable(table);
	}
		
	static void readSudokuTable(){
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		N = n*n;
		N2 = N*N;
	    originalTable= new int[N][N];
	    int i, j;
	    for (i=0; i<N; i++){
	    	for(j=0; j<N; j++)
	    		originalTable[i][j] = in.nextInt();	    
	    }
	    in.close();
	}	
	
	public void fillStartingCells(){
		int i, j, k;
		for(i=0; i<N; i++){
			for(j=0; j<N; j++){
				k = originalTable[i][j];
				if (k>0){
					// in our exact cover solution, there need to be the row k*N2+N*i+j
					// we assume that the input <table> has no collisions
					// otherwise this code could produce meaningless and very unexpected results
					Cell p = dancingTable.CellsOfM[(k-1)*N2+N*i+j][N*(k-1)+i];
					// we consider any of the cells in the row k*N2+N*i+j that contains true
					dancingTable.addRowToSolutions(p);	
				}				
			}			
		}
	}
	
	public void Solve(){
		dancingTable.Solve();
	}
	public void printSudokuSolution(){
		dancingTable.printSolutions();
	}
	public static void main(String args[]){
		Sudoku mySudoku = new Sudoku();
		mySudoku.Solve();	
		mySudoku.printSudokuSolution();
	}
	
}
