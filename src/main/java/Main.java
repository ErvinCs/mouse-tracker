import java.nio.file.FileSystems;
import java.sql.Time;

/**
 * Entry point to the program.
 * Additionally provides the Text UI and access to commands through the KeyboardInputReader
 */
public class Main {

    public static void main(String[] args) {
        MouseTracker mouseTracker = new MouseTracker();
        KeyboardInputReader kbInputReader = new KeyboardInputReader(mouseTracker);

        boolean isRunning = true;
        float runFor = 1000000000;
        float hasRunFor = 0;

        String instructions = "R - Begin/Stop Recording \t S - Save Recording to SVG \t X - Exit Application";
        String outputLocation = FileSystems.getDefault().getPath(".").toString();
        System.out.println("Output Location: " + outputLocation);
        System.out.println(instructions);

        kbInputReader.ToggleRecoding();

        TimeManager.reset();
        while (hasRunFor  < runFor) {
            hasRunFor += TimeManager.deltaTime() % 1000000000;
            //System.out.println(hasRunFor + "\n");
            mouseTracker.update();
        }

        kbInputReader.ToggleRecoding();
    }
}
