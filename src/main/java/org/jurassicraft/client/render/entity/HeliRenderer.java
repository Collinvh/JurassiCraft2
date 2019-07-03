package org.jurassicraft.client.render.entity;

import org.jurassicraft.client.model.animation.entity.vehicle.HelicopterAnimator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HeliRenderer extends HelicopterRenderer {

    public HeliRenderer(RenderManager manager) {
        super(manager, "helicopter");
    }

    @Override
    protected HelicopterAnimator createCarAnimator() {
        return new HelicopterAnimator();
    }
}