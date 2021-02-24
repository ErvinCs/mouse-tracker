import processing.core.PApplet;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EntryPoint { //extends PApplet {
    /*private GraphicsEnvironment graphicsEnvironment;
    private GraphicsDevice graphicsDevice;
    private Frame frame;
    private float scale;
    private boolean isRecording;
    private MouseTracker mouseTracker;

    public EntryPoint() {
        isRecording = false;
        mouseTracker = new MouseTracker();

        frame = new Frame("MouseTracker - ErvinCs - github.com/ErvinCs");
        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        scale = Math.min((float) graphicsDevice.getDisplayMode().getWidth(), 1280) / (float) graphicsDevice.getDisplayMode().getWidth();
        frame.setSize((int) (graphicsDevice.getDisplayMode().getWidth() * scale), (int) (graphicsDevice.getDisplayMode().getHeight() * scale));
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }

    public void keyReleased() {
        switch (key)
        {
            case 'r':
            case 'R':
                ToggleRecoding();
                break;
        }
    }

    public void ToggleRecoding()
    {
        if (!isRecording) {
            isRecording = true;
            mouseTracker.begin();
        } else {
            isRecording = false;
            mouseTracker.end();
        }
    }*/
}
