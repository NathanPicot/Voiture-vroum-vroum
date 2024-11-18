import java.awt.*;

public class EnemyCar {
    private int x, y;
    private int speed;
    private int speedAcc = 0;

    public EnemyCar(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update(int score) {
        y += speed; // Les ennemis descendent
        
        if (y > 600) { // Réapparaît en haut de l'écran
        	this.speedAcc += 1;
        	this.speed += this.speedAcc/5;
        	System.out.println("Speed : " + this.speedAcc);
            y = -100;
            x = 10 + (int) (Math.random() * 700); // Nouvelle position horizontale aléatoire
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE); // Couleur de l'ennemi
        g.fillRect(x, y, 50, 100); // Rectangle représentant l'ennemi
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 100); // Rectangle pour les collisions
    }
}
