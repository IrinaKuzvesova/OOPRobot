package gui.dialogs;

import gui.MainApplicationFrame;
import gui.dialogs.Dialog;
import org.w3c.dom.events.Event;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
            owner.getGameWindow().dispose();
            System.exit(0);
        }
    }
}
