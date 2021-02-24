import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls the program execution according to keyboard input.
 * R - Begin/Stop Recording
 * S - Save Recording to SVG
 * X - Exit Application
 */
public class InputReader implements KeyListener  {
    private boolean isRecording;
    private MouseTracker mouseTracker;

    public InputReader(MouseTracker mouseTracker) {
        isRecording = false;
        this.mouseTracker = mouseTracker;
    }

    public void ToggleRecoding() {
        if (!isRecording) {
            isRecording = true;
            mouseTracker.begin();
        } else {
            isRecording = false;
            mouseTracker.end();
        }
    }

    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        switch (key)
        {
            case 'x':
            case 'X':
                System.exit(0);
                break;
            case 'r':
            case 'R':
                ToggleRecoding();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("...");
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("...");
    }
}
