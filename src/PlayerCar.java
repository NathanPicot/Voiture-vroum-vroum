import java.awt.*;

public class PlayerCar {
    private int x, y; // Position de la voiture
    private int speed = 30; // Vitesse de déplacement

    public PlayerCar(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        if (x > 50) x -= speed;
    }

    public void moveRight() {
        if (x < 700) x += speed;
    }

    public void update() {
        // Logique future si nécessaire (boost, freinage...)
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 50, 100); // Représentation simplifiée d'une voiture
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 100); // Rectangle pour les collisions
    }
}
