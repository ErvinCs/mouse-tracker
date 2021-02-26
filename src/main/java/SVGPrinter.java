import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates files and writes SVG data to them according to the given pointer information.
 * Can draw lines to depict mouse movement or circles to show that the mouse has not moved.
 */
public class SVGPrinter {
    private File file;
    private String filename;
    private BufferedWriter bufferedWriter;

    private int lineWidth;
    private int circleRadius;

    private Point lastPoint;
    private Set<Point> points;

    private static int screenWidth;
    private static int screenHeight;

    public SVGPrinter() {
        lineWidth = 2;
        circleRadius = 10;
        points = new HashSet<>();
        lastPoint = null;
        initScreenSize();
    }

    public static void printScreenSize() {
        System.out.println("Screen Width = " + screenWidth);
        System.out.println("Screen Height = " + screenHeight + "\n");
    }

    public void startDrawLine(Point A, Point B) {
        try {
            System.out.println("Start draw line");
            bufferedWriter.append("<path d=\"M" + A.x + " " + A.y + " L" + B.x + " " + B.y);

            System.out.println("Line Started Drawing At: X=" + A.x + " Y=" + A.y);
            lastPoint = B;
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Failed on StartDrawLine!");
        }
    }

    public void continueDrawLine(Point C) {
        try {
            System.out.println("Continue draw line");
            bufferedWriter.append(" L" + C.x + " " + C.y);
            lastPoint = C;
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Failed on ContinueDrawLine!");
        }
    }

    public void finishDrawLine() {
        try {
            System.out.println("FinishDrawLine");

            bufferedWriter.append("\" style=\"stroke:black;stroke-width:2;fill:none;\"></path>\n");
            bufferedWriter.flush();

            System.out.println("Line Finished Drawing At: X=" + lastPoint.x + " Y=" + lastPoint.y);
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Failed on ContinueDrawLine!");
        }
    }

    public void drawCircle(Point P, float radiusMultiplier) {
        if (!points.contains(P)) {
            System.out.println("Draw circleR");
            points.add(P);
            float radius = radiusMultiplier > 1 ? circleRadius * radiusMultiplier : circleRadius;
            try {
                bufferedWriter.append("<circle cx=" + P.x + " cy=" + P.y + " r=" + radius +
                        " style=\"stroke:black; stroke-width:" + lineWidth + ";fill:none;\"></circle>\n");
                bufferedWriter.flush();

                System.out.println("Circle Drawn At: X=" + P.x + " Y=" + P.y + " Radius=" + radius);
            } catch (IOException ex) {
                System.err.println("SVGPrinter: Could not draw circle!");
            }
        }

    }

    public void endDrawing() {
        try
        {
            bufferedWriter.append("    </svg>\n  </body>\n");
            bufferedWriter.append("</html>");
            bufferedWriter.flush();
            bufferedWriter.close();
            points.clear();

            System.out.println("Finished Writing File: " + filename + "\n");
        } catch (IOException ex)
        {
            System.err.println("SVGPrinter: An issue occured while saving the file!");
        }
    }

    public void beginDrawing() {
        filename = generateFilename();

        file = new File(filename + ".html");
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Could not create file!");
        }

        try
        {
            bufferedWriter.write("<!DOCTYPE html>\n");
            bufferedWriter.append("<html>\n  <head>\n    <title>SVGPrint</title>\n  </head>\n");
            bufferedWriter.append("  <body>\n    <svg width=\"" + screenWidth + "\" height=\"" + screenHeight + "\">\n");
            bufferedWriter.flush();

            System.out.println("File Created: " + filename + "\n");
        } catch (IOException ex)
        {
            System.err.println("SVGPrinter: An issue occured while initializing the file!");
        }
    }

    private String generateFilename() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private void initScreenSize() {
        int minx=0, miny=0, maxx=0, maxy=0;
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for(GraphicsDevice device : environment.getScreenDevices()){
            Rectangle bounds = device.getDefaultConfiguration().getBounds();
            minx = Math.min(minx, bounds.x);
            miny = Math.min(miny, bounds.y);
            maxx = Math.max(maxx,  bounds.x+bounds.width);
            maxy = Math.max(maxy, bounds.y+bounds.height);
        }
        Rectangle screen = new Rectangle(minx, miny, maxx-minx, maxy-miny);

        screenWidth = screen.width;
        screenHeight = screen.height;
    }
}