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
    }

    public void drawLine(Point A, Point B) {
        try {
            bufferedWriter.append("<line x1=\"" + A.x + "\" y1=\"" + A.y + "\" x2=\"" + B.x + "\" y2=\"" + B.y + "\"" +
                    "style=\"stroke:black; stroke-width:" + lineWidth + "\"></line>\n");
            bufferedWriter.flush();
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Could not draw line!");
        }
    }

    public void drawCircle(Point P) {
        if (!points.contains(P)) {
            points.add(P);
            try {
                bufferedWriter.append("<circle cx=" + P.x + " cy=" + P.y + " r=" + circleRadius + "></circle>\n");
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