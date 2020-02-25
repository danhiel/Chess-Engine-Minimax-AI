package chesspieces;

import java.util.ArrayList;
import java.util.List;

import chessboard.Tile;

/**
 * The class provides basic chess piece functionalities.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public abstract class Piece {

    private final boolean IS_WHITE_PIECE;
    protected int piecePosition;

    public Piece(boolean isWhite, int piecePosition) {
        this.IS_WHITE_PIECE = isWhite;
        this.piecePosition = piecePosition;
    }

    public boolean isPieceWhite() {
        return IS_WHITE_PIECE;
    }

    public int getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(int piecePosition) {
        this.piecePosition = piecePosition;
    }

    public abstract String getPieceType();

    public abstract int getPieceValue();

    public abstract List<Integer> getAllMoves(Tile[] chessBoard);

    protected List<Integer> getRepeatedMoves(Tile[] chessBoard, int[] moveSet) {
        List<Integer> allRepeatedMoves = new ArrayList<Integer>();

        for (int move : moveSet) {
            int finalPosition = this.piecePosition + move;

            while (isWithinLegalColumns(finalPosition, finalPosition - move)
                    && chessBoard[finalPosition].getAssignedPiece() == null) {
                allRepeatedMoves.add(finalPosition);
                finalPosition += move;
            }

            if (isWithinLegalColumns(finalPosition, finalPosition - move)
                    && isEnemy(chessBoard[finalPosition])) {
                allRepeatedMoves.add(finalPosition);
            }
        }
        return allRepeatedMoves;
    }

    protected boolean isEnemy(Tile tile) {
        Piece assignedPiece = tile.getAssignedPiece();
        if (assignedPiece != null) {
            return assignedPiece.IS_WHITE_PIECE != this.IS_WHITE_PIECE;
        }
        return false;
    }

    protected boolean isWithinLegalColumns(int finalPosition, int currentPosition) {
        if (finalPosition >= 0 && finalPosition <= 63) {
            int finalColumns = finalPosition % 8;
            int currentColumns = currentPosition % 8;

            int leftRowBoundary = currentColumns - 2;
            int rightRowBoundary = currentColumns + 2;

            // If your final position is +/- 2 columns of your current position, return true.
            return leftRowBoundary <= finalColumns && finalColumns <= rightRowBoundary;
        }
        return false;
    }
}