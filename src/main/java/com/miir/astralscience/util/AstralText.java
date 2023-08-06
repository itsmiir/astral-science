package com.miir.astralscience.util;

import com.miir.astralscience.AstralScience;
import net.minecraft.util.Identifier;

public abstract class AstralText {
    public static Identifier deorbitify(Identifier original) {
        if (original.getNamespace().equals(AstralScience.MOD_ID)) {
            String[] splits = original.getPath().split("_");
            return new Identifier(AstralScience.MOD_ID, splits[0]);
        }
        return original;
    }

    public static String deorbitify(String original) {
        if(original.contains("_")) {
            String[] splits = original.split("_");
            return splits[0];
        }
        return original;
    }

    public static Identifier orbitify(Identifier original) {
        if(!original.getPath().contains(AstralScience.ORBIT_SUFFIX)) {
            String newPath = original.getPath() + AstralScience.ORBIT_SUFFIX;
            return new Identifier(AstralScience.MOD_ID, newPath);
        }
        return original;
    }

    public static String orbitify(String original) {
        if(!original.contains(AstralScience.ORBIT_SUFFIX)) {
            return original + AstralScience.ORBIT_SUFFIX;
        }
        return original;
    }
}
