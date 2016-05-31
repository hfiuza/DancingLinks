import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Piece extends Element {
    private int id;
    private ArrayList<Piece> symmetries;

    public Piece(int id, int nrows, int ncolumns, ArrayList<String> element) {
        super(nrows, ncolumns, element);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    //rotate this piece 90 degree
    private Piece getRotation () {
        ArrayList<String> aux = new ArrayList<>();
        for (int j = 0; j < ncolumns; j++) {
            String s = "";
            for (int i = nrows - 1; i >= 0; i--)
                s += element.get(i).charAt(j);
            aux.add(s);
        }
        Piece p = new Piece(this.id, ncolumns, nrows, aux);
        return p;
    }

    //get Y axe Symmetry
    private Piece getYSymmetry () {
        ArrayList<String> aux = new ArrayList<>();
        for (int i = 0; i < nrows; i++) {
            StringBuilder sb = new StringBuilder(element.get(i));
            aux.add(sb.reverse().toString());
        }
//        System.out.println("Get Y original");
//        for (int i = 0; i < element.size(); i++) {
//            System.out.println(element.get(i));
//        }
//        System.out.println("Get Y simetria");
//        for (int i = 0; i < aux.size(); i++) {
//            System.out.println(aux.get(i));
//        }
        return new Piece(this.id, nrows, ncolumns, aux);
    }

    //get X axe Symmetry
    private Piece getXSymmetry () {
        ArrayList<String> aux = new ArrayList<>();
        aux.addAll(this.element);
        Collections.reverse(aux);

        return new Piece(this.id, nrows, ncolumns, aux);
    }

    //check if the piece position on the board is possible or not
    private boolean isPossible (Board board, int r, int c, int indexPiece) {
        Piece p = symmetries.get(indexPiece);

        if (c + p.ncolumns > board.getNcolumns()) return false;
        if (r + p.nrows > board.getNrows()) return false;

        for (int i = 0; i < p.nrows; i++)
            for (int j = 0; j < p.ncolumns; j++)
                if (p.element.get(i).charAt(j) == '*' &&
                        board.element.get(i + r).charAt(j + c) != '*')
                    return false;
        return true;
    }

    //print piece in a specific position
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

    //fill a row (possible position) of the piece
    private boolean[] fillRow (int r, int c, int ncols, int npieces, HashMap<String, Integer> indexToCol, int indexPiece) {
        Piece p = symmetries.get(indexPiece);

        boolean[] row = new boolean[ncols + npieces];
        for (int i = 0; i < row.length; i++) row[i] = false;
        //debug
        row[ncols + id] = true;

        for (int i = 0; i < p.element.size(); i++) {
            String s = p.element.get(i);
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

    //add row to the listj
    private void addRows (Board board, ArrayList<boolean[]> ans,
            int ncols, int npieces, HashMap<String, Integer> indexToCol, int indexPiece) {

        Piece p = symmetries.get(indexPiece);
        for (int i = 0; i < board.element.size(); i++) {
            String s = board.element.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (isPossible(board, i, j, indexPiece)) {
                    //debug
//                    p.printPieceOnBoard(i, j, board);
//                    System.out.println();

                    boolean[] row = fillRow(i, j, ncols, npieces, indexToCol, indexPiece);
                    ans.add(row);

                }
            }
        }
    }

    //get the equivalent exact cover matrix
    public ArrayList<boolean[]> matrixOfPossibilities
            (HashMap<String, Integer> indexToCol, Board board, int ncols, int npieces) {
        //get symmetries

        symmetries = new ArrayList<>();

        Piece aux = new Piece(this.id, this.nrows, this.ncolumns, this.element);

        symmetries.add(aux);
        aux = getYSymmetry();
        symmetries.add(aux);
        aux = getXSymmetry();
        symmetries.add(aux);
        aux = aux.getYSymmetry();
        symmetries.add(aux);

        aux = getRotation();
        symmetries.add(aux);
        Piece rotation = aux.getYSymmetry();
        symmetries.add(rotation);
        rotation = aux.getXSymmetry();
        symmetries.add(rotation);
        rotation = rotation.getYSymmetry();
        symmetries.add(rotation);

        ArrayList<boolean[]> ans = new ArrayList<>();

        for (int i = 0; i < symmetries.size(); i++) {
            boolean isDifferent = true;
            Piece p = symmetries.get(i);
            for (int j = 0; j < i; j++) {
                if (p.isEqual(symmetries.get(j))) {
                    isDifferent = false;
                    break;
                }
            }
            if (isDifferent)
                addRows(board, ans, ncols, npieces, indexToCol, i);
        }

        return ans;
    }
}
