import java.awt.*;

public class Road {
    private int[] linesY = {0, 200, 400, 600}; // Position des lignes blanches
    private int speed = 10;

    public void update() {
        for (int i = 0; i < linesY.length; i++) {
            linesY[i] += speed;
            if (linesY[i] > 600) linesY[i] = 0; // Réinitialiser la position
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 800, 600); // Route
        g.setColor(Color.WHITE);
        for (int y : linesY) {
            g.fillRect(390, y, 20, 100); // Lignes centrales
        }
    }
}
