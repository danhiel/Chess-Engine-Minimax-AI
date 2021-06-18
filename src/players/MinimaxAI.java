package players;

import chessboard.TileUI;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;
import java.util.Stack;

public class MinimaxAI extends Player {

    public MinimaxAI(TileUI[] boardTiles, MoveAlgorithm moveAlgorithm,
                     Stack<MoveHistory> moveHistory, boolean isWhitePlayer) {
        super(boardTiles, moveAlgorithm, moveHistory, isWhitePlayer);
    }

    @Override
    public void move() {

    }

    public void move(int a) {}
}
