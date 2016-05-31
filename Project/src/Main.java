
public class Main {

    public static void printMatrix (boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                System.out.print(matrix[i][j] ? "1" : "0");
            System.out.println();
        }
    }

    public static void main (String args[]) {
    	// at first, we print the original table we read in the entries
    	// then we print one solution as well as the total number of solutions
    	
        if (args[0].equals("emc")) {
        	//emc problem
    		boolean [][]M = null;
    		int[] secondaryColumns=new int[1]; // we use an array to implement passage by reference
    		M = Table.readExactCoverProblem(secondaryColumns);
    		printMatrix(M);
    		Table mytable = new Table(M, secondaryColumns[0]);
    		mytable.Solve();
    		mytable.printSolutions();
        	
        } else if (args[0].equals("pavage")) {
            Pavage pavage = new Pavage();
            pavage.readInput();
            boolean[][] reducedMatrix = pavage.getReducedMatrix();
            //printMatrix(reducedMatrix);

            Table instance = new Table(reducedMatrix, 0);
            instance.Solve();

            int[] solutionRows = instance.getSolutionsRowLabels();
            instance.printSolutions();
            pavage.showSolution(reducedMatrix, solutionRows);
        	
        } else if(args[0].equals("sudoku")){
        	// sudoku problem
        	// input form : in the first line is the length of the Sudoku table
        	// the following lines represent the lines of the Sudoku table
        	// using DLX algorithm allows us to solve 16x16 Sudoku puzzles
        	// while a simple backtracking can only solve 9x9 Sudoku puzzles
        	System.out.println("Sudoku");
    		Sudoku mySudoku = new Sudoku();
    		mySudoku.Solve();  	
    		mySudoku.printSudokuSolution();
        }
        else{
        	System.out.println("Wrong argument");
        }

    }
}
