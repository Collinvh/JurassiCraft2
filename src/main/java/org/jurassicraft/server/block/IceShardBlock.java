package org.jurassicraft.server.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jurassicraft.server.item.ItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IceShardBlock extends Block {
    IceShardBlock() {
        super(Material.ROCK);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = new ArrayList<>();

        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        if (rand.nextDouble() < 0.2 + ((fortune / 3.0) * 0.8)) {
            ret.add(new ItemStack(ItemHandler.SEA_LAMPREY));
        } else if (rand.nextDouble() < 0.2) {
            ret.add(new ItemStack(Blocks.ICE));
        }

        return ret;
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }
}
