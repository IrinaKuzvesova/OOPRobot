package gui;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Robot {
    private final Timer timer = initTimer();

    private double positionX;
    private double positionY;
    private double robotDirection = 0;
    private final double maxVelocity;
    private final double maxAngularVelocity;
    private final int gazeLength;

    private Food target;
    private int targetPositionX;
    private int targetPositionY;
    private boolean foundFood = true;

    private final Color color;

    private final GameWindow gameOwner;
    private final GameVisualizer owner;
    private int currentScore = 0;
    private final int id;

    private Timer initTimer() {
        return new Timer("events generator", true);
    }

    public Robot(double startX, double startY, Color color, GameWindow gameWindow, GameVisualizer owner, int id) {
        this.id = id;
        this.owner = owner;
        positionX = startX;
        positionY = startY;
        targetPositionX = getX();
        targetPositionY = getY();
        target = new Food(getX(), getY(), Color.yellow, 0);
        this.color = color;
        gameOwner = gameWindow;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                findTarget();
            }
        }, 0, 50);
        gazeLength = 70 * (id + 1);
        maxVelocity = 0.016 * (id + 1);
        maxAngularVelocity = 0.005 * (id + 1);
    }

    public int getX() {
        return (int)(positionX + 0.5);
    }

    public int getY() {
        return (int)(positionY + 0.5);
    }

    public int getTargetX() {
        return targetPositionX;
    }

    public int getTargetY() {
        return targetPositionY;
    }

    public double getDirection() {
        return robotDirection;
    }

    public Color getColor() { return color; }

    public Food getTarget() {
        return target;
    }

    public int getGazeLength() {return gazeLength; }

    private void setTargetPosition(int x, int y) {
        targetPositionX = x;
        targetPositionY = y;
    }

    public void setTarget(Food food) {
        this.target = food;
        setTargetPosition(food.getLocationX(), food.getLocationY());
    }

    public boolean isFoundFood() {
        return foundFood;
    }

    public void move() {
        double distance = distance(targetPositionX, targetPositionY, positionX, positionY);
        if (distance < 8) {
            synchronized (KeyHolder.scoreKey) {
                currentScore += target.getPrice();
                foundFood = true;
                DataTransmitter.updateRobot(id, this);
                owner.deleteTarget(target);
            }
            return;
        }
        foundFood = false;
        double velocity = maxVelocity;
        double angleToTarget = angleTo(positionX, positionY, targetPositionX, targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > robotDirection) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -maxAngularVelocity;
        }
        turnRobot(angularVelocity, 4);
        moveRobot(velocity, angularVelocity, 10);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        double newX = positionX + velocity / angularVelocity *
                (Math.sin(robotDirection  + angularVelocity * duration) -
                        Math.sin(robotDirection));
        newX = applyLimits(newX, 0, gameOwner.getWidth() - 20);
        if (!Double.isFinite(newX)) {
            newX = positionX + velocity * duration * Math.cos(robotDirection);
        }

        double newY = positionY - velocity / angularVelocity *
                (Math.cos(robotDirection  + angularVelocity * duration) -
                        Math.cos(robotDirection));
        newY = applyLimits(newY, 0, gameOwner.getHeight() - 40);
        if (!Double.isFinite(newY)) {
            newY = positionY + velocity * duration * Math.sin(robotDirection);
        }
        positionX = newX;
        positionY = newY;
    }

    private void turnRobot(double angularVelocity, double duration) {
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0)
            angle += 2*Math.PI;
        while (angle >= 2*Math.PI)
            angle -= 2*Math.PI;
        return angle;
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void findTarget() {
        Food target = owner.getFoodAt(getX(), getY());
        if (target != null) {
            this.target = target;
            targetPositionX = target.getLocationX();
            targetPositionY = target.getLocationY();
            return;
        }
        moveToRandomDirection();
    }

    private void moveToRandomDirection() {
        Random random = new Random();
        int coefX;
        if (positionX < 3) {
            coefX = 2;
        }
        else {
            if (positionX > gameOwner.getWidth() - 43)
                coefX = -2;
            else
                coefX = 2 * (random.nextInt(2)) - 1;
        }
        int coefY;
        if (positionY < 3) {
            coefY = 2;
        }
        else {
            if (targetPositionY > gameOwner.getHeight() - 23)
                coefY = -2;
            else
                coefY = 2 * (random.nextInt(2)) - 1;
        }
        targetPositionX = targetPositionX + coefX * gazeLength;
        targetPositionY = targetPositionY + coefY * gazeLength;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getId() {
        return id;
    }
}
