package com.miir.astralscience.util.math;

import net.minecraft.util.math.Vec3i;

public abstract class AstralMath {
    public static Vec3i hexToRGB(String hex) {
        String s = hex.substring(0, 2);
        String t = hex.substring(2, 4);
        String u = hex.substring(4, 6);
        int r = Integer.parseInt(s, 16);
        int g = Integer.parseInt(t, 16);
        int b = Integer.parseInt(u, 16);
        return new Vec3i(r, g, b);
    }
}
