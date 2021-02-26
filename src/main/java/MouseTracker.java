import java.awt.*;
import java.sql.Time;

/**
 * Gets the mouse coordinates every according to TimeManager.Record time.
 * Draws an svg path according to the mouse movement every TimeManager tick.
 * If the mouse has not moved for .5s then it begins a DrawCircle phase, increasing the radius of the circle until the mouse is moved.
 */
public class MouseTracker {
    private Point previousPoint;
    private Point currentPoint;
    private double timeSinceMoved;

    private final float CIRCLE_INCREASE_PER_TICK = 0.0002f / TimeManager.RecordTimeDivider;
    private final float CIRCLE_MAX_RADIUS = 4;
    private float stopCircleRadiusMultiplier;

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
        stopCircleRadiusMultiplier = 0;
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
                if (stopCircleRadiusMultiplier > 0) {
                    onStay(stopCircleRadiusMultiplier);
                    stopCircleRadiusMultiplier = 0;
                }
                onMoved();
            } else {
                if (TimeManager.hasStopTimePassed()) {
                    stopCircleRadiusMultiplier = stopCircleRadiusMultiplier > CIRCLE_MAX_RADIUS ? CIRCLE_MAX_RADIUS : stopCircleRadiusMultiplier + CIRCLE_INCREASE_PER_TICK;
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

    public void onStay(float circleRadiusMultiplier) {
        if (isDrawingLine) {
            svgPrinter.finishDrawLine();
            isDrawingLine = false;
        }
        svgPrinter.drawCircle(currentPoint, circleRadiusMultiplier);
    }

    private void updateHasMoved() {
        if (previousPoint.x == currentPoint.x && previousPoint.y == currentPoint.y) {
            hasMoved = false;
        } else {
            hasMoved = true;
        }
    }
}