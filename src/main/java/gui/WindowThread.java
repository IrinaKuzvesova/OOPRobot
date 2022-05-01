package gui;

import gui.dialogs.FrameDialog;

import javax.swing.*;

public class WindowThread extends Thread{
    private final int id;
    private final MainApplicationFrame owner;
    private GameWindow gameWindow;

    public WindowThread(int id, MainApplicationFrame owner) {
        this.id = id;
        this.owner = owner;
        gameWindow = new GameWindow(id);
        gameWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameWindow.addInternalFrameListener(new FrameDialog(owner, gameWindow));
        owner.addWindow(gameWindow);
    }

    @Override
    public void run() {
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    @Override
    public void interrupt() {
        try {
            gameWindow.dispose();
        }
        catch (Exception e){

        }
        super.interrupt();
    }
}