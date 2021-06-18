package chessboard;

import chesspieces.Piece;

/**
 * Tracks and updates information of a single tile.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Tile {

    protected final int tileID;
    protected Piece assignedPiece;
    
    /**
     * Constructor for Tile.
     * 
     * @param tileID the tile ID of the chessboard.
     * @param assignedPiece the assigned chesspiece to the tile.
     */
    public Tile(int tileID, Piece assignedPiece) {
        this.tileID = tileID;
        this.assignedPiece = assignedPiece;
    }

    /**
     * Gets the ID of this tile.
     * @return integer tile ID
     */
    public int getTileID() {
        return tileID;
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
        this.assignedPiece = assignedPiece;
    }
}