package org.jurassicraft.server.plugin.jei.category.skeletonassembly;

import org.jurassicraft.server.entity.Dinosaur;

public class SkeletonInput {
    public final Dinosaur dinosaur;
    public final boolean fresh;

    public SkeletonInput(Dinosaur dinosaur, boolean fresh) {
        this.dinosaur = dinosaur;
        this.fresh = fresh;
    }
}
