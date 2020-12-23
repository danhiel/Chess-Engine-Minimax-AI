package chessboard;

import chesspieces.Piece;
import userinterface.TileUI;

/**
 * Tracks and updates information of a single tile.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Tile {

    private final int tileID;
    private final TileUI tileUI;
    private Piece assignedPiece;
    
    public Tile(int tileID, Piece assignedPiece) {
        this.tileID = tileID;
        this.assignedPiece = assignedPiece;
        tileUI = new TileUI(tileID, assignedPiece);
    }

    /**
     * Gets the ID of this tile.
     * @return integer tile ID
     */
    public int getTileID() {
        return tileID;
    }

    /**
     * Returns the tile user interface class of this tile.
     * @return tileUI
     */
    public TileUI getTileUI() {
        return tileUI;
    }

    /**
     * Gets the assigned chess piece of this tile.
     * @return object chess piece.
     */
    public Piece getAssignedPiece() {
        return assignedPiece;
    }

    /**
     * Sets this tiles assigned chess piece.
     * @param assignedPiece Piece chess piece.
     */
    public void setAssignedPiece(Piece assignedPiece) {
        tileUI.setAssignedPiece(assignedPiece);
        this.assignedPiece = assignedPiece;
    }
}