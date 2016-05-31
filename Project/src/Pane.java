
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class Pane extends JPanel {
    public int NUM_ROW;
    public int NUM_COLUMN;

    ArrayList<CellPane> listCell;


    public Pane(int NUM_ROW, int NUM_COLUMN) {
        this.NUM_ROW = NUM_ROW;
        this.NUM_COLUMN = NUM_COLUMN;

        listCell = new ArrayList<>();

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        for (int row = 0; row < NUM_ROW; row++) {
            for (int col = 0; col < NUM_COLUMN; col++) {
                gbc.gridx = col;
                gbc.gridy = row;

                CellPane cellPane = new CellPane();
                Border border = null;
                if (row < NUM_ROW - 1) {
                    if (col < NUM_COLUMN - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < NUM_COLUMN - 1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }
                cellPane.setBorder(border);
                add(cellPane, gbc);
                listCell.add(cellPane);
            }
        }
    }

    public void setCellColor (int x, int y, Color color) {
        int linearIndex = x * NUM_COLUMN + y;
        CellPane cell = listCell.get(linearIndex);
        cell.setBackground(color);
    }
}
