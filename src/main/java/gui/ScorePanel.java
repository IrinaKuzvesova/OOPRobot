package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ScorePanel extends JPanel{
    private final Robot[] robots = new Robot[4];
    private final Timer timer = initTimer();
    private static ResourceBundle rb = ResourceBundle.getBundle(
            "scorePanel",
            Locale.getDefault()
    );

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public ScorePanel() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 10);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawString(rb.getString("robot"), 20, 20);
        g2d.drawString(rb.getString("score"), 90, 20);
        for (int i = 0; i < DataTransmitter.numberOfRobot(); i++) {
            try {
                g2d.setPaint(DataTransmitter.getRobot(i).getColor());
                g2d.drawString(String.valueOf(i), 20, (i + 2) * 20);
                g2d.drawString(String.valueOf(DataTransmitter.getRobot(i).getCurrentScore()), 100, (i + 2) * 20);
            }
            catch (Exception e) {
                g2d.setPaint(Color.BLACK);
                g2d.drawString(String.valueOf(i), 20, (i + 2) * 20);
                g2d.drawString("0", 100, (i + 2) * 20);
            }
        }
    }

    public void setLocale(Locale locale){
        rb = ResourceBundle.getBundle("scorePanel", locale);
    }
}