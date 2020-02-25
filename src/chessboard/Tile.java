package chessboard;

import chesspieces.Piece;

import javax.swing.*;
import java.awt.*;

/**
 * Tracks and updates information for each of the
 * 64 Chess Tiles. In charge of each tile and chess
 * piece appearance and tracking each chess piece.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Tile {

    private static final Color DARK_COLOR = new Color(204, 255, 255);
    private static final Color LIGHT_COLOR = new Color(0, 153, 153);
    private static final Color HIGHLIGHT_DARK = new Color(176, 181, 176);
    private static final Color HIGHLIGHT_LIGHT = new Color(120, 128, 120);

    private final int tileID;
    private Piece assignedPiece;

    private final JPanel tilePanel;
    private JLabel pieceImage;

    public Tile(int tileID, Piece assignedPiece) {
        this.tileID = tileID;
        this.assignedPiece = assignedPiece;

        tilePanel = new JPanel();
        pieceImage = new JLabel();

        assignDefaultTileColor();
        assignChessPieceImage();
    }

    public int getTileID() {
        return tileID;
    }

    public Piece getAssignedPiece() {
        return assignedPiece;
    }

    public JPanel getTileJPanel() {
        return tilePanel;
    }

    public JLabel getPieceImage() {
        return pieceImage;
    }

    public void setAssignedPiece(Piece assignedPiece) {
        this.assignedPiece = assignedPiece;
    }

    public void setPieceImage(JLabel pieceImage) {
        if (pieceImage != null) {
            this.pieceImage = pieceImage;
            tilePanel.add(pieceImage);
            repaintTilePanel();
        }
    }

    public void resetTilePanel() {
        tilePanel.removeAll();
        repaintTilePanel();
        assignChessPieceImage();
    }

    public void repaintTilePanel() {
        tilePanel.repaint();
        tilePanel.revalidate();
    }

    public void highlightTileColor() {
        assignTileColor(HIGHLIGHT_DARK, HIGHLIGHT_LIGHT);
    }

    public void assignDefaultTileColor() {
        assignTileColor(DARK_COLOR, LIGHT_COLOR);
    }

    private void assignTileColor(Color dark, Color light) {
        if ((tileID / 8) % 2 == 0) {
            if (tileID % 2 == 0) {
                tilePanel.setBackground(dark);
            } else {
                tilePanel.setBackground(light);
            }
        } else {
            if (tileID % 2 == 0) {
                tilePanel.setBackground(light);
            } else {
                tilePanel.setBackground(dark);
            }
        }
    }

    private void assignChessPieceImage() {
        if (assignedPiece != null) {
            if (assignedPiece.isPieceWhite()) {
                pieceImage = new JLabel(new ImageIcon("img/White"
                        + assignedPiece.getPieceType() + ".png"));
            } else {
                pieceImage = new JLabel(new ImageIcon("img/Black"
                        + assignedPiece.getPieceType() + ".png"));
            }
            tilePanel.add(pieceImage);
        }
    }
}