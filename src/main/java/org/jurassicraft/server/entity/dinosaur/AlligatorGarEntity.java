package org.jurassicraft.server.entity.dinosaur;

import org.jurassicraft.server.entity.SwimmingDinosaurEntity;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.vecmath.Vector3f;

public class AlligatorGarEntity extends SwimmingDinosaurEntity
{
    public AlligatorGarEntity(World world) {
        super(world);
        this.target(new DinosaurClassAttackPredicate(EntitySquid.class));
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        return null;
    }

    @Override
    public Vector3f getDinosaurCultivatorRotation() {
        return new Vector3f(-90F, 0, 0);
    }
}