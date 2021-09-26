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
        this.pieceAttacked = calculatePieceAttacked(chessBoard, oldPosID,
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
            
            // updates newPos if the piece attacked was done by en passant rule.
            if (pieceAttacked == null) {
                newPos = recentMove.getPieceMoved().getPiecePosition();
            } else if (pieceAttacked.getPieceType().equals("Pawn") &&
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

    private Piece calculatePieceAttacked(TileUI[] chessBoard, int moveFromID,
                                         int moveToID, Piece pieceMoved) {
        Piece pieceAttacked = chessBoard[moveToID].getAssignedPiece();
        
        // Gets the pawn that was attacked by en passant.
        if (pieceMoved.getPieceType().equals("Pawn")
                && moveFromID % 8 != moveToID % 8
                && pieceAttacked == null) {
            int enpassantPosition = (moveToID % 8 - moveFromID % 8) + moveFromID;
            return chessBoard[enpassantPosition].getAssignedPiece();

        // Gets piece rook if castling occurred.
        } else if (pieceMoved.getPieceType().equals("King")
                    && pieceMoved.getIsFirstMove()) {
            if (moveToID - moveFromID == -2) {
                return chessBoard[moveToID - 2].getAssignedPiece();
            } else if (moveToID - moveFromID == 2){
                return chessBoard[moveToID + 1].getAssignedPiece();
            }
        }
        return pieceAttacked;
    }

    private void updatePiecePositions(TileUI[] chessBoard, Piece pieceMoved,
                                      Piece pieceAttacked, int moveFromID,
                                      int moveToID) {
        if (pieceAttacked != null && isSpecialMove(pieceAttacked, moveToID)) {
            chessBoard[pieceAttacked.getPiecePosition()].setAssignedPiece(null);

            if (pieceAttacked.getIsPieceWhite() == pieceMoved.getIsPieceWhite()) {
                int castlePosition;
                if (moveToID - moveFromID > 0) {
                    castlePosition = moveToID - 1;
                } else {
                    castlePosition = moveToID + 1;
                }
                chessBoard[castlePosition].setAssignedPiece(pieceAttacked);
                pieceAttacked.setPiecePosition(castlePosition);
            }
        }
        pieceMoved.setPiecePosition(moveToID);
        chessBoard[moveFromID].setAssignedPiece(null);
        chessBoard[moveToID].setAssignedPiece(pieceMoved);
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

    private void repaintChessBoard(TileUI[] chessBoard,
                                   Piece pieceAttacked,
                                   int moveFromID, int moveToID) {
        chessBoard[moveFromID].resetTilePanel();
        chessBoard[moveToID].resetTilePanel();
        System.out.println(pieceAttacked);

        // If recent move was a special move then repaint impacted tiles.
        if (pieceAttacked != null) {
            Piece pieceMoved = chessBoard[moveToID].getAssignedPiece();
            chessBoard[pieceAttacked.getPiecePosition()].resetTilePanel();

            // If recent move was castled
            if (pieceMoved.getPieceType().equals("King")) {
                if (moveFromID - moveToID == 2) {
                    chessBoard[moveToID - 2].resetTilePanel();
                } else if (moveFromID - moveToID == -2) {
                    chessBoard[moveToID + 1].resetTilePanel();
                }
            }
        }
        System.out.println(moveFromID + " " + moveToID);
    }
}
