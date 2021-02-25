import java.nio.file.FileSystems;

/**
 * Entry point to the program.
 * Additionally provides the Text UI and access to commands through the KeyboardInputReader
 */
public class Main {

    public static void main(String[] args) {
        MouseTracker mouseTracker = new MouseTracker();
        InputReader kbInputReader = new InputReader(mouseTracker);

        boolean isRunning = true;

        String instructions = "R - Begin/Stop Recording \t X - Exit Application";
        System.out.println(instructions);

        kbInputReader.ToggleRecoding();

        TimeManager.reset();
        while (isRunning) {
            // If pressed X then Exit
            // If pressed R then kbInputReader.ToggleRecording();
            mouseTracker.update();
        }

        //kbInputReader.ToggleRecoding();
    }
}
