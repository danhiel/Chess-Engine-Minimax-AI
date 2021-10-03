package gamestate;

import chessboard.TileUI;
import chesspieces.*;

import java.util.Stack;

/**
 * The Move Algorithm that updates the main chess board and the pieces position.
 *
 * @author  Danhiel Vu
 * @version 1.0
 * @since   3/28/2021
 */
public class MoveAlgorithm {

    private final Stack<MoveHistory> moveHistory;
    private Piece pieceAttacked;
    
    /**
     * Constructor for the MoveAlgorithm class.
     * @param moveHistory tracks the move history.
     */
    public MoveAlgorithm(Stack<MoveHistory> moveHistory) {
        this.moveHistory = moveHistory;
        pieceAttacked = null;
    }

    /**
     * Moves a piece from it's old tile ID to the new old tile ID and
     * updates the chess board UI.
     * 
     * @param chessBoard the main chessboard that tracks board-state.
     * @param moveFromID the tile ID the piece is moving from.
     * @param moveToID the tile ID the piece is moving to.
     */
    public void movePieceToSquare(TileUI[] chessBoard,
                                  int moveFromID,
                                  int moveToID) {
        simulateMovePieceToSquare(chessBoard, moveFromID, moveToID);
        repaintChessBoard(chessBoard, chessBoard[moveToID].getAssignedPiece(),
                          pieceAttacked, moveFromID,
                          moveHistory.peek().getOldPieceAttackedID());
    }

    /**
     * Moves a piece from it's old tile ID to the new old tile ID withou
     * updating the chess board UI.
     * 
     * @param chessBoard the main chessboard that tracks board-state.
     * @param moveFromID the tile ID the piece is moving from.
     * @param moveToID the tile ID the piece is moving to.
     */
    public void simulateMovePieceToSquare(TileUI[] chessBoard,
                                          int moveFromID,
                                          int moveToID) {
        Piece pieceMoved = chessBoard[moveFromID].getAssignedPiece();
        this.pieceAttacked = calculatePieceAttacked(chessBoard, moveFromID,
                                               moveToID, pieceMoved);

        saveMoveToHistory(moveToID, pieceMoved, pieceAttacked);
        pieceMoved.setIsFirstMove(false);
        updatePiecePositions(chessBoard, pieceMoved,
                             pieceAttacked, moveFromID,
                             moveToID);
    }

    public void undoMove(TileUI[] chessBoard) {
        if (!moveHistory.isEmpty()) {
            MoveHistory recentMove = moveHistory.peek();
            Piece pieceMoved = recentMove.getPieceMoved();
            Piece pieceAttacked = recentMove.getPieceAttacked();

            int recentPieceMovedID = pieceMoved.getPiecePosition();
            int recentPieceAttackedID = pieceAttacked != null
                                        ? pieceAttacked.getPiecePosition() : -1;
            
            simulateUndoMove(chessBoard);
            repaintChessBoard(chessBoard, pieceMoved, pieceAttacked,
                              recentPieceMovedID, recentPieceAttackedID);
        }
    }

    public void simulateUndoMove(TileUI[] chessBoard) {
        MoveHistory recentMove = moveHistory.pop();
        Piece pieceMoved = recentMove.getPieceMoved();
        Piece pieceAttacked = recentMove.getPieceAttacked();
        int oldPieceMovedID = recentMove.getOldPieceMovedID();
        int oldPieceAttackedID = recentMove.getOldPieceAttackedID();
        
        // Update pieceMoved chessboard positions
        chessBoard[pieceMoved.getPiecePosition()].setAssignedPiece(null);
        chessBoard[oldPieceMovedID].setAssignedPiece(pieceMoved);
        pieceMoved.setPiecePosition(oldPieceMovedID);
        pieceMoved.setIsFirstMove(recentMove.isFirstMove());

        // Update pieceAttacked chessboard positions
        if (pieceAttacked != null) {
            chessBoard[pieceAttacked.getPiecePosition()].setAssignedPiece(null);
            chessBoard[oldPieceAttackedID].setAssignedPiece(pieceAttacked);
            pieceAttacked.setPiecePosition(oldPieceAttackedID);
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
            System.out.println("ayeee");
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

        if (pieceMoved.getPieceType() == "Pawn") {
            if ((moveToID >= 0 && moveToID < 8) || (moveToID >= 56 && moveToID < 64)) {
            }
        }

        pieceMoved.setPiecePosition(moveToID);
        chessBoard[moveFromID].setAssignedPiece(null);
        chessBoard[moveToID].setAssignedPiece(pieceMoved);
    }

    private boolean isSpecialMove(Piece pieceAttacked, int finalPosition) {
        return pieceAttacked.getPiecePosition() != finalPosition;
    }

    /**
     * Save a move to the MoveHistory. A move consist of the ID the moved piece was from, 
     * the ID the attacked piece was from, the piece moved, the piece attacked,
     * and if it's the moved piece first move.
     * 
     * @param moveToID the tile ID the piece is moving to.
     * @param pieceMoved the piece that is being moved.
     * @param pieceAttacked the piece that was impacted by the piece moved.
     */
    private void saveMoveToHistory(int moveToID, Piece pieceMoved, Piece pieceAttacked) {
        moveToID = pieceAttacked != null ? pieceAttacked.getPiecePosition() : moveToID;
        moveHistory.push(new MoveHistory(pieceMoved.getPiecePosition(),
                                         moveToID,
                                         pieceMoved,
                                         pieceAttacked,
                                         pieceMoved.getIsFirstMove()));
    }

    private void repaintChessBoard(TileUI[] chessBoard,
                                   Piece pieceMoved, Piece pieceAttacked,
                                   int oldPieceMovedID, int oldPieceAttackedID) {
        chessBoard[oldPieceMovedID].resetTilePanel();
        chessBoard[pieceMoved.getPiecePosition()].resetTilePanel();
        //System.out.println(pieceAttacked);

        // If recent move was a special move then repaint impacted tiles.
        if (pieceAttacked != null) {
            chessBoard[pieceAttacked.getPiecePosition()].resetTilePanel();
            chessBoard[oldPieceAttackedID].resetTilePanel();
        }
        System.out.println(oldPieceMovedID + " " + oldPieceAttackedID);
    }
}
