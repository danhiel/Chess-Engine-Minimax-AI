package chesspieces;

import chessboard.Tile;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

/**
 * The class tracks basic information such as team color,
 * piece position, piece value, and first move for the pawn.
 * Also calculates all the possible legal moves for the pawn
 * at its current position.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Pawn extends Piece {

    private static final int[] PAWN_MOVE_SET = {8, 16, 7, 9};
    private static final int PIECE_VALUE = 1;
    private boolean isFirstMove;
    private Stack<MoveHistory> moveHistory;

    public Pawn(boolean isWhitePiece, boolean isBotSide, int piecePosition, 
                Stack<MoveHistory> moveHistory) {
        super(isWhitePiece, isBotSide, piecePosition);
        this.moveHistory = moveHistory;
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
        return "Pawn";
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
        Set<Integer> allMoves = new HashSet<Integer>();

        for (int move : PAWN_MOVE_SET) {
            int finalPosition = piecePosition + getRespectiveMove(move);

            if (isWithinLegalColumns(finalPosition, piecePosition)) {
                addNormalMoves(allMoves, chessBoard, finalPosition, move);
                addAttackMoves(allMoves, chessBoard, finalPosition, move);
            }
        }
        return allMoves;
    }

    private int getRespectiveMove(int move) {
        if (IS_BOT_SIDE) {
            return move * -1;
        }
        return move;
    }

    private void addNormalMoves(Set<Integer> allMoves, Tile[] chessBoard,
                                      int finalPosition, int move) {
        if (chessBoard[finalPosition].getAssignedPiece() == null) {
            if (move == 8) {
                allMoves.add(finalPosition);

            } else if ((move == 16) && isFirstMove && chessBoard[piecePosition
                    + getRespectiveMove(8)].getAssignedPiece() == null) {
                allMoves.add(finalPosition);
            }
        }
    }

    private void addAttackMoves(Set<Integer> allMoves, Tile[] chessBoard,
                                      int finalPosition, int move) {
        if ((move == 7 || move == 9)) {
            if (isEnemy(chessBoard[finalPosition])) {
                allMoves.add(finalPosition);

            } else if (isWithinEnpassantRows()
                    && isEnpassantLegal(chessBoard, move)) {
                allMoves.add(finalPosition);
            }
        }
    }

    private boolean isWithinEnpassantRows() {
        return IS_BOT_SIDE && (piecePosition >= 24 && piecePosition <= 31)
                || !IS_BOT_SIDE && (piecePosition >= 32 && piecePosition <= 39);
    }

    private boolean isEnpassantLegal(Tile[] chessBoard, int move) {
        if (!moveHistory.isEmpty()) {
            MoveHistory recentMove = moveHistory.peek();

            if (recentMove.isFirstMove() && recentMove.getPieceMoved()
                    .getPieceType().equals("Pawn")) {

                Piece recentPiece = recentMove.getPieceMoved();
                Piece enpassantPiece = null;
                if ((move == 7 && IS_BOT_SIDE) || (move == 9 && !IS_BOT_SIDE)) {
                    enpassantPiece = chessBoard[piecePosition + 1].getAssignedPiece();
                } else if ((move == 9 && IS_BOT_SIDE) || (move == 7 && !IS_BOT_SIDE)) {
                    enpassantPiece = chessBoard[piecePosition - 1].getAssignedPiece();
                }

                return recentPiece == enpassantPiece;
            }
        }
        return false;
    }
}
