package chesspieces;

import chessboard.Tile;
import java.util.ArrayList;
import java.util.List;

/**
 * The class tracks basic information such as team color,
 * piece position, and piece value for the king
 * Also calculates all the possible legal moves for the king
 * at its current position.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class King extends Piece {

    private static final int[] KING_MOVE_SET = {-9, -8, -7, -1, 1, 7, 8, 9};
    private static final int[] WHITE_CASTLE_MOVE_SET = {58, 59, 62, 61};
    private static final int[] BLACK_CASTLE_MOVE_SET = {2, 3, 5, 6};
    private static final int PIECE_VALUE = 10;
    private Rook leftRook;
    private Rook rightRook;
    private boolean isFirstMove;

    public King(boolean isWhitePiece, int piecePosition,
                Piece firstRook, Piece secondRook) {
        super(isWhitePiece, piecePosition);
        this.leftRook = (Rook) firstRook;
        this.rightRook = (Rook) secondRook;
        isFirstMove = true;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setIsFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    @Override
    public String getPieceType() {
        return "King";
    }

    @Override
    public int getPieceValue() {
        return PIECE_VALUE;
    }

    @Override
    public List<Integer> getAllMoves(Tile[] chessBoard) {
        List<Integer> allMoves = new ArrayList<Integer>();

        for (int move : KING_MOVE_SET) {
            int finalPosition = piecePosition + move;

            addNormalMoves(allMoves, chessBoard, finalPosition);
            addCastlingMoves(allMoves, chessBoard);
        }
        return allMoves;
    }

    private void addNormalMoves(List<Integer> allMoves, Tile[] chessBoard, int finalPosition) {
        if (isWithinLegalColumns(finalPosition, piecePosition)
                && (chessBoard[finalPosition].getAssignedPiece() == null
                || isEnemy(chessBoard[finalPosition]))) {
            allMoves.add(finalPosition);
        }
    }

    private void addCastlingMoves(List<Integer> allMoves, Tile[] chessBoard) {
        if (isFirstMove) {
            if (leftRook.isFirstMove() && checkIfLeftCastlingLegal(chessBoard)) {
                allMoves.add(piecePosition - 2);
            }
            if (rightRook.isFirstMove() && checkIfRightCastlingLegal(chessBoard)) {
                allMoves.add(piecePosition + 2);
            }
        }
    }

    private boolean checkIfLeftCastlingLegal(Tile[] chessBoard) {
        for (int i = leftRook.piecePosition + 1; i < this.piecePosition; i++) {
            if (chessBoard[i].getAssignedPiece() != null) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfRightCastlingLegal(Tile[] chessBoard) {
        for (int i = rightRook.piecePosition - 1; i > this.piecePosition; i--) {
            if (chessBoard[i].getAssignedPiece() != null) {
                return false;
            }
        }
        return true;
    }
}
