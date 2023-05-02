import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        JPanel panel = new JPanel(new BorderLayout());



        NewGame newGame = new NewGame();
        newGame.setQuestionIndex(0);
        JPanel newGamePanel = newGame.createPanel();

        newGamePanel.setVisible(false);


        panel.add(newGamePanel, BorderLayout.CENTER);
        Buttons buttons = new Buttons(newGamePanel);
        JPanel buttonsPanel = buttons.createPanel();
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(Color.GRAY); // установка серого фона для всего окна
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}

