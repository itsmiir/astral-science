//package com.miir.astralscience.client.gui.screen.ingame;
//
//import com.miir.interstellar.Interstellar;
//import com.miir.astralscience.AstralClient;
//import com.miir.astralscience.block.entity.StarshipHelmBlockEntity;
//import com.miir.interstellar.dimension.PlanetInfo;
//import com.miir.interstellar.generation.NameGen;
//import com.miir.interstellar.item.InterstellarItems;
//import com.miir.astralscience.screen.StarshipHelmScreenHandler;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.screen.ingame.HandledScreen;
//import net.minecraft.client.gui.widget.AbstractButtonWidget;
//import net.minecraft.client.render.*;
//import net.minecraft.client.texture.TextureManager;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.text.AstralText;
//import net.minecraft.text.TranslatableText;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.Vec2f;
//
//import java.util.ArrayList;
//
//@Environment(EnvType.CLIENT)
//public class GalacticMapScreen extends InterstellarScreen {
//    protected final ItemStack map;
//    public final HandledScreen<StarshipHelmScreenHandler> parent;
//    private Vec2f focus;
//    private float zoom;
//
//    public static final Identifier MAP_BACKGROUND = Interstellar.id("textures/gui/map_background-0-0.png");
//    public static final Identifier MILKY_WAY = Interstellar.id("textures/gui/milky_way.png");
//
//    public GalacticMapScreen(ItemStack map, StarshipHelmBlockEntity helm, HandledScreen<StarshipHelmScreenHandler> parent) {
//        super(new TranslatableText("gui.ntrstlr.galactic_map"), helm);
//        this.parent = parent;
//        if (map.getItem().equals(InterstellarItems.GALACTIC_MAP)) {
//            this.map = map;
//        } else {
//            this.map = new ItemStack(InterstellarItems.GALACTIC_MAP);
//        }
//        this.client = MinecraftClient.getInstance();
//        this.x = 0;
//        this.y = 0;
////        this.focus = new Vec2f(this.helm.getDestination().x, this.helm.getDestination().y);
//        this.focus = AstralClient.LAST_MAP_FOCUS;
//        this.zoom = AstralClient.LAST_MAP_ZOOM;
//    }
//
//    @Override
//    public void init(MinecraftClient client, int width, int height) {
//        super.init(client, width, height);
//        this.addButton(new InterstellarTexturedButton(
//                0, 50,
//                20, 18,
//                0, 38,
//                19,
//                StarshipHelmScreen.MAP_BUTTONS,
//                (button) -> this.client.openScreen(new SystemScreen(this.map, this.helm, this.parent, this.helm.getDestination())),
//                new TranslatableText("gui.ntrstlr.galaxymap"),
//                this
//        ));
//    }
//
//    @Override
//    public void onClose() {
//        if (this.client != null) {
//            this.client.openScreen(parent);
//        }
//    }
//
//    @Override
//    public AstralText getTitle() {
//        return new TranslatableText("gui.ntrstlr.galactic_map");
//    }
//
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
//        if (this.zoom <= 1F && this.zoom >= 0F) {
//            this.zoom += amount / 100;
//            if (this.zoom >= 1F) {
//                this.zoom = 1.0F;
//            }
//            if (this.zoom <= 0.0F) {
//                this.zoom = 0.0F;
//            }
//            this.init();
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        this.x = 0;
//        this.y = 0;
//        String coords = mouseX + ", " + mouseY;
//        super.render(matrices, mouseX, mouseY, delta);
//        this.drawBackgroundElements(matrices);
//        this.drawPlanets();
//        MinecraftClient.getInstance().textRenderer.draw(matrices, new TranslatableText("gui.ntrstlr.destination"), x + 5, y + 5, 0x90ff00);
//        MinecraftClient.getInstance().textRenderer.draw(matrices, AstralText.of(NameGen.fromId(this.parent.getScreenHandler().getHelm().getDestination().name)), x + 5, y + 15, 0x90ff00);
//        MinecraftClient.getInstance().textRenderer.draw(matrices, AstralText.of(coords), x + 5, y + 25, 0x90ff00);
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
//    private void drawPlanets() {
//        this.buttons.clear();
//        int offsetX = this.width / 2;
//        int offsetY = this.height / 2;
//        if (this.map.getTag() != null && this.client != null) {
//            CompoundTag tag = this.map.getOrCreateTag();
//            ArrayList<String> dimensions = new ArrayList<>();
//            for (int i = 1; i <= tag.getInt("dimensions"); i++) {
//                dimensions.add(tag.getString(Integer.toString(i)));
//            }
//            for (String dimension :
//                    dimensions) {
//                try {
//                    PlanetInfo hostPlanetInfo = PlanetInfo.getPlanetInfo(NameGen.deorbitify(dimension));
//                    if (hostPlanetInfo.getHostName().equals(Interstellar.id(Interstellar.ROOT_BLACK_HOLE_CODE).toString())) {
//                        PacketByteBuf packet = PacketByteBufs.create();
//                        packet.writeString(dimension);
//                        packet.writeFloat(this.zoom);
//                        try {
//                            int x = (int) (hostPlanetInfo.getX() + offsetX);
//                            int y = (int) (hostPlanetInfo.getY() + offsetY);
//                            int u = hostPlanetInfo.type.equals("star") ? 32 : (Integer.toBinaryString(hostPlanetInfo.flags)).endsWith("1") ? 16 : 0;
//                            int v = 0;
//                            if (client.currentScreen instanceof GalacticMapScreen) {
//                                ((GalacticMapScreen) client.currentScreen).addButton(
//                                        new PlanetDestinationButton(
//                                                offsetX + (int) (x * (this.zoom + 1)) - (int) this.focus.x, offsetY + (int) (y * (this.zoom + 1)) - (int) this.focus.y,
//                                                u, v,
//                                                16,
//                                                16,
//                                                dimension,
//                                                this
//                                        ));
//                            }
//                        } catch (NullPointerException ignored) {
//                        }
//                    }
//                } catch (NullPointerException ignored) {
//                }
//            }
//        }
//    }
//
//    public void setFocus(Vec2f vec2f) {
//        this.focus = vec2f;
//    }
//
//    public void setFocus(float x, float y) {
//        this.setFocus(new Vec2f(x, y));
//    }
//
//    public void setDestination(String pathOrbital) {
//        PacketByteBuf packet = PacketByteBufs.create();
//        packet.writeString(pathOrbital);
//        packet.writeBlockPos(this.parent.getScreenHandler().getHelm().getPos());
//        ClientPlayNetworking.send(Interstellar.SET_DESTINATION, packet);
//        this.parent.getScreenHandler().getHelm().setDestination(PlanetInfo.getPlanetInfo(NameGen.deorbitify(pathOrbital)));
//    }
//
//    @Override
//    public void renderBackground(MatrixStack matrices) {
//        if (this.client != null) {
//            Tessellator tessellator = Tessellator.getInstance();
//            BufferBuilder bufferBuilder = tessellator.getBuffer();
//            this.client.getTextureManager().bindTexture(MILKY_WAY);
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
//            bufferBuilder.vertex(0.0D, this.height, 0.0D).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
//            bufferBuilder.vertex(this.width, this.height, 0.0D).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
//            bufferBuilder.vertex(this.width, 0.0D, 0.0D).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
//            bufferBuilder.vertex(0.0D, 0.0D, 0.0D).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
//            bufferBuilder.end();
//            BufferRenderer.draw(bufferBuilder);
//            this.client.getTextureManager().bindTexture(MAP_BACKGROUND);
//            float f = 16f + this.zoom * 16;
//            int i = 250;
//            int r = 100;
//            int g = 100;
//            int b = 100;
//            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
//
////            render the four quadrants of the map grid, it's done this way to make zooming into the center of the map easier
//
////            bottom right
//            bufferBuilder.vertex(    this.width / 2D,             this.height,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(            this.width,             this.height,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(            this.width,     this.height / 2D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(    this.width / 2D,     this.height / 2D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//
////            top left
//            bufferBuilder.vertex(    this.width / 2D,                   0D,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(                 0D,                   0D,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(                 0D,     this.height / 2D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(    this.width / 2D,     this.height / 2D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//
////            top right
//            bufferBuilder.vertex(    this.width / 2D,   this.height / 2.0D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(            this.width,   this.height / 2.0D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(            this.width,                 0.0D,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(    this.width / 2D,                 0.0D,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//
////            bottom left
//            bufferBuilder.vertex(    this.width / 2D,     this.height / 2D,         0.0D).texture(                  0.0F,                           0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(                 0D,     this.height / 2D,         0.0D).texture((float) this.width / f,                           0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(                 0D,             this.height,         0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(    this.width / 2D,             this.height,         0.0D).texture(                  0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//
//            tessellator.draw();
//        }
//    }
//
//    @Override
//    public <T extends AbstractButtonWidget> T addButton(T button) {
//        return super.addButton(button);
//    }
//
//    @Override
//    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
//        assert this.client != null;
//        int i = (int)(deltaX * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth());
//        int j = (int)(deltaY * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight());
//        Vec2f newFocus = new Vec2f(this.focus.x - i*4, this.focus.y - j*4);
//        this.focus = newFocus;
//        return true;
//    }
//}
