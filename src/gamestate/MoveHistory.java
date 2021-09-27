package gamestate;

import chesspieces.Piece;

public class MoveHistory {

    private final int movedFromID;
    private final int movedToID;
    private final Piece pieceMoved;
    private final Piece pieceAttacked;
    private final boolean isFirstMove;

    public MoveHistory(int movedFromID, int movedToID, Piece pieceMoved,
                       Piece pieceAttacked, boolean isFirstMove) {
        this.movedFromID = movedFromID;
        this.movedToID = movedToID;
        this.pieceMoved = pieceMoved;
        this.pieceAttacked = pieceAttacked;
        this.isFirstMove = isFirstMove;
    }

    public int getMovedFromID() {
        return movedFromID;
    }

    public int getMovedToID() {
        return movedToID;
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