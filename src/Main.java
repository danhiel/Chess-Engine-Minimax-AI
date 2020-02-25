import chessboard.GameBoard;
import chessboard.UserInterface;
import gamestate.MoveAlgorithm;
import gamestate.MoveHistory;
import players.Human;
import players.Player;

import java.util.Stack;

/**
 * The program allows the user to play a game of Chess
 * either locally or against an AI.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Main {

    public static void main(String[] args) {
        Stack<MoveHistory> moveHistory = new Stack<MoveHistory>();
        GameBoard gameBoard = new GameBoard(moveHistory);
        MoveAlgorithm moveAlgorithm = new MoveAlgorithm(gameBoard.getChessBoard(), moveHistory);
        Player whitePlayer = new Human(gameBoard.getChessBoard(), moveAlgorithm, moveHistory, true);
        Player blackPlayer = new Human(gameBoard.getChessBoard(), moveAlgorithm, moveHistory, false);
        UserInterface userInterface = new UserInterface(gameBoard.getChessBoard(), moveAlgorithm, moveHistory);

        userInterface.createGameUI();
    }
}