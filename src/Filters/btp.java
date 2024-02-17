package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class btp implements PixelFilter, Interactive {

    private static short threshold = 35;
    private short red;
    private static short red2 = 245;
    private static short red3 = 255;
    private static short blue;
    private static short blue2 = 64;
    private short green;
    private static short green2 = 75;
    private ArrayList<Point2> whitePixels;

    public btp() {
        whitePixels = new ArrayList<>();
    }

    @Override
    public DImage processImage(DImage image) {
        BlurFilter blur = new BlurFilter();
        DImage newImage = blur.processImage(image);

        short[][] red = newImage.getRedChannel();
        short[][] green = newImage.getGreenChannel();
        short[][] blue = newImage.getBlueChannel();

        // doing the masking
        //doMasking(image, red, green, blue);

        //Locate center
        findingCenter(red, green, blue);

        return image;
    }

    private void doMasking(DImage image, short[][] red, short[][] green, short[][] blue) {
        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                short colorDistance = findColorDistance(red[r][c], green[r][c], blue[r][c], this.red, this.green, this.blue);
                short colorDistance2 = findColorDistance(red[r][c], green[r][c], blue[r][c], this.red2, this.green2, this.blue2);
                if (colorDistance <= threshold) {
                    red[r][c] = 255;
                    green[r][c] = 255;
                    blue[r][c] = 255;
                } else if (colorDistance2 <= threshold) {
                    red[r][c] = 255;
                    green[r][c] = 255;
                    blue[r][c] = 255;
                } else {
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 0;
                }
            }
        }
        image.setColorChannels(red, green, blue);
    }

    private void findingCenter(short[][] red, short[][] green, short[][] blue) {
//        int side = 3; // square size
//        int r = (int) (Math.random() * red.length);
//        int c = (int) (Math.random() * red[0].length);
//        while (red[r][c] != 255 && green[r][c] != 255 && blue[r][c] != 255) {
//            r = (int) (Math.random() * red.length);
//            c = (int) (Math.random() * red[0].length);
//        }
//        printCenter(red, green, blue, r, c, side);

        Point2 whiteRC = new Point2(0, 0);
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[r].length; c++) {
                if (red[r][c] == 255 && green[r][c] == 255 && blue[r][c] == 255) {
                    whiteRC.setR(r);
                    whiteRC.setC(c);
                   whitePixels.add(whiteRC);
                }
            }
        }

        int randomWhitePixel = (int)(1 + Math.random() * whitePixels.size());
        System.out.println(randomWhitePixel + " is random index");
        Point2 rightSide = new Point2(0, 0);
        for (int r = whitePixels.get(randomWhitePixel).getR(); r < red.length; r++) {
            for (int c = whitePixels.get(randomWhitePixel).getC(); c < red[r].length - 1; c++) {
                if (red[r][c] == 255 && green[r][c] == 255 && blue[r][c] == 255 && red[r][c + 1] == 0 && green[r][c + 1] == 0 && blue[r][c + 1] == 0) {
                    if (MagnitudeOf(r, c, whitePixels.get(randomWhitePixel).getR(), whitePixels.get(randomWhitePixel).getC()) >= 84) {
                        rightSide.setR(r);
                        rightSide.setC(c);
                        //System.out.println(" came inside ");
                    }
                }
            }
        }
        System.out.println("R " + rightSide.getR() + " \t C " + rightSide.getR());

    }

    private int MagnitudeOf(int r, int c, int r2, int c2) {
        return (int)( Math.sqrt( Math.pow(r2 - r, 2) + Math.pow(c2 - c, 2) ) );
    }

    private void printCenter(short[][] red, short[][] green, short[][] blue, int r, int c, int side) {
        int prevCenterR = r, prevCenterC = c; // initial position
        int centerR = prevCenterR + 1, centerC = prevCenterC + 1;

        while (prevCenterC != centerC && prevCenterR != centerR) {
            int averageR = 0, averageC = 0, countWhites = 0;
            if (numOfBlacks(red, green, blue) <= side) side++;
            else {
                prevCenterR = centerR;
                prevCenterC = centerC; // putting the current center into the previous center variable to replace the new center
                changeCenter(red, green, blue, centerR, centerC, side, r, c);
            }
        }
    }

    private int numOfBlacks ( short[][] red, short[][] green, short[][] blue){
        int numBlacks = 0;
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if ((red[r][c] == 0 && green[r][c] == 0 && blue[r][c] == 0)) numBlacks++;
            }
        }
        return numBlacks;
    }
    @Override
    public void mouseClicked ( int mouseX, int mouseY, DImage img){
        red = img.getRedChannel()[mouseY][mouseX];
        green = img.getGreenChannel()[mouseY][mouseX];
        blue = img.getBlueChannel()[mouseY][mouseX];
    }
    @Override
    public void keyPressed ( char key){
        if (key == '+') {
            if (threshold <= 222) {
                threshold += 5;
            }
        }
        if (key == '-') {
            if (threshold >= 0) {
                threshold -= 5;
            }
        }
    }
    private short findColorDistance ( short r, short g, short b, short red, short blue, short green){
        return (short) Math.sqrt(Math.pow(Math.abs(r - red), 2) + Math.pow(Math.abs(b - blue), 2) + Math.pow(Math.abs(g - green), 2));
    }
    private void changeCenter ( short[][] red, short[][] green, short[][] blue, int centerR, int centerC, int side,int r, int c){
        int countWhites = 0, averageR = 0, averageC = 0;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if ((red[i + r][j + c] == 255 && green[i + r][j + c] == 255 && blue[i + r][j + c] == 255)) {
                    averageR += i;
                    averageC += j;
                    countWhites++;
                }
            }
        }
        centerR = averageR / countWhites;
        centerC = averageC / countWhites;
    }
}