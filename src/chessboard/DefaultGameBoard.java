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

    private final Piece allyLeftRook;
    private final Piece allyRightRook;
    private final Piece allyKing;
    private final Piece enemyLeftRook;
    private final Piece enemyRightRook;
    private final Piece enemyKing;

    private final Piece[] whitePiecesSet;
    private final Piece[] blackPiecesSet;

    public DefaultGameBoard(final Stack<MoveHistory> moveHistory,
                            boolean playerWhiteSide) {
        this.moveHistory = moveHistory;
        this.standardDefaultBoard = setUpStandardBoard(playerWhiteSide);
        this.mainChessBoard = setUpMainBoard();

        allyKing = new King(playerWhiteSide, 60);;
        enemyKing = new King(playerWhiteSide == false, 4);

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

    private Piece[] setUpStandardBoard(boolean playerWhiteSide) {
        Piece[] standardBoard = new Piece[64];
        standardBoard[0] = new Rook(playerWhiteSide == false, 0);
        standardBoard[1] = new Knight(playerWhiteSide == false, 1);
        standardBoard[2] = new Bishop(playerWhiteSide == false, 2);
        standardBoard[3] = new Queen(playerWhiteSide == false, 3);
        standardBoard[4] = enemyKing;
        standardBoard[5] = new Bishop(playerWhiteSide == false, 5);
        standardBoard[6] = new Knight(playerWhiteSide == false, 6);
        standardBoard[7] = new Rook(playerWhiteSide == false, 7);

        for (int i = 8; i < 16; i++) {
            standardBoard[i] = (new Pawn(playerWhiteSide == false, i, moveHistory));
            standardBoard[i + 40] = (new Pawn(playerWhiteSide, i + 40, moveHistory));
        }

        standardBoard[56] = new Rook(playerWhiteSide, 56);
        standardBoard[57] = new Knight(playerWhiteSide, 57);
        standardBoard[58] = new Bishop(playerWhiteSide, 58);
        standardBoard[59] = new Queen(playerWhiteSide, 59);
        standardBoard[60] = allyKing;
        standardBoard[61] = new Bishop(playerWhiteSide, 61);
        standardBoard[62] = new Knight(playerWhiteSide, 62);
        standardBoard[63] = new Rook(playerWhiteSide, 63);

        return standardBoard;
    }
}