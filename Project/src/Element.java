import java.util.ArrayList;
import java.util.Objects;

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

    public boolean isEqual(Element e) {
        if (nrows != e.nrows) return false;
        if (ncolumns != e.ncolumns) return false;
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncolumns; j++)
                if (!element.get(i).equals(e.element.get(i)))
                    return false;
        }
        return true;
    }

    public int getNrows() {
        return nrows;
    }

    public int getNcolumns() {
        return ncolumns;
    }
}
