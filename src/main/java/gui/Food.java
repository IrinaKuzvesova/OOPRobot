package gui;

import java.awt.*;

public class Food {
    private int locationX;
    private int locationY;
    private Color color;
    private int price;

    public Food(int x, int y, Color color, int price) {
        locationX = x;
        locationY = y;
        this.color = color;
        this.price = price;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public Color getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }
}