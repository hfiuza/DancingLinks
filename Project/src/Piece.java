import com.sun.prism.shader.DrawRoundRect_Color_AlphaTest_Loader;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Piece extends Element {
    private int id;

    public Piece(int id, int nrows, int ncolumns, ArrayList<String> element) {
        super(nrows, ncolumns, element);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private void Rotation () {
        ArrayList<String> aux = new ArrayList<>();
        for (int j = 0; j < ncolumns; j++) {
            String s = "";
            for (int i = nrows - 1; i >= 0; i--)
                s += element.get(i).charAt(j);
            aux.add(s);
        }
        int t = ncolumns;
        ncolumns = nrows;
        nrows = t;
        element = aux;
    }

    private boolean isYSymmetry () {
        for (int i = 0; i < nrows; i++) {
            StringBuilder sb = new StringBuilder(element.get(i));
            String s = sb.reverse().toString();
            if (!s.equals(element.get(i))) return false;
        }
        return true;
    }

    private void YSymmetry () {
        ArrayList<String> newPiece = new ArrayList<>();
        for (int i = 0; i < nrows; i++) {
            StringBuilder sb = new StringBuilder(element.get(i));
            element.set(i, sb.reverse().toString());
        }
    }


    private boolean isXSymmetry () {
        for (int j = 0; j < ncolumns; j++) {
            String s = "";
            for (int i = nrows - 1; i >= 0; i--) {
                s += element.get(i).charAt(j);
            }
            for (int i = 0; i < nrows; i++)
                if (s.charAt(i) != element.get(i).charAt(j))
                    return false;
        }
        return true;
    }

    private void XSymmetry () {
        for (int j = 0; j < ncolumns; j++) {
            String s = "";
            for (int i = nrows - 1; i >= 0; i--) {
                s += element.get(i).charAt(j);
            }

            for (int i = 0; i < nrows; i++) {
                String pre = element.get(i).substring(0, j);
                String pos = j == ncolumns - 1 ? "" : element.get(i).substring(j + 1);
                String aux = pre + s.charAt(i) + pos;
                element.set(i, aux);
            }
        }
    }

    private boolean isPossible (Board board, int r, int c) {
        if (c + ncolumns > board.getNcolumns()) return false;
        if (r + nrows > board.getNrows()) return false;

        for (int i = 0; i < nrows; i++)
            for (int j = 0; j < ncolumns; j++)
                if (element.get(i).charAt(j) == '*' &&
                        board.element.get(i + r).charAt(j + c) != '*')
                    return false;
        return true;
    }

    private void printPieceOnBoard(int r, int c, Board board) {
        for (int i = 0; i < board.element.size(); i++) {
            String s = board.element.get(i);
            for (int j = 0; j < s.length(); j++) {
                char cBoard = board.element.get(i).charAt(j);
                if (i < r || i >= r + nrows || j < c || j >= c + ncolumns) {
                    System.out.print(cBoard);
                } else {
                    char cPiece = element.get(i - r).charAt(j - c);
                    if (cPiece == cBoard) {
                        System.out.print(cPiece == '*' ? '0' : '.');
                    } else
                        System.out.print('*');
                }
            }
            System.out.println();
        }
    }

    private boolean[] fillRow (int r, int c, int ncols, int npieces, HashMap<String, Integer> indexToCol) {
        boolean[] row = new boolean[ncols + npieces];
        for (int i = 0; i < row.length; i++) row[i] = false;
        //debug
        row[ncols + id] = true;

        for (int i = 0; i < element.size(); i++) {
            String s = element.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '*') {
                    String key = Integer.toString(i + r);
                    key += Integer.toString(j + c);
                    int col = indexToCol.get(key);
                    row[col] = true;
                }
            }
        }

        return row;
    }

    private void addRows (Board board, ArrayList<boolean[]> ans,
            int ncols, int npieces, HashMap<String, Integer> indexToCol) {
        for (int i = 0; i < board.element.size(); i++) {
            String s = board.element.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (isPossible(board, i, j)) {
                    //debug
                    printPieceOnBoard(i, j, board);
                    System.out.println();

                    boolean[] row = fillRow(i, j, ncols, npieces, indexToCol);
                    ans.add(row);

                }
            }
        }
    }

    public ArrayList<boolean[]> matrixOfPossibilities
            (HashMap<String, Integer> indexToCol, Board board, int ncols, int npieces) {

        boolean symmetryY = isYSymmetry();
        boolean symmetryX = isXSymmetry();

        ArrayList<boolean[]> ans = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            addRows(board, ans, ncols, npieces, indexToCol);
            Rotation();
        }

        if (!symmetryX) {
            XSymmetry();
            addRows(board, ans, ncols, npieces, indexToCol);
        }

        if (!symmetryY) {
            YSymmetry();
            addRows(board, ans, ncols, npieces, indexToCol);
        }
        return ans;
    }
}
