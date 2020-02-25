package chessboard;

import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Creates and displays the User Interface for the Chess game.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */

public class UserInterface {

    private final Tile[] chessBoard;
    private final MoveAlgorithm moveAlgorithm;
    private final Stack<MoveHistory> moveHistory;

    private final JPanel chessBoardPanel;
    private final JLayeredPane boardJLayeredPane;
    private final JFrame frame;

    public UserInterface(Tile[] chessBoard, MoveAlgorithm moveAlgorithm,
                         Stack<MoveHistory> moveHistory) {
        this.chessBoard = chessBoard;
        this.moveAlgorithm = moveAlgorithm;
        this.moveHistory = moveHistory;

        chessBoardPanel = new JPanel();
        boardJLayeredPane = new JLayeredPane();
        frame = new JFrame("Chess");
    }

    public void createGameUI() {
        setUpChessBoardPanel();
        setUpMainPanel();
        setUpJFrame();
    }

    private void setUpMainPanel() {
        boardJLayeredPane.setPreferredSize(new Dimension(550, 550));
        boardJLayeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boardJLayeredPane.add(chessBoardPanel, JLayeredPane.DEFAULT_LAYER);
        chessBoardPanel.setBounds(0 ,0, 550, 550);
    }

    private void setUpChessBoardPanel() {
        chessBoardPanel.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 64; i++) {
            Tile boardTile = chessBoard[i];
            JPanel tileJPanel = boardTile.getTileJPanel();

            chessBoardPanel.add(tileJPanel);
            setUpTileMouseListeners(boardTile, tileJPanel);
        }
    }

    private void setUpTileMouseListeners(Tile boardTile, JPanel tileJPanel) {
        TileMouseListener tileMouseListener = new TileMouseListener(boardTile,
                chessBoard, boardJLayeredPane, moveAlgorithm, moveHistory);
        tileJPanel.addMouseMotionListener(tileMouseListener);
        tileJPanel.addMouseListener(tileMouseListener);
    }

    private void setUpJFrame() {
        frame.add(boardJLayeredPane);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Toolkit tk = Toolkit.getDefaultToolkit();
        frame.setLocation(tk.getScreenSize().width / 2 - frame.getWidth() / 2,
                tk.getScreenSize().height / 4);
    }
}