package gamestate;

import chesspieces.Piece;

public class MoveHistory {

    private final int oldPieceMovedPos;
    private final int oldPieceAttackedPos;
    private final Piece pieceMoved;
    private final Piece pieceAttacked;
    private final boolean isFirstMove;

    public MoveHistory(int currentPosition, int finalPosition, Piece pieceMoved,
                       Piece pieceAttacked, boolean isFirstMove) {
        this.oldPieceMovedPos = currentPosition;
        this.oldPieceAttackedPos = finalPosition;
        this.pieceMoved = pieceMoved;
        this.pieceAttacked = pieceAttacked;
        this.isFirstMove = isFirstMove;
    }

    public int getOldPieceMovedPos() {
        return oldPieceMovedPos;
    }

    public int getOldPieceAttackedPos() {
        return oldPieceAttackedPos;
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