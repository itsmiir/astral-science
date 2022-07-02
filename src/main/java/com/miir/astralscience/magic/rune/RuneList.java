package com.miir.astralscience.magic.rune;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RuneList {
    private String runes;

    public RuneList() {
        this.runes = "";
    }
    public RuneList(char[] runes) {
        StringBuilder runeList = new StringBuilder();
        for (char c :
                runes) {
            runeList.append(c);
        }
        this.runes = runeList.toString();
    }

    public char getRune(int index) {
        if (index < AstralRunes.MAX_LENGTH && index >= 0) {
            return this.runes.toCharArray()[index];
        } else {
            throw new IllegalArgumentException("Invalid index for RuneList!");
        }
    }
    public void addRune(char rune) {
        this.runes = this.runes + rune;
    }

    public boolean cast(CastingInfo castingInfo) {
        return false;
    }
}
