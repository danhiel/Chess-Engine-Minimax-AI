package players;

import chessboard.Tile;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;
import java.util.Stack;

public class MinimaxAI extends Player {

    public MinimaxAI(Tile[] boardTiles, MoveAlgorithm moveAlgorithm,
                     Stack<MoveHistory> moveHistory, boolean isWhitePlayer) {
        super(boardTiles, moveAlgorithm, moveHistory, isWhitePlayer);
    }

    @Override
    public void move() {

    }

    public void move(int a) {}
}
