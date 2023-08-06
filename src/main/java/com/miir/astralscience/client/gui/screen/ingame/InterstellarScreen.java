//package com.miir.astralscience.client.gui.screen.ingame;
//
//import com.miir.astralscience.block.entity.StarshipHelmBlockEntity;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.screen.AstralScreens;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.text.AstralText;
//
//public abstract class InterstellarScreen extends AstralScreens {
//    protected int x;
//    protected int y;
//    public StarshipHelmBlockEntity helm;
//    protected InterstellarScreen(AstralText title, StarshipHelmBlockEntity helm) {
//        super(title);
//        this.helm = helm;
//    }
//    @Override
//    public void init(MinecraftClient client, int width, int height) {
//        this.client = client;
//        this.itemRenderer = client.getItemRenderer();
//        this.textRenderer = client.textRenderer;
//        this.width = width;
//        this.height = height;
//        this.buttons.clear();
//        this.children.clear();
//        this.setFocused(null);
//        super.init();
//        this.x = (width) / 2;
//        this.y = (height) / 2;
//    }
//
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        this.renderBackground(matrices);
//        super.render(matrices, mouseX, mouseY, delta);
//    }
//}
