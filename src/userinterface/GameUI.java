package userinterface;

import chessboard.TileUI;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Creates and displays the Game Interface for the Chess game. In charge of setting up
 * the chessboard frame, panels, and mouse listeners for each chessboard tile.
 *
 * @author  Danhiel Vu
 * @version 1.0
 * @since   3/28/2021
 */
public class GameUI {

    private final TileUI[] chessBoard;
    private final MoveAlgorithm moveAlgorithm;
    private final Stack<MoveHistory> moveHistory;

    private final JPanel chessBoardPanel;
    private final JLayeredPane boardJLayeredPane;
    private final JFrame frame;

    /**
     * Constructor for GameUI.
     * 
     * @param chessBoard the main chessboard that tracks board-state.
     * @param moveAlgorithm controls piece movement in the game. 
     * @param moveHistory tracks move history.
     */
    public GameUI(TileUI[] chessBoard, MoveAlgorithm moveAlgorithm,
                         Stack<MoveHistory> moveHistory) {
        this.chessBoard = chessBoard;
        this.moveAlgorithm = moveAlgorithm;
        this.moveHistory = moveHistory;

        chessBoardPanel = new JPanel();
        boardJLayeredPane = new JLayeredPane();
        frame = new JFrame("Chess");
    }

    /**
     * Creates the Chess game UI.
     */
    public void createGameUI() {
        setUpChessBoardPanel();
        setUpMainPanel();
        setUpJFrame();
    }

    /**
     * Sets up the Chessboard with all the relevant pieces.
     */
    private void setUpChessBoardPanel() {
        chessBoardPanel.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < 64; i++) {
            TileUI boardTile = chessBoard[i];
            JPanel tileJPanel = boardTile.getTileJPanel();

            chessBoardPanel.add(tileJPanel);
            setUpTileMouseListeners(boardTile, tileJPanel);
        }
    }

    /**
     * Sets up a second layer on the chessboard to enable chesspiece mouse track.
     */
    private void setUpMainPanel() {
        boardJLayeredPane.setPreferredSize(new Dimension(512, 512));
        boardJLayeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boardJLayeredPane.add(chessBoardPanel, JLayeredPane.DEFAULT_LAYER);
        chessBoardPanel.setBounds(0 ,0, 512, 512);
    }

    /**
     * Sets up mouse listeners for each tile on the chessboard.
     * @param boardTile a singular tile taken from the 64 tiles on the main chessboard.
     * @param tileJPanel the JPanel of the boardTile.
     */
    private void setUpTileMouseListeners(TileUI boardTile, JPanel tileJPanel) {
        TileMouseListener tileMouseListener = new TileMouseListener(boardTile,
                chessBoard, boardJLayeredPane, moveAlgorithm, moveHistory);
        tileJPanel.addMouseMotionListener(tileMouseListener);
        tileJPanel.addMouseListener(tileMouseListener);
    }

    /**
     * Sets up the JFrame of the GameUI.
     */
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