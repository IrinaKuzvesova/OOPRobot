package gui.dialogs;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class FrameDialog extends InternalFrameAdapter {
    private JFrame owner;
    private JInternalFrame frame;

    public FrameDialog(JFrame owner, JInternalFrame frame) {
        this.owner = owner;
        this.frame = frame;
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent internalFrameEvent) {
        Dialog dialog = new Dialog(owner);
        dialog.setLocale(owner.getLocale());
        dialog.setVisible(true);
        JInternalFrame frame = internalFrameEvent.getInternalFrame();
        if (dialog.is_closed()) {
            internalFrameEvent.getInternalFrame().setVisible(false);
            frame.dispose();
        }
    }
}