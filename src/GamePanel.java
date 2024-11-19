import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener, Runnable {
    private Thread gameThread;
    private boolean running;
    private boolean isGameOver; // Indique si le jeu est terminé
    private boolean isInitGame; // Indique si le jeux vient de ce reinitialisé
    private PlayerCar playerCar;
    private Road road;
    private List<EnemyCar> enemyCars;
    private int score;
    public Clip clip;
    public String songPath;
    public GamePanel() {
        this.setFocusable(true);
        this.addKeyListener(this);

        initGame(); // Initialisation du jeu
    }
    String osName = System.getProperty("os.name").toLowerCase();
    


    // Initialise ou réinitialise le jeu
    public void initGame() {
        if (osName.contains("win")) {
            songPath = "Voiture-Vroum-Vroum\\song\\";
        } else {
            songPath = "./song/";
        }
        playerCar = new PlayerCar(350, 420);
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
        isInitGame = true;
    }
    /**
     * Lance le jeux
     */
    public void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Actualise le jeux
     */
    @Override
    public void run() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(
                    songPath + "vitesse.wav"));
            // Get a sound clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.setFramePosition(0); 
            clip.start();
            clip.loop(ABORT);
            
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        while (running) {
            if (!isGameOver) { // Ne met à jour que si le jeu n'est pas terminé
                updateGame();
                repaint();

            }else{
                clip.stop();

            }
            if(isInitGame){
                clip.setFramePosition(0);
                clip.start();
                clip.loop(ABORT);
                isInitGame=false;
            }
            try {
                Thread.sleep(16); // 60 FPS (approx.)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Met a jours les données du jeux
     */
    private void updateGame() {
        road.update();
        score ++;
        // Mise à jour des ennemis
        for (EnemyCar enemy : enemyCars) {
            enemy.update(score);
        }

        // Vérification des collisions
        checkCollisions();
    }

    /**
     * Met a jour les different affichages du jeux
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        road.draw(g);
        playerCar.draw(g);

        for (EnemyCar enemy : enemyCars) {
            enemy.draw(g);
        }
        drawScore(g);
        if (isGameOver) {
            drawGameOver(g);
        }
    }
    /**
     * Affiche l'écran de Game over lors d'une colision avec un autre voiture
     * @param g
     */
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
                try {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(
                            songPath + "accident.wav"));
                    // Get a sound clip resource.
                    Clip clipAccident = AudioSystem.getClip();
                    // Open audio clip and load samples from the audio input stream.
                    clipAccident.open(audioIn);
                    clipAccident.setFramePosition(0); 
                    clipAccident.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    /**
     * Affiche le score en temps réel sur l'écran
     * @param g 
     */
    private void drawScore(Graphics g){

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Score : " + score, 10 , 50);

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
                clip.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
