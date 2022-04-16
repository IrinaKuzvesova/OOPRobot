package gui.serialize;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Saver implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean icon;
    public boolean isActive;

    public Saver(int x, int y, int width, int heigth, boolean icon, boolean isActive) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = heigth;
        this.icon = icon;
        this.isActive = isActive;
    }

    public void save(String name) {
        try {
            FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") +"\\"+name+".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        }
        catch (Exception e){
            //just ignore
        }
    }
}