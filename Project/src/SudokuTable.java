
public class SudokuTable extends Table{

	public SudokuTable(boolean[][] M) {
		super(M);
	}

	public void printSolutions(){
		System.out.println("We're now going to print the solutions");		
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
				System.out.print(M[i][j]+" ");
			}
			System.out.println("");
		}
		System.out.println("We finished printing the solutions");
		System.exit(0);
	}

	public static void main(String args[]){
		boolean [][]M = null;
		M = Table.readExactCoverProblem();
		printMatrix(M);
		SudokuTable mytable = new SudokuTable(M);
		mytable.Solve();
	}
	
}
