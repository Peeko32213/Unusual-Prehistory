package com.peeko32213.unusualprehistory.core.events;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.animation.ServerResourceCache;
import com.peeko32213.unusualprehistory.common.data.analyzer.AnalyzerRecipeJsonManager;
import com.peeko32213.unusualprehistory.common.data.encyclopedia.EncyclopediaCodec;
import com.peeko32213.unusualprehistory.common.data.encyclopedia.EncyclopediaJsonManager;
import com.peeko32213.unusualprehistory.common.data.encyclopedia.ItemWeightedPairCodec;
import com.peeko32213.unusualprehistory.common.data.lootfruit.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.lootfruit.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.HwachavenatorEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.DunkleosteusEntity;
import com.peeko32213.unusualprehistory.common.message.*;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.*;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void onLivingEntityParticle(LivingEntityChangeParticleEvent event) {
        // 1) DO NOTHING: Let the particle spawn as normal
        //    Just don't call event.setCanceled(true), and don't modify any fields.
        //    So if the entity is not a Creeper or Zombie, we'll do nothing, so it spawns
        //    normally. (We only handle Creeper/Zombie in the conditions below.)

        // 2) REPLACE THE PARTICLE for CREEPERS
        //    Suppose you want all Creepers to spawn END_ROD particles instead.
        if (event.getEntity() instanceof Creeper) {
            event.setParticleData(ParticleTypes.END_ROD);
        }

        // 3) CANCEL THE PARTICLE for ZOMBIES
        //    Suppose you want to completely remove all particles from Zombies.
        if (event.getEntity() instanceof Zombie) {
            event.setCanceled(true);
            return;
        }

        // 4) Optional further manipulation (if not canceled):
        //    Example: Move the spawn point or change the velocity
        // event.setX(event.getX() + 1.0D);
        // event.setYSpeed(event.getYSpeed() + 0.05D);
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(ServerStartedEvent event) {
        try{
            AnalyzerRecipeJsonManager.populateRecipeMap(event.getServer().getLevel(Level.OVERWORLD));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void sendMapPackets(PlayerEvent.PlayerLoggedInEvent event){

    }
    @SubscribeEvent
    public static void synchDataPack(OnDatapackSyncEvent event){
        ServerPlayer player = event.getPlayer();
        List<ServerPlayer> playerList = event.getPlayerList().getPlayers();
        Map<Integer, List<LootFruitCodec>> lootFruitsTier = LootFruitJsonManager.getTierTrades();
        Map<Item, List<LootFruitCodec>> lootFruits = LootFruitJsonManager.getTrades();
        Map<Item, List<ItemWeightedPairCodec>> analyzerRecipes = AnalyzerRecipeJsonManager.getRecipes();
        Map<ResourceLocation, EncyclopediaCodec> encyclopediaEntries = EncyclopediaJsonManager.getEncyclopediaEntries();
        EncyclopediaCodec rootPage = EncyclopediaJsonManager.getRootPage();
       if(player != null){
           UPMessages.sendToPlayer(new LootFruitTierPacketS2C(lootFruitsTier), player);
           UPMessages.sendToPlayer(new LootFruitPacketS2C(lootFruits), player);
           UPMessages.sendToPlayer(new AnalyzerRecipeS2C(analyzerRecipes), player);
           UPMessages.sendToPlayer(new EncyclopediaS2C(encyclopediaEntries), player);
           UPMessages.sendToPlayer(new EncyclopediaRootPageS2C(rootPage), player);
       }

       if(!playerList.isEmpty()){
           for(ServerPlayer player1 : playerList){
               ServerLevel serverLevel = (ServerLevel) player1.level();
               if(AnalyzerRecipeJsonManager.getRecipes().isEmpty()){
                   AnalyzerRecipeJsonManager.populateRecipeMap(serverLevel);
               }
               Map<Item, List<ItemWeightedPairCodec>> analyzerRecipesReload = AnalyzerRecipeJsonManager.getRecipes();
               UPMessages.sendToPlayer(new LootFruitTierPacketS2C(lootFruitsTier), player1);
               UPMessages.sendToPlayer(new LootFruitPacketS2C(lootFruits), player1);
               UPMessages.sendToPlayer(new AnalyzerRecipeS2C(analyzerRecipesReload), player1);
               UPMessages.sendToPlayer(new EncyclopediaS2C(encyclopediaEntries), player1);
               UPMessages.sendToPlayer(new EncyclopediaRootPageS2C(rootPage), player1);
           }
       }

//        ServerPlayer player = event.getPlayer();
//        List<ServerPlayer> playerList = event.getPlayerList().getPlayers();
//        Map<ResourceLocation, JsonObject> modelObjects = ServerResourceCache.getModelObjects();
//
//
//        if (player != null) {
//            SMessages.send(new SynchResourceObjectToClientPacket<>(player.getId(), ServerResourceCache.getModelObjects(), true), PacketDistributor.PLAYER.with(player));
//            SMessages.send(new SynchResourceObjectToClientPacket<>(player.getId(), ServerResourceCache.getAnimationObject(), false), PacketDistributor.PLAYER.with(player));
//        }
//
//
//        if (playerList != null && !playerList.isEmpty()) {
//            for (ServerPlayer player1 : playerList) {
//                SMessages.send(new SynchResourceObjectToClientPacket<>(player1.getId(), ServerResourceCache.getModelObjects(), true), PacketDistributor.PLAYER.with(player1));
//                SMessages.send(new SynchResourceObjectToClientPacket<>(player1.getId(), ServerResourceCache.getAnimationObject(), false), PacketDistributor.PLAYER.with(player1));
//            }
//        }
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new AnalyzerRecipeJsonManager());
        event.addListener(new LootFruitJsonManager());
        event.addListener(new EncyclopediaJsonManager());
        event.addListener(ServerResourceCache::reload);
    }

    @SubscribeEvent
    public void onLootLevelEvent(LootingLevelEvent event) {
        DamageSource src = event.getDamageSource();
        if (src != null) {
            if (src.getEntity() instanceof DunkleosteusEntity) {
                event.setLootingLevel(event.getLootingLevel() + 3);
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (!event.getEntity().getUseItem().isEmpty() && event.getSource() != null && event.getSource().getEntity() != null) {
            if (event.getEntity().getUseItem().getItem() == UPItems.TRIKE_SHIELD.get()) {
                if (event.getSource().getEntity() instanceof LivingEntity living && !living.isFallFlying()) {
                    boolean flag = false;
                    if (living.distanceTo(event.getEntity()) <= 4
                            && !living.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 1));
                        flag = true;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity living) {

                LivingEntity entity = event.getEntity();

                ItemStack itemStack = living.getItemInHand(InteractionHand.MAIN_HAND);
                if(!itemStack.hasTag()) return;
                CompoundTag tag = itemStack.getTag();
                if(tag == null || !tag.contains("megalania_damage")) return;;
                int count = tag.getInt("megalania_damage");
                float amount = event.getAmount();
                amount *= 0.75;
                int hpReduction = 0;

                if(entity.hasEffect(UPEffects.HEALTH_REDUCTION.get())){
                    hpReduction = Objects.requireNonNull(entity.getEffect(UPEffects.HEALTH_REDUCTION.get())).getAmplifier() + 1;
                }

                MobEffectInstance mobEffectInstance = new MobEffectInstance(UPEffects.HEALTH_REDUCTION.get(), 120, hpReduction);
                entity.addEffect(mobEffectInstance);
                event.setAmount(amount);
                count--;
                tag.putInt("megalania_damage", count);
                if(count <= 0) tag.remove("megalania_damage");
                itemStack.setTag(tag);
            }
    }

    @SubscribeEvent
    public static void renderMegalaniaPoisonToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(!stack.hasTag()) return;
        CompoundTag tag = stack.getTag();
        if(tag == null || !tag.contains("megalania_damage")) return;
        int amount = tag.getInt("megalania_damage");
        MutableComponent component = Component.translatable("unusualprehistory.megalania_damage", amount).withStyle(ChatFormatting.BLUE);
        List<Component> toolTip = event.getToolTip();
        event.getToolTip().add(component);
    }

    @SubscribeEvent
    //cant be canceled
    public void preventClick(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof DunkleosteusEntity dunkleosteus) {
            dunkleosteus.killed();
        }

        if (event.getSource().getEntity() instanceof HwachavenatorEntity dinosaurAnimal) {
            dinosaurAnimal.killed();
        }
    }

    @SubscribeEvent
    //cant be canceled
    public void axeOneHitWoodDestroy(PlayerInteractEvent.LeftClickBlock event) {
        if(event.getEntity() != null && !event.getLevel().isClientSide){
            Player player = event.getEntity();
            ServerLevel serverLevel = (ServerLevel) event.getLevel();
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(!itemStack.is(UPItems.HANDMADE_BATTLEAXE.get())) return;
            BlockPos pos = event.getPos();
            BlockState state = serverLevel.getBlockState(pos);
            RandomSource randomSource = serverLevel.random;
            boolean giveDrops = randomSource.nextInt(100) < 10;
            if(state.is(BlockTags.MINEABLE_WITH_AXE)) {
                if(!giveDrops){
                    serverLevel.destroyBlock(pos, false);
                } else {
                    serverLevel.destroyBlock(pos, true);
                }
            }
        }
    }

    @SubscribeEvent
    //cant be canceled
    public void preventClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    //cant be canceled
    public void preventClick(PlayerInteractEvent.RightClickEmpty event) {
        if (event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventDamage(AttackEntityEvent event) {
        if (event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())) {
            event.setCanceled(true);
        }
    }

//    @SubscribeEvent
//    public static void synchResources(PlayerEvent.PlayerLoggedInEvent event) {
//        if (event.getEntity() == null || event.getEntity().level().isClientSide) return;
//        ServerLevel level = (ServerLevel) event.getEntity().level();
//        ServerPlayer player = (ServerPlayer) event.getEntity();
//        UPMessages.send(new SynchResourceObjectToClientPacket<>(player.getId(), ServerResourceCache.getModelObjects(), true), PacketDistributor.PLAYER.with(player));
//        UPMessages.send(new SynchResourceObjectToClientPacket<>(player.getId(), ServerResourceCache.getAnimationObject(), false), PacketDistributor.PLAYER.with(player));
//    }
}





