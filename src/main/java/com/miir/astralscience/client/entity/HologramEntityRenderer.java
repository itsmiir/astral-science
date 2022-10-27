package com.miir.astralscience.client.entity;

import com.miir.astralscience.entity.InteractableHologramEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class HologramEntityRenderer extends EntityRenderer<InteractableHologramEntity> {
    public HologramEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(InteractableHologramEntity entity) {
        return null;
    }
}
