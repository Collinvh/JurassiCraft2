package org.jurassicraft.server.dinosaur;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.BiomeDictionary;
import org.jurassicraft.server.entity.Diet;
import org.jurassicraft.server.entity.SleepTime;
import org.jurassicraft.server.entity.dinosaur.CoelacanthEntity;
import org.jurassicraft.server.food.FoodType;
import org.jurassicraft.server.period.TimePeriod;

public class CoelacanthDinosaur extends Dinosaur {
    public CoelacanthDinosaur() {
        super();

        this.setName("Coelacanth");
        this.setDinosaurClass(CoelacanthEntity.class);
        this.setDinosaurType(DinosaurType.PASSIVE);
        this.setTimePeriod(TimePeriod.DEVONIAN);
        this.setEggColorMale(0x707B94, 0x3B4963);
        this.setEggColorFemale(0x7C775E, 0x4D4A3B);
        this.setHealth(3, 10);
        this.setSpeed(0.35, 0.40);
        this.setAttackSpeed(1.5);
        this.setStrength(0.5, 3);
        this.setMaximumAge(this.fromDays(30));
        this.setEyeHeight(0.35F, 1.8F);
        this.setSizeX(0.1F, 1.0F);
        this.setSizeY(0.1F, 1.0F);
        this.setStorage(9);
        this.setDiet(Diet.PISCIVORE.get().withModule(new Diet.DietModule(FoodType.FILTER)));
        this.setSleepTime(SleepTime.NOCTURNAL);
        this.setBones("anal_fin", "caudal_fin", "first_dorsal_fin", "pectoral_fin_bones", "pelvic_fin_bones", "second_dorsal_fin", "skull", "spine", "teeth");
        this.setHeadCubeName("Head");
        this.setScale(1.8F, 0.22F);
        this.setMaxHerdSize(1);
        this.setOffset(0.0F, 1.1F, -0.2F);
        this.setAttackBias(100.0);
        this.setMarineAnimal(true);
        this.setSpawn(10, BiomeDictionary.getBiomesForType(BiomeDictionary.Type.OCEAN));
        this.setBreeding(true, 1, 3, 15, true, false);
        String[][] recipe =     {{"", "second_dorsal_fin", "first_dorsal_fin", ""},
                {"caudal_fin", "spine", "pectoral_fin_bones","skull"},
                {"anal_fin","","pelvic_fin_bones","teeth"}};
        this.setRecipe(recipe);
    }

    @Override
    public void applyMeatEffect(EntityPlayer player, boolean cooked) {
        if (!cooked) {
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 400, 1));
        }
        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
    }
}
