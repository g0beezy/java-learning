import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private static final int TILE_SIZE = 25;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int TOTAL_TILES = (WIDTH * HEIGHT) / (TILE_SIZE * TILE_SIZE);

    private final int[] x = new int[TOTAL_TILES];
    private final int[] y = new int[TOTAL_TILES];

    private int bodyParts = 3;
    private int foodX, foodY;
    private int score = 0;

    private char direction = 'R'; // R=右 L=左 U=上 D=下
    private boolean running = false;
    private boolean gameOver = false;
    private Timer timer;
    private final Random random = new Random();

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        startGame();
    }

    private void startGame() {
        bodyParts = 3;
        score = 0;
        direction = 'R';
        gameOver = false;

        for (int i = 0; i < bodyParts; i++) {
            x[i] = 150 - i * TILE_SIZE;
            y[i] = 150;
        }

        spawnFood();
        running = true;

        if (timer != null) timer.stop();
        timer = new Timer(120, this);
        timer.start();
    }

    private void spawnFood() {
        foodX = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        foodY = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            // 画食物
            g.setColor(Color.RED);
            g.fillOval(foodX, foodY, TILE_SIZE, TILE_SIZE);

            // 画蛇
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 200, 0));
                } else {
                    g.setColor(new Color(0, 150, 0));
                }
                g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
            }

            // 画分数
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g.drawString("分数: " + score, 10, 25);

        } else if (gameOver) {
            // 游戏结束画面
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 50));
            String text = "游戏结束!";
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(text, (WIDTH - metrics.stringWidth(text)) / 2, HEIGHT / 2 - 30);

            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 28));
            String scoreText = "最终分数: " + score;
            metrics = getFontMetrics(g.getFont());
            g.drawString(scoreText, (WIDTH - metrics.stringWidth(scoreText)) / 2, HEIGHT / 2 + 20);

            g.setFont(new Font("微软雅黑", Font.PLAIN, 20));
            String restartText = "按 空格键 重新开始";
            metrics = getFontMetrics(g.getFont());
            g.drawString(restartText, (WIDTH - metrics.stringWidth(restartText)) / 2, HEIGHT / 2 + 60);
        }
    }

    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U': y[0] -= TILE_SIZE; break;
            case 'D': y[0] += TILE_SIZE; break;
            case 'L': x[0] -= TILE_SIZE; break;
            case 'R': x[0] += TILE_SIZE; break;
        }
    }

    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            bodyParts++;
            score++;
            spawnFood();
        }
    }

    private void checkCollisions() {
        // 撞自己
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }

        // 撞墙
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }

        if (!running) {
            gameOver = true;
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (direction != 'U') direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_SPACE:
                if (gameOver) startGame();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("贪吃蛇");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
