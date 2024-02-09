package Filters;
import Interfaces.PixelFilter;
import core.DImage;
import javax.swing.*;

public class BlurFilter implements PixelFilter {
    int radius =  15; //test
    @Override
    public DImage processImage(DImage img) {

        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();


        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length-radius; c++) {

                int totalR  = 0, totalG = 0, totalB = 0;
                for (int i = 0; i < radius; i++) {
                    totalR += red[r][c+i];
                    totalG += green[r][c+i];
                    totalB += blue[r][c+i];
                }
                red[r][c] = (short) (totalR/radius);
                green[r][c] = (short) (totalG/radius);
                blue[r][c] = (short) (totalB/radius);

            }
        }
        img.setColorChannels(red,green,blue);
        return img;
    }

}
