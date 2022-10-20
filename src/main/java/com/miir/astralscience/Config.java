package com.miir.astralscience;

public class Config {
    
//    general
    public static final int ORBIT_HEIGHT = 2072;
    public static final int ORBIT_DROP_HEIGHT = 2048;
    public static final double GRAVITY_ORBIT = 25.0D;

    // Omeia

    //    omeian gravity
    public static final float GRAVITY_OMEIA = 5.0F;

    // width of Omeia's green belt
    public static final int GREEN_BELT_WIDTH = 20_000;
    // width of the transition period (one on each side
    public static final int BROWN_BELT_WIDTH = 1000;

    //    client
    //    how high to start rendering stars and such
    public static final double ATMOSPHERIC_CULL_HEIGHT = 512;
    //    how high to render thick fog on reentry
    public static final int ATMOSPHERIC_FOG_HEIGHT = (int)ATMOSPHERIC_CULL_HEIGHT / 2;
    public static final int IRIS_SURFACE_HEIGHT = 150;
    public static final int PLANET_SIZE = 512;
}
