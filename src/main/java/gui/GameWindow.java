package gui;

import gui.serialize.Saver;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final String name = "game";
    private final GameVisualizer m_visualizer;
    private static final ResourceBundle rb = ResourceBundle.getBundle(
            "gameWindow",
            Locale.getDefault()
    );

    public GameWindow() 
    {
        super(rb.getString("title"), true, true, true, true);
        m_visualizer = new GameVisualizer(this);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        new WindowCreator(this, name).setSizes();
        show();
    }

    @Override
    public void dispose() {
        Rectangle bounds = this.getBounds();
        Saver size = new Saver(bounds.x, bounds.y, bounds.width, bounds.height, this.isIcon, this.isSelected);
        size.save(name);
        super.dispose();
    }
}
