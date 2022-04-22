package gui.dialogs;

import gui.MainApplicationFrame;
import gui.WindowThread;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CloseDialog extends WindowAdapter{
    private MainApplicationFrame owner;

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
        Dialog dialog = new Dialog(owner);
        dialog.setVisible(true);
        if (dialog.is_closed()) {
            owner.getLogWindow().dispose();
            owner.getScoreWindow().dispose();
            for (WindowThread windowThread: owner.getWindowThreads()) {
                windowThread.interrupt();
            }
            System.exit(0);
        }
    }
}
