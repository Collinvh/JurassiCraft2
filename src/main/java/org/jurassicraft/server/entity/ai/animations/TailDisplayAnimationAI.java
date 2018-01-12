package org.jurassicraft.server.entity.ai.animations;

import net.minecraft.entity.ai.EntityAIBase;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.ai.Mutex;

public class TailDisplayAnimationAI extends EntityAIBase {
    protected DinosaurEntity entity;

    public TailDisplayAnimationAI(DinosaurEntity entity) {
        super();
        this.entity = entity;
        this.setMutexBits(Mutex.ANIMATION);
    }

    @Override
    public boolean shouldExecute() {
        return !this.entity.isBusy() && this.entity.getRNG().nextDouble() < 0.01;
    }

    @Override
    public void startExecuting() {
        this.entity.setAnimation(EntityAnimation.TAIL_DISPLAY.get());
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }
}

