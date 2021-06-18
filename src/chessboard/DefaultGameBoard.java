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
    private final TileUI[] mainChessBoard;
    
    private final Piece[] whitePiecesSet;
    private final Piece[] blackPiecesSet;

    /**
     * Constructor for DefaultGameBoard.
     * 
     * @param moveHistory the move history of the game.
     * @param isPlayerWhiteSide true if player is white sided, false if not.
     */
    public DefaultGameBoard(final Stack<MoveHistory> moveHistory,
                            boolean isPlayerWhiteSide) {
        this.moveHistory = moveHistory;
        this.standardDefaultBoard = setUpStandardBoard(isPlayerWhiteSide);
        this.mainChessBoard = setUpMainBoard();

        whitePiecesSet = saveChessPieces(0);
        blackPiecesSet = saveChessPieces(48);
    }

    /**
     * Returns a tileUI of size 64 that represents the game's chessboard.
     * 
     * @return main tile chess board.
     */
    public TileUI[] getChessBoard() {
        return mainChessBoard;
    }
    
    /**
     * Returns all of the white chess pieces.
     * 
     * @return Piece[] representing all of the white chess pieces.
     */
    public Piece[] getWhitePieces() {
        return whitePiecesSet;
    }
    
    /**
     * Returns all of the black chess pieces.
     * 
     * @return Piece[] representing all of the black chess pieces.
     */
    public Piece[] getBlackPieces() {
        return blackPiecesSet;
    }

    /**
     * Sets up the TileUI[] board representing our (main) chessboard.
     * 
     * @return main tile chessboard.
     */
    private TileUI[] setUpMainBoard() {
        TileUI[] chessBoard = new TileUI[64];
        for (int i = 0; i < chessBoard.length; i++) {
            chessBoard[i] = new TileUI(i, standardDefaultBoard[i]);
        }
        return chessBoard;
    }

    /**
     * Saves the chess pieces to an array.  
     * 
     * @param index the starting index of a chessboard to iterate through to index + 16.
     * @return Piece[] representing a set of chess pieces.
     */
    private Piece[] saveChessPieces(int index) {
        Piece[] chessPieces = new Piece[16];
        for (int i = index; i < index + 16; i++) {
            chessPieces[i - index] = mainChessBoard[i].getAssignedPiece();
        }
        return chessPieces;
    }

    /**
     * Sets up the main board for the pieces standard starting chess positions.
     * 
     * @param isPlayerWhiteSide true if the player is white side.
     * @return Piece[] representing the starting positions for each piece on chessboard.
     */
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