

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class CellPane extends JPanel {
    private final int DIM_X = 50;
    private final int DIM_Y = 50;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIM_X, DIM_Y);
    }
}

