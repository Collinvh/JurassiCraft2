package org.jurassicraft.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import org.jurassicraft.server.block.entity.DNASynthesizerBlockEntity;
import org.jurassicraft.server.container.slot.CustomSlotOLD;
import org.jurassicraft.server.container.slot.SynthesizableItemSlot;
import org.jurassicraft.server.item.ItemHandler;

public class DNASynthesizerContainer extends MachineContainerOLD {
    private DNASynthesizerBlockEntity dnaSynthesizer;

    public DNASynthesizerContainer(InventoryPlayer playerInventory, TileEntity tileEntity) {
        super((IInventory) tileEntity);

        this.dnaSynthesizer = (DNASynthesizerBlockEntity) tileEntity;
        this.addSlotToContainer(new SynthesizableItemSlot(this.dnaSynthesizer, 0, 38, 22));
        this.addSlotToContainer(new CustomSlotOLD(this.dnaSynthesizer, 1, 24, 49, stack -> stack.getItem() == ItemHandler.EMPTY_TEST_TUBE));
        this.addSlotToContainer(new CustomSlotOLD(this.dnaSynthesizer, 2, 50, 49, stack -> stack.getItem() == ItemHandler.DNA_NUCLEOTIDES));

        int i;

        for (i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlotToContainer(new Slot(this.dnaSynthesizer, i + (j * 2) + 3, i * 18 + 119, j * 18 + 26));
            }
        }

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
            this.dnaSynthesizer.closeInventory(player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.dnaSynthesizer.isUsableByPlayer(player);
    }
}
