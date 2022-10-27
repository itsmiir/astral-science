package com.miir.astralscience.client.gui.screen.ingame;

import com.miir.astralscience.AstralClient;
import com.miir.astralscience.AstralScience;
import com.miir.astralscience.item.AstralItems;
import com.miir.astralscience.screen.StarshipHelmScreenHandler;
import com.miir.astralscience.world.dimension.AstralDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;

import java.util.ArrayList;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class StarshipHelmScreen extends HandledScreen<StarshipHelmScreenHandler> {

    public static final Identifier TEXTURE = AstralScience.id("textures/gui/container/starship_helm.png");
    public static final Identifier MAP_BUTTONS = AstralScience.id("textures/gui/map_button.png");
    private static final Identifier ICONS = AstralScience.id("textures/gui/planet_icons.png");
    public static final Identifier MAP_BACKGROUND = AstralScience.id("textures/gui/map_background-0-0.png");
    public static final Identifier MILKY_WAY = AstralScience.id("textures/gui/milky_way.png");


    public int mode;
    public ItemStack map;
    private float zoom;
    private Vec2f focus;

    private Random random;
    private ArrayList<String> dimensions;

    public StarshipHelmScreen(StarshipHelmScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.mode = 0;
        this.zoom = 0;

        this.random = new Random();
        this.dimensions = new ArrayList<>();
        this.focus = AstralClient.LAST_MAP_FOCUS;
        this.zoom = AstralClient.LAST_MAP_ZOOM;

        if (this.handler.inventory.getStack(3).getItem().equals(AstralItems.GALACTIC_MAP)) {
            this.map = this.handler.inventory.getStack(3);
        } else {
            this.map = new ItemStack(AstralItems.GALACTIC_MAP);
        }
    }

    @Override
    protected void init() {
        super.init();
        //                case 0 -> {
        //                    this.buttons.clear();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        switch (this.mode) {
            case 0 -> {
                super.render(matrices, mouseX, mouseY, delta);
                this.drawDestination(63, 19, matrices);
                this.drawEnergy(matrices, delta);
                this.drawMouseoverTooltip(matrices, mouseX, mouseY);
            }
            case 1 -> {
                this.x = 0;
                this.y = 0;
                String coords = mouseX + ", " + mouseY;
//                super.render(matrices, mouseX, mouseY, delta);
//                this.drawBackgroundElements(matrices);
                this.drawBackground(matrices, delta, mouseX, mouseY);
                MinecraftClient.getInstance().textRenderer.draw(matrices, Text.translatable("gui.ntrstlr.destination"), x + 5, y + 5, 0x90ff00);
                MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of(AstralDimensions.fromId(this.getScreenHandler().getHelm().getDestination().getValue().getPath())), x + 5, y + 15, 0x90ff00);
                MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of(coords), x + 5, y + 25, 0x90ff00);
            }
            case 2 -> this.drawBackground(matrices, delta, mouseX, mouseY);
        }
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        if (this.client != null) {
            int i;
            int j;
                    this.renderBackground(matrices);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    this.client.getTextureManager().bindTexture(TEXTURE);
                    i = this.x;
                    j = this.y;
                    this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferBuilder = tessellator.getBuffer();
                    this.client.getTextureManager().bindTexture(MILKY_WAY);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                    bufferBuilder.vertex(0.0D, this.height, 0.0D).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
                    bufferBuilder.vertex(this.width, this.height, 0.0D).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
                    bufferBuilder.vertex(this.width, 0.0D, 0.0D).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
                    bufferBuilder.vertex(0.0D, 0.0D, 0.0D).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
                    BufferRenderer.drawWithShader(bufferBuilder.end());
                    this.client.getTextureManager().bindTexture(MAP_BACKGROUND);
                    float f = 16f + this.zoom * 16;
                    i = 250;
                    int r = 100;
                    int g = 100;
                    int b = 100;
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

//            render the four quadrants of the map grid, it's done this way to make zooming into the center of the map easier

//            bottom right
                    bufferBuilder.vertex(this.width / 2D, this.height, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width, this.height, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width, this.height / 2D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width / 2D, this.height / 2D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();

//            top left
                    bufferBuilder.vertex(this.width / 2D, 0D, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(0D, 0D, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(0D, this.height / 2D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width / 2D, this.height / 2D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();

//            top right
                    bufferBuilder.vertex(this.width / 2D, this.height / 2.0D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width, this.height / 2.0D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width, 0.0D, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width / 2D, 0.0D, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();

//            bottom left
                    bufferBuilder.vertex(this.width / 2D, this.height / 2D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(0D, this.height / 2D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(0D, this.height, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
                    bufferBuilder.vertex(this.width / 2D, this.height, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
                    tessellator.draw();
        }
    }

    public void drawDestination(int x, int y, MatrixStack matrices) {
        if (this.client != null) {
            TextRenderer textRenderer = this.client.textRenderer;
            if (this.handler.getHelm().getDestination() != null) {
                String destination = this.handler.getHelm().getDestination().getValue().getPath();
                String displayName = AstralDimensions.fromId(destination);
                if (this.handler.getHelm().isLinked()) {
                    textRenderer.draw(matrices, Text.translatable("gui.ntrstlr.destination"), this.x + x, y + this.y, 0x90ff00);
                    textRenderer.draw(matrices, displayName, this.x + x, y + this.y + 10, 0x90ff00);
                } else {
                    textRenderer.draw(matrices, Text.translatable("gui.ntrstlr.unlinked"), this.x + x, y + this.y, 0xd51717);
                }
            }
        }
    }

    protected void drawEnergy(MatrixStack matrices, float delta) {
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
    }

    protected void drawBackgroundElements(MatrixStack matrices) {
        if (this.client != null) {
            TextureManager textureManager = this.client.getTextureManager();
            textureManager.bindTexture(StarshipHelmScreen.MAP_BUTTONS);
            this.drawTexture(matrices, this.x, this.y, 70, 0, 145 - 70, 33);
        }
    }



    @Override
    public void close() {
        if (this.mode > 0) {
            this.mode -= 1;
            this.init();
        } else {
            super.close();
        }
    }



    enum MODE {
        MAIN,
        GALAXY,
        SYSTEM
    }
}
