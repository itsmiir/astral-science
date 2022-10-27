//package com.miir.astralscience.client.gui.screen.ingame;
//
//import com.miir.interstellar.Interstellar;
//import com.miir.astralscience.block.entity.StarshipHelmBlockEntity;
//import com.miir.astralscience.client.gui.screen.PlanetElement;
//import com.miir.astralscience.client.gui.screen.ScreenElement;
//import com.miir.interstellar.dimension.PlanetInfo;
//import com.miir.interstellar.generation.NameGen;
//import com.miir.astralscience.screen.StarshipHelmScreenHandler;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.screen.ingame.HandledScreen;
//import net.minecraft.client.render.*;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.item.ItemStack;
//import net.minecraft.text.AstralText;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.Vec2f;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//import static com.miir.astralscience.client.gui.screen.ingame.GalacticMapScreen.MILKY_WAY;
//
//public class SystemScreen extends InterstellarScreen {
//    protected final ItemStack map;
//    public final HandledScreen<StarshipHelmScreenHandler> parent;
//    private final PlanetInfo focusStar;
//    private final Random random;
//    private ArrayList<String> dimensions;
//    private ArrayList<PlanetElement> icons;
//    private static final Identifier ICONS = Interstellar.id("textures/gui/planet_icons.png");
//    private int b;
//    private int a;
//
//    public SystemScreen(ItemStack map, StarshipHelmBlockEntity helm, HandledScreen<StarshipHelmScreenHandler> parent, PlanetInfo hostStar) {
//        super(AstralText.of(NameGen.fromId(hostStar.toString()) + " System"), helm);
//        this.map = map;
//        this.parent = parent;
//        this.x = 0;
//        this.y = 0;
//        this.focusStar =hostStar;
//        this.random = new Random();
//        this.dimensions = new ArrayList<>();
//        this.icons = new ArrayList<>();
//    }
//
//    @Override
//    public void init(MinecraftClient client, int width, int height) {
//        super.init(client, width, height);
//        int ratio = this.width / this.height;
//        this.b = random.nextInt(this.height / 2);
//        this.a = ratio * b;
//        for (PlanetInfo dimension :
//                this.focusStar.children) {
//            this.dimensions.add(dimension.name + Interstellar.ORBIT_SUFFIX);
//        }
//        if (this.client != null) {
////            CompoundTag tag = this.map.getOrCreateTag();
////            for (int i = 1; i <= tag.getInt("dimensions"); i++) {
////                if (tag.getString(Integer.toString(i)).contains("_orbit")) {
////                    this.dimensions.add(tag.getString(Integer.toString(i)));
////                }
////            }
//            for (String dimension :
//                    dimensions) {
//                this.icons.add(new PlanetElement(this, Interstellar.id(dimension), new Vec2f(0, 0), new Vec2f(16, 16), random.nextInt(25), ((int) AstralMath.abs(PlanetInfo.getPlanetInfo(dimension).getX() - this.focusStar.getX()) * 2) + 50));
//            }
//        }
//    }
//
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        this.renderBackground(matrices);
//        this.drawPlanets(matrices);
//        this.drawHost(matrices);
//    }
//
//    private void drawPlanets(MatrixStack matrices) {
//        int centerX = this.width / 2 - 4;
//        int centerY = this.height / 2 - 4;
//        for (PlanetElement dimension : this.icons) {
//            // math time
//            //            x : y
//            float ratio = 1 / 1;
//            float t = (float) (((System.nanoTime() / 500000) % (1000000 * AstralMath.PI)));
//            this.client.getTextureManager().bindTexture(ICONS);
//            t = t / 10000;
//            int procession = dimension.random;
//            int x = (int)(centerX + (dimension.radius * AstralMath.cos(t + procession)));
//            int y = (int)(centerY + (dimension.radius * ratio * AstralMath.sin(t + procession)));
//            this.drawTexture(matrices, x, y, 0, 0, ((int) dimension.displaySize.x), ((int) dimension.displaySize.y));
//            dimension.pos = new Vec2f(x, y);
//        }
//    }
//
//
//    @Override
//    public void onClose() {
//        if (this.client != null) {
//            this.client.openScreen(new GalacticMapScreen(this.map, this.helm, this.parent));
//        }
//    }
//
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
//        return true;
//    }
//
//    @Override
//    public void renderBackground(MatrixStack matrices, int vOffset) {
//        super.renderBackground(matrices, vOffset);
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
//            this.client.getTextureManager().bindTexture(GalacticMapScreen.MAP_BACKGROUND);
//            float f = 16f;
//            int i = 250;
//            int r = 100;
//            int g = 100;
//            int b = 100;
//            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
//
////            render the four quadrants of the map grid, it's done this way to make zooming into the center of the map easier
//
////            bottom right
//            bufferBuilder.vertex(this.width / 2D, this.height, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width, this.height, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width, this.height / 2D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width / 2D, this.height / 2D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();
//
////            top left
//            bufferBuilder.vertex(this.width / 2D, 0D, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(0D, 0D, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(0D, this.height / 2D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width / 2D, this.height / 2D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();
//
////            top right
//            bufferBuilder.vertex(this.width / 2D, this.height / 2.0D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width, this.height / 2.0D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width, 0.0D, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width / 2D, 0.0D, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//
////            bottom left
//            bufferBuilder.vertex(this.width / 2D, this.height / 2D, 0.0D).texture(0.0F, 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(0D, this.height / 2D, 0.0D).texture((float) this.width / f, 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(0D, this.height, 0.0D).texture((float) this.width / f, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//            bufferBuilder.vertex(this.width / 2D, this.height, 0.0D).texture(0.0F, (float) this.height / f + 0.0F).color(r, g, b, i).next();
//
//            tessellator.draw();
////        }
//        }
//    }
//
//    @Override
//    public boolean isMouseOver(double mouseX, double mouseY) {
//        System.out.println(mouseX);
//        return super.isMouseOver(mouseX, mouseY);
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        for (ScreenElement element :
//                this.icons) {
//            if (element.contains(new Vec2f(((float) mouseX), ((float) mouseY)))) {
//                element.activate();
//                return true;
//            }
//        }
//        return false;
//    }
//    private void drawHost(MatrixStack matrices) {
//        int centerX = this.width / 2 - 8;
//        int centerY = this.height / 2 - 8;
//        this.client.getTextureManager().bindTexture(ICONS);
//        this.drawTexture(matrices, centerX, centerY, 32, 0, 16, 16);
//    }
//
//}
