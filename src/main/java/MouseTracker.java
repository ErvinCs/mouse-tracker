import java.awt.*;

/**
 * Gets the mouse coordinates every 0.05f seconds.
 * If the mouse moved then it draws a line between the two points.
 * If the mouse has not moved then it begins a DrawCircle phase, increasing the radius of the circle until the mouse is moved.
 */
public class MouseTracker {
    private PointerInfo previousPoint;
    private PointerInfo currentPoint;
    private double timeSinceMoved;
    private boolean hasMoved;

    private SVGPrinter svgPrinter;

    public MouseTracker() {
        previousPoint = MouseInfo.getPointerInfo();
        currentPoint = MouseInfo.getPointerInfo();
        timeSinceMoved = 0;
        hasMoved = false;
    }

    public void update() {
        previousPoint = MouseInfo.getPointerInfo();
        currentPoint = MouseInfo.getPointerInfo();
        timeSinceMoved = hasMoved ? 0 : timeSinceMoved + TimeManager.deltaTime();
    }

    public PointerInfo getPreviousPoint() {
        return previousPoint;
    }

    public PointerInfo getCurrentPoint() {
        return currentPoint;
    }

    public double getTimeSinceMoved() {
        return timeSinceMoved;
    }

    public void onMoved() {
        svgPrinter.drawLine(previousPoint, currentPoint);
    }

    public void onClicked() {
        svgPrinter.drawCircle(currentPoint);
    }
}