package players;

import chessboard.TileUI;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;

import java.util.Stack;

public abstract class Player {

    protected final TileUI[] boardTiles;
    protected final MoveAlgorithm moveAlgorithm;
    protected final Stack<MoveHistory> moveHistory;

    protected final boolean isPlayerWhite;

    public Player(TileUI[] boardTiles, MoveAlgorithm moveAlgorithm,
                  Stack<MoveHistory> moveHistory, boolean isWhitePlayer) {
        this.boardTiles = boardTiles;
        this.moveAlgorithm = moveAlgorithm;
        this.moveHistory = moveHistory;

        this.isPlayerWhite = isWhitePlayer;
    }

    public abstract void move();

    public abstract void move(int tileID);
}
