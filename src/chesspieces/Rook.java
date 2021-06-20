package chesspieces;

import chessboard.Tile;
import gamestate.MoveAlgorithm;

import java.util.Set;

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

    public Rook(boolean isWhitePiece, boolean isBotSide, int piecePosition) {
        super(isWhitePiece, isBotSide, piecePosition);
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
    public Set<Integer> getAllLegalMoves(Tile[] chessBoard, MoveAlgorithm moveAlg) {
        return getAllMoves(chessBoard, moveAlg);
    }

    @Override
    public Set<Integer> getAllMoves(Tile[] chessBoard, MoveAlgorithm moveAlg) {
        return getRepeatedMoves(chessBoard, ROOK_MOVE_SET);
    }
}
