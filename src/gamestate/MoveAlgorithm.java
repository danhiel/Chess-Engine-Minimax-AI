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
        System.out.println(oldPosID);
        Piece pieceMoved = chessBoard[oldPosID].getAssignedPiece();
        pieceAttacked = calculatePieceAttacked(chessBoard, oldPosID,
                                               newPosID, pieceMoved);

        saveMoveToHistory(pieceMoved, pieceAttacked);
        pieceMoved.setIsFirstMove(false);
        updatePiecePositions(chessBoard, pieceMoved,
                             pieceAttacked, oldPosID,
                             newPosID);
    }

    public void undoMove(TileUI[] chessBoard) {
        if (!moveHistory.isEmpty()) {
            MoveHistory recentMove = moveHistory.peek();
            Piece pieceAttacked = recentMove.getPieceAttacked();
            int oldPos = recentMove.getPieceMovedPos();
            int newPos = recentMove.getPieceAttackedPos();
            if (pieceAttacked == null) {
                newPos = recentMove.getPieceMoved().getPiecePosition();
            } else if (pieceAttacked.getPieceType() == "Pawn" &&
                        (newPos == oldPos + 1 || newPos == oldPos - 1)) {
                newPos = recentMove.getPieceMoved().getPiecePosition();
            }
            simulateUndoMove(chessBoard);
            repaintChessBoard(chessBoard, pieceAttacked, oldPos, newPos);
        }
    }

    public void simulateUndoMove(TileUI[] chessBoard) {
        MoveHistory recentMove = moveHistory.pop();
        Piece pieceMoved = recentMove.getPieceMoved();
        Piece pieceAttacked = recentMove.getPieceAttacked();
        int fromPositionID = recentMove.getPieceMovedPos();
        int toPositionID = recentMove.getPieceAttackedPos();
        
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
                                         int newPosID, Piece pieceMoved) {
        Piece pieceAttacked = chessBoard[newPosID].getAssignedPiece();
        
        if (pieceMoved.getPieceType().equals("Pawn")
                && oldPosID % 8 != newPosID % 8
                && pieceAttacked == null) {
            int enpassantPosition = (newPosID % 8 - oldPosID % 8) + oldPosID;
            return chessBoard[enpassantPosition].getAssignedPiece();

        } else if (pieceMoved.getPieceType().equals("King")
                    && pieceMoved.getIsFirstMove()) {
            if (newPosID - oldPosID == -2) {
                return chessBoard[newPosID - 2].getAssignedPiece();
            } else if (newPosID - oldPosID == 2){
                return chessBoard[newPosID + 1].getAssignedPiece();
            }
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
                                   int oldPosID, int newPosID) {
        chessBoard[oldPosID].resetTilePanel();
        chessBoard[newPosID].resetTilePanel();

        // If pieceAttacked was castled then repaint the tiles affected by castling
        if (pieceAttacked != null && isSpecialMove(pieceAttacked, newPosID)) {
            chessBoard[pieceAttacked.getPiecePosition()].resetTilePanel();

            if (pieceAttacked.getPieceType().equals("Rook")) {
                int castlePosition;
                if (newPosID - oldPosID > 0) {
                    castlePosition = newPosID + 1;
                } else {
                    castlePosition = newPosID - 2;
                }
                chessBoard[castlePosition].resetTilePanel();
            }
        }
    }
}
