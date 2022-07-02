package com.miir.astralscience.util.math;

public abstract class MiirMath {
    /**
     * Simple linear interpolation function.
     * @param x1: the x coordinate of the first point
     * @param y1: the y coordinate of the first point
     * @param x2: the x coordinate of the second point
     * @param y2: the y coordinate of the second point
     * @param targetx: the value of x you are trying to find the y value for
     * @return the y-value corresponding to the given x value
     */
    public static float linearInterpolate(float x1, float y1, float x2, float y2, float targetx) {
        float slope = (y2 - y1) / (x2 - x1);
        float yIntercept = y1 - slope * x1;
        return slope * targetx + yIntercept;
    }
}
