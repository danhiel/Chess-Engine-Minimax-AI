package chesspieces;

import chessboard.Tile;
import gamestate.MoveAlgorithm;

import java.util.Set;

/**
 * The class tracks basic information such as team color,
 * piece position, and piece value for the queen.
 * Also calculates all the possible legal moves for the queen
 * at its current position.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Queen extends Piece {

    private static final int[] QUEEN_MOVE_SET = {-9, -8, -7, -1, 1, 7, 8, 9};
    private static final int PIECE_VALUE = 7;

    public Queen(boolean isWhitePiece, boolean isBotSide, int piecePosition) {
        super(isWhitePiece, isBotSide, piecePosition);
    }

    @Override
    public String getPieceType() {
        return "Queen";
    }

    @Override
    public int getPieceValue() {
        return PIECE_VALUE;
    }

    @Override
    public Set<Integer> getAllLegalMoves(Tile[] chessBoard, MoveAlgorithm moveAlg) {
        return getAllMoves(chessBoard, moveAlg);
    }

    @Override
    public Set<Integer> getAllMoves(Tile[] chessBoard, MoveAlgorithm moveAlg) {
        return getRepeatedMoves(chessBoard, QUEEN_MOVE_SET);
    }
}
