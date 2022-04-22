package gui;

import java.awt.*;
import java.util.*;

public class FoodGenerator {
    private static final Color[] colors = new Color[]{Color.blue, Color.red, Color.cyan, Color.MAGENTA, Color.GREEN};
    private Set<Food> foods;
    private Queue<Point> points = new LinkedList<>();
    private final Timer timer = initTimer();

    private static Timer initTimer() {
        Timer timer = new Timer("food manager loop", true);
        return timer;
    }

    public FoodGenerator(Set<Food> foodSet) {
        foods = foodSet;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                generateFood();
            }
        }, 0, 1000);
    }

    public void addPointsRequest(Point point) {
        points.add(point);
    }

    public void generateFood() {
        Point point = points.poll();
        if (point == null)
            return;
        Random random = new Random();
        int cost = random.nextInt(colors.length);
        Food newFood = new Food(point.x, point.y, colors[cost], cost);
        foods.add(newFood);
    }
}