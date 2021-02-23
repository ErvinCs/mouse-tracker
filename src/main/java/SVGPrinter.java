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
    private FileWriter fileWriter;
    private int lineWidth;
    private int circleRadius;

    public SVGPrinter() {
        filename = generateFilename();
        file = new File(filename + ".html");
        try {
            fileWriter = new FileWriter(file);
        }catch (IOException ex) {
            System.err.println("SVGPrinter: Could not create file!");
        }
        initializeFileStructure();

        lineWidth = 2;
        circleRadius = 10;
    }

    public void drawLine(PointerInfo A, PointerInfo B) {
        fileWriter.write("<line x1=\"" + A.x + "\" y1=\"" + A.y + "\" x2=\"" + B.x + "\" y2=\"" + B.y + "\"" +
                "style=\"stroke:black; stroke-width:" + lineWidth + "\" />");
    }

    public void drawCircle(PointerInfo P) {
        fileWriter.write("<circle cx=" + P.x + "cy=" + P.y + "r=" + circleRadius + "/>");
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

    private void initializeFileStructure() {
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