package userinterface;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chesspieces.Piece;

/**
 * Tracks and updates user interface of a single tile.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class TileUI {

    // Default colors of tiles
    private static final Color DARK_COLOR = new Color(204, 255, 255);
    private static final Color LIGHT_COLOR = new Color(0, 153, 153);
    private static final Color HIGHLIGHT_DARK = new Color(176, 181, 176);
    private static final Color HIGHLIGHT_LIGHT = new Color(120, 128, 120);

    private final int tileID;
    private Piece assignedPiece;

    private final JPanel tilePanel;
    private JLabel pieceImage;
    
    public TileUI(int tileID, Piece assignedPiece) {
        this.tileID = tileID;
        this.assignedPiece = assignedPiece;

        tilePanel = new JPanel();
        pieceImage = new JLabel();

        // Initializes the tile panel's background color and chess piece image.
        assignDefaultTileColor();
        assignChessPieceImage();
    }

    /**
     * Sets assigned piece to the given chess piece.
     * @param chessPiece given chess Piece
     */
    public void setAssignedPiece(Piece chessPiece) {
        this.assignedPiece = chessPiece;
    }
    
    /**
     * Gets the panel of this tile.
     * @return JPanel tile.
     */
    public JPanel getTileJPanel() {
        return tilePanel;
    }


    /**
     * Gets the image of this tile.
     * @return JLabel chess piece image.
     */
    public JLabel getPieceImage() {
        return pieceImage;
    }

    /**
     * Sets this tiles piece image.
     * @param pieceImage JLabel chess piece image.
     */
    public void setPieceImage(JLabel pieceImage) {
        if (pieceImage != null) {
            this.pieceImage = pieceImage;
            tilePanel.add(pieceImage);
            repaintTilePanel();
        }
    }

    /**
     * Removes all images on this tile and assign back
     * a new image based on the assigned chess piece.
     */
    public void resetTilePanel() {
        tilePanel.removeAll();
        repaintTilePanel();
        assignChessPieceImage();
    }

    /**
     * Repaints the tile panel.
     */
    public void repaintTilePanel() {
        tilePanel.repaint();
        tilePanel.revalidate();
    }

    /**
     * Assigns a highlighted background color to this tile.
     */
    public void assignHighlightTileColor() {
        assignTileColor(HIGHLIGHT_DARK, HIGHLIGHT_LIGHT);
    }

    /**
     * Assigns the default background color to this tile.
     */
    public void assignDefaultTileColor() {
        assignTileColor(DARK_COLOR, LIGHT_COLOR);
    }

    /**
     * Assign a tile color to this tile based on its ID.
     * @param darkColor Color dark.
     * @param lightColor Color light.
     */
    private void assignTileColor(Color darkColor, Color lightColor) {
        if ((tileID / 8) % 2 == 0) {
            if (tileID % 2 == 0) {
                tilePanel.setBackground(darkColor);
            } else {
                tilePanel.setBackground(lightColor);
            }
        } else {
            if (tileID % 2 == 0) {
                tilePanel.setBackground(lightColor);
            } else {
                tilePanel.setBackground(darkColor);
            }
        }
    }

    /**
     * Assigns a JLabel chess piece image to this tile.
     */
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