import java.util.ArrayList;

/**
 * Created by diego on 28/05/16.
 */
abstract class Element {
    protected int nrows;
    protected int ncolumns;
    protected ArrayList<String> element;

    public Element(int nrows, int ncolumns, ArrayList<String> element) {
        this.nrows = nrows;
        this.ncolumns = ncolumns;
        this.element = element;
    }

    public int getNrows() {
        return nrows;
    }

    public int getNcolumns() {
        return ncolumns;
    }
}
