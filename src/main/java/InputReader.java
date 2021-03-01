import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Controls the program execution according to keyboard input.
 * R - Begin/Stop Recording
 * X - Exit Application
 */
public class InputReader {
    private BufferedReader reader;
    private char input;

    public InputReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printWelcomeMessage() {
        System.out.println("\nEnter a command and hit enter.\n" + "The application will record your mouse in the background.\n" +
                "A new file is created in the application directory for each recording\n");
    }

    public void printInstructions() {
        System.out.println("R - Begin/Stop Recording \t H - Help \t X - Exit Application\n");
    }

    public char getInput() throws IOException {
        input = (char)reader.read();
        return input;
    }
}
