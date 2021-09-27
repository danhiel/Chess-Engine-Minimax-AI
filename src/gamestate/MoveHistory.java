package gamestate;

import chesspieces.Piece;

public class MoveHistory {

    private final int oldPieceMovedID;
    private final int oldPieceAttackedID;
    private final Piece pieceMoved;
    private final Piece pieceAttacked;
    private final boolean isFirstMove;

    public MoveHistory(int oldPieceMovedID, int oldPieceAttackedID, Piece pieceMoved,
                       Piece pieceAttacked, boolean isFirstMove) {
        this.oldPieceMovedID = oldPieceMovedID;
        this.oldPieceAttackedID = oldPieceAttackedID;
        this.pieceMoved = pieceMoved;
        this.pieceAttacked = pieceAttacked;
        this.isFirstMove = isFirstMove;
    }

    public int getOldPieceMovedID() {
        return oldPieceMovedID;
    }

    public int getOldPieceAttackedID() {
        return oldPieceAttackedID;
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public Piece getPieceAttacked() {
        return pieceAttacked;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }
}