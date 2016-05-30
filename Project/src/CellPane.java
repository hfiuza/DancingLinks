

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class CellPane extends JPanel {
    private final int DIM_X = 25;
    private final int DIM_Y = 25;

    private Color defaultBackground;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIM_X, DIM_Y);
    }
}

