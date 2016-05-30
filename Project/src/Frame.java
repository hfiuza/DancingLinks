


import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Frame implements Runnable {
    private Pane pane;
    private boolean[][] matrix;
    private int[] solutionRows;
    private int[][] colToIndex;
    private int nrows;
    private int ncols;


    public Frame(boolean[][] matrix, int[] solutionRows, int[][] colToIndex, int nrows, int ncols) {
        this.matrix = matrix;
        this.solutionRows = solutionRows;
        this.colToIndex = colToIndex;
        this.nrows = nrows;
        this.ncols = ncols;
    }

    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }

        JFrame frame = new JFrame("Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        pane = new Pane(nrows, ncols);

        for (int i = 0; i < solutionRows.length; i++) {
            Color color = new Color((int)(Math.random() * 0x1000000));
            for (int j = 0; j < colToIndex.length; j++) {
                if (matrix[solutionRows[i] - 1][j]) {
                    int x = colToIndex[j][0];
                    int y = colToIndex[j][1];
                    System.out.print(x);
                    System.out.println(y);

                    pane.setCellColor(x, y, color);
                }
            }
        }

        frame.add(pane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

