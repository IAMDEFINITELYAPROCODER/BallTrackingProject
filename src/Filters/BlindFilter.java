package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;

public class BlindFilter implements PixelFilter{
    private short thickness;
    private short seperation;

    public BlindFilter() {
        String response = JOptionPane.showInputDialog("Enter a thickness of blind value");
        this.thickness = (short)(Integer.parseInt(response));

        String response2 = JOptionPane.showInputDialog("Enter a separation of blinds value");
        this.seperation = (short)(Integer.parseInt(response));

    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        for (int row = 0; row < grid.length-4; row += this.seperation) {
                for (int column = 0; column < grid[row].length-4; column++) {
                    // thickness
                    for (int i = 0; i < this.thickness; i++) {
                        grid[row+i][column] = 0;
                    }

                }
        }
        img.setPixels(grid);
        return img;
    }
}
