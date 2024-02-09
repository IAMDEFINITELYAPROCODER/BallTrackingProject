package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

public class btp implements PixelFilter, Interactive {

    private short threshold;
    private short red;
    private short blue;
    private short green;

    public btp() {
        threshold = 32;
    }

    @Override
    public DImage processImage(DImage image) {
        short[][] red = image.getRedChannel();
        short[][] green = image.getGreenChannel();
        short[][] blue = image.getBlueChannel();

        // doing the masking
        doMasking(image, red, green , blue);

        //Locate center
        findingCenter(red, green, blue);

        return image;
    }

    private void doMasking(DImage image, short[][] red, short[][] green, short[][] blue) {
        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                short colorDistance = findColorDistance(red[r][c], green[r][c], blue[r][c]);
                if (colorDistance <= threshold) {
                    red[r][c] = 255;
                    green[r][c] = 255;
                    blue[r][c] = 255;
                }
                else {
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 0;
                }
            }
        }
        image.setColorChannels(red, green, blue);
    }

    private void findingCenter(short[][] red, short[][] green, short[][] blue) {
        int averageRow = 0, averageCol = 0, Whitepixels = 0;
        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[row].length; col++) {
                if (red[row][col] == 255 && green[row][col] == 255 && blue[row][col] == 255) {
                    averageRow += row;
                    averageCol += col;
                    Whitepixels ++;
                }
            }
        }
        if (Whitepixels != 0) {
            averageRow /= Whitepixels;
            averageCol /= Whitepixels;
        }
        System.out.println(averageRow + " gg " + averageCol);
        System.out.println(red.length + " " + red[0].length);

        red[(short)averageRow][(short)averageCol] = 255;
        green[(short)averageRow][(short)averageCol] = 0;
        blue[(short)averageRow][(short)averageCol] = 0;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        red = img.getRedChannel()[mouseX][mouseY];
        green = img.getGreenChannel()[mouseX][mouseY];
        blue = img.getBlueChannel()[mouseX][mouseY];
    }

    @Override
    public void keyPressed(char key) {
        if (key == '+') {
            if (threshold <= 222) {
                threshold += 5;
            }
        }
        // wassup

        if (key == '-') {
            if (threshold >= 0) {
                threshold -= 5;
            }
        }
    }

    private short findColorDistance(short r, short g, short b) {
        return (short)Math.sqrt( Math.pow(Math.abs(r-this.red), 2) + Math.pow(Math.abs(b-this.blue), 2) + Math.pow(Math.abs(g-this.green), 2));
    }
}
