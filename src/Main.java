import userinterface.StartUI;

/**
 * The program allows the user to play a game of Chess
 * against an Min-max AI.
 *
 * @author  Danhiel Vu
 * @version 2.0
 * @since   1/23/2020
 */
public class Main {

    public static void main(String[] args) {
        //Stack<MoveHistory> moveHistory = new Stack<MoveHistory>();
        //GameBoard gameBoard = new GameBoard(moveHistory);
        //MoveAlgorithm moveAlgorithm = new MoveAlgorithm(gameBoard.getChessBoard(), moveHistory);
        //Player whitePlayer = new Human(gameBoard.getChessBoard(), moveAlgorithm, moveHistory, true);
        //Player blackPlayer = new Human(gameBoard.getChessBoard(), moveAlgorithm, moveHistory, false);
        //UserInterface userInterface = new UserInterface(gameBoard.getChessBoard(), moveAlgorithm, moveHistory);

        //userInterface.createGameUI();

        StartUI startUI = new StartUI();
        startUI.createStartUI();
    }
}