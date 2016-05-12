import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


	

	class Cell {
		Cell R, L, U, D; //U and D might be instances of column
		Column C; //instance of class column
		int i, j;
		public Cell(Column C, int i, int j){
			R = L = U = D = null;
			this.C = C;
			this.i = i;
			this.j = j;
		}
	};

	class Column extends Cell{
		//R and L are instances of Column
		String name;
		int size;
		public Column(String n, int j){
			super(null, 0, j);
			this.C = this;
			name = n;
		}
		
	};


	class Table {
		Column header;
		protected ArrayList<Cell> solutions = new ArrayList<Cell>();
		Cell [][] CellsOfM;
		boolean foundSolution;
		
	 	public Table(boolean M[][]){
			int m = M.length;
			int n = M[0].length;
			CellsOfM = new Cell[m][n];
			header = new Column(null, 0);
			header.C = null;
			Column p = header;
			Column q;
			
			// We first create a linked list of linked lists structure 
			for(int j=0; j<n; j++){
				q = new Column(Integer.toString(j+1), j+1); 
				p.R = q;
				q.L = p;
				p = q;
				Cell t = p;
				Cell r;
				for(int i=0; i<m; i++){
					if(M[i][j]==true){
						r = new Cell(p, i+1, j+1);
						t.D = r;
						r.U = t;
						t = r;	
						CellsOfM[i][j] = t;
						p.size++;
					}
				}			
				t.D = p;
				p.U = t;	
				if (p.size==0){
					System.out.println("For this matrix, we are sure that the exact cover has no solution");
				}			
			}	
			p.R = header;
			header.L = p;
			
			// then we add the links between elements of different linked lists
			for(int i=0; i<m; i++){
				Cell t = null;
				Cell r;
				Cell firstNotNull;
				int j;
				for(j=0; j<n && M[i][j]==false; j++);
				if (j==n) continue;
				firstNotNull = CellsOfM[i][j];
				t = firstNotNull;
				for(;j<n; j++){
					if(M[i][j]){
						r = CellsOfM[i][j];
						t.R = r;
						r.L = t;
						t = r;
					}
				}
				t.R = firstNotNull;
				firstNotNull.L = t;
			}
			
		}
		public void print_sizes(){
			Column p = (Column) header.R;
			while (p!=header){			
				System.out.println(p.size);		
				p = (Column) p.R;
			}
			System.out.println("");
		}
		private void auxAddRowToSolutions(Cell p){
			// adds the row that contains the cell p and covers its intersecting columns, 
			// except the one that contains p
			solutions.add(p);
			Cell q = p.R;
			while(q!=p){
				Cover(q.C);				
				q = q.R;
			}		
		}
		public void addRowToSolutions(Cell p){
			Cover(p.C);
			auxAddRowToSolutions(p);
		}
		private void auxRemoveRowFromSolutions(Cell p){
			// does the reverse operation
			p = solutions.get(solutions.size()-1);
			solutions.remove(solutions.size()-1);
			Cell q = p.L;
			while(q!=p){
				Uncover(q.C);
				q = q.L;
			}			
		}
		public void removeRowFromSolutions(Cell p){
			auxRemoveRowFromSolutions(p);
			Uncover(p.C);
		}
		public void printSolutions(){
			System.out.println("We're now going to print the solutions");
			int[] labels = getSolutionsRowLabels();
			for(int row : labels){
				System.out.print(row+" ");
			}
			System.out.println("");
			System.out.println("We finished printing the solutions");
		}
		int [] getSolutionsRowLabels (){
			int[] labels = new int[solutions.size()];
			int i=0;
			for(Cell elem : solutions){
				labels[i++] = elem.i;
			}
			return labels;
		}
		public void Cover(Column c){
			c.R.L = c.L;
			c.L.R = c.R;
			Cell p = c.D;
			Cell q;
			while(p!=c){
				q = p.R;
				while(q!=p){
					q.D.U = q.U;
					q.U.D = q.D;
					q.C.size--;
					q = q.R;
				}
				p=p.D;
			}	
		}
		public void Uncover(Column c){
			Cell p = c.U;
			Cell q;
			while(p!=c){
				q = p.L;
				while(q!=p){
					q.C.size++;
					q.U.D = q;
					q.D.U = q;
					q = q.L;				
				}			
				p = p.U;
			}	
			c.L.R = c;
			c.R.L = c;
		}
		
		public void Solve(){
			foundSolution = false;
			RecSolve();
		}
		public void RecSolve(){
			if (foundSolution)
				return;
			if (header.R == header){
				printSolutions();	
				foundSolution = true;
				return;
			}
			//We look for the column that contains the least number of 1s
			Column cmin=(Column) header.R, c;
			int smin = cmin.size;
			for(c = (Column) header.R; c!=header; c= (Column) c.R){
				if (smin > c.size){
					smin = c.size;
					cmin = c;
				}
			}
			if(smin==0){
				return;
			}
			Cover(cmin);
			Cell p = cmin.D;
			while(p!=cmin){
				auxAddRowToSolutions(p);
				RecSolve();
				auxRemoveRowFromSolutions(p);
				p = p.D;	
			}
			Uncover(cmin);		
		}
		void printMatrix(boolean M[][]){
			int m = M.length;
			int n = M[0].length;
			for(int i=0; i<m; i++){
				for(int j=0; j<n; j++){
					if (M[i][j]) System.out.print(1);
					else System.out.print(0);
					System.out.print(" ");
				}			
				System.out.println("");
			}
		}
		
		boolean [][] readExactCoverProblem (){
	        Scanner in;
			try {
				in = new Scanner(new File("input/inExactCover.txt"));
		        int m = in.nextInt();
		        int n= in.nextInt();
		        boolean[][] M = new boolean [m][n];
		        for (int i=0; i<m; i++){
		        	for(int j=0; j<n; j++){
		        		int cur = in.nextInt();
		        	   	if (cur==1) M[i][j] = true;
		        	   	else M[i][j] = false;
		        	}
		        }
		        in.close();
		        return M;
		    } catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
	};

	class SudokuTable extends Table{
	
		public SudokuTable(boolean[][] M) {
			super(M);
		}
	
		public void printSolutions(){
			int [] labels = getSolutionsRowLabels();
			int N2 = labels.length;
			int N = (int) Math.sqrt((double)N2);
			int n = (int) Math.sqrt((double)N);
			int [][]M = new int [N][N];
			int code, val, row, column;
			
			for (int i=0; i<N2; i++){
				int x = code = labels[i]-1;
				val = code/N2+1;
				int k = val-1;
				row = (code%N2)/N;
				column = code%N;
				//System.out.println("i = "+i+", x = "+x+", val = "+val+", row = "+row+", column = "+column+". For exact cover's sake: columns are "+(N*k+row)+", "+(N2+N*k+column)+", "+2*N2+N*k + n*(row/n)+(column/n));
				M[row][column] = val;
			}
			
			for(int i=0; i<N; i++){
				for(int j=0; j<N; j++){
					System.out.print( ((char) ('A' + M[i][j]-1))+" ");
				}
				System.out.println("");
			}
			System.out.println("");
		}	
	};
class Sudoku {	
	static int n; //typically, n=3, N=9,N2=81
	static int N;
	static int N2;
	static int [][] originalTable;
	SudokuTable dancingTable;
	
	public Sudoku(Scanner in){
		readSudokuTable(in);
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
					table[k*N2+N*i+j][N*k+i] = true; // for (i, j) is in the lines i
					table[k*N2+N*i+j][N2+N*k+j] = true; //for (i, j) is in the column j
					table[k*N2+N*i+j][2*N2+N*k + n*(i/n)+(j/n)] = true; // for (i, j) is in the subsquare n*i+j
					table[k*N2+N*i+j][3*N2+N*i+j]=true; //means that the position (i, j) is filled				
				}				
			}			
		}
		dancingTable = new SudokuTable(table);
	}
		
	static void readSudokuTable(Scanner in){
		n = 4;
		N = n*n;
		N2 = N*N;
      	originalTable= new int[N][N];
      	int i, j;
      	String s;
      	for (i=0; i<N; i++){
      		s = in.next();
      		//System.out.println("i = "+i+" s = "+s+" size of s = "+s.length());
      		for(j=0; j<N; j++)
      			originalTable[i][j] = (int) (1+s.charAt(j)-'A');      		
      	}		
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
	public static void main(String args[]){

			Scanner in = new Scanner(System.in);
			int t = in.nextInt();
			while(t>0){
				Sudoku mySudoku = new Sudoku(in);
				mySudoku.Solve();	
				t--;
			}
			in.close();
	}
}
