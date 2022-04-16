package gui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Dialog extends JDialog {
    private boolean closed = false;

    public Dialog(JFrame owner) {
        super(owner, "Подтверждение", true);
        JLabel label = new JLabel("Уверены, что хотите выйте?");
        label.setLocation(200, 200);
        add(label);

        JButton yes = new JButton("ДА");
        yes.addActionListener(event -> {
            setVisible(false);
            closed = true;
        });

        JButton no = new JButton("HET");
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