package org.jurassicraft.server.maps;

import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import org.jurassicraft.JurassiCraft;

public class VillagerTradeHandler {

    public static void init() {
        ResourceLocation location = new ResourceLocation("minecraft", "librarian");
        VillagerRegistry.VillagerProfession profession = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(location);
        if(!profession.getRegistryName().equals(location)) {
            JurassiCraft.getLogger().error("Could not find librarian maps profession");
        } else {
            VillagerRegistry.VillagerCareer career = profession.getCareer(1);
            if(career.getName().equals("cartographer")) {
                career.addTrade(1, (merchant, recipeList, random) -> {
                    World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
                    BlockPos blockpos = MapUtils.getVisitorCenterPosition();
                    ItemStack itemstack = ItemMap.setupNewMap(world, (double)blockpos.getX(), (double)blockpos.getZ(), (byte)3, true, true);
                    ItemMap.renderBiomePreviewMap(world, itemstack);
                    MapData.addTargetDecoration(itemstack, blockpos, "+", MapDecoration.Type.MANSION);
                    itemstack.setTranslatableName("filled_map.jurassicraft.visitorcenter");
                    recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, random.nextInt(12) + 16), new ItemStack(Items.COMPASS), itemstack));
                });
                JurassiCraft.getLogger().info("Successfully registered maps trade");
            } else {
                JurassiCraft.getLogger().error("Could not find cartographer maps career");
            }
        }
    }

}
