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
    private int food1X, food1Y;
    private int food2X, food2Y;
    private int score = 0;

    private char direction = 'R'; // R=右 L=左 U=上 D=下
    private boolean running = false;
    private boolean gameOver = false;
    private boolean choosingDifficulty = true;
    private int difficulty = 0; // 0=未选择
    private int speed = 160;
    private int initialBodyParts = 2;
    private int growAmount = 1;
    private String difficultyName = "";
    private Timer timer;
    private final Random random = new Random();

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }

    private void startGame() {
        bodyParts = initialBodyParts;
        score = 0;
        direction = 'R';
        gameOver = false;
        choosingDifficulty = false;

        for (int i = 0; i < bodyParts; i++) {
            x[i] = 150 - i * TILE_SIZE;
            y[i] = 150;
        }

        spawnFood1();
        spawnFood2();
        running = true;

        if (timer != null) timer.stop();
        timer = new Timer(speed, this);
        timer.start();
    }

    private void setDifficulty(int level) {
        switch (level) {
            case 1: // 地狱
                speed = 100;
                initialBodyParts = 3;
                growAmount = 2;
                difficultyName = "地狱";
                break;
            case 2: // 困难
                speed = 120;
                initialBodyParts = 2;
                growAmount = 1;
                difficultyName = "困难";
                break;
            case 3: // 中等
                speed = 160;
                initialBodyParts = 2;
                growAmount = 1;
                difficultyName = "中等";
                break;
            case 4: // 简单
                speed = 200;
                initialBodyParts = 2;
                growAmount = 1;
                difficultyName = "简单";
                break;
        }
        difficulty = level;
        startGame();
    }

    private void spawnFood1() {
        food1X = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        food1Y = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
    }

    private void spawnFood2() {
        food2X = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        food2Y = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (choosingDifficulty) {
            // 难度选择界面
            g.setColor(new Color(0, 200, 0));
            g.setFont(new Font("微软雅黑", Font.BOLD, 50));
            String title = "贪 吃 蛇";
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(title, (WIDTH - metrics.stringWidth(title)) / 2, 120);

            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 24));
            metrics = getFontMetrics(g.getFont());
            String sub = "选择难度";
            g.drawString(sub, (WIDTH - metrics.stringWidth(sub)) / 2, 180);

            g.setFont(new Font("微软雅黑", Font.PLAIN, 22));
            metrics = getFontMetrics(g.getFont());

            g.setColor(new Color(255, 50, 50));
            String d1 = "1 - 地狱  ";
            g.drawString(d1, (WIDTH - metrics.stringWidth(d1)) / 2, 260);

            g.setColor(new Color(255, 150, 50));
            String d2 = "2 - 困难  ";
            g.drawString(d2, (WIDTH - metrics.stringWidth(d2)) / 2, 310);

            g.setColor(new Color(255, 255, 50));
            String d3 = "3 - 中等  ";
            g.drawString(d3, (WIDTH - metrics.stringWidth(d3)) / 2, 360);

            g.setColor(new Color(50, 255, 50));
            String d4 = "4 - 简单  ";
            g.drawString(d4, (WIDTH - metrics.stringWidth(d4)) / 2, 410);

            g.setColor(Color.GRAY);
            g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
            metrics = getFontMetrics(g.getFont());
            String hint = "按数字键 1-4 选择";
            g.drawString(hint, (WIDTH - metrics.stringWidth(hint)) / 2, 480);

        } else if (running) {
            // 画食物1（红色）
            g.setColor(Color.RED);
            g.fillOval(food1X, food1Y, TILE_SIZE, TILE_SIZE);

            // 画食物2（橙色）
            g.setColor(Color.ORANGE);
            g.fillOval(food2X, food2Y, TILE_SIZE, TILE_SIZE);

            // 画蛇
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 200, 0));
                } else {
                    g.setColor(new Color(0, 150, 0));
                }
                g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
            }

            // 画分数和难度
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 18));
            g.drawString("分数: " + score + "  难度: " + difficultyName, 10, 25);

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
            String restartText = "空格键 重新开始 / ESC 选择难度";
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
        if (x[0] == food1X && y[0] == food1Y) {
            bodyParts += growAmount;
            score++;
            spawnFood1();
        }
        if (x[0] == food2X && y[0] == food2Y) {
            bodyParts += growAmount;
            score++;
            spawnFood2();
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
        int key = e.getKeyCode();

        if (choosingDifficulty) {
            if (key == KeyEvent.VK_1 || key == KeyEvent.VK_NUMPAD1) setDifficulty(1);
            if (key == KeyEvent.VK_2 || key == KeyEvent.VK_NUMPAD2) setDifficulty(2);
            if (key == KeyEvent.VK_3 || key == KeyEvent.VK_NUMPAD3) setDifficulty(3);
            if (key == KeyEvent.VK_4 || key == KeyEvent.VK_NUMPAD4) setDifficulty(4);
            return;
        }

        switch (key) {
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_SPACE:
                if (gameOver) startGame();
                break;
            case KeyEvent.VK_ESCAPE:
                if (gameOver) {
                    choosingDifficulty = true;
                    repaint();
                }
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
