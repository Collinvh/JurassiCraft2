package org.jurassicraft.server.item;

import org.jurassicraft.server.entity.item.MuralEntity;
import org.jurassicraft.server.tab.TabHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MuralItem extends Item {
    public MuralItem() {
        this.setCreativeTab(TabHandler.DECORATIONS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    	ItemStack stack = player.getHeldItem(hand);
        if (side != EnumFacing.DOWN && side != EnumFacing.UP) {
            BlockPos offset = pos.offset(side);

            if (player.canPlayerEdit(offset, side, stack)) {
                MuralEntity mural = new MuralEntity(world, offset, side);

                if (!mural.onValidSurface()) {
                	if(world.isRemote) {
                		TextComponentString text = new TextComponentString(TextFormatting.RED + "Try Using the Mural on a Bigger Surface!");
                		player.sendStatusMessage(text, true);
                	}
                }
                
                if (mural.onValidSurface()) {
                    if (!world.isRemote) {
                        world.spawnEntity(mural);	
                    }

                    stack.shrink(1);

                    return EnumActionResult.SUCCESS;
                }
            }
        }

        return EnumActionResult.PASS;
    }
}
