package chesspieces;

import chessboard.Tile;

import java.util.List;

/**
 * The class tracks basic information such as team color,
 * piece position, and piece value for the bishop.
 * Also calculates all the possible legal moves for the bishop
 * at its current position.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Bishop extends Piece {

    private static final int[] BISHOP_MOVE_SET = {-9, -7, 7, 9};
    private static final int PIECE_VALUE = 3;

    public Bishop(boolean isWhitePiece, boolean isBotSide, int piecePosition) {
        super(isWhitePiece, isBotSide, piecePosition);
    }

    @Override
    public String getPieceType() {
        return "Bishop";
    }

    @Override
    public int getPieceValue() {
        return PIECE_VALUE;
    }

    @Override
    public List<Integer> getAllMoves(Tile[] chessBoard) {
        return getRepeatedMoves(chessBoard, BISHOP_MOVE_SET);
    }
}
