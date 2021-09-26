package gamestate;

import java.util.HashSet;
import java.util.Set;

import chessboard.TileUI;
import chesspieces.Piece;

/**
 * Keeps track of the game state: Retrieves enemy moves and calculates
 * whether either kings are check.
 *
 * @author  Danhiel Vu
 * @version 1.0
 * @since   3/28/2020
 */
public class GameState {

    private TileUI[] chessBoard;
    private MoveAlgorithm moveAlgorithm;

    private Piece whiteKing;
    private Piece blackKing;

    private Set<Piece> whitePieces;
    private Set<Piece> blackPieces;
    
    /**
     * Constructor for the GameState class. Tracks all black and white pieces.
     * 
     * @param chessBoard the main chessboard that tracks board-state.
     * @param moveAlgorithm manipulates chess piece movement wihtin the main chessboard.
     * @param isWhiteSide true if the player is white sided, false otherwise.
     */
    public GameState(TileUI[] chessBoard, MoveAlgorithm moveAlgorithm, boolean isWhiteSide) {
        this.chessBoard = chessBoard;
        this.moveAlgorithm = moveAlgorithm;

        if (isWhiteSide) {
            this.whiteKing = chessBoard[60].getAssignedPiece();
            this.blackKing = chessBoard[4].getAssignedPiece();
            this.whitePieces = saveChessPieces(48);
            this.blackPieces = saveChessPieces(0);
        } else {
            this.whiteKing = chessBoard[4].getAssignedPiece();
            this.blackKing = chessBoard[60].getAssignedPiece();
            this.whitePieces = saveChessPieces(0);
            this.blackPieces = saveChessPieces(48);
        }
    }

    /**
     * Returns the chessboard.
     * 
     * @return TileUI chessboard.
     */
    public TileUI[] getChessBoard() {
        return chessBoard;
    }

    /**
     * Returns the set of all enemy moves.
     * 
     * @param isWhiteSide true if ally is white side, false otherwise.
     * @return set of integers representing the tile ID the enemy can move to without
     * checking if the King is in check.
     */
    public Set<Integer> getAllEnemyMoves(boolean isWhiteSide) {
        Set<Piece> enemyAllPieces = isWhiteSide ? blackPieces : whitePieces;
        Set<Integer> results = new HashSet<Integer>();
        for (Piece piece : enemyAllPieces) {
            piece = chessBoard[piece.getPiecePosition()].getAssignedPiece();
            if (piece != null) {
                results.addAll(piece.getAllMoves(chessBoard));
            }
        }
        return results;
    }

    /**
     * Returns the set of all legal enemy moves.
     * 
     * @param isWhiteSide true if ally is white side, false otherwise.
     * @return set of integers representing the tile ID the enemy can move to.
     */
    public Set<Integer> getAllLegalEnemyMoves(boolean isWhiteSide) {
        Set<Piece> alivePieces = isWhiteSide ? blackPieces : whitePieces;
        Set<Integer> results = new HashSet<Integer>();
        for (Piece piece : alivePieces) {
            piece = chessBoard[piece.getPiecePosition()].getAssignedPiece();
            if (piece != null) {
                results.addAll(piece.getAllLegalMoves(this, chessBoard, moveAlgorithm));
            }
        }
        return results;
    }

    /**
     * Returns whether the given side has their king check or not.
     * 
     * @param isWhiteSide true if white side, false if not.
     * @return true if the given side has their king check, false otherwise.
     */
    public boolean calcIfAllyKingIsCheck(boolean isWhiteSide) {
        Set<Piece> enemyAlivePieces = isWhiteSide ? blackPieces : whitePieces;
        Piece allyKing = isWhiteSide ? whiteKing : blackKing;

        for (Piece enemyPiece : enemyAlivePieces) {
            enemyPiece = chessBoard[enemyPiece.getPiecePosition()].getAssignedPiece();
            if (enemyPiece != null) {
                Set<Integer> allMoves = enemyPiece.getAllMoves(chessBoard);
                if (allMoves.contains(allyKing.getPiecePosition())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Saves the chess pieces to an array.  
     * 
     * @param index the starting index of a chessboard to iterate through to index + 16.
     * @return Piece[] representing a set of chess pieces.
     */
    private Set<Piece> saveChessPieces(int index) {
        Set<Piece> chessPieces = new HashSet<Piece>();
        for (int i = index; i < index + 16; i++) {
            chessPieces.add(chessBoard[i].getAssignedPiece());
        }
        return chessPieces;
    }
}
