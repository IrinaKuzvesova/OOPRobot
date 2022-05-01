package gui;
import gui.serialize.Saver;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScoreWindow extends JInternalFrame {
    private final String name = "Score";
    private final ScorePanel scorePanel = new ScorePanel();
    private static ResourceBundle rb = ResourceBundle.getBundle(
            "scoreWindow",
            Locale.getDefault()
    );

    //rb.getString("title")

    public ScoreWindow() {
        super(rb.getString("score"), true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scorePanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        show();
    }

    @Override
    public void dispose() {
        Rectangle bounds = this.getBounds();
        Saver saver = new Saver(bounds.x, bounds.y, bounds.width, bounds.height, this.isIcon, this.isSelected);
        saver.save(name);
        super.dispose();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setLocale(Locale locale){
        rb = ResourceBundle.getBundle("scoreWindow", locale);
        this.setTitle(rb.getString("score"));
        scorePanel.setLocale(locale);
    }
}