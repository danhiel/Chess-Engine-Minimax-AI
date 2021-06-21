package chesspieces;

import chessboard.TileUI;

import java.util.HashSet;
import java.util.Set;

/**
 * The class tracks basic information such as team color,
 * piece position, and piece value for the knight.
 * Also calculates all the possible legal moves for the knight
 * at its current position.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Knight extends Piece {

    private static final int[] KNIGHT_MOVE_SET = {-17, -15, -10, -6, 6, 10, 15, 17};
    private static final int PIECE_VALUE = 3;

    public Knight(boolean isWhitePiece, boolean isBotSide, int piecePosition) {
        super(isWhitePiece, isBotSide, piecePosition);
    }

    @Override
    public String getPieceType() {
        return "Knight";
    }

    @Override
    public int getPieceValue() {
        return PIECE_VALUE;
    }

    @Override
    public Set<Integer> getAllMoves(TileUI[] chessBoard) {
        Set<Integer> allMoves = new HashSet<Integer>();

        for (int move : KNIGHT_MOVE_SET) {
            int finalPosition = piecePosition + move;

            if (isWithinLegalColumns(finalPosition, piecePosition)) {
                if (chessBoard[finalPosition].getAssignedPiece() == null
                        || isEnemy(chessBoard[finalPosition])){
                    allMoves.add(finalPosition);
                }
            }
        }
        return allMoves;
    }
}
