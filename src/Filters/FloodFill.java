package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class FloodFill implements PixelFilter {

// change boolean to values such as 127 and 255 so that you can differentiate between visited and not. Boolean can take too much space.

    private boolean[][] visited; // all the points visited that are white

    @Override
    public DImage processImage(DImage image) {


        short[][] grid = image.getBWPixelGrid();
        visited = new boolean[image.getHeight()][image.getWidth()];
        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                visited[r][c] = false;
            }
        }

        short[][] newGrid = image.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] > 0 && !visited[r][c]) {
                    ArrayList<Point2> curVis = new ArrayList<>();
                    floodFill(r, c, curVis, grid);
                    System.out.println(" R " + r + " C " + c);
                    if (curVis.size() >= 50) {
                        Point2 center = getAverage(curVis); // finding center
                        System.out.println(center.getR() + " " + center.getC());
                        //newGrid[center.getR()][center.getC()] = 0;

                        for (int i = 0; i < grid.length; i++) {
                            for (int j = 0; j < grid[i].length; j++) {
                                if (Math.abs(i-center.getR()) < 2 && Math.abs(j-center.getC()) < 2) {
                                    newGrid[i][j] = 0;
                                } else if(newGrid[i][j]!=254){
                                    // newGrid[i][j] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }

        image.setPixels(newGrid);
        return image;
    }

    private Point2 getAverage(ArrayList<Point2> curVis) {
        Point2 center;
        int r = 0, c = 0;
        for (int i = 0; i < curVis.size(); i++) {
            r += curVis.get(i).getR();
            c += curVis.get(i).getC();
        }
        center = new Point2(r / curVis.size(), c / curVis.size());
        return center;
    }

    private void floodFill(int r, int c, ArrayList<Point2> curVis, short[][] grid) {
        visited[r][c] = true;
        curVis.add(new Point2(r,c));

        // top pixel
        if ((r - 1) >= 0 && !visited[r - 1][c] && grid[r - 1][c] > 0) {
            visited[r - 1][c] = true;
            floodFill(r - 1, c, curVis, grid);
        }
        // bottom pixel
        if ((r + 1) > grid.length && !visited[r - 1][c] && grid[r + 1][c] > 0) {
            visited[r + 1][c] = true;
            floodFill(r + 1, c, curVis, grid);
        }
        // right pixel
        if ((c + 1) > grid.length && !visited[r][c + 1] && grid[r][c + 1] > 0) {
            visited[r][c + 1] = true;
            floodFill(r, c + 1, curVis, grid);
        }
        // left pixel
        if ((c - 1) >= 0 && !visited[r][c - 1] && grid[r][c - 1] > 0) {
            visited[r][c - 1] = true;
            floodFill(r, c - 1, curVis, grid);
        }
    }
}