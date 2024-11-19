import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyCar {
    private int x, y;
    private int speed;
    private int speedAcc = 0;
    private int id;
    private Image enemyCarImage;
    String osName = System.getProperty("os.name").toLowerCase();
    public String imgPath;

    /**
     * Definit les voiture enemy
     * @param x
     * @param y
     * @param speed
     * @param id
     */
    public EnemyCar(int x, int y, int speed, int id) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.id = id;
    }

    /**
     * Met a jour le positionnement de la voiture
     * @param score
     */
    public void update(int score) {
        y += speed; // Les ennemis descendent
        
        if (y > 600) { // Réapparaît en haut de l'écran
        	this.speedAcc += 1;
        	this.speed += this.speedAcc/10;
            y = -100;
            x = 10 + (int) (Math.random() * 700); // Nouvelle position horizontale aléatoire
        }
    }

    /**
     * Dessine la voiture
     * @param g
     */
    public void draw(Graphics g) {
        if (osName.contains("win")) {
            imgPath = "Voiture-Vroum-Vroum\\img\\";
        } else {
            imgPath = "./img/";
        }
        try {
            enemyCarImage = ImageIO.read(new File(imgPath +"voiture-mechant.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        if (enemyCarImage != null) {
            g.drawImage(enemyCarImage, x, y, 50, 100, null); // Dessiner l'image (redimensionnée)
        } else {
            g.setColor(Color.BLUE); // Couleur de l'ennemi
            g.fillRect(x, y, 50, 100); // Rectangle représentant l'ennemi
        }
    }

    /**
     * Dessine la hitbox pour la collision des voiture
     * @return
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 100); // Rectangle pour les collisions
    }
}
