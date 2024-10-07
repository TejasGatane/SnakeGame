import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception 
    {
        int boardWidth = 700;
        int boardHeight = 700;

        JFrame frame = new JFrame("snake");
        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
