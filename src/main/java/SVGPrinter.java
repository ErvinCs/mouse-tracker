import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creates files and writes SVG data to them according to the given pointer information.
 * Can draw lines to depict mouse movement or circles to show that the mouse has not moved for more than half a second.
 */
public class SVGPrinter {
    private File file;
    private String filename;
    //TODO - Use a buffered Writer
    private FileWriter fileWriter;
    private int lineWidth;
    private int circleRadius;

    public SVGPrinter() {
        lineWidth = 2;
        circleRadius = 10;
    }

    public void drawLine(PointerInfo A, PointerInfo B) {
        try {
            fileWriter.write("<line x1=\"" + A.getLocation().x + "\" y1=\"" + A.getLocation().y + "\" x2=\"" + B.getLocation().x + "\" y2=\"" + B.getLocation().y + "\"" +
                    "style=\"stroke:black; stroke-width:" + lineWidth + "\" />");
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Could not draw line!");
        }
    }

    public void drawCircle(PointerInfo P) {
        try {
            fileWriter.write("<circle cx=" + P.getLocation().x + "cy=" + P.getLocation().y + "r=" + circleRadius + "/>");
        } catch (IOException ex) {
            System.err.println("SVGPrinter: Could not draw circle!");
        }

    }

    public void endDrawing() {
        try
        {
            fileWriter.write("    </svg>  </body>");
            fileWriter.write("</html>");
        } catch (IOException ex)
        {
            System.err.println("SVGPrinter: An issue occured while saving the file!");
        }
    }

    public void beginDrawing() {
        filename = generateFilename();

        file = new File(filename + ".html");
        try {
            fileWriter = new FileWriter(file);
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Could not create file!");
        }

        try
        {
            fileWriter.write("<!DOCTYPE html>\n");
            fileWriter.write("<html>\n  <head>\n    <title>SVGPrint</title>\n  </head>\n");
            fileWriter.write("  <body>\n    <svg>\n");
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