import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener, Runnable {
    private Thread gameThread;
    private boolean running;
    private boolean isGameOver; // Indique si le jeu est terminé
    private PlayerCar playerCar;
    private Road road;
    private List<EnemyCar> enemyCars;
    private int score;
    public GamePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);

        initGame(); // Initialisation du jeu
    }

    // Initialise ou réinitialise le jeu
    public void initGame() {
        playerCar = new PlayerCar(350, 500);
        road = new Road();
        score = 0;
        
        // Initialisation des ennemis
        enemyCars = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int x = 100 + (int) (Math.random() * 600);
            int y = -100 * i;
            int speed = 5 + (int) (Math.random() * 5);
            enemyCars.add(new EnemyCar(x, y, speed));
        }

        isGameOver = false;
        running = true;
    }

    public void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) {
            if (!isGameOver) { // Ne met à jour que si le jeu n'est pas terminé
                updateGame();
                repaint();
            }
            try {
                Thread.sleep(16); // 60 FPS (approx.)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        road.update();
        playerCar.update();
        score ++;
        // Mise à jour des ennemis
        for (EnemyCar enemy : enemyCars) {
            enemy.update(score);
        }

        // Vérification des collisions
        checkCollisions();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        road.draw(g);
        playerCar.draw(g);

        for (EnemyCar enemy : enemyCars) {
            enemy.draw(g);
        }

        if (isGameOver) {
            drawGameOver(g);
        }
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150)); // Fond semi-transparent
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2 - 50);

        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Score : " + score, getWidth() / 2 - 100, getHeight() / 2 + 20);
        
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Press R to Restart", getWidth() / 2 - 100, getHeight() / 2 + 80);
    }

    private void checkCollisions() {
        for (EnemyCar enemy : enemyCars) {
            if (playerCar.getBounds().intersects(enemy.getBounds())) {
                System.out.println("Collision! Game Over! \n score :" + score);
                isGameOver = true; // Le jeu est terminé
                break;
            }
        }
    }

    private void drawScore(Graphics g){

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Score : " + score, getWidth() / 2 - 150, getHeight() / 2 - 50);

        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Score : " + score, getWidth() / 2 - 100, getHeight() / 2 + 20);
        
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Press R to Restart", getWidth() / 2 - 100, getHeight() / 2 + 80);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) playerCar.moveLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) playerCar.moveRight();
        } else {
            // Redémarre le jeu quand "R" est pressé
            if (e.getKeyCode() == KeyEvent.VK_R) {
                initGame(); // Réinitialise les variables
                repaint(); // Redessine l'écran
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
