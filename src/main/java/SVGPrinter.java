import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Creates files and writes SVG data to them according to the given pointer information.
 * Can draw lines to depict mouse movement or circles to show that the mouse has not moved for more than half a second.
 */
public class SVGPrinter {
    private File file;
    private String filename;
    private BufferedWriter bufferedWriter;
    private int lineWidth;
    private int circleRadius;

    private float screenWidth = 1600;
    private float screenHeight = 900;

    private Set<Point> points;

    public SVGPrinter() {
        lineWidth = 2;
        circleRadius = 10;
        points = new HashSet<>();
        //TODO - Init screenWidth
        //TODO - Init screenHeight
    }

    public void startDrawLine(Point A, Point B) {
        try {
            bufferedWriter.append("<path d=\"M" + A.x + " " + A.y + " L" + B.x + " " + B.y);
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Failed on StartDrawLine!");
        }
    }

    public void continueDrawLine(Point C) {
        try {
            bufferedWriter.append(" L" + C.x + " " + C.y);
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Failed on ContinueDrawLine!");
        }
    }

    public void finishDrawLine() {
        try {
            bufferedWriter.append("\" style=\"stroke:black;stroke-width:2;fill:none;\"></path>\n");
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Failed on ContinueDrawLine!");
        }
    }

    public void drawCircle(Point P, float radiusMultiplier) {
        if (!points.contains(P)) {
            points.add(P);
            float radius = radiusMultiplier > 1 ? circleRadius * radiusMultiplier : circleRadius;
            try {
                bufferedWriter.append("<circle cx=" + P.x + " cy=" + P.y + " r=" + radius +
                        " style=\"stroke:black; stroke-width:" + lineWidth + ";fill:none;\"></circle>\n");
                bufferedWriter.flush();
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
}