package com.peeko32213.unusualprehistory.common.entity.msc.util.dino;

import com.google.common.collect.ImmutableList;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public abstract class EntityWorldSpawnable extends LivingEntity implements IAnimatable {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityWorldSpawnable.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> WIDTH_SCALE = SynchedEntityData.defineId(EntityWorldSpawnable.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HEIGHT_SCALE = SynchedEntityData.defineId(EntityWorldSpawnable.class, EntityDataSerializers.FLOAT);
    public static final ResourceLocation MAMMOTH_LOOT = prefix("entities/iceberg/mammoth");
    public static final ResourceLocation SMILODON_LOOT = prefix("entities/iceberg/smilodon");
    public static final ResourceLocation ERYON_LOOT = prefix("entities/iceberg/eryon");
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final Logger LOGGER = LogManager.getLogger();
    private boolean hasGivenDna;
    private boolean canGiveItems;
    private int itemCount;
    public EntityWorldSpawnable(EntityType<? extends LivingEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        //this.refreshDimensions();
    }

    public static AttributeSupplier.Builder bakeAttributes() {
        return Mob.createMobAttributes()
                //HEALTH
                .add(Attributes.MAX_HEALTH, 10.0D)
                //SPEED
                .add(Attributes.MOVEMENT_SPEED, 0D);
    }


    protected abstract ItemStack getDnaItem();
    private int currentDrop;
    protected abstract int dropCount();

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.isEmpty() && pHand == InteractionHand.MAIN_HAND) {
            if (!hasGivenDna) {
                ItemStack dna = getDnaItem();
                if (!dna.isEmpty() && !level.isClientSide) {
                    this.spawnAtLocation(dna, 1);
                    hasGivenDna = true;
                }
            } else if (dropCount() > 0 && ++currentDrop < dropCount()) {
                itemDrop();
            } else {
                this.playSound(SoundEvents.SKELETON_DEATH);
                this.remove(RemovalReason.KILLED);
            }
        }
        return super.interact(pPlayer, pHand);
    }

    protected abstract ResourceLocation getDeadLootTable();


    public void itemDrop() {
        ItemStack drop = getSoundForDrop();
        if (!drop.isEmpty() && !level.isClientSide) {
            this.spawnAtLocation(drop, 1);
        }
    }

    protected ItemStack getSoundForDrop() {
        ItemStack lootItem = getItemFromLootTable();
        if (lootItem.getItem() == UPItems.FROZEN_FOSSIL.get() || lootItem.getItem() == UPItems.SMILODON_EMBRYO.get()) {
            this.playSound(SoundEvents.AMETHYST_BLOCK_HIT, 1, 1);
        } else {
            this.playSound(SoundEvents.BOOK_PAGE_TURN, 1, 1);
        }
        return lootItem;
    }

    public ItemStack getItemFromLootTable() {
        if (this.level.getServer() == null) {
            return ItemStack.EMPTY;
        }
        LootTable loottable = this.level.getServer().getLootTables().get(this.getDeadLootTable());
        LootContext.Builder lootcontext$builder = this.createLootContext(false, DamageSource.GENERIC);
        for (ItemStack itemstack : loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.ENTITY))) {
            return itemstack;
        }
        return ItemStack.EMPTY;
    }


    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (HEIGHT_SCALE.equals(pKey) || WIDTH_SCALE.equals(pKey)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.hasGivenDna = compound.getBoolean("hasGivenDna");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasGivenDna", this.hasGivenDna);
    }


    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (source == DamageSource.IN_WALL || source == DamageSource.GENERIC) {
            return false;
        }

        return false;
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Nullable
    protected abstract String getFrozenState();

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(getFrozenState() != null){
        event.getController().setAnimation(new AnimationBuilder().addAnimation(getFrozenState()));
        }
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
