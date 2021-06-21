package players;

import chessboard.TileUI;
import chesspieces.Piece;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import java.util.Set;
import java.util.Stack;

public class Human extends Player {

    private Piece highlightedPiece;
    private Set<Integer> highlightedMoves;

    public Human(TileUI[] boardTiles, MoveAlgorithm moveAlgorithm,
                 Stack<MoveHistory> moveHistory, boolean isPlayerWhite) {
        super(boardTiles, moveAlgorithm, moveHistory, isPlayerWhite);
        highlightedPiece = null;
        highlightedMoves = null;
    }

    @Override
    public void move(int tileID) {
        Piece selectedPiece = boardTiles[tileID].getAssignedPiece();

        if (checkIsPlayersTurn(selectedPiece))
            if (highlightedPiece == null) {
                highlightPiece(selectedPiece);
            } else if (highlightedPiece.equals(selectedPiece)) {
                unhighlightPiece(null);
            } else {
                unhighlightPiece(null);
                highlightPiece(selectedPiece);

        } else if (highlightedPiece != null) {
            unhighlightPiece(null);
            if (highlightedMoves.contains(tileID)) {
                this.moveAlgorithm.movePieceToSquare(boardTiles, tileID,
                        highlightedPiece.getPiecePosition());
            }
        }
    }

    private boolean checkIsPlayersTurn(Piece selectedPiece) {
        if (selectedPiece == null) {
            return false;
        }
        return selectedPiece.isPieceWhite() && isPlayerWhite;
    }

    private void highlightPiece(Piece selectedPiece) {
        highlightedPiece = selectedPiece;
        highlightedMoves = selectedPiece.getAllMoves(boardTiles);
        boardTiles[selectedPiece.getPiecePosition()].assignHighlightTileColor();
        System.out.println(highlightedMoves);
        for (int moveID : highlightedMoves) {
            this.boardTiles[moveID].assignHighlightTileColor();
        }
    }

    private void unhighlightPiece(Piece selectedPiece) {
        boardTiles[highlightedPiece.getPiecePosition()].assignDefaultTileColor();
        for (int movePosition : highlightedMoves) {
            this.boardTiles[movePosition].assignDefaultTileColor();
        }
        highlightedPiece = selectedPiece;
    }

    public void move() {}
}
