package gamestate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    /**
     * Returns whether the given side has their king check or not.
     * 
     * @param isWhiteSide true if white side, false if not.
     * @return true if the given side has their king check, false otherwise.
     */
    public boolean calcIfKingIsCheck(boolean isWhiteSide) {
        Set<Piece> alivePieces = isWhiteSide ? aliveBlackPieces : aliveWhitePieces;
        Piece king = isWhiteSide ? whiteKing : blackKing;

        for (Piece piece : alivePieces) {
            Set<Integer> allMoves = piece.getAllMoves(chessBoard);
            if (allMoves.contains(king.getPiecePosition())) {
                System.out.println(allMoves.toString());
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
