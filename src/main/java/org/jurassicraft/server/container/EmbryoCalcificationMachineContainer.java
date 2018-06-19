package org.jurassicraft.server.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import org.jurassicraft.server.block.entity.EmbryoCalcificationMachineBlockEntity;
import org.jurassicraft.server.container.slot.CustomSlotOLD;
import org.jurassicraft.server.container.slot.SyringeSlot;

public class EmbryoCalcificationMachineContainer extends MachineContainerOLD {
    private EmbryoCalcificationMachineBlockEntity calcificationMachine;

    public EmbryoCalcificationMachineContainer(InventoryPlayer playerInventory, TileEntity tileEntity) {
        super((IInventory) tileEntity);

        this.calcificationMachine = (EmbryoCalcificationMachineBlockEntity) tileEntity;

        this.addSlotToContainer(new SyringeSlot(this.calcificationMachine, 0, 34, 14));
        this.addSlotToContainer(new CustomSlotOLD(this.calcificationMachine, 1, 34, 50, stack -> stack.getItem() == Items.EGG));

        this.addSlotToContainer(new Slot(this.calcificationMachine, 2, 97, 32));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!player.world.isRemote) {
            this.calcificationMachine.closeInventory(player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.calcificationMachine.isUsableByPlayer(player);
    }
}
