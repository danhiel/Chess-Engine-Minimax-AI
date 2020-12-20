package userinterface;

import chessboard.Tile;
import chesspieces.Piece;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import java.util.List;

public class TileMouseListener implements MouseListener, MouseMotionListener {

    private final Tile boardTile;
    private final Tile[] chessBoard;
    private final JLayeredPane boardJLayeredPane;
    private final Stack<MoveHistory> moveHistory;
    private final MoveAlgorithm moveAlgorithm;

    private static JLabel savedPieceImage = null;
    private static Piece savedPiece = null;
    private static List<Integer> savedMoves = null;

    public TileMouseListener(Tile boardTile, Tile[] chessBoard, JLayeredPane boardJLayeredPane,
                             MoveAlgorithm moveAlgorithm, Stack<MoveHistory> moveHistory) {
        this.boardTile = boardTile;
        this.chessBoard = chessBoard;
        this.moveAlgorithm = moveAlgorithm;
        this.moveHistory = moveHistory;
        this.boardJLayeredPane = boardJLayeredPane;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Piece pieceSelected = boardTile.getAssignedPiece();
        if (isRespectivePlayersTurn(pieceSelected)) {

            if (this.savedPiece == pieceSelected) {
                unhighlightAllMoves();
                this.savedPiece = null;

            } else if (savedPiece == null) {
                savedPiece = pieceSelected;
                savedMoves = pieceSelected.getAllMoves(chessBoard);
                highlightAllMoves();
                transferPieceImageToDragLayer();
                updatePieceImageLocation();

            } else if (isPieceSelectedAlly(pieceSelected)) {
                unhighlightAllMoves();
                this.savedPiece = pieceSelected;
                this.savedMoves = pieceSelected.getAllMoves(chessBoard);
                highlightAllMoves();
                transferPieceImageToDragLayer();
                updatePieceImageLocation();

            } else if (savedPiece != null) {
                unhighlightAllMoves();
                if (savedMoves.contains(boardTile.getTileID())) {
                    moveAlgorithm.movePieceToSquare(savedPiece.getPiecePosition(),
                            boardTile.getTileID());
                }
                savedPiece = null;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (savedPieceImage != null) {
            Component comp = boardTile.getTileJPanel().getParent();
            Component otherComp = comp.getComponentAt(comp.getParent().getMousePosition());
            int height = comp.getSize().height / 8;
            int width = comp.getSize().width / 8;
            int total = (otherComp.getLocation().y / height) * 8;
            total += otherComp.getLocation().x / width;
            if (boardTile.getTileJPanel() == otherComp){
                boardTile.setPieceImage(savedPieceImage);
                boardJLayeredPane.repaint();
                savedPieceImage = null;
            } else {
                unhighlightAllMoves();
                if (savedMoves.contains(total)) {
                    boardTile.setPieceImage(savedPieceImage);
                    boardJLayeredPane.repaint();
                    savedPieceImage = null;
                    moveAlgorithm.movePieceToSquare(savedPiece.getPiecePosition(),
                            total);
                } else {
                    boardTile.setPieceImage(savedPieceImage);
                    boardJLayeredPane.repaint();
                    savedPieceImage = null;
                }
                savedPiece = null;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (savedPiece != null) {
            updatePieceImageLocation();
        }
    }

    private boolean isRespectivePlayersTurn(Piece pieceSelected) {
        int totalTurns = moveHistory.size();
        if (savedPiece != null) {
            return true;
        } else if (pieceSelected != null) {
            return (totalTurns % 2 == 0) == pieceSelected.isPieceWhite();
        }
        return false;
    }

    private boolean isPieceSelectedAlly(Piece pieceSelected) {
        return pieceSelected != null
                && this.savedPiece.isPieceWhite() == pieceSelected.isPieceWhite();
    }

    private void highlightAllMoves() {
        chessBoard[savedPiece.getPiecePosition()].highlightTileColor();
        for (int moveID : savedMoves) {
            this.chessBoard[moveID].highlightTileColor();
        }
    }

    private void unhighlightAllMoves() {
        chessBoard[savedPiece.getPiecePosition()].assignDefaultTileColor();
        for (int moveID : savedMoves) {
            this.chessBoard[moveID].assignDefaultTileColor();
        }
    }

    private void transferPieceImageToDragLayer() {
        savedPieceImage = boardTile.getPieceImage();
        boardJLayeredPane.add(savedPieceImage, JLayeredPane.DRAG_LAYER);
        boardTile.repaintTilePanel();
    }

    private void updatePieceImageLocation() {
        Point mousePosition = boardJLayeredPane.getMousePosition();
        if (mousePosition != null) {
            savedPieceImage.setLocation(mousePosition.x - (savedPieceImage.getHeight() / 2),
                    mousePosition.y - (savedPieceImage.getWidth() / 2));
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            moveAlgorithm.undoMove();
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}
}
