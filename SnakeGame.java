import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;

public class SnakeGame extends JPanel implements ActionListener, KeyListener
{
    private class Panel
    {
        int x;
        int y;

        Panel(int x, int y) 
        {
            this.x = x;
            this.y = y;
        }

    }
    int boardWidth;
    int boardHeight;
    int panelSize = 25;


    //Snake
    Panel SnakeMouth;
    ArrayList<Panel> snakeBody;

    //food
    Panel food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.gray);
        addKeyListener(this);
        setFocusable(true);

        SnakeMouth = new Panel(5,5 );
        snakeBody = new ArrayList<Panel>();

        food = new Panel(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //food
        g.setColor(Color.green);
        g.fill3DRect(food.x * panelSize, food.y * panelSize, panelSize, panelSize, true);


        //snake
        g.setColor(Color.black);
        
        g.fill3DRect(SnakeMouth.x * panelSize, SnakeMouth.y * panelSize, panelSize, panelSize, true);


        //snake Body
        for (int i = 0; i < snakeBody.size(); i++){
            Panel snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * panelSize, snakePart.y * panelSize, panelSize, panelSize, true);
        }
        
        //score count
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        if(gameOver)
        {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), panelSize - 20, panelSize);

        }
        else{
            g.drawString("Score:" + String.valueOf(snakeBody.size()), panelSize - 20, panelSize);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/panelSize); 
        food.y = random.nextInt(boardHeight/panelSize);
    }

    public boolean collision(Panel panel1, Panel panel2){
        return panel1.x == panel2.x && panel1.y == panel2.y;
    }

    public void move()
    {
        //eat food
        if (collision(SnakeMouth, food))
        {
            snakeBody.add(new Panel(food.x, food.y));
            placeFood();
        }

        //snake Body
        for(int i = snakeBody.size()-1; i >= 0; i--)
        {
            Panel snakePart = snakeBody.get(i);
            if(i == 0)
            {
                snakePart.x = SnakeMouth.x;
                snakePart.y = SnakeMouth.y;

            }
            else
            {
                Panel prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Mouth
        SnakeMouth.x += velocityX;
        SnakeMouth.y += velocityY;

        //gameOver conditions
        for(int i = 0; i < snakeBody.size(); i++)
        {
            Panel snakePart = snakeBody.get(i);

            //collision 
            if (collision(SnakeMouth, snakePart))
            {
                gameOver = true;
            }
        }

        if(SnakeMouth.x*panelSize < 0 || SnakeMouth.x*panelSize > boardWidth ||
           SnakeMouth.y*panelSize < 0 || SnakeMouth.y * panelSize > boardWidth)
           {
            gameOver = true;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {

        move();
        repaint();
        if (gameOver) 
        {
            gameLoop.stop();
        }
    
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1)
        {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1)
        {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1)
        {
            velocityX = -1;
            velocityY = 0;

        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1)
        {
            velocityX = 1;
            velocityY = 0;
        }
    }
}
