package chesspieces;

import chessboard.TileUI;

import java.util.HashSet;
import java.util.Set;

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
    private static final int PIECE_VALUE = 10;
    private boolean isFirstMove;

    public King(boolean isWhitePiece, boolean isBotSide, int piecePosition) {
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
        return "King";
    }

    @Override
    public int getPieceValue() {
        return PIECE_VALUE;
    }

    @Override
    public Set<Integer> getAllMoves(TileUI[] chessBoard) {
        Set<Integer> allMoves = new HashSet<Integer>();

        for (int move : KING_MOVE_SET) {
            int finalPosition = piecePosition + move;

            addNormalMoves(allMoves, chessBoard, finalPosition);
            addCastlingMoves(allMoves, chessBoard);
        }
        return allMoves;
    }

    private void addNormalMoves(Set<Integer> allMoves,
                                TileUI[] chessBoard,
                                int finalPosition) {
        if (isWithinLegalColumns(finalPosition, piecePosition)
                && (chessBoard[finalPosition].getAssignedPiece() == null
                    || isEnemy(chessBoard[finalPosition]))) {
            allMoves.add(finalPosition);
        }
    }

    private void addCastlingMoves(Set<Integer> allMoves, TileUI[] chessBoard) {
        if (isFirstMove) {
            Rook leftRook = getRook(chessBoard, true);
            Rook rightRook = getRook(chessBoard, false);

            if (leftRook != null 
                    && leftRook.isFirstMove() 
                    && checkIfLeftCastlingLegal(chessBoard, leftRook)) {
                allMoves.add(piecePosition - 2);
            }
            if (rightRook != null 
                    && rightRook.isFirstMove()
                    && checkIfRightCastlingLegal(chessBoard, rightRook)) {
                allMoves.add(piecePosition + 2);
            }
        }
    }

    private Rook getRook(TileUI[] chessBoard, boolean isLeftSide) {
        int rookPosition = getRookPosition(isLeftSide);
        Piece chessPiece = chessBoard[rookPosition].getAssignedPiece();
        if (chessPiece != null 
            && chessPiece.getPieceType() == "Rook") {
            return (Rook) chessPiece;
        }
        return null;
    }

    private int getRookPosition(boolean isLeftSide) {
        if (isLeftSide) {
            return this.piecePosition - 4;
        } else {
            return this.piecePosition + 3;
        }
    }

    private boolean checkIfLeftCastlingLegal(TileUI[] chessBoard, Rook leftRook) {
        for (int i = leftRook.piecePosition + 1; i < this.piecePosition; i++) {
            if (chessBoard[i].getAssignedPiece() != null) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfRightCastlingLegal(TileUI[] chessBoard, Rook rightRook) {
        for (int i = rightRook.piecePosition - 1; i > this.piecePosition; i--) {
            if (chessBoard[i].getAssignedPiece() != null) {
                return false;
            }
        }
        return true;
    }
}
