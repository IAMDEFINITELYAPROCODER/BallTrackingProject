package Filters;
import Interfaces.PixelFilter;
import core.DImage;
import javax.swing.*;

public class BlurFilter implements PixelFilter {
    int response = Integer.parseInt(JOptionPane.showInputDialog("What radius would you like?"));
    int radius =  response;
    @Override
    public DImage processImage(DImage img) {
        short [] [] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length-radius; c++) {

                int total  = 0;
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