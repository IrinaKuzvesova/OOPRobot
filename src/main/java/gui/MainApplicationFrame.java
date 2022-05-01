package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.dialogs.CloseDialog;
import gui.dialogs.Dialog;
import gui.dialogs.FrameDialog;
import gui.dialogs.UploadDialog;
import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private GameWindow[] gameWindows = new GameWindow[4];
    private WindowThread[] windowThreads = new WindowThread[4];
    private LogWindow logWindow;
    private ScoreWindow scoreWindow;
    public boolean isLoad;
    private Locale mainLocale = Locale.getDefault();
    private HashMap<String, JMenu> allMenu = new HashMap<>();
    private HashMap<String, JMenuItem> allMenuItem = new HashMap<>();

    private ResourceBundle rb = ResourceBundle.getBundle(
            "mainApplicationFrame", mainLocale
    );

    public MainApplicationFrame() {
        UploadDialog dialog = new UploadDialog(this);
        dialog.setVisible(true);

        int inset = 50;
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);
        scoreWindow = createScoreWindow();
        logWindow = createLogWindow();
        if(isLoad){
            new WindowCreator(logWindow, logWindow.name).setSizes();
            new WindowCreator(scoreWindow, scoreWindow.getName()).setSizes();
        }
        else{
            logWindow.setLocation(0, 0);
            logWindow.setSize(200, 300);

            scoreWindow.setLocation(0, 301);
            scoreWindow.setSize(200, 300);
        }
        addWindow(scoreWindow);
        addWindow(logWindow);

        for (int i=0; i<gameWindows.length; i++) {
            windowThreads[i] = new WindowThread(i, this);
            windowThreads[i].start();
            gameWindows[i] = windowThreads[i].getGameWindow();
        }

        setJMenuBar(generateMenuBar());
    }

    public GameWindow getGameWindow(int index) {
        return gameWindows[index];
    }

    public WindowThread[] getWindowThreads() {
        return windowThreads;
    }

    public GameWindow[] getGameWindows() {
        return gameWindows;
    }

    public  LogWindow getLogWindow() {
        return logWindow;
    }

    public ScoreWindow getScoreWindow() {
        return scoreWindow;
    }

    private ScoreWindow createScoreWindow() {
        scoreWindow = new ScoreWindow();
        scoreWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        scoreWindow.addInternalFrameListener(new FrameDialog(this, scoreWindow));
        return scoreWindow;
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        logWindow.addInternalFrameListener(new FrameDialog(this, logWindow));
        Logger.debug(rb.getString("protocolWorks"));
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
        JMenuItem systemScheme = makeMenuItem(rb.getString("systemScheme"),
                (event) -> {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    this.invalidate();
                });
        lookAndFeelMenu.add(systemScheme);
        JMenuItem universalScheme = makeMenuItem(rb.getString("universalScheme"),
                (event) -> {
                    setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    this.invalidate();
                });
        lookAndFeelMenu.add(universalScheme);

        allMenuItem.put("systemScheme", systemScheme);
        allMenuItem.put("universalScheme", universalScheme);

        JMenu testMenu = makeMenu(rb.getString("tests"), KeyEvent.VK_T, rb.getString("testCommands"));
        JMenuItem logMessages = makeMenuItem(rb.getString("logMessages"),
                (event) -> Logger.debug(rb.getString("newLine")));
        // поменять локаль?
        testMenu.add(logMessages);

        JMenu exitMenu = makeMenu(rb.getString("exit"), KeyEvent.VK_V, rb.getString("exitGame"));

        JMenuItem exit = makeMenuItem(rb.getString("exit"),
                (event) -> {
                    CloseDialog closeDialog = new CloseDialog(this);
                    closeDialog.onPushedCloseButton(event);
                }
        );
        exitMenu.add(exit);

        allMenuItem.put("logMessages", logMessages);
        allMenuItem.put("exit", exit);

        JMenu localeMenu = makeMenu(rb.getString(
                "locale"),
                KeyEvent.VK_V, rb.getString("switchLocale")
        );

        JMenuItem ru_locale = makeMenuItem(rb.getString("ru_locale"),
                (event) -> {
                    switchLocale(Locale.getDefault());
                    this.invalidate();
                });

        localeMenu.add(ru_locale);
        JMenuItem en_locate = makeMenuItem(rb.getString("en_locate"),
                (event) -> {
                    switchLocale( new Locale("en", "US"));
                    this.invalidate();
                });

        localeMenu.add(en_locate);
        allMenuItem.put("en_locate", en_locate);
        allMenuItem.put("ru_locale", ru_locale);


        allMenu.put("displayMode", lookAndFeelMenu);
        allMenu.put("tests", testMenu);
        allMenu.put("exit", exitMenu);
        allMenu.put("locale", localeMenu);

        menuBar.add(localeMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenu);
        return menuBar;
    }

    private void switchLocale(Locale loc){
        mainLocale = loc;
        rb = ResourceBundle.getBundle(
                "mainApplicationFrame", mainLocale
        );
        setLocale(loc);
        updateLocale(mainLocale);
    }

    private void updateLocale(Locale loc){
        for (String menuName: allMenu.keySet()) {
            JMenu menu = allMenu.get(menuName);
            menu.setText(rb.getString(menuName));
        }
        for (String menuItemName: allMenuItem.keySet()) {
            JMenuItem menu = allMenuItem.get(menuItemName);
            menu.setText(rb.getString(menuItemName));
        }

        logWindow.setLocale(loc);
        scoreWindow.setLocale(loc);
        for(int i = 0; i < windowThreads.length; i++){
            windowThreads[i].getGameWindow().setLocale(loc);
        }
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
