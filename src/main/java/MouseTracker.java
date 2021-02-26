import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Gets the mouse coordinates every according to TimeManager.Record time.
 * Draws an svg path according to the mouse movement every TimeManager tick.
 * If the mouse has not moved for .5s then it begins a DrawCircle phase, increasing the radius of the circle until the mouse is moved.
 */
public class MouseTracker implements Runnable {
    private Thread worker;
    private final AtomicBoolean isRunning;

    private Point previousPoint;
    private Point currentPoint;
    private double timeSinceMoved;

    private final float CIRCLE_INCREASE_PER_TICK = 0.00072f / TimeManager.RecordTimeDivider;
    private final float CIRCLE_MAX_RADIUS = 4;
    private float stopCircleRadiusMultiplier;

    private boolean hasMoved;
    private boolean isDrawingLine;
    private SVGPrinter svgPrinter;

    public MouseTracker() {
        isRunning = new AtomicBoolean(false);

        previousPoint = MouseInfo.getPointerInfo().getLocation();
        currentPoint = MouseInfo.getPointerInfo().getLocation();
        timeSinceMoved = 0;

        stopCircleRadiusMultiplier = 0;

        hasMoved = false;
        isDrawingLine = false;
        svgPrinter = new SVGPrinter();
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        if (isDrawingLine) {
            svgPrinter.finishDrawLine();
        }
        svgPrinter.endDrawing();

        isRunning.set(false);

        timeSinceMoved = 0;

        stopCircleRadiusMultiplier = 0;

        hasMoved = false;
        isDrawingLine = false;
    }

    public void run() {
        isRunning.set(true);
        svgPrinter.beginDrawing();
        while (isRunning.get()) {
            if (TimeManager.hasRecordTimePassed()) {
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
                //System.out.println("MouseInfo CurrentPoint=" + currentPoint.toString());
            }
        }
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