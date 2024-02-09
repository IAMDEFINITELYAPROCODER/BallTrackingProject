package Filters;
import Interfaces.PixelFilter;
import core.DImage;
import javax.swing.*;

public class BlurFilter implements PixelFilter {
    int radius =  6; //test
    @Override
    public DImage processImage(DImage img) {

        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();


        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length-radius; c++) {

                int totalR  = 0, totalG = 0, totalB = 0;
                for (int i = 0; i < radius; i++) {
                    total += grid[r][c+i];
                }
                grid [r][c] = (short) (total/radius);
            }
        }
        img.setPixels(grid);
        return img;
    }

}