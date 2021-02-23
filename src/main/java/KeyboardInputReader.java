import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls the program execution according to keyboard input.
 * R - Begin/Stop Recording
 * S - Save Recording to SVG
 * X - Exit Application
 */
public class KeyboardInputReader implements KeyListener  {
    private boolean isRecording;

    public KeyboardInputReader() {
        isRecording = false;
    }

    public void ToggleRecoding()
    {
        if (isRecording)
        {

        }
        else
        {

        }
        throw new NotImplementedException();
    }

    public void SaveRecording()
    {
        throw new NotImplementedException();
    }

    public void ExitApplication()
    {
        throw new NotImplementedException();
    }

    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        switch (key)
        {
            case 'x':
            case 'X':
                ExitApplication();
                break;
            case 'r':
            case 'R':
                ToggleRecoding();
                break;
            case 's':
            case 'S':
                SaveRecording();
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
