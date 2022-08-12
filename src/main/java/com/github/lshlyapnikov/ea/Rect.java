package com.github.lshlyapnikov.ea;

/**
 * https://blogs.oracle.com/javamagazine/post/escape-analysis-in-the-hotspot-jit-compiler
 */
public class Rect {
    private final int w;
    private final int h;

    public Rect(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public int area() {
        return w * h;
    }

    public boolean sameArea(Rect other) {
        return this.area() == other.area();
    }

    public static void main(final String[] args) {
        java.util.Random rand = new java.util.Random();
        int sameArea = 0;
        for (int i = 0; i < 100_000_000; i++) {
            Rect r1 = new Rect(rand.nextInt(5), rand.nextInt(5));
            Rect r2 = new Rect(rand.nextInt(5), rand.nextInt(5));
            if (r1.sameArea(r2)) {
                sameArea++;
            }
        }
        System.out.println("Same area: " + sameArea);
    }
}
