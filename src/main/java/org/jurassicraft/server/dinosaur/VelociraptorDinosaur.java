//package org.jurassicraft.server.dinosaur;
//
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.BiomeDictionary;
//
//import org.jurassicraft.JurassiCraft;
//import org.jurassicraft.server.entity.Diet;
//import org.jurassicraft.server.entity.SleepTime;
//import org.jurassicraft.server.entity.dinosaur.VelociraptorEntity;
//import org.jurassicraft.server.period.TimePeriod;
//
//public class VelociraptorDinosaur extends Dinosaur {
//    public VelociraptorDinosaur() {
//        super();
//
//        this.setName("Velociraptor");
//        this.setDinosaurClass(VelociraptorEntity.class);
//        this.setDinosaurBehaviourType(DinosaurBehaviourType.AGGRESSIVE);
//        this.setTimePeriod(TimePeriod.CRETACEOUS);
//        this.setEggColorMale(0xA06238, 0x632D11);
//        this.setEggColorFemale(0x91765D, 0x5A4739);
//        this.setSpeed(0.2, 0.3);
//        this.setAttackSpeed(1.25);
//        this.setHealth(10, 35);
//        this.setStrength(1, 8);
//        this.setMaximumAge(this.fromDays(45));
//        this.setEyeHeight(0.45F, 1.7F);
//        this.setSizeX(0.5F, 1.0F);
//        this.setSizeY(0.5F, 1.8F);
//        this.setStorage(27);
//        this.setDiet(Diet.CARNIVORE.get());
//        this.setSleepTime(SleepTime.DIURNAL);
//        this.setBones("claw", "tooth", "skull","foot_bones","leg_bones","neck_vertebrae","ribcage","shoulder_bone","tail_vertebrae","arm_bones");
//        this.setHeadCubeName("Head");
//        this.setScale(1.3F, 0.3F);
//        this.setImprintable(true);
//        this.setDefendOwner(true);
//        this.setMaxHerdSize(18);
//        this.setAttackBias(600.0);
//        this.setCanClimb(true);
//        this.setBreeding(BirthType.EGG_LAYING,1, 7, 28, false, true);
//        this.setJumpHeight(3);
//        String[][] recipe = {
//                {"", "","neck_vertebrae","skull"},
//                {"tail_vertebrae", "ribcage","shoulder_bone","tooth"},
//                {"leg_bones", "leg_bones", "arm_bones", "claw"},
//                {"foot_bones", "foot_bones", "", ""}};
//        this.setRecipe(recipe);
//        this.setShadowSize(1.5F);
//        this.setSpawn(10, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE);
//    }
//}
