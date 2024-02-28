package Filters;
public class Point2 {
    private int r, c;

    public Point2(int r, int c) {
        super();
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setC(int c) {
        this.c = c;
    }
    public String toString(){
        return this.r + ", " + this.c;
    }

    @Override
    public int hashCode() {
        return (r<<8) | c;
    }
    public boolean equals(Point2 other){
        return other.r == this.r && other.c == this.c;
    }
}