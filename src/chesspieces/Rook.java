package chesspieces;

import chessboard.Tile;

import java.util.List;

/**
 * The class tracks basic information such as team color,
 * piece position, and piece value for the Rook.
 * Also calculates all the possible legal moves for the Rook
 * at its current position.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Rook extends Piece {

    private static final int[] ROOK_MOVE_SET = {-8, -1, 1, 8};
    private static final int PIECE_VALUE = 5;
    private boolean isFirstMove;

    public Rook(boolean isWhitePiece, int piecePosition) {
        super(isWhitePiece, piecePosition);
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
        return "Rook";
    }

    @Override
    public int getPieceValue() {
        return PIECE_VALUE;
    }

    @Override
    public List<Integer> getAllMoves(Tile[] chessBoard) {
        return getRepeatedMoves(chessBoard, ROOK_MOVE_SET);
    }
}
