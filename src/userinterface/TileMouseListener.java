package userinterface;

import chessboard.TileUI;
import chesspieces.Piece;
import gamestate.GameState;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import java.util.HashSet;
import java.util.Set;

/**
 * Set up for TileMouseListener for the chessboard tiles. Enables the user to drag
 * and drop pieces to move, use clicking actions to move a piece, or right click
 * to undo.
 *
 * @author  Danhiel Vu
 * @version 1.0
 * @since   3/28/2021
 */
public class TileMouseListener implements MouseListener, MouseMotionListener {

    private final GameState gameState;
    private final TileUI chessTile;
    private final TileUI[] chessBoard;
    private final JLayeredPane boardJLayeredPane;
    private final Stack<MoveHistory> moveHistory;
    private final MoveAlgorithm moveAlg;

    private static JLabel savedPieceImage = null;
    private static Piece savedPiece = null;
    private static Set<Integer> savedMoves = null;

    /**
     * Constructor for the TileMouseListener.
     * 
     * @param chessTile the given singular tile taken from the chessboard.
     * @param chessBoard the main chessboard that tracks board-state.
     * @param boardJLayeredPane the layered pane that will help track mouse position.
     * @param moveAlg controls piece movement in the game.
     * @param moveHistory tracks move history.
     */
    public TileMouseListener(TileUI chessTile,
                             GameState gameState,
                             MoveAlgorithm moveAlg,
                             Stack<MoveHistory> moveHistory,
                             JLayeredPane boardJLayeredPane) {
        this.chessTile = chessTile;
        this.gameState = gameState;
        this.chessBoard = gameState.getChessBoard();
        this.moveAlg = moveAlg;
        this.moveHistory = moveHistory;
        this.boardJLayeredPane = boardJLayeredPane;
    }

    /**
     * When the mouse is pressed, highlight the chesspiece the mouse is placed on.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Piece selectedPiece = chessTile.getAssignedPiece();
            if (isRespectivePlayersTurn(selectedPiece)) {

                // Unselects the selected piece.
                if (savedPiece == selectedPiece) {
                    unhighlightAllMoves();
                    savedPiece = null;

                // Selects a piece.
                } else if (selectedPiece != null && savedPiece == null) {
                    savedPiece = selectedPiece;
                    savedMoves = selectedPiece.getAllLegalMoves(gameState, chessBoard, moveAlg);
                    highlightAllMoves();
                    transferPieceImageToDragLayer();
                    updatePieceImageLocation();

                // Unselects piece if move is invalid, otherwise move the piece to selected area.
                } else if (savedPiece != null) {
                    unhighlightAllMoves();
                    if (isPieceSelectedAlly(selectedPiece)) {
                        savedPiece = selectedPiece;
                        savedMoves = selectedPiece.getAllLegalMoves(gameState, chessBoard, moveAlg);
                        highlightAllMoves();
                        transferPieceImageToDragLayer();
                        updatePieceImageLocation();
                    } else {
                        if (savedMoves.contains(chessTile.getTileID())) {
                            moveAlg.movePieceToSquare(chessBoard, savedPiece.getPiecePosition(),
                                    chessTile.getTileID());
                            if (gameState.calcIfKingIsCheck(!savedPiece.getIsPieceWhite())) {
                                if (getAllLegalEnemyMoves(savedPiece.getIsPieceWhite()).isEmpty()) {
                                    System.out.println("Checkmate");
                                }
                            }
                        }
                        savedPiece = null;
                    }
                }
            }
        }
    }

    /**
     * When the mouse is released, if the mouse did not move from the original tile
     * it was pressed on, do nothing. Otherwise, move the chesspiece to the tile the
     * mouse was released on if it is a legal move.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (savedPieceImage != null) {

            // Get the chessBoardJPanel height and width size
            Component comp = chessTile.getTileJPanel().getParent();
            int height = comp.getSize().height / 8;
            int width = comp.getSize().width / 8;

            // Get chessBoardJLayeredPane and use this to get the mouse position.
            Component parentComp = comp.getComponentAt(comp.getParent().getMousePosition());
            int total = (parentComp.getLocation().y / height) * 8;
            total += parentComp.getLocation().x / width;


            if (chessTile.getTileJPanel() == parentComp){
                chessTile.setPieceImage(savedPieceImage);
                boardJLayeredPane.repaint();
                savedPieceImage = null;
            } else {
                unhighlightAllMoves();
                if (savedMoves.contains(total)) {
                    chessTile.setPieceImage(savedPieceImage);
                    boardJLayeredPane.repaint();
                    savedPieceImage = null;
                    moveAlg.movePieceToSquare(chessBoard,
                            savedPiece.getPiecePosition(), total);
                    if (gameState.calcIfKingIsCheck(!savedPiece.getIsPieceWhite())) {
                            System.out.println("King is check");
                            // Create a UI to tell the user the king is checked.
                    }
                } else {
                    chessTile.setPieceImage(savedPieceImage);
                    boardJLayeredPane.repaint();
                    savedPieceImage = null;
                }
                savedPiece = null;
            }
        }
    }

    /**
     * Update the piece image as you click and drag a piece.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (savedPiece != null) {
            updatePieceImageLocation();
        }
    }

    /**
     * Undo the move if the right mouse button was clicked.
     */
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (savedPiece != null) {
                unhighlightAllMoves();
                savedPiece = null;
            }
            moveAlg.undoMove(chessBoard);
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    
    public Set<Integer> getAllLegalEnemyMoves(boolean isWhiteSide) {
        Set<Piece> alivePieces = isWhiteSide ? gameState.getAliveBlackPieces() : gameState.getAliveWhitePieces();
        Set<Integer> results = new HashSet<Integer>();
        for (Piece piece : alivePieces) {
            results.addAll(piece.getAllLegalMoves(gameState, chessBoard, moveAlg));
        }
        System.out.println(results.toString());
        return results;
    }

    /**
     * Returns true if the selected piece is the players turn.
     * Otherwise, return false.
     * 
     * @param selectedPiece the selected piece.
     * @return true if its the players turn, false if not.
     */
    private boolean isRespectivePlayersTurn(Piece selectedPiece) {
        int totalTurns = moveHistory.size();
        if (savedPiece != null) {
            return true;
        } else if (selectedPiece != null) {
            return (totalTurns % 2 == 0) == selectedPiece.getIsPieceWhite();
        }
        return false;
    }

    /**
     * If the piece selected is an ally, then return true. Otherwise return false.
     * 
     * @param selectedPiece the selected piece.
     * @return true if selected piece is an ally, false otherwise.
     */
    private boolean isPieceSelectedAlly(Piece selectedPiece) {
        return selectedPiece != null
                && savedPiece.getIsPieceWhite() == selectedPiece.getIsPieceWhite();
    }

    /**
     * Highlights all the valid moves of the selected piece.
     */
    private void highlightAllMoves() {
        chessBoard[savedPiece.getPiecePosition()].assignHighlightTileColor();
        for (int moveID : savedMoves) {
            this.chessBoard[moveID].assignHighlightTileColor();
        }
    }

    /**
     * Unhighlights all valid moves.
     */
    private void unhighlightAllMoves() {
        chessBoard[savedPiece.getPiecePosition()].assignDefaultTileColor();
        for (int moveID : savedMoves) {
            this.chessBoard[moveID].assignDefaultTileColor();
        }
    }

    /**
     * Transfers the piece image to the drag JLayer.
     */
    private void transferPieceImageToDragLayer() {
        savedPieceImage = chessTile.getPieceImage();
        boardJLayeredPane.add(savedPieceImage, JLayeredPane.DRAG_LAYER);
        chessTile.repaintTilePanel();
    }

    /**
     * Updates the piece image location.
     */
    private void updatePieceImageLocation() {
        Point mousePosition = boardJLayeredPane.getMousePosition();
        if (mousePosition != null) {
            savedPieceImage.setLocation(mousePosition.x - (savedPieceImage.getHeight() / 2),
                    mousePosition.y - (savedPieceImage.getWidth() / 2));
        }
    }
}
