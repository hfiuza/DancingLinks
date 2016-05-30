
public class Main {

    public static void printMatrix (boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                System.out.print(matrix[i][j] ? "1" : "0");
            System.out.println();
        }
    }

    public static void main (String args[]) {
//        if (args[0].equals("emc")) {
//            //emc problem
//        } else if (args[0].equals("pavage")) {
//
//        } else {
//
//        }

        //pavage problem - mudar entrada
        Pavage pavage = new Pavage();
        pavage.readInput(args[0]);
        boolean[][] reducedMatrix = pavage.getReducedMatrix();
        printMatrix(reducedMatrix);

        Table instance = new Table(reducedMatrix);
        instance.Solve();

        int[] solutionRows = instance.getSolutionsRowLabels();
        System.out.println(solutionRows.length);

        for (int i = 0; i < solutionRows.length; i++) {
            for (int j = 0; j < reducedMatrix[solutionRows[i]-1].length; j++)
                System.out.print(reducedMatrix[solutionRows[i]-1][j] ? "1" : "0");
            System.out.println();
        }

        pavage.showSolution(reducedMatrix, solutionRows);
    }
}
