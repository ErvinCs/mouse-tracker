/**
 * Measures time and computes how much time ago a mouse movement has been made.
 */
public class TimeManager {
    public static long RecordTime = 1000000000 / 80;
    //public static long StopTime = 1000000000 / 2;

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

    public static boolean hasRecordTimePassed() {
        return deltaTime() >= RecordTime;
    }

    //public static boolean hasStopTimePassed() { return deltaTime() >= StopTime;}
}
