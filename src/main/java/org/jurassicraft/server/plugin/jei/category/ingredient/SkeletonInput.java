package org.jurassicraft.server.plugin.jei.category.ingredient;

import org.jurassicraft.server.dinosaur.Dinosaur;

public class SkeletonInput {
    public final Dinosaur dinosaur;
    public final boolean fresh;

    public SkeletonInput(Dinosaur dinosaur, boolean fresh) {
        this.dinosaur = dinosaur;
        this.fresh = fresh;
    }
}
