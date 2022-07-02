//package com.miir.astralscience.client.gui.screen.ingame;
//
//import com.miir.interstellar.Interstellar;
//import com.miir.astralscience.AstralClient;
//import com.miir.astralscience.client.gui.screen.PlanetElement;
//import com.miir.interstellar.dimension.PlanetInfo;
//import com.miir.interstellar.generation.NameGen;
//import com.miir.interstellar.item.GalacticMapItem;
//import com.miir.interstellar.item.InterstellarItems;
//import com.miir.astralscience.screen.StarshipHelmScreenHandler;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.font.TextRenderer;
//import net.minecraft.client.gui.screen.ingame.HandledScreen;
//import net.minecraft.client.render.*;
//import net.minecraft.client.texture.TextureManager;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.text.Text;
//import net.minecraft.text.TranslatableText;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.Vec2f;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//@Environment(EnvType.CLIENT)
//public class StarshipHelmScreen extends HandledScreen<StarshipHelmScreenHandler> {
//
//    public static final Identifier TEXTURE = Interstellar.id("textures/gui/container/starship_helm.png");
//    public static final Identifier MAP_BUTTONS = Interstellar.id("textures/gui/map_button.png");
//    private static final Identifier ICONS = Interstellar.id("textures/gui/planet_icons.png");
//    public static final Identifier MAP_BACKGROUND = Interstellar.id("textures/gui/map_background-0-0.png");
//    public static final Identifier MILKY_WAY = Interstellar.id("textures/gui/milky_way.png");
//
//
//    public int mode;
//    public ItemStack map;
//    private float zoom;
//    private Vec2f focus;
//
//    private Random random;
//    private ArrayList<String> dimensions;
//    private ArrayList<PlanetElement> icons;
//
//    public StarshipHelmScreen(StarshipHelmScreenHandler handler, PlayerInventory inventory, Text title) {
//        super(handler, inventory, title);
//        this.mode = 0;
//        this.zoom = 0;
//
//        this.random = new Random();
//        this.dimensions = new ArrayList<>();
//        this.icons = new ArrayList<>();
//        this.focus = AstralClient.LAST_MAP_FOCUS;
//        this.zoom = AstralClient.LAST_MAP_ZOOM;
//
//        if (this.handler.inventory.getStack(3).getItem().equals(InterstellarItems.GALACTIC_MAP)) {
//            this.map = this.handler.inventory.getStack(3);
//        } else {
//            this.map = new ItemStack(InterstellarItems.GALACTIC_MAP);
//        }
//    }
//
//    @Override
//    protected void init() {
//        if (this.client != null) {
//            switch (this.mode) {
//                case 0:
//                    this.buttons.clear();
//                    super.init();
//                    this.addButton(new InterstellarTexturedButton(this.x + 149, this.y + 16, 20, 18, 0, 0, 19, MAP_BUTTONS, (buttonWidget) -> {
//                        this.mode = 1; // (new GalacticMapScreen(this.handler.inventory.getStack(3), this.handler.getHelm(), this));
//                        this.init();
//                    },
//                            new TranslatableText("gui.ntrstlr.open_map"),
//                            this
//                    ));
//                    this.addButton(new InterstellarTexturedButton(
//                            this.x + 149,
//                            this.y + 35,
//                            20, 18,
//                            21, 0,
//                            19, MAP_BUTTONS,
//                            (buttonWidget) -> {
//                                PacketByteBuf packet = PacketByteBufs.create();
//                                packet.writeBlockPos(this.handler.getHelm().getPos());
//                                packet.writeString(this.handler.getHelm().getDestination().name);
//                                ClientPlayNetworking.send(Interstellar.WARP, packet);
//                            },
//                            new TranslatableText("gui.ntrstlr.warp"),
//                            this
//                    ));
//                    break;
//                case 1:
//                    this.addButton(new InterstellarTexturedButton(
//                            0, 50,
//                            20, 18,
//                            0, 38,
//                            19,
//                            StarshipHelmScreen.MAP_BUTTONS,
//                            (button) -> {
//                                this.mode = 2;
//                                this.init();
//                            },
//                            new TranslatableText("gui.ntrstlr.galaxymap"),
//                            this
//                    ));
//            }
//        }
//    }
//
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        switch (this.mode) {
//            case 0:
//                super.render(matrices, mouseX, mouseY, delta);
//                this.drawDestination(63, 19, matrices);
//                this.drawEnergy(matrices, delta);
//                this.drawMouseoverTooltip(matrices, mouseX, mouseY);
//                break;
//            case 1:
//                this.x = 0;
//                this.y = 0;
//                String coords = mouseX + ", " + mouseY;
////                super.render(matrices, mouseX, mouseY, delta);
////                this.drawBackgroundElements(matrices);
//                this.drawBackground(matrices, delta, mouseX, mouseY);
//                this.drawPlanets(matrices);
//                MinecraftClient.getInstance().textRenderer.draw(matrices, new TranslatableText("gui.ntrstlr.destination"), x + 5, y + 5, 0x90ff00);
//                MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of(NameGen.fromId(this.getScreenHandler().getHelm().getDestination().name)), x + 5, y + 15, 0x90ff00);
//                MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of(coords), x + 5, y + 25, 0x90ff00);
//                break;
//            case 2:
//                this.drawBackground(matrices, delta, mouseX, mouseY);
//                this.drawPlanets(matrices);
//                this.drawHost(matrices);
//                break;
//        }
//    }
//
//    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
//        if (this.client != null) {
//            switch (this.mode) {
//                case 0:
//                    this.renderBackground(matrices);
//                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//                    this.client.getTextureManager().bindTexture(TEXTURE);
//                    int i = this.x;
//                    int j = this.y;
//                    this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
//                    break;
//                case 1:
//                case 2:
//                    Tessellator tessellator = Tessellator.getInstance();
//                    BufferBuilder bufferBuilder = tessellator.getBuffer();
//                    this.client.getTextureManager().bindTexture(MILKY_WAY);
//                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
//                    bufferBuilder.vertex(0.0D, this.height, 0.0D).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
//                    bufferBuilder.vertex(this.width, this.height, 0.0D).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
//                    bufferBuilder.vertex(this.width, 0.0D, 0.0D).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
//                    bufferBuilder.vertex(0.0D, 0.0D, 0.0D).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
//                    bufferBuilder.end();
//                    BufferRenderer.draw(bufferBuilder);
//                    this.client.getTextureManager().bindTexture(MAP_BACKGROUND);
//                    float f = 16f + this.zoom * 16;
//                    i = 250;
//                    int r = 100;
//                    int g = 100;
//                    int b = 100;
//                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
//
////            render the four quadrants of the map grid, it's done this way to make zooming into the center of the map easier
//
////            bottom right
//                    bufferBuilder.vertex(    this.width / 2D,             this.height,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(            this.width,             this.height,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(            this.width,     this.height / 2D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(    this.width / 2D,     this.height / 2D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//
////            top left
//                    bufferBuilder.vertex(    this.width / 2D,                   0D,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(                 0D,                   0D,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(                 0D,     this.height / 2D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(    this.width / 2D,     this.height / 2D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//
////            top right
//                    bufferBuilder.vertex(    this.width / 2D,   this.height / 2.0D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(            this.width,   this.height / 2.0D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(            this.width,                 0.0D,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(    this.width / 2D,                 0.0D,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//
////            bottom left
//                    bufferBuilder.vertex(    this.width / 2D,     this.height / 2D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(                 0D,     this.height / 2D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(                 0D,             this.height,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//                    bufferBuilder.vertex(    this.width / 2D,             this.height,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//
//                    tessellator.draw();
//                    break;
//            }
//        }
//    }
//
//    public void drawDestination(int x, int y, MatrixStack matrices) {
//        if (this.client != null) {
//            TextRenderer textRenderer = this.client.textRenderer;
//            if (this.handler.getHelm().getDestination().name != null) {
//                String destination = this.handler.getHelm().getDestination().name;
//                String displayName = NameGen.fromId(destination);
//                if (this.handler.getHelm().isLinked()) {
//                    textRenderer.draw(matrices, new TranslatableText("gui.ntrstlr.destination"), this.x + x, y + this.y, 0x90ff00);
//                    textRenderer.draw(matrices, displayName, this.x + x, y + this.y + 10, 0x90ff00);
//                } else {
//                    textRenderer.draw(matrices, new TranslatableText("gui.ntrstlr.unlinked"), this.x + x, y + this.y, 0xd51717);
//                }
//            }
//        }
//    }
//
//    protected void drawEnergy(MatrixStack matrices, float delta) {
//    }
//
//    @Override
//    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
//        super.drawForeground(matrices, mouseX, mouseY);
//    }
//
//    protected void drawBackgroundElements(MatrixStack matrices) {
//        if (this.client != null) {
//            TextureManager textureManager = this.client.getTextureManager();
//            textureManager.bindTexture(StarshipHelmScreen.MAP_BUTTONS);
//            this.drawTexture(matrices, this.x, this.y, 70, 0, 145 - 70, 33);
//        }
//    }
//
//    private void drawHost(MatrixStack matrices) {
//        int centerX = this.width / 2 - 8;
//        int centerY = this.height / 2 - 8;
//        this.client.getTextureManager().bindTexture(ICONS);
//        this.drawTexture(matrices, centerX, centerY, 32, 0, 16, 16);
//    }
//
//
//    private void drawPlanets(MatrixStack matrices) {
//        switch (this.mode) {
//            case 1:
//                this.buttons.clear();
//                int offsetX = this.width / 2;
//                int offsetY = this.height / 2;
//                if (this.map.getTag() != null && this.client != null) {
//                    CompoundTag tag = this.map.getOrCreateTag();
//                    ArrayList<String> dimensions = new ArrayList<>();
//                    for (int i = 1; i <= tag.getInt("dimensions"); i++) {
//                        dimensions.add(tag.getString(Integer.toString(i)));
//                    }
//                    for (String dimension :
//                            dimensions) {
//                        try {
//                            PlanetInfo hostPlanetInfo = PlanetInfo.getPlanetInfo(NameGen.deorbitify(dimension));
//                            if (hostPlanetInfo.getHostName().equals(Interstellar.id(Interstellar.ROOT_BLACK_HOLE_CODE).toString())) {
//                                PacketByteBuf packet = PacketByteBufs.create();
//                                packet.writeString(dimension);
//                                packet.writeFloat(this.zoom);
//                                try {
//                                    int x = (int) (hostPlanetInfo.getX() + offsetX);
//                                    int y = (int) (hostPlanetInfo.getY() + offsetY);
//                                    int u = hostPlanetInfo.type.equals("star") ? 32 : (Integer.toBinaryString(hostPlanetInfo.flags)).endsWith("1") ? 16 : 0;
//                                    int v = 0;
//                                    if (client.currentScreen instanceof GalacticMapScreen) {
//                                        ((GalacticMapScreen) client.currentScreen).addButton(
//                                                new PlanetDestinationButton(
//                                                        offsetX + (int) (x * (this.zoom + 1)) - (int) this.focus.x, offsetY + (int) (y * (this.zoom + 1)) - (int) this.focus.y,
//                                                        u, v,
//                                                        16,
//                                                        16,
//                                                        dimension,
//                                                        this
//                                                ));
//                                    }
//                                } catch (NullPointerException ignored) {
//                                }
//                            }
//                        } catch (NullPointerException ignored) {
//                        }
//                    }
//                }
//                break;
//            case 2:
//                int centerX = this.width / 2 - 4;
//                int centerY = this.width / 2 - 4;
//                for (PlanetElement dimension : this.icons) {
//                    // math time
//                    //            x : y
//                    float ratio = 1 / 1;
//                    float t = (float) (((System.nanoTime() / 500000) % (1000000 * AstralMath.PI)));
//                    this.client.getTextureManager().bindTexture(ICONS);
//                    t = t / 10000;
//                    int procession = dimension.random;
//                    int x = (int) (centerX + (dimension.radius * AstralMath.cos(t + procession)));
//                    int y = (int) (centerY + (dimension.radius * ratio * AstralMath.sin(t + procession)));
//                    this.drawTexture(matrices, x, y, 0, 0, ((int) dimension.displaySize.x), ((int) dimension.displaySize.y));
//                    dimension.pos = new Vec2f(x, y);
//                }
//        }
//    }
//
//    @Override
//    public void onClose() {
//        if (this.mode > 0) {
//            this.mode -= 1;
//            this.init();
//        } else {
//            super.onClose();
//        }
//    }
//
//
//
//    enum MODE {
//        MAIN,
//        GALAXY,
//        SYSTEM
//    }
//}
