/**
 * Measures time and computes how much time ago a mouse movement has been made.
 */
public class TimeManager {
    public static long HalfSecond = 1000000000 / 2;

    private static long lastTime;

    public TimeManager() {
        lastTime = System.nanoTime();
    }

    public static long deltaTime(){
        return (System.nanoTime() - lastTime);
    }

    public static void reset(){
        lastTime = System.nanoTime();
    }

    public static boolean hasHalfSecondPassed() {
        return deltaTime() >= HalfSecond;
    }
}
