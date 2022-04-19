package gui;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import gui.serialize.Saver;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    private String name = "log";

    private static final ResourceBundle rb = ResourceBundle.getBundle(
            "logWindow",
            //Locale.getDefault()
            new Locale("en", "US")
    );

    public LogWindow(LogWindowSource logSource) {

        super(rb.getString("title"), true, true, true, true); m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        updateLogContent();
        new WindowCreator(this, name).setSizes();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void unregister() {
        m_logSource.unregisterListener(this);
    }

    @Override
    public void dispose() {
        Rectangle bounds = this.getBounds();
        Saver size = new Saver(bounds.x, bounds.y, bounds.width, bounds.height, this.isIcon, this.isSelected);
        size.save(name);
        unregister();
        super.dispose();
    }
}
