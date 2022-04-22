package gui;

import gui.serialize.Saver;

import java.awt.*;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.util.Locale;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrame
{
    private final String name;
    private final GameVisualizer gameVisualizer;
    private final int id;

    private static final ResourceBundle rb = ResourceBundle.getBundle(
            "gameWindow",
            //Locale.getDefault()
            new Locale("en", "US")
    );

    public GameWindow(int id) {
        super(rb.getString("title"), true, true, true, true);
        this.name = "game" + id;
        this.id = id;
        gameVisualizer = new GameVisualizer(this, id);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameVisualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        // загружает сохранения
        new WindowCreator(this, name).setSizes();
        setResizable(false);
        show();
    }

    @Override
    public void dispose() {
        Rectangle bounds = this.getBounds();
        DataTransmitter.killRobot(id);
        gameVisualizer.dispose();
        Saver size = new Saver(bounds.x, bounds.y, bounds.width, bounds.height, this.isIcon, this.isSelected);
        size.save(name);
        super.dispose();
    }
}
