import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Table {
	Column header;
	protected ArrayList<Cell> solutions = new ArrayList<Cell>();
	Cell [][] CellsOfM;
	boolean foundSolution;
    public ArrayList<Cell> solution2 = new ArrayList<>();
	
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
		int[] labels = new int[solution2.size()];
		int i=0;
		for(Cell elem : solution2){
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
		// finds and prints all solutions
		foundSolution =false;
		RecSolve();
		if(!foundSolution){
			System.out.println("There is no solution");
			System.out.println("");
		}
	}
	public void RecSolve(){
		if (foundSolution) return;
		if (header.R == header){
            solution2.addAll(solutions);
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
	static void printMatrix(boolean M[][]){
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
	
	static boolean [][] readExactCoverProblem (){
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
	
	
	public static void main(String args[]){
		boolean [][]M = null;
		M = Table.readExactCoverProblem();
		printMatrix(M);
		Table mytable = new Table(M);
		mytable.Solve();
	}
	
}
