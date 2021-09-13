package gamestate;

import java.util.HashSet;
import java.util.Set;

import chessboard.TileUI;
import chesspieces.Piece;

public class GameState {

    private TileUI[] chessBoard;

    private Piece whiteKing;
    private Piece blackKing;

    private Set<Piece> aliveWhitePieces;
    private Set<Piece> aliveBlackPieces;
    
    public GameState(TileUI[] chessBoard, boolean isWhiteSide) {
        this.chessBoard = chessBoard;

        if (isWhiteSide) {
            this.whiteKing = chessBoard[60].getAssignedPiece();
            this.blackKing = chessBoard[4].getAssignedPiece();
            this.aliveWhitePieces = saveChessPieces(48);
            this.aliveBlackPieces = saveChessPieces(0);
        } else {
            this.whiteKing = chessBoard[4].getAssignedPiece();
            this.blackKing = chessBoard[60].getAssignedPiece();
            this.aliveWhitePieces = saveChessPieces(0);
            this.aliveBlackPieces = saveChessPieces(48);
        }
    }

    public TileUI[] getChessBoard() {
        return chessBoard;
    }

    public Piece getWhiteKing() {
        return whiteKing;
    }

    public Piece getBlackKing() {
        return blackKing;
    }

    public Set<Piece> getAliveWhitePieces() {
        return aliveWhitePieces;
    }

    public Set<Piece> getAliveBlackPieces() {
        return aliveBlackPieces;
    }

    public void removePieceFromAlive(Piece chessPiece) {
        if (chessPiece.getIsPieceWhite()) {
            aliveWhitePieces.remove(chessPiece);
        } else {
            aliveBlackPieces.remove(chessPiece);
        }
    }

    public void addPieceToAlive(Piece chessPiece) {
        if (chessPiece.getIsPieceWhite()) {
            aliveWhitePieces.add(chessPiece);
        } else {
            aliveBlackPieces.add(chessPiece);
        }
    }

    public Set<Integer> getAllEnemyMoves(boolean isWhiteSide) {
        Set<Piece> alivePieces = isWhiteSide ? aliveBlackPieces : aliveWhitePieces;
        Set<Integer> results = new HashSet<Integer>();
        for (Piece piece : alivePieces) {
            results.addAll(piece.getAllMoves(chessBoard));
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
        Set<Piece> enemyAlivePieces = isWhiteSide ? aliveBlackPieces : aliveWhitePieces;
        Piece allyKing = isWhiteSide ? whiteKing : blackKing;

        for (Piece enemyPiece : enemyAlivePieces) {
            Set<Integer> allMoves = enemyPiece.getAllMoves(chessBoard);
            if (allMoves.contains(allyKing.getPiecePosition())) {
                return true;
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
