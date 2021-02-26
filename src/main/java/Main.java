import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Entry point to the program.
 * Additionally provides the Text UI and access to commands through the KeyboardInputReader
 */
public class Main {
    public static void main(String[] args) {
        // Disable Logs
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Input Keyboard Hook
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        InputReader inputReader = new InputReader();
        GlobalScreen.addNativeKeyListener(inputReader);

        // Instructions
        String instructions = "R - Begin/Stop Recording \t X - Exit Application";
        System.out.println(instructions);
        SVGPrinter.printScreenSize();

        // Application code
        while (inputReader.IsRunning) {
            if (inputReader.IsRecording) {
                inputReader.MouseTracker.update();
            }
        }
    }
}
