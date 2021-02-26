import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Controls the program execution according to keyboard input.
 * R - Begin/Stop Recording
 * X - Exit Application
 */
public class InputReader implements NativeKeyListener {
    public MouseTracker MouseTracker;
    public boolean IsRunning;
    public boolean IsRecording;

    public InputReader() {
        MouseTracker = new MouseTracker();
        IsRunning = true;
        IsRecording = false;
    }

    public void ToggleRecording() {
        if (!IsRecording) {
            System.out.println("Recording Started");
            IsRecording = true;
            MouseTracker.begin();
        } else {
            System.out.println("Recording Ended");
            IsRecording = false;
            MouseTracker.end();
        }
    }

    // -------------------- KEYBOARD EVENTS --------------------

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        switch (nativeKeyEvent.getKeyCode())
        {
            case NativeKeyEvent.VC_X:
                // Exit
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException nativeHookException) {
                    nativeHookException.printStackTrace();
                }
                if (IsRecording) {
                    System.out.println("Recording Ended");
                    IsRecording = false;
                    MouseTracker.end();
                }
                IsRunning = false;
                System.out.println("Application Shutting Down");
                System.exit(0);
                break;

            case NativeKeyEvent.VC_R:
                // Toggle Recording
                ToggleRecording();
                break;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        //Not Implemeneted
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        //Not Implemented
    }
}
