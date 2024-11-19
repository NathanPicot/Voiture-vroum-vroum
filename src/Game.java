import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("OutRun Style Game");
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Taille de la fenêtre
        frame.setResizable(false);
        frame.setVisible(true);
        
        panel.startGame(); // Démarrage de la boucle du jeu
    }
}
