package gui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

class Dialog extends JDialog {
    private boolean closed = false;
    private static final ResourceBundle rb = ResourceBundle.getBundle(
            "dialog",
            Locale.getDefault()
    );

    public Dialog(JFrame owner) {
        super(owner, rb.getString("title"), true);
        JLabel label = new JLabel(rb.getString("confText"));
        label.setLocation(200, 200);
        add(label);

        JButton yes = new JButton(rb.getString("yesButton"));
        yes.addActionListener(event -> {
            setVisible(false);
            closed = true;
        });

        JButton no = new JButton(rb.getString("noButton"));
        no.addActionListener(event -> setVisible(false));

        JPanel panel = new JPanel();
        panel.add(yes);
        panel.add(no);
        add(panel, BorderLayout.SOUTH);
        setSize(260, 160);
        setLocation(400, 400);
        setResizable(false);
    }

    public boolean is_closed() {
        return closed;
    }
}