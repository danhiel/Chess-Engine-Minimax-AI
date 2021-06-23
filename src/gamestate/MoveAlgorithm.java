package gamestate;

import chessboard.TileUI;
import chesspieces.*;

import java.util.Stack;

public class MoveAlgorithm {

    private final Stack<MoveHistory> moveHistory;
    private Piece pieceAttacked;
    
    public MoveAlgorithm(Stack<MoveHistory> moveHistory) {
        this.moveHistory = moveHistory;
        pieceAttacked = null;
    }

    public void movePieceToSquare(TileUI[] chessBoard,
                                  int oldPosID,
                                  int newPosID) {
        simulateMovePieceToSquare(chessBoard, oldPosID, newPosID);
        repaintChessBoard(chessBoard, pieceAttacked, oldPosID, newPosID);
    }

    public void simulateMovePieceToSquare(TileUI[] chessBoard,
                                          int oldPosID,
                                          int newPosID) {
        Piece pieceMoved = chessBoard[oldPosID].getAssignedPiece();
        String pieceMovedType = pieceMoved.getPieceType();
        pieceAttacked = calculatePieceAttacked(chessBoard, oldPosID,
                                               newPosID, pieceMovedType);

        saveMoveToHistory(pieceMoved, pieceAttacked);
        pieceMoved.setIsFirstMove(false);
        updatePiecePositions(chessBoard, pieceMoved,
                             pieceAttacked, oldPosID,
                             newPosID);
    }

    public void undoMove(TileUI[] chessBoard) {
        simulateUndoMove(chessBoard);
    }

    public void simulateUndoMove(TileUI[] chessBoard) {
        MoveHistory recentMove = moveHistory.pop();
        Piece pieceMoved = recentMove.getPieceMoved();
        Piece pieceAttacked = recentMove.getPieceAttacked();
        int fromPositionID = recentMove.getOldPieceMovedPos();
        int toPositionID = recentMove.getOldPieceAttackedPos();
        
        // Update pieceMoved chessboard positions
        chessBoard[pieceMoved.getPiecePosition()].setAssignedPiece(null);
        chessBoard[fromPositionID].setAssignedPiece(pieceMoved);
        pieceMoved.setPiecePosition(fromPositionID);
        pieceMoved.setIsFirstMove(recentMove.isFirstMove());

        // Update pieceAttacked chessboard positions
        if (pieceAttacked != null) {
            chessBoard[pieceAttacked.getPiecePosition()].setAssignedPiece(null);
            chessBoard[toPositionID].setAssignedPiece(pieceAttacked);
            pieceAttacked.setPiecePosition(toPositionID);
        }
    }

    private Piece calculatePieceAttacked(TileUI[] chessBoard, int oldPosID,
                                         int newPosID, String pieceMovedType) {
        Piece pieceAttacked = chessBoard[newPosID].getAssignedPiece();
        
        if (pieceMovedType.equals("Pawn")
                && oldPosID % 8 != newPosID % 8
                && pieceAttacked == null) {
            int enpassantPosition = (newPosID % 8 - oldPosID % 8) + oldPosID;
            return chessBoard[enpassantPosition].getAssignedPiece();

        } else if (pieceMovedType.equals("King")) {
            int castlePosition;
            if (newPosID - oldPosID > 0) {
                castlePosition = newPosID + 1;
            } else {
                castlePosition = newPosID - 2;
            }
            return chessBoard[castlePosition].getAssignedPiece();
        }
        return pieceAttacked;
    }

    private void updatePiecePositions(TileUI[] chessBoard, Piece pieceMoved,
                                      Piece pieceAttacked, int currentPosition,
                                      int finalPosition) {
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
                                         pieceAttackedPos,
                                         pieceMoved,
                                         pieceAttacked,
                                         pieceMoved.getIsFirstMove()));
    }

    private void repaintChessBoard(TileUI[] chessBoard, Piece pieceAttacked,
                                   int currentPosition, int finalPosition) {
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
}
