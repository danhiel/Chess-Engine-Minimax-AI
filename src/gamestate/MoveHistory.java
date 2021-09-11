package gamestate;

import chesspieces.Piece;

public class MoveHistory {

    private final int pieceMovedPos;
    private final int pieceAttackedPos;
    private final Piece pieceMoved;
    private final Piece pieceAttacked;
    private final boolean isFirstMove;

    public MoveHistory(int pieceMovedPos, int pieceAttackedPos, Piece pieceMoved,
                       Piece pieceAttacked, boolean isFirstMove) {
        this.pieceMovedPos = pieceMovedPos;
        this.pieceAttackedPos = pieceAttackedPos;
        this.pieceMoved = pieceMoved;
        this.pieceAttacked = pieceAttacked;
        this.isFirstMove = isFirstMove;
    }

    public int getPieceMovedPos() {
        return pieceMovedPos;
    }

    public int getPieceAttackedPos() {
        return pieceAttackedPos;
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