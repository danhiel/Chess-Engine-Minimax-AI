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

    private Map<Integer, HashSet<Piece>> whiteThreatenedTiles;
    private Map<Integer, HashSet<Piece>> blackThreatenedTiles;
    
    public GameState(TileUI[] chessBoard, boolean isWhiteSide) {
        this.chessBoard = chessBoard;

        if (isWhiteSide) {
            this.whiteKing = chessBoard[60].getAssignedPiece();
            this.blackKing = chessBoard[4].getAssignedPiece();
            this.aliveWhitePieces = saveChessPieces(0);
            this.aliveBlackPieces = saveChessPieces(48);
        } else {
            this.whiteKing = chessBoard[4].getAssignedPiece();
            this.blackKing = chessBoard[60].getAssignedPiece();
            this.aliveWhitePieces = saveChessPieces(48);
            this.aliveBlackPieces = saveChessPieces(0);
        }

        whiteThreatenedTiles = new HashMap<Integer, HashSet<Piece>>();
        blackThreatenedTiles = new HashMap<Integer, HashSet<Piece>>();
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
     * Recalculates the threatened tiles being manipulated. 
     * 
     * @param piece the piece that was most recently moved.
     * @param oldPos the tile position the piece was before moving.
     * @return
     */
    public void calcThreatenedTiles(Piece piece, int oldPos) {
        Map<Integer, HashSet<Piece>> threatenedTiles = piece.getIsPieceWhite() ?
                                                       whiteThreatenedTiles :
                                                       blackThreatenedTiles;
        Map<Integer, HashSet<Piece>> oppThreatenedTiles = piece.getIsPieceWhite() ?
                                                          whiteThreatenedTiles :
                                                          blackThreatenedTiles;

        Set<Piece> attackingPieces = threatenedTiles.get(oldPos);
        for (Piece attackingPiece : attackingPieces) {
            Set<Integer> allMoves = attackingPiece.getAllMoves(chessBoard);
            threatenedTiles.remove(oldPos);
            for (Integer move : allMoves) {
                if (!threatenedTiles.containsKey(move)) {
                    threatenedTiles.put(move, new HashSet<>());
                }
                threatenedTiles.get(move).add(attackingPiece);
            }
        }

        Set<Integer> currPieceMoves = piece.getAllMoves(chessBoard);
        for (int move : currPieceMoves) {
            if (!oppThreatenedTiles.containsKey(move)) {
                oppThreatenedTiles.put(move, new HashSet<>());
            }
            oppThreatenedTiles.get(move).add(piece);
        }


    }

    public boolean calcIsWhiteCheck() {
        for (Piece piece : aliveBlackPieces) {
            Set<Integer> allMoves = piece.getAllMoves(chessBoard);
            if (allMoves.contains(whiteKing.getPiecePosition())) {
                return true;
            }
        }
        return false;
    }

    public boolean calcIsBlackCheck() {
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
