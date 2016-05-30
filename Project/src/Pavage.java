import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Pavage {
    private Board board;
    private ArrayList<Piece> pieces;

    private ArrayList<String> readMatrix (BufferedReader br, int n) throws IOException {
        ArrayList<String> lines = new ArrayList<>(); //board
        for (int i = 0; i < n; i++) {
            lines.add(br.readLine());
        }
        return lines;
    }

    private int getNumberOfHoles () {
        int nholes = 0;
        for (String s : board.element) {
            for (int i = 0; i < s.length(); i++)
                if (s.charAt(i) == '.')
                    nholes++;
        }
        return nholes;
    }

    public void readInput(String filename) {
        BufferedReader br = null;
        try {
            //read board
            br = new BufferedReader(new FileReader(filename));
            int C = Integer.parseInt(br.readLine()); //number of columns
            int L = Integer.parseInt(br.readLine()); //number of rows
            board = new Board(L, C, readMatrix(br, L));

            //read pieces
            int P = Integer.parseInt(br.readLine()); //number of pieces
            pieces = new ArrayList<>();
            for (int i = 0; i < P; i++) {
                C = Integer.parseInt(br.readLine()); //number of columns
                L = Integer.parseInt(br.readLine()); //number of rows
                Piece piece = new Piece(i, L, C, readMatrix(br, L));
                pieces.add(piece);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int[][] getColtoIndex (int ncolumns) {
        // colToIndex[i] -> column i : element board[colToIndex[i], colToIndex[j]]
        int[][] colToIndex = new int[ncolumns][2];
        int k = 0;
        for (int i = 0; i < board.element.size(); i++) {
            String s = board.element.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) != '.') {
                    colToIndex[k][0] = i;
                    colToIndex[k][1] = j;
                    k++;
                }
            }
        }
        return colToIndex;
    }

    private HashMap<String, Integer> getIndexToCol (int ncolumns) {
        int[][] colToIndex = getColtoIndex(ncolumns);
        HashMap<String, Integer> indexToCol = new HashMap<>();
        for (int i = 0; i < ncolumns; i++) {
            String s = Integer.toString(colToIndex[i][0]);
            s += Integer.toString(colToIndex[i][1]);
            indexToCol.put(s, i);
        }
        return indexToCol;
    }

    public boolean[][] getReducedMatrix () {
        int ncolumns = board.getNrows() * board.getNcolumns() - getNumberOfHoles();

        ArrayList<boolean[]> matrix = new ArrayList<>();
        HashMap<String, Integer> indexToCol = getIndexToCol(ncolumns);

        for (Piece piece : pieces) {

            ArrayList<boolean[]> pieceMatrix;
            pieceMatrix = piece.matrixOfPossibilities(indexToCol, board, ncolumns, pieces.size());
            matrix.addAll(pieceMatrix);
        }

        int nrows = matrix.size();
        boolean[][] ans = new boolean[nrows][ncolumns + pieces.size()];
        for (int i = 0; i < nrows; i++)
            for (int j = 0; j < ncolumns + pieces.size(); j++)
                ans[i][j] = matrix.get(i)[j];
        return ans;
    }

    public void showSolution (boolean[][] matrix, int[] solutionRows) {

        int ncolumns = board.getNrows() * board.getNcolumns() - getNumberOfHoles();
        int[][] colToIndex = getColtoIndex(ncolumns);

        Frame frame = new Frame(matrix, solutionRows, colToIndex, board.getNrows(), board.getNcolumns());
        new Thread(frame).start();

    }
}
