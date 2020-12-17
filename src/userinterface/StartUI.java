package userinterface;

import chessboard.DefaultGameBoard;
import gamestate.MoveHistory;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class StartUI {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final JButton buttonWhiteSide;
    private final JButton buttonBlackSide;

    public StartUI() {
        this.frame = new JFrame("Chess");
        this.mainPanel = new JPanel();
        this.buttonWhiteSide = new JButton("White Side");
        this.buttonBlackSide = new JButton("Black Side");
    }

    /**
     * Creates the start user interface.
     */
    public void createStartUI() {
        setUpMainPanel();
        setUpButtons();
        setUpAndDisplayFrame();
    }

    /**
     * Sets up the main panel of the frame.
     */
    private void setUpMainPanel() {
        // Sets up Panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create labels
        JLabel labelHeader = new JLabel("<html><h1>Chess Engine & AI</h1></html>");
        JLabel labelDescription = new JLabel("By Danhiel Vu");
        JLabel labelInstruction = new JLabel("Pick a side to start on:");

        // Center labels
        labelHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelDescription.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add labels to panel
        mainPanel.add(labelHeader);
        mainPanel.add(labelDescription);
        mainPanel.add(labelInstruction);
    }

    /**
     * Sets up buttons and action when clicked. When button is clicked,
     * the player chosed a side and the Chess game will commence.
     */
    private void setUpButtons() {
        // Center buttons
        buttonWhiteSide.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBlackSide.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonWhiteSide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stack<MoveHistory> moveHistory = new Stack<MoveHistory>();
                DefaultGameBoard gameBoard = new DefaultGameBoard(moveHistory);

                frame.dispose();
            }
        });

        buttonBlackSide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Add buttons to main panel
        mainPanel.add(buttonWhiteSide);
        mainPanel.add(buttonBlackSide);
    }

    /**
     * Sets up and displays the GUI.
     */
    private void setUpAndDisplayFrame() {
        // Add content to frame
        frame.add(mainPanel);

        // Sets up Window
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the window
        frame.pack();
        frame.setVisible(true);

        // Sets location
        Toolkit tk = Toolkit.getDefaultToolkit();
        frame.setLocation(tk.getScreenSize().width / 2 - frame.getWidth() / 2,
                tk.getScreenSize().height / 4);
    }
}
