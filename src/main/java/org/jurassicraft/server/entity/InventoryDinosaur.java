package org.jurassicraft.server.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.Random;

public class InventoryDinosaur implements IInventory {
    private DinosaurEntity entity;

    private ItemStack[] inventory;

    public InventoryDinosaur(DinosaurEntity entity) {
        this.entity = entity;
        this.inventory = new ItemStack[entity.getDinosaur().getStorage()];
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i) {
            NBTTagCompound slotTag = new NBTTagCompound();
            slotTag.setByte("Slot", (byte) i);
            this.inventory[i].writeToNBT(slotTag);
            nbttaglist.appendTag(slotTag);
        }

        nbt.setTag("Items", nbttaglist);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound slotTag = nbttaglist.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(slotTag);
            int j = slotTag.getByte("Slot") & 255;

            if (j >= 0 && j < this.inventory.length) {
                this.setInventorySlotContents(j, stack);
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (!this.inventory[index].isEmpty()) {
            ItemStack itemstack;

            if (this.inventory[index].getCount() <= count) {
                itemstack = this.inventory[index];
                this.setInventorySlotContents(index, ItemStack.EMPTY);
                return itemstack;
            } else {
                itemstack = this.inventory[index].splitStack(count);

                if (this.inventory[index].getCount() == 0) {
                    this.setInventorySlotContents(index, ItemStack.EMPTY);
                }

                return itemstack;
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack itemstack = this.inventory[index];
        this.setInventorySlotContents(index, ItemStack.EMPTY);
        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory[index] = stack;

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return !this.entity.isDead && player.getDistance(this.entity) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.getSizeInventory(); i++) {
            this.setInventorySlotContents(i, ItemStack.EMPTY);
        }
    }

    @Override
    public String getName() {
        return this.entity.getName();
    }

    @Override
    public boolean hasCustomName() {
        return this.entity.hasCustomName();
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.entity.getDisplayName();
    }

    public void dropItems(World world, Random rand) {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                float offsetX = rand.nextFloat() * 0.8F + 0.1F;
                float offsetY = rand.nextFloat() * 0.8F + 0.1F;
                float offsetZ = rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.getCount() > 0) {
                    int j = rand.nextInt(21) + 10;

                    if (j > itemstack.getCount()) {
                        j = itemstack.getCount();
                    }

                    j -= itemstack.getCount();
                    EntityItem itemEntity = new EntityItem(world, this.entity.posX + offsetX, this.entity.posY + offsetY, this.entity.posZ + offsetZ, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
                    float multiplier = 0.05F;
                    itemEntity.motionX = (float) rand.nextGaussian() * multiplier;
                    itemEntity.motionY = (float) rand.nextGaussian() * multiplier + 0.2F;
                    itemEntity.motionZ = (float) rand.nextGaussian() * multiplier;
                    world.spawnEntity(itemEntity);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}