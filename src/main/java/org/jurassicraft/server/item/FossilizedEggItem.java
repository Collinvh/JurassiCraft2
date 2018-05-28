package org.jurassicraft.server.item;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.jurassicraft.server.api.GrindableItem;
import org.jurassicraft.server.block.NestFossilBlock;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.tab.TabHandler;
import org.jurassicraft.server.util.LangHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FossilizedEggItem extends Item implements GrindableItem {
    public FossilizedEggItem() {
        super();
        this.setCreativeTab(TabHandler.FOSSILS);
        this.setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return new LangHelper("item.fossilized_egg.name").build();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (NestFossilBlock.Variant variant : NestFossilBlock.Variant.values()) {
            subItems.add(new ItemStack(item, 1, variant.ordinal()));
        }
    }

    @Override
    public boolean isGrindable(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getGroundItem(ItemStack stack, Random random) {
        NBTTagCompound tag = stack.getTagCompound();

        int outputType = random.nextInt(3);

        if (outputType == 0) {
            List<Dinosaur> dinosaurs = EntityHandler.getPrehistoricDinosaurs();

            ItemStack output = new ItemStack(ItemHandler.SOFT_TISSUE, 1, EntityHandler.getDinosaurId(dinosaurs.get(random.nextInt(dinosaurs.size()))));
            output.setTagCompound(tag);
            return output;
        } else if (outputType == 1) {
            return new ItemStack(Items.DYE, 1, 15);
        }

        return new ItemStack(Items.FLINT);
    }

    @Override
    public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {
        List<Pair<Float, ItemStack>> list = Lists.newArrayList();
        NBTTagCompound tag = inputItem.getTagCompound();
        List<Dinosaur> dinosaurs = EntityHandler.getPrehistoricDinosaurs();
        float single = 100F/3F;
        float dinoSingle = single / dinosaurs.size();
        for(Dinosaur dinosaur : dinosaurs) {
            ItemStack output = new ItemStack(ItemHandler.SOFT_TISSUE, 1, EntityHandler.getDinosaurId(dinosaur));
            output.setTagCompound(tag);
            list.add(Pair.of(dinoSingle, output));
        }
        list.add(Pair.of(single, new ItemStack(Items.DYE, 1, 15)));
        list.add(Pair.of(single, new ItemStack(Items.FLINT)));

        return list;
    }
}
