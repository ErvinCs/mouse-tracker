import java.awt.*;
import java.io.*;


public class ResolutionConverter {
    private static int targetWidth = SVGPrinter.screenWidth * 4; //3840;
    private static int targetHeight = SVGPrinter.screenHeight * 4; //2160;
    private static String extension = "_enlarged.svg";

    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;

    public static void convertTo4K(File file, int totalScreenWidth, int totalScreenHeight, int numberOfDevices, float lineWidth) {
        // Create new file at 4k resolution
        System.out.println("Converting to virtual screen resolution\n");

        int width = targetWidth;
        int height = targetHeight;
        String newName = file.getName();
        int extensionPos = newName.lastIndexOf('.');
        if (extensionPos > 0) {
            newName = newName.substring(0, extensionPos);
            newName += extension;
        } else {
            System.err.println("ResolutionConverter: Could not find file extension!");
        }

        System.out.println("Virtual Screen Width: " + targetWidth);
        System.out.println("Virtual Screen Height: " + targetHeight + "\n");

        // Open newFile for writing and file for reading
        File newFile = new File(newName);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(newFile));

            bufferedReader = new BufferedReader(new FileReader(file));
            // Read and skip the first 4 lines
            for(int i = 0; i < 4; i++) {
                bufferedReader.readLine();
            }

            // Read until the first M in the path
            char readChar = '\0';
            while(readChar != 'M' && readChar !='m') {
                readChar = (char)bufferedReader.read();
            }
        } catch (IOException ex) {
            System.err.println("ResolutionConverter: Could not open file for writing! " + ex.getMessage());
        }

        try {
            boolean isReadingFile = true;

            // Begin drawing
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            bufferedWriter.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\" \n" +
                    "         \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">\n");
            bufferedWriter.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + width + "\" height=\"" + height + "\">\n");
            bufferedWriter.flush();

            // Path drawing
            bufferedWriter.append("  <path d=\"M");
            boolean isFirstCoord;
            boolean isReadingPoints;
            boolean isFirstPoint = true;
            Point point = new Point(0, 0);

            while (isReadingFile) {
                char nextChar = (char)bufferedReader.read();
                isFirstCoord = true;
                isReadingPoints = true;
                point.x = 0;
                point.y = 0;

                if (nextChar == '\"') {
                    isReadingFile = false;
                } else {
                    if (isFirstPoint) {
                        isFirstPoint = false;
                    } else {
                        bufferedWriter.append(" L");
                        nextChar = (char)bufferedReader.read();
                    }
                    String tempCache = String.valueOf(nextChar);
                    while(isReadingPoints) {
                        nextChar = (char)bufferedReader.read();
                        if (nextChar == ' ' || nextChar == '\"') {
                            if (isFirstCoord) {
                                point.x = Integer.parseInt(tempCache);
                                tempCache = "";
                                isFirstCoord = false;
                            } else {
                                point.y = Integer.parseInt(tempCache);
                                tempCache = "";
                                isReadingPoints = false;
                            }
                            if (nextChar == '\"') {
                                isReadingFile = false;
                            }
                        } else if (nextChar != 'L'){
                            tempCache += String.valueOf(nextChar);
                        }
                    }
                    Point convertedPoint = convertPointTo4K(point);
                    bufferedWriter.append(convertedPoint.x + " " + convertedPoint.y);
                    bufferedWriter.flush();
                }

            }
            // End drawing
            bufferedWriter.append("\" style=\"stroke:black;stroke-width:" + lineWidth + ";fill:none;\"></path>\n");
            bufferedWriter.append("</svg>\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            System.err.println("ResolutionConverter: Aan issue occured while converting the file! " + ex.getMessage());
        }
        System.out.println("File converted. Created file: " + newName + "\n");
    }

    private static Point convertPointTo4K(Point point) {
        Point newPoint = new Point();
        newPoint.x = Math.round((point.x-0)*(targetWidth -0)/(SVGPrinter.screenWidth-0))+0;
        newPoint.y = Math.round((point.y - 0)*(targetHeight - 0)/(SVGPrinter.screenHeight-0))+0;

        //System.out.println("OldPoint = " + point.toString());
        //System.out.println("NewPoint = " + newPoint.toString());

        return newPoint;
    }
}
