package gui;

public class DataTransmitter {
    private static final Robot[] robots = new Robot[4];
    public static void updateRobot(int id, Robot robot) {
        robots[id] = robot;
    }

    public static Robot getRobot(int i) {
        try {
            return robots[i];
        }
        catch (Exception e) {
            return null;
        }
    }

    public static int numberOfRobot() {
        return robots.length;
    }

    public static void killRobot(int id) {
        robots[id] = null;
    }
}