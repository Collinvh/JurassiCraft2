package org.jurassicraft.server.entity.ai.animations;

import net.minecraft.entity.ai.EntityAIBase;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.ai.core.Mutex;

public class RearingUpAnimationAI extends EntityAIBase
{
    protected DinosaurEntity entity;

    public RearingUpAnimationAI(DinosaurEntity entity)
    {
        super();
        this.entity = entity;
        this.setMutexBits(Mutex.ANIMATION);
    }

    @Override
    public boolean shouldExecute()
    {
        return !this.entity.isBusy() && this.entity.getRNG().nextDouble() < 0.01;
    }

    @Override
    public void startExecuting()
    {
        this.entity.setAnimation(EntityAnimation.REARING_UP.get());
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return false;
    }
}

