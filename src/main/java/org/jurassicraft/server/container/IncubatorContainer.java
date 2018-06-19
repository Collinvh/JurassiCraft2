package org.jurassicraft.server.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.jurassicraft.server.api.IncubatorEnvironmentItem;
import org.jurassicraft.server.block.entity.IncubatorBlockEntity;
import org.jurassicraft.server.container.slot.CustomSlotOLD;
import org.jurassicraft.server.item.ItemHandler;

import java.util.function.Predicate;

public class IncubatorContainer extends MachineContainerOLD {
    private IncubatorBlockEntity incubator;

    public IncubatorContainer(InventoryPlayer playerInventory, TileEntity tileEntity) {
        super((IInventory) tileEntity);

        this.incubator = (IncubatorBlockEntity) tileEntity;
        Predicate<ItemStack> eggPredicate = stack -> stack.getItem() == ItemHandler.EGG;
        this.addSlotToContainer(new CustomSlotOLD(this.incubator, 0, 33, 28, eggPredicate));
        this.addSlotToContainer(new CustomSlotOLD(this.incubator, 1, 56, 21, eggPredicate));
        this.addSlotToContainer(new CustomSlotOLD(this.incubator, 2, 79, 14, eggPredicate));
        this.addSlotToContainer(new CustomSlotOLD(this.incubator, 3, 102, 21, eggPredicate));
        this.addSlotToContainer(new CustomSlotOLD(this.incubator, 4, 125, 28, eggPredicate));

        this.addSlotToContainer(new CustomSlotOLD(this.incubator, 5, 79, 49, stack -> stack.getItem() instanceof IncubatorEnvironmentItem || Block.getBlockFromItem(stack.getItem()) instanceof IncubatorEnvironmentItem));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!player.world.isRemote) {
            this.incubator.closeInventory(player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.incubator.isUsableByPlayer(player);
    }
}
