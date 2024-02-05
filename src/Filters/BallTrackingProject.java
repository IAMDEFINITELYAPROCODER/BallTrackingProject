package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

public class BallTrackingProject implements PixelFilter, Interactive {

    private short threshold;
    private short red;
    private short blue;
    private short green;

    public BallTrackingProject() {
        threshold = 32;
    }

    @Override
    public DImage processImage(DImage image) {
        short[][] red = image.getRedChannel();
        short[][] green = image.getGreenChannel();
        short[][] blue = image.getBlueChannel();

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

        image.setColorChannels(red, blue, green);
        return image;
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
