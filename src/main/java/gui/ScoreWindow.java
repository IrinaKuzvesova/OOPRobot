package gui;
import gui.serialize.Saver;

import javax.swing.*;
import java.awt.*;

public class ScoreWindow extends JInternalFrame {
    private final String name = "Score";
    private final ScorePanel scorePanel = new ScorePanel();

    public ScoreWindow() {
        super("Очки", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scorePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        // загружает сохранение
        new WindowCreator(this, name).setSizes();
        show();
    }

    @Override
    public void dispose() {
        Rectangle bounds = this.getBounds();
        Saver saver = new Saver(bounds.x, bounds.y, bounds.width, bounds.height, this.isIcon, this.isSelected);
        saver.save(name);
        super.dispose();
    }
}