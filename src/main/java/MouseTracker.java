import java.awt.*;
import java.sql.Time;

/**
 * Gets the mouse coordinates every 0.05f seconds.
 * If the mouse moved then it draws a line between the two points.
 * If the mouse has not moved then it begins a DrawCircle phase, increasing the radius of the circle until the mouse is moved.
 */
public class MouseTracker {
    private Point previousPoint;
    private Point currentPoint;
    private double timeSinceMoved;
    private boolean hasMoved;
    private boolean isDrawing;
    private boolean isDrawingLine;
    private SVGPrinter svgPrinter;

    public MouseTracker() {
        previousPoint = MouseInfo.getPointerInfo().getLocation();
        currentPoint = MouseInfo.getPointerInfo().getLocation();
        timeSinceMoved = 0;
        hasMoved = false;
        isDrawing = false;
        isDrawingLine = false;
        svgPrinter = new SVGPrinter();
    }

    public void begin() {
        isDrawing = true;
        svgPrinter.beginDrawing();
    }

    public void update() {
        if (isDrawing && TimeManager.hasRecordTimePassed()) {
            previousPoint = new Point(currentPoint);
            currentPoint = MouseInfo.getPointerInfo().getLocation();
            updateHasMoved();
            timeSinceMoved = hasMoved ? 0 : timeSinceMoved + TimeManager.deltaTime();

            if (hasMoved) {
                TimeManager.reset();
                onMoved();
            } else {
                if (TimeManager.hasStopTimePassed()) {
                    onStay();
                }
            }
        }
    }

    public void end() {
        isDrawing = false;
        svgPrinter.endDrawing();
    }

    public void onMoved() {
        if (isDrawingLine) {
            svgPrinter.continueDrawLine(currentPoint);
        } else {
            isDrawingLine = true;
            svgPrinter.startDrawLine(previousPoint, currentPoint);
        }
    }

    public void onStay() {
        if (isDrawingLine) {
            svgPrinter.finishDrawLine();
            isDrawingLine = false;
        }
        svgPrinter.drawCircle(currentPoint);
    }

    private void updateHasMoved() {
        if (previousPoint.x == currentPoint.x && previousPoint.y == currentPoint.y) {
            hasMoved = false;
        } else {
            hasMoved = true;
        }
    }
}