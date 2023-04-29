import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements KeyListener, Runnable {
    private Thread gameThread;
    private boolean isRunning;
    private boolean isFiring;
    private int shipX = 50;
    private int shipY = 250;
    private int bulletX;
    private int bulletY;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setTitle("Simple Game");
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Рисуем корабль
        g2d.fillRect(shipX, shipY, 50, 20);

        // Рисуем пулю
        if (isFiring) {
            g2d.fillRect(bulletX, bulletY, 5, 5);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isFiring = true;
            bulletX = shipX + 20;
            bulletY = shipY;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void run() {
        while (isRunning) {

            shipX += 2;


            if (shipX > getWidth()) {
                shipX = -50;
            }


            if (isFiring) {
                bulletY -= 5;
            }


            if (bulletY < 0) {
                isFiring = false;
            }


            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.setVisible(true);
        game.start();
    }
}


