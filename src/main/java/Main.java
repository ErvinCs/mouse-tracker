import java.io.IOException;

/**
 * Entry point to the program.
 * Contains the program loop.
 */
public class Main {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        inputReader.printWelcomeMessage();
        inputReader.printInstructions();

        boolean isRunning = true;
        boolean isRecording = false;
        MouseTracker mouseTracker = new MouseTracker();

        // Application code
        while (isRunning) {
            try {
                char input = inputReader.getInput();
                switch (input) {
                    case 'R':
                    case 'r':
                        if (!isRecording) {
                            isRecording = true;
                            mouseTracker.start();
                        } else {
                            isRecording = false;
                            mouseTracker.stop();
                        }
                        break;
                    case 'h':
                    case 'H':
                        inputReader.printInstructions();
                        break;
                    case 'X':
                    case 'x':
                        if (isRecording) {
                            mouseTracker.stop();
                            isRecording = false;
                        }
                        isRunning = false;
                        break;
                }
            }catch (IOException ex) {
                System.out.println("An issue occurred. Exception Thrown: " + ex.getMessage());
            }
        }

        System.exit(0);
    }
}
