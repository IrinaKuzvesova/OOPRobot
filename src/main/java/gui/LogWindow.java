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
import log.Logger;

public class LogWindow extends JInternalFrame implements LogChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    public String name = "log";

    private static ResourceBundle rb = ResourceBundle.getBundle(
            "logWindow",
            Locale.getDefault()
            //new Locale("en", "US")
    );

    public LogWindow(LogWindowSource logSource) {
        super(rb.getString("title"), true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        updateLogContent();
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
        unregister();
        super.dispose();
    }

    public void setLocale(Locale locale){
        rb = ResourceBundle.getBundle("logWindow", locale);
        this.setTitle(rb.getString("title"));
        Logger.debug(rb.getString("protocolWorks"));
    }
}
