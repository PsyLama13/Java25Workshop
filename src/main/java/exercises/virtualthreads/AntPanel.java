package exercises.virtualthreads;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AntPanel extends JPanel {
    private final List<Ant> ants;

    public AntPanel(List<Ant> ants) {
        this.ants = ants;
        // Timer für Animation
        Timer timer = new Timer(30, e -> {
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        setBackground(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;
        for (Ant ant : ants) {
            g2d.setColor(new Color(ant.rgbColor, false));
            g2d.drawLine(
                    (int)ant.from.x(),
                    (int)ant.from.y(),
                    (int)ant.to.x(),
                    (int)ant.to.y()
            );
        }
    }
}
