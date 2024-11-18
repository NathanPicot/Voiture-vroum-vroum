import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
    }

    /**
     * Fait le deplacement sur la gauche
     */
    public void moveRight() {
        if (x < 700) x += speed;
    }

    /**
     * Affiche notre voiture
     * @param g
     */
    public void draw(Graphics g) {
        try {
            playerCarImage = ImageIO.read(new File("./img/voiture-gentille.png"));
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
