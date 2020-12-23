package chessboard;

import chesspieces.*;
import gamestate.MoveHistory;
import java.util.Stack;

/**
 * Creates a Chess Board that will
 * track the state of the game.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class DefaultGameBoard {

    private final Stack<MoveHistory> moveHistory;
    private final Piece[] standardDefaultBoard;
    private final Tile[] mainChessBoard;
    
    private final Piece[] whitePiecesSet;
    private final Piece[] blackPiecesSet;

    public DefaultGameBoard(final Stack<MoveHistory> moveHistory,
                            boolean isPlayerWhiteSide) {
        this.moveHistory = moveHistory;
        this.standardDefaultBoard = setUpStandardBoard(isPlayerWhiteSide);
        this.mainChessBoard = setUpMainBoard();

        whitePiecesSet = saveChessPieces(0);
        blackPiecesSet = saveChessPieces(48);
    }

    public Tile[] getChessBoard() {
        return mainChessBoard;
    }
    
    public Piece[] getWhitePieces() {
        return whitePiecesSet;
    }
    
    public Piece[] getBlackPieces() {
        return blackPiecesSet;
    }

    private Tile[] setUpMainBoard() {
        Tile[] chessBoard = new Tile[64];
        for (int i = 0; i < chessBoard.length; i++) {
            chessBoard[i] = new Tile(i, standardDefaultBoard[i]);
        }
        return chessBoard;
    }

    private Piece[] saveChessPieces(int index) {
        Piece[] chessPieces = new Piece[16];
        for (int i = index; i < index + 16; i++) {
            chessPieces[i - index] = mainChessBoard[i].getAssignedPiece();
        }
        return chessPieces;
    }

    private Piece[] setUpStandardBoard(boolean isPlayerWhiteSide) {
        Piece[] standardBoard = new Piece[64];
        boolean startBotSide = true;
        boolean startTopSide = false;

        standardBoard[0] = new Rook(isPlayerWhiteSide == false, startTopSide, 0);
        standardBoard[1] = new Knight(isPlayerWhiteSide == false, startTopSide, 1);
        standardBoard[2] = new Bishop(isPlayerWhiteSide == false, startTopSide, 2);
        standardBoard[3] = new Queen(isPlayerWhiteSide == false, startTopSide, 3);
        standardBoard[4] = new King(isPlayerWhiteSide == false, startTopSide, 4);
        standardBoard[5] = new Bishop(isPlayerWhiteSide == false, startTopSide, 5);
        standardBoard[6] = new Knight(isPlayerWhiteSide == false, startTopSide, 6);
        standardBoard[7] = new Rook(isPlayerWhiteSide == false, startTopSide, 7);

        for (int i = 8; i < 16; i++) {
            standardBoard[i] = (new Pawn(isPlayerWhiteSide == false,
                                         startTopSide, i, moveHistory));
            standardBoard[i + 40] = (new Pawn(isPlayerWhiteSide,
                                              startBotSide, i + 40, moveHistory));
        }

        standardBoard[56] = new Rook(isPlayerWhiteSide, startBotSide, 56);
        standardBoard[57] = new Knight(isPlayerWhiteSide, startBotSide, 57);
        standardBoard[58] = new Bishop(isPlayerWhiteSide, startBotSide, 58);
        standardBoard[59] = new Queen(isPlayerWhiteSide, startBotSide, 59);
        standardBoard[60] = new King(isPlayerWhiteSide, startBotSide, 60);
        standardBoard[61] = new Bishop(isPlayerWhiteSide, startBotSide, 61);
        standardBoard[62] = new Knight(isPlayerWhiteSide, startBotSide, 62);
        standardBoard[63] = new Rook(isPlayerWhiteSide, startBotSide, 63);

        return standardBoard;
    }
}