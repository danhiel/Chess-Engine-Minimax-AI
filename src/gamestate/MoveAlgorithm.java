package gamestate;

import chessboard.Tile;
import chesspieces.*;

import java.util.List;
import java.util.Stack;

public class MoveAlgorithm {

    private final Tile[] chessBoard;
    private final Stack<MoveHistory> moveHistory;
    private boolean isFirstMove;
    private Piece pieceAttacked;
    
    public MoveAlgorithm(Tile[] chessBoard, Stack<MoveHistory> moveHistory) {
        this.chessBoard = chessBoard;
        this.moveHistory = moveHistory;
        isFirstMove = true;
        pieceAttacked = null;
    }

    public void undoMove() {
        simulateUndoMove();
    }

    public void simulateUndoMove() {
        MoveHistory recentMove = moveHistory.pop();
        Piece pieceMoved = recentMove.getPieceMoved();
        Piece pieceAttacked = recentMove.getPieceAttacked();
        boolean isFirstMove = recentMove.isFirstMove();
        int fromPositionID = recentMove.getOldPieceMovedPos();
        int toPositionID = recentMove.getOldPieceAttackedPos();

        if (pieceAttacked != null) {
            chessBoard[pieceAttacked.getPiecePosition()].setAssignedPiece(null);
            pieceAttacked.setPiecePosition(toPositionID);
            chessBoard[toPositionID].setAssignedPiece(pieceAttacked);
        }
        chessBoard[pieceMoved.getPiecePosition()].setAssignedPiece(null);
        pieceMoved.setPiecePosition(fromPositionID);
        chessBoard[fromPositionID].setAssignedPiece(pieceMoved);
    }

    public void movePieceToSquare(int currentPosition, int finalPosition) {
        simulateMovePieceToSquare(currentPosition, finalPosition);
        repaintChessBoard(pieceAttacked, currentPosition, finalPosition);
    }

    public void simulateMovePieceToSquare(int currentPosition, int finalPosition) {
        Piece pieceMoved = chessBoard[currentPosition].getAssignedPiece();
        String pieceMovedType = pieceMoved.getPieceType();
        pieceAttacked = calculatePieceAttacked(currentPosition,
                finalPosition, pieceMovedType);

        saveMoveToHistory(pieceMoved, pieceAttacked);
        updatePieceFirstMove(pieceMovedType, pieceMoved);
        updatePiecePositions(pieceMoved, pieceAttacked, currentPosition, finalPosition);
    }

    private Piece calculatePieceAttacked(int currentPosition, int finalPosition,
                                         String pieceMovedType) {
        Piece pieceAttacked = chessBoard[finalPosition].getAssignedPiece();
        if (pieceMovedType.equals("Pawn")
                && currentPosition % 8 != finalPosition % 8
                && pieceAttacked == null) {
            int enpassantPosition = (finalPosition % 8 - currentPosition % 8) + currentPosition;
            return chessBoard[enpassantPosition].getAssignedPiece();

        } else if (pieceMovedType.equals("King")) {
            int castlePosition;
            if (finalPosition - currentPosition > 0) {
                castlePosition = finalPosition + 1;
            } else {
                castlePosition = finalPosition - 2;
            }
            return chessBoard[castlePosition].getAssignedPiece();
        }
        return pieceAttacked;
    }

    private void updatePieceFirstMove(String pieceMovingType, Piece pieceMoving) {
        if (pieceMovingType.equals("Pawn")) {
            ((Pawn) pieceMoving).setIsFirstMove(false);
        } else if (pieceMovingType.equals("Rook")) {
            ((Rook) pieceMoving).setIsFirstMove(false);
        } else if (pieceMovingType.equals("King")) {
            ((King) pieceMoving).setIsFirstMove(false);
        } else {
            isFirstMove = false;
        }
    }

    private void updatePiecePositions(Piece pieceMoved, Piece pieceAttacked,
                                      int currentPosition, int finalPosition) {
        if (pieceAttacked != null && isSpecialMove(pieceAttacked, finalPosition)) {
            chessBoard[pieceAttacked.getPiecePosition()].setAssignedPiece(null);

            if (pieceAttacked.getPieceType().equals("Rook")) {
                int castlePosition;
                if (finalPosition - currentPosition > 0) {
                    castlePosition = finalPosition - 1;
                } else {
                    castlePosition = finalPosition + 1;
                }
                chessBoard[castlePosition].setAssignedPiece(pieceAttacked);
                pieceAttacked.setPiecePosition(castlePosition);
            }
        }
        pieceMoved.setPiecePosition(finalPosition);
        chessBoard[currentPosition].setAssignedPiece(null);
        chessBoard[finalPosition].setAssignedPiece(pieceMoved);
    }

    private boolean isSpecialMove(Piece pieceAttacked, int finalPosition) {
        return pieceAttacked.getPiecePosition() != finalPosition;
    }

    private void saveMoveToHistory(Piece pieceMoved, Piece pieceAttacked) {
        int pieceAttackedPos = -1;
        if (pieceAttacked != null) {
            pieceAttackedPos = pieceAttacked.getPiecePosition();
        }
        moveHistory.push(new MoveHistory(pieceMoved.getPiecePosition(),
                pieceAttackedPos, pieceMoved, pieceMoved, isFirstMove));
        isFirstMove = true;
    }

    private void repaintChessBoard(Piece pieceAttacked, int currentPosition, int finalPosition) {
        chessBoard[currentPosition].resetTilePanel();
        chessBoard[finalPosition].resetTilePanel();
        if (pieceAttacked != null && isSpecialMove(pieceAttacked, finalPosition)) {
            chessBoard[pieceAttacked.getPiecePosition()].resetTilePanel();

            if (pieceAttacked.getPieceType().equals("Rook")) {
                int castlePosition;
                if (finalPosition - currentPosition > 0) {
                    castlePosition = finalPosition + 1;
                } else {
                    castlePosition = finalPosition - 2;
                }
                chessBoard[castlePosition].resetTilePanel();
            }
        }
    }

    public void pruneKingInCheckMoves(List<Integer> allMoves) {
    }
}
