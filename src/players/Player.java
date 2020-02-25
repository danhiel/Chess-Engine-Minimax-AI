package players;

import chessboard.Tile;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import java.util.Stack;

public abstract class Player {

    protected final Tile[] boardTiles;
    protected final MoveAlgorithm moveAlgorithm;
    protected final Stack<MoveHistory> moveHistory;

    protected final boolean isPlayerWhite;

    public Player(Tile[] boardTiles, MoveAlgorithm moveAlgorithm,
                  Stack<MoveHistory> moveHistory, boolean isWhitePlayer) {
        this.boardTiles = boardTiles;
        this.moveAlgorithm = moveAlgorithm;
        this.moveHistory = moveHistory;

        this.isPlayerWhite = isWhitePlayer;
    }

    public abstract void move();

    public abstract void move(int tileID);
}
