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
public class GameBoard {
    
    private static final Piece WHITE_LEFT_ROOK = new Rook(true,  56);
    private static final Piece WHITE_RIGHT_ROOK = new Rook(true, 63);
    private static final Piece WHITE_KING = new King(true, 60,
            WHITE_LEFT_ROOK, WHITE_RIGHT_ROOK);;

    private static final Piece BLACK_LEFT_ROOK = new Rook(false, 0);
    private static final Piece BLACK_RIGHT_ROOK = new Rook(false,7);
    private static final Piece BLACK_KING = new King(false, 4,
            BLACK_LEFT_ROOK, BLACK_RIGHT_ROOK);

    private final Stack<MoveHistory> moveHistory;
    private final Piece[] standardBoard;
    private final Tile[] chessBoard;
    private final Piece[] whitePiecesSet;
    private final Piece[] blackPiecesSet;

    public GameBoard(final Stack<MoveHistory> moveHistory) {
        this.moveHistory = moveHistory;
        standardBoard = setUpStandardBoard();
        chessBoard = setUpMainBoard();
        whitePiecesSet = saveChessPieces(0);
        blackPiecesSet = saveChessPieces(16);
    }

    public Tile[] getChessBoard() {
        return chessBoard;
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
            chessBoard[i] = new Tile(i, standardBoard[i]);
        }
        return chessBoard;
    }

    private Piece[] saveChessPieces(int index) {
        Piece[] chessPieces = new Piece[16];
        for (int i = index; i < index + 16; i++) {
            chessPieces[i - index] = chessBoard[i].getAssignedPiece();
        }
        return chessPieces;
    }

    private Piece[] setUpStandardBoard() {
        Piece[] standardBoard = new Piece[64];
        standardBoard[0] = BLACK_LEFT_ROOK;
        standardBoard[1] = new Knight(false, 1);
        standardBoard[2] = new Bishop(false, 2);
        standardBoard[3] = new Queen(false, 3);
        standardBoard[4] = BLACK_KING;
        standardBoard[5] = new Bishop(false, 5);
        standardBoard[6] = new Knight(false, 6);
        standardBoard[7] = BLACK_RIGHT_ROOK;

        for (int i = 8; i < 16; i++) {
            standardBoard[i] = (new Pawn(false, i, moveHistory));
            standardBoard[i + 40] = (new Pawn(true, i + 40, moveHistory));
        }

        standardBoard[56] = WHITE_LEFT_ROOK;
        standardBoard[57] = new Knight(true, 57);
        standardBoard[58] = new Bishop(true, 58);
        standardBoard[59] = new Queen(true, 59);
        standardBoard[60] = WHITE_KING;
        standardBoard[61] = new Bishop(true, 61);
        standardBoard[62] = new Knight(true, 62);
        standardBoard[63] = WHITE_RIGHT_ROOK;

        return standardBoard;
    }
}