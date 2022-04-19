package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.dialogs.CloseDialog;
import gui.dialogs.FrameDialog;
import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private GameWindow gameWindow;
    private LogWindow logWindow;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final ResourceBundle rb = ResourceBundle.getBundle(
            "mainApplicationFrame",
            Locale.getDefault()
    );

    public MainApplicationFrame() {
        int inset = 50;
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);

        logWindow = createLogWindow();
        addWindow(logWindow);

        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameWindow.addInternalFrameListener(new FrameDialog(this, gameWindow));
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        setMinimumSize(logWindow.getSize());
        logWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        logWindow.addInternalFrameListener(new FrameDialog(this, logWindow));
        Logger.debug(rb.getString("protocolWorks"));
        return logWindow;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public  LogWindow getLogWindow() {
        return logWindow;
    }


    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = makeMenu(rb.getString(
                "displayMode"),
                KeyEvent.VK_V, rb.getString("appDisplayModeControl")
        );
        lookAndFeelMenu.add(makeMenuItem(rb.getString("systemScheme"),        (event) -> {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    this.invalidate();
                }));
        lookAndFeelMenu.add(makeMenuItem(rb.getString("universalScheme"),       (event) -> {
                    setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    this.invalidate();
                }));

        JMenu testMenu = makeMenu(rb.getString("tests"), KeyEvent.VK_T, rb.getString("testCommands"));
        testMenu.add(makeMenuItem(rb.getString("logMessages"),
                (event) -> Logger.debug(rb.getString("newLine"))));

        JMenu exitMenu = makeMenu(rb.getString("exit"), KeyEvent.VK_V, rb.getString("exitGame"));

        exitMenu.add(makeMenuItem(rb.getString("exit"),
                (event) -> {
                    CloseDialog closeDialog = new CloseDialog(this);
                    closeDialog.onPushedCloseButton(event);
                }
        ));

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenu);
        return menuBar;
    }

    private JMenuItem makeMenuItem(String text, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text, KeyEvent.VK_S);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    private JMenu makeMenu(String name, int event, String description) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(event);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
