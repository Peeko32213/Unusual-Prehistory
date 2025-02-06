// package com.peeko32213.unusualprehistory.common.entity.eggs;

// import com.mojang.serialization.Codec;
// import net.minecraft.network.chat.Component;
// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.util.StringRepresentable;

// import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

// public enum EggVariant implements StringRepresentable {
//     SPOTS("spots", 0),
//     STRIPES("stripes", 1);



//     public static final Codec<EggVariant> CODEC = StringRepresentable.fromEnum(EggVariant::values);
//     private final String name;
//     private final Component displayName;
//     private final int nr;
//     private final ResourceLocation smallVariantLocation;
//     private final ResourceLocation normalVariantLocation;
//     private final ResourceLocation mediumVariantLocation;
//     private final ResourceLocation massiveVariantLocation;
//     EggVariant(String variant, int nr) {
//         this.name = variant;
//         this.nr = nr;
//         this.displayName = Component.translatable("entity.dinosaur_egg_" + this.name);
//         this.smallVariantLocation = prefix("textures/entity/eggs/small_egg_"+variant+".png");
//         this.normalVariantLocation = prefix("textures/entity/eggs/normal_egg_"+variant+".png");
//         this.mediumVariantLocation = prefix("textures/entity/eggs/medium_egg_"+variant+".png");
//         this.massiveVariantLocation = prefix("textures/entity/eggs/massive_egg_"+variant+".png");
//     }

//     @Override
//     public String getSerializedName() {
//         return name;
//     }

//     public Component displayName() {
//         return this.displayName;
//     }


//     public ResourceLocation getVariantForEggSize(EggSize size) {
//         int scale = size.getSizeNr();
//         return switch (scale){
//             default -> smallVariantLocation;
//             case 1 -> normalVariantLocation;
//             case 2 -> mediumVariantLocation;
//             case 3 -> massiveVariantLocation;
//         };
//     }

//     public ResourceLocation getVariantForEggSize(int scale) {
//         return switch (scale){
//             default -> smallVariantLocation;
//             case 1 -> normalVariantLocation;
//             case 2 -> mediumVariantLocation;
//             case 3 -> massiveVariantLocation;
//         };
//     }

//     public int getNr() {
//         return nr;
//     }


// }
