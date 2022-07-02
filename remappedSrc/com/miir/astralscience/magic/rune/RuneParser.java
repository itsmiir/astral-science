package com.miir.astralscience.magic.rune;

public abstract class RuneParser {

    public static int getRune(String n) {
        return switch (n) {
            case "figma" -> 0;
            case "waergo" -> 1;
            case "physe" -> 2;
            case "kyter" -> 3;
            case "caphta" -> 4;
            case "pierke" -> 5;
            case "telos" -> 6;
            case "akyp" -> 7;
            case "elef" -> 8;
            case "veris" -> 9;
            case "mica" -> 10;
            case "nix" -> 11;
            case "plexes" -> 12;
            case "deimos" -> 13;
            case "allente" -> 14;
            case "ike" -> 15;
            default -> 0;
        };
    }
    public static char encodeRune(String rune) {
        return switch (rune) {
            case "figma" -> '0';
            case "waergo" -> '1';
            case "physe" -> '2';
            case "kyter" -> '3';
            case "caphta" -> '4';
            case "pierke" -> '5';
            case "telos" -> '6';
            case "akyp" -> '7';
            case "elef" -> '8';
            case "veris" -> '9';
            case "mica" -> 'a';
            case "nix" -> 'b';
            case "plexes" -> 'c';
            case "deimos" -> 'd';
            case "allente" -> 'e';
            case "ike" -> 'f';
            default -> '0';
        };
    }
    public static int[] decodeRunes(String s) {
        char[] chars = s.toCharArray();
        int[] runes = new int[AstralRunes.MAX_LENGTH];
        int i = 0;
        for (char c :
                chars) {
            switch (c) {
                case '0' -> {
                    runes[i] = 0;
                    i++;
                }
                case '1' -> {
                    runes[i] = 1;
                    i++;
                }
                case '2' -> {
                    runes[i] = 2;
                    i++;
                }
                case '3' -> {
                    runes[i] = 3;
                    i++;
                }
                case '4' -> {
                    runes[i] = 4;
                    i++;
                }
                case '5' -> {
                    runes[i] = 5;
                    i++;
                }
                case '6' -> {
                    runes[i] = 6;
                    i++;
                }
                case '7' -> {
                    runes[i] = 7;
                    i++;
                }
                case '8' -> {
                    runes[i] = 8;
                    i++;
                }
                case '9' -> {
                    runes[i] = 9;
                    i++;
                }
                case 'a' -> {
                    runes[i] = 10;
                    i++;
                }
                case 'b' -> {
                    runes[i] = 11;
                    i++;
                }
                case 'c' -> {
                    runes[i] = 12;
                    i++;
                }
                case 'd' -> {
                    runes[i] = 13;
                    i++;
                }
                case 'e' -> {
                    runes[i] = 14;
                    i++;
                }
                case 'f' -> {
                    runes[i] = 15;
                    i++;
                }
            }
        }
        return runes;
    }

    public static boolean parse(RuneList runes, CastingInfo castingInfo) {
        boolean hasTarget = false;
        boolean isEntityTarget = false;
        if (castingInfo.hasTarget()) {
            hasTarget = true;
            isEntityTarget = castingInfo.target.isEntity;
        }
//        the first rune is the command (or a modifier to a command)
        switch (runes.getRune(0)) {
            case '0':
                break;
            case '5':
                if (!hasTarget) {
                    return false;
                }
            case 'b':
                if (!hasTarget) {
                    return false;
                }
            case 'e':
                if (!hasTarget) {
                    return false;
                }
            case 'f':
                if (!hasTarget) {
                    return false;
                }
//                runes that are not commands or modifiers
            case '2':
            case '3':
            case '7':
            case '8':
            case '9':
            case 'a':
            default:
                return false;
        }
        return false;
    }
}
