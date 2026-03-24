package ch.irix.jdk25test.devmeeting.virtualthreads;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadDemo extends JFrame {

    void main() {
//        SwingUtilities.invokeLater(DemoVirtualThread::new);
    }

    public VirtualThreadDemo() {
        Rectangle bounds = new Rectangle(0, 0, 600, 400);
        setupUI(bounds);
        List<Ant> ants = createAnts(50000, bounds);

        //old way
//        ExecutorService service = Executors.newCachedThreadPool();
//        for (Ant ant : ants) {
//            service.submit(ant);
//        }

        //new: virtual Threads
        for (Ant ant : ants) {
            Thread.ofVirtual().start(ant);
        }
        showUI(ants);
    }

    //------------------------


    private List<Ant> createAnts(int size, Rectangle bounds) {
        List<Ant> ants = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Point start = randomPoint(bounds);
            ants.add(new Ant(start, bounds));
        }
        return ants;
    }

    private Point randomPoint(Rectangle bounds) {
        return new Point(
                Math.random() * bounds.width,
                Math.random() * bounds.height
        );
    }

    private void setupUI(Rectangle bounds) {
        setTitle("Ant-Simulation");
        setSize(bounds.width, bounds.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void showUI(List<Ant> ants) {
        AntPanel panel = new AntPanel(ants);
        add(panel);
        setVisible(true);
    }
}
