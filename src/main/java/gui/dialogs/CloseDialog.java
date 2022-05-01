package gui.dialogs;

import gui.MainApplicationFrame;
import gui.WindowThread;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class CloseDialog extends WindowAdapter{
    private MainApplicationFrame owner;
    Dialog dialog;

    public CloseDialog(MainApplicationFrame  owner){
        this.owner = owner;
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        close();
    }
    public void onPushedCloseButton(ActionEvent event) {
        close();
    }

    private void close() {
        dialog = new Dialog(owner);
        dialog.setLocale(owner.getLocale());
        dialog.setVisible(true);
        if (dialog.is_closed()) {
            owner.getLogWindow().dispose();
            owner.getScoreWindow().dispose();
            owner.saveWindows();
            for (WindowThread windowThread: owner.getWindowThreads()) {
                windowThread.interrupt();
            }
            System.exit(0);
        }
    }
    public void setLocale(Locale locale){
        dialog.setLocale(locale);
    }
}
