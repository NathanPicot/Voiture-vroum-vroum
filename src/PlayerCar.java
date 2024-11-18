import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
// import javax.sound.sampled.AudioInputStream;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;
// import javax.sound.sampled.LineUnavailableException;
// import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.*;



public class PlayerCar {
    private int x, y; // Position de la voiture
    private int speed = 30; // Vitesse de déplacement
    private Image playerCarImage;
    
    /**
     * Definie notre voiture
     * @param x
     * @param y
     */
    public PlayerCar(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Fait le deplacement sur la droite
     */
    public void moveLeft() {
        if (x > 50) x -= speed;
        drift();
    }

    /**
     * Fait le deplacement sur la gauche
     */
    public void moveRight() {
        if (x < 700) x += speed;
        drift();
        
    }

    public void drift() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("./song/tourne.wav"));
            // Obtenir une ressource de clip audio
            Clip clip = AudioSystem.getClip();
            // Ouvrir le clip audio et charger les échantillons à partir du flux audio
            clip.open(audioIn);
            clip.setFramePosition(0); // Rejoue depuis le début
            clip.start();
    
            // Configurer et démarrer un timer pour arrêter le son après 1 seconde
            javax.swing.Timer timer = new javax.swing.Timer(300, e -> {
                clip.stop();
                clip.close(); // Libère les ressources
            });
            timer.setRepeats(false); // Exécuter une seule fois
            timer.start(); // Démarrer le timer
    
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Affiche notre voiture
     * @param g
     */
    public void draw(Graphics g) {
        try {
            playerCarImage = ImageIO.read(new File("./img/voiture-bleu-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } 
        if (playerCarImage != null) {
            g.drawImage(playerCarImage, x, y, 50, 100, null); // Dessiner l'image (redimensionnée)
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, 50, 100); // Si l'image n'est pas disponible, dessiner un rectangle par défaut
        }
        // g.setColor(Color.RED);
        // g.fillRect(x, y, 50, 100); // Représentation simplifiée d'une voiture
    }

    /**
     * Definit la hitbox de notre voiture pour géré les colisions
     * @return
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 100); // Rectangle pour les collisions
    }
}
