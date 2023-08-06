package com.miir.astralscience.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;

public class ScreenElement {
    public final Identifier id;
    public Vec2f pos;
    public Vec2f size;
    public Vec2f displaySize;
    public boolean activated;
    public int random;
    public Screen screen;

    public ScreenElement(Screen screen, Identifier id, Vec2f pos, Vec2f size, int random) {
        this.id = id;
        this.pos = pos;
        this.size = size;
        this.displaySize = size;
        this.random = random;
        this.screen = screen;
    }
    public boolean contains(Vec2f testPos) {
        return (
                this.pos.x < testPos.x &&
                this.pos.x + this.displaySize.x > testPos.x &&
                this.pos.y < testPos.y &&
                this.pos.y + this.displaySize.y > testPos.y
        );
    }

    public void activate() {
        if (!this.activated) {
            this.displaySize = new Vec2f(this.displaySize.x * 2, this.displaySize.y * 2);
            this.activated = true;
        } else {
            this.displaySize = new Vec2f(this.displaySize.x / 2, this.displaySize.y / 2);
            this.activated = false;
        }
    }
}




