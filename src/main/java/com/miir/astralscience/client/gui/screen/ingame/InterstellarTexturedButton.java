package com.miir.astralscience.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InterstellarTexturedButton extends TexturedButtonWidget {
    protected Text renderText;
    protected Screen screen;
    public InterstellarTexturedButton(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, PressAction pressAction, Text renderText, Screen screen) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, pressAction);
        this.renderText = renderText;
        this.screen = screen;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderButton(matrices, mouseX, mouseY, delta);
        if (this.isHovered() && MinecraftClient.getInstance() != null) {
            MinecraftClient client = MinecraftClient.getInstance();
            int x = client.getWindow().getWidth();
            int y = client.getWindow().getHeight();
            screen.renderTooltip(matrices, this.renderText, mouseX, mouseY);
        }
    }
}
