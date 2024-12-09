package org.com.snake_game;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class Snake_Game extends JPanel implements ActionListener {

	 private final int WIDTH = 600;
	    private final int HEIGHT = 400;
	    private final int DOT_SIZE = 10;
	    private final int ALL_DOTS = 1600;
	    private final int RAND_POS = 29;
	    private final int DELAY = 140;
	    
	    private final int[] x = new int[ALL_DOTS];
	    private final int[] y = new int[ALL_DOTS];

	    private int dots;
	    private int food_x;
	    private int food_y;
	    private boolean left = false, right = true, up = false, down = false;
	    private boolean inGame = true;

	    private Timer timer;
	    private Random random;

	    public Snake_Game() {
	        random = new Random();
	        setBackground(Color.black);
	        setFocusable(true);

	        addKeyListener(new KeyAdapter() {
	            public void keyPressed(KeyEvent e) {
	                int key = e.getKeyCode();

	                if ((key == KeyEvent.VK_LEFT) && (!right)) {
	                    left = true;
	                    up = false;
	                    down = false;
	                }

	                if ((key == KeyEvent.VK_RIGHT) && (!left)) {
	                    right = true;
	                    up = false;
	                    down = false;
	                }

	                if ((key == KeyEvent.VK_UP) && (!down)) {
	                    up = true;
	                    right = false;
	                    left = false;
	                }

	                if ((key == KeyEvent.VK_DOWN) && (!up)) {
	                    down = true;
	                    right = false;
	                    left = false;
	                }
	            }
	        });

	        startGame();
	    }

	    public void startGame() {
	        dots = 3;

	        for (int z = 0; z < dots; z++) {
	            x[z] = 50 - z * DOT_SIZE;
	            y[z] = 50;
	        }

	        placeFood();

	        timer = new Timer(DELAY, this);
	        timer.start();
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        if (inGame) {
	            move();
	            checkCollision();
	            checkFood();
	            repaint();
	        }
	    }

	    public void move() {
	        for (int z = dots; z > 0; z--) {
	            x[z] = x[(z - 1)];
	            y[z] = y[(z - 1)];
	        }

	        if (left) x[0] -= DOT_SIZE;
	        if (right) x[0] += DOT_SIZE;
	        if (up) y[0] -= DOT_SIZE;
	        if (down) y[0] += DOT_SIZE;
	    }

	    public void checkCollision() {
	        for (int z = dots; z > 0; z--) {
	            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z])) {
	                inGame = false;
	            }
	        }

	        if (x[0] < 0) inGame = false;
	        if (x[0] >= WIDTH) inGame = false;
	        if (y[0] < 0) inGame = false;
	        if (y[0] >= HEIGHT) inGame = false;
	    }

	    public void checkFood() {
	        if ((x[0] == food_x) && (y[0] == food_y)) {
	            dots++;
	            placeFood();
	        }
	    }

	    public void placeFood() {
	        food_x = random.nextInt(RAND_POS) * DOT_SIZE;
	        food_y = random.nextInt(RAND_POS) * DOT_SIZE;
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        doDrawing(g);
	    }

	    public void doDrawing(Graphics g) {
	        if (inGame) {
	            g.setColor(Color.green);
	            g.fillRect(x[0], y[0], DOT_SIZE, DOT_SIZE);

	            for (int z = 1; z < dots; z++) {
	                if (z % 2 == 0) {
	                    g.setColor(Color.yellow);
	                } else {
	                    g.setColor(Color.orange);
	                }
	                g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
	            }

	            g.setColor(Color.red);
	            g.fillRect(food_x, food_y, DOT_SIZE, DOT_SIZE);

	            Toolkit.getDefaultToolkit().sync();

	        } else {
	            gameOver(g);
	        }
	    }

	    public void gameOver(Graphics g) {
	        String msg = "Game Over";
	        Font small = new Font("Helvetica", Font.BOLD, 14);
	        FontMetrics metr = getFontMetrics(small);

	        g.setColor(Color.white);
	        g.setFont(small);
	        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
	    }

	    public static void main(String[] args) {
	        JFrame frame = new JFrame();
	        Snake_Game game = new Snake_Game();

	        frame.add(game);
	        frame.setTitle("Snake Game");
	        frame.setSize(game.WIDTH, game.HEIGHT);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }
	
}
