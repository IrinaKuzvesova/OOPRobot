package gui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
// TO DO: по нажатию кнопки понять, нужно ли загружать сохранение
// при закрытии окна не загружать сохранение




public class UploadDialog extends JDialog {
    private boolean closed = false;

    private static final ResourceBundle rb = ResourceBundle.getBundle(
            "dialog",
            //Locale.getDefault()
            new Locale("en", "US")
    );

    public UploadDialog(JFrame owner) {
        // TO DO: добавить локализацию
        super(owner, "Есть доступное сохранение", true);
        JLabel label = new JLabel("Загрузить сохранение?");label.setLocation(200, 200);
        add(label);

        JButton yes = new JButton(rb.getString("yesButton"));
//        yes.addActionListener(event -> {
//            setVisible(false);
//            closed = true;
//        });

        JButton no = new JButton(rb.getString("noButton"));
//        no.addActionListener(event -> setVisible(false));

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
