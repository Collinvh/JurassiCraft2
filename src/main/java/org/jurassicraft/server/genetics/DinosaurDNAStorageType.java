package org.jurassicraft.server.genetics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.item.ItemHandler;

import java.util.List;

public class DinosaurDNAStorageType implements StorageType {
    private DinoDNA dna;

    @Override
    public ItemStack createItem() {
        ItemStack output = ItemHandler.DNA.getItemStack(this.dna.getDinosaur());
        NBTTagCompound compound = new NBTTagCompound();
        this.dna.writeToNBT(compound);
        output.setTagCompound(compound);
        return output;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        this.dna.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.dna = DinoDNA.readFromNBT(nbt);
    }

    @Override
    public void addInformation(ItemStack stack, List<String> tooltip) {
        this.dna.addInformation(stack, tooltip);
    }

}
