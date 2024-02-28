package Filters;
import Interfaces.PixelFilter;
import core.DImage;
import java.util.ArrayList;

public class FindCenters implements PixelFilter {
    ArrayList<Point2> allWhitePixels;

    @Override
    public DImage processImage(DImage image) {

        short[][] red = image.getRedChannel();
        short[][] green = image.getGreenChannel();
        short[][] blue = image.getBlueChannel();

        // getting all white pixels
        allWhitePixels = new ArrayList<>();
        Point2 p;
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (red[r][c] == 255 && green[r][c] == 255 && blue[r][c] == 255) {
                    p = new Point2(r, c);
                    allWhitePixels.add(p);
                }
            }
        }

        // getting and printing the centers onto the screen
        while (allWhitePixels.size() > 0){
            Point2 center = Center(red, green, blue, allWhitePixels);
            // setting 5 by 5 grid for the center so that it is visible
            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 5; c++) {
                    red[center.getR() + r - 2][center.getC() + c - 2] = 255;
                    green[center.getR() + r - 2][center.getC() + c - 2] = 0;
                    blue[center.getR() + r - 2][center.getC() + c - 2] = 0;
                }
            }
        }

        image.setColorChannels(red,green,blue);
        return image;
    }

    private Point2 Center(short[][]red, short[][]green, short[][]blue, ArrayList<Point2> allWhitePixels) {

        Point2 randomWhite = allWhitePixels.get(((int) (Math.random() * allWhitePixels.size())));

        int row = randomWhite.getR();
        int col = randomWhite.getC();
        int Right = randomWhite.getR(); // Right part of circle
        int Left = randomWhite.getR(); // Left Part of circle
        int Up = randomWhite.getC();  // Top of circle
        int Down = randomWhite.getC(); // bottom of circle
        int radius; // radius of circle
        int rTotal = 0;
        int cTotal = 0;


        while (red[Right][col] != 0) {
            Right++;
        }
        rTotal += Right;
        while (red[Left][col] != 0) {
            Left--;
        }
        rTotal += Left;

        while (red[row][Up] != 0) {
            Up++;
        }
        cTotal += Up;
        while (red[row][Down] != 0) {
            Down--;
        }
        cTotal += Down;

        Point2 center = new Point2(rTotal/2 , cTotal/2);
        int rDist = (center.getR() - Right);
        int cDist = (center.getC() - Up);
        radius = (int)Math.sqrt(rDist*rDist + cDist*cDist);
        System.out.println("Radius: " +  radius + " Center: " + center);

        deleteCurrentCircle(center, radius, red, green, blue);

        return center;
    }

    private void deleteCurrentCircle(Point2 center, int radius, short[][] red, short[][] green, short[][] blue) {

        for (int r = center.getR() - radius - 3; r <= center.getR() + radius + 3; r++) {
            for (int c = center.getC() - radius - 3; c <= center.getC() + radius + 3; c++) {
                // making the white circles go away on the screen
//                red[r][c] = 0;
//                green[r][c] = 0;
//                blue[r][c] = 0;

                // remove this point from the white pixels arraylist (removing points on the current white circle from the arraylist)
                Point2 p = new Point2(r, c);
                remove(allWhitePixels, p);
            }
        }
    }

    private static void remove(ArrayList<Point2> points, Point2 point){
        for (int i = 0; i < points.size(); i++) {
            if(point.equals(points.get(i))){
                points.remove(i);
                i--;
            }
        }
    }
}