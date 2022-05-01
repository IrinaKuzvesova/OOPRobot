package gui;

public class PaintManager {
    private static int lastID = 0;
    private static final int numberOfScreen = 3;
    public static boolean getAccessToPaint(int id)
    {
        int last = lastID;
        int prev = (id) % numberOfScreen;
        if (prev == last) {
            lastID = (lastID + 1) % numberOfScreen;
            return true;
        }
        return false;
    }
}