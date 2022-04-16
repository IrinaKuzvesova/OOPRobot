package gui;

import gui.serialize.Saver;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class WindowCreator {
    private JInternalFrame owner;
    private final String ownerName;

    public WindowCreator(JInternalFrame owner, String name) {
        this.owner = owner;
        ownerName = name;
    }

    public void setSizes(){
        Saver windowParameters = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(System.getProperty("user.dir") +"\\"+ownerName+".txt");
            System.out.println("from "+System.getProperty("user.dir") +"\\"+ownerName+".txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            windowParameters = (Saver) objectInputStream.readObject();
            owner.setLocation(windowParameters.x, windowParameters.y);
            owner.setSize(windowParameters.width, windowParameters.height);
            owner.setIcon(windowParameters.icon);
        } catch (Exception e) {
            owner.setLocation(0, 0);
            owner.setSize(1000, 800);
        }
    }
}