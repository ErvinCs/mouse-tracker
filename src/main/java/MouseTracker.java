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
    private boolean isDrawing;
    private SVGPrinter svgPrinter;

    public MouseTracker() {
        previousPoint = MouseInfo.getPointerInfo();
        currentPoint = MouseInfo.getPointerInfo();
        timeSinceMoved = 0;
        hasMoved = false;
        isDrawing = false;
        svgPrinter = new SVGPrinter();
    }

    public void begin() {
        isDrawing = true;
        svgPrinter.beginDrawing();
    }

    public void update() {
        if (isDrawing && TimeManager.hasHalfSecondPassed()) {
            previousPoint = MouseInfo.getPointerInfo();
            currentPoint = MouseInfo.getPointerInfo();
            updateHasMoved();
            timeSinceMoved = hasMoved ? 0 : timeSinceMoved + TimeManager.deltaTime();

            if (hasMoved) {
                onMoved();
            } else {
                onStay();
            }
        }
    }

    public void end() {
        isDrawing = false;
        svgPrinter.endDrawing();
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

    public void onStay() {
        svgPrinter.drawCircle(currentPoint);
    }

    private void updateHasMoved() {
        if (previousPoint == currentPoint) {
            hasMoved = false;
        } else {
            TimeManager.reset();
            hasMoved = true;
        }
    }
}