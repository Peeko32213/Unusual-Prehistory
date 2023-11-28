package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.MammothMeleeAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Collections;
import java.util.List;

public class EntityMammoth extends EntityBaseDinosaurAnimal implements Shearable, net.minecraftforge.common.IForgeShearable, ContainerListener {
    public static final ResourceLocation MAMMOTH_LOOT = new ResourceLocation("unusualprehistory", "gameplay/mammoth_loot");
    private static final EntityDataAccessor<Boolean> IS_TRUNKING = SynchedEntityData.defineId(EntityMammoth.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> HOLD_ITEM = SynchedEntityData.defineId(EntityMammoth.class, EntityDataSerializers.ITEM_STACK);

    private Ingredient temptationItems;
    public SimpleContainer mammothInventory;
    private int dirtyCooldown;
    private static final RawAnimation MAMMOTH_WALK = RawAnimation.begin().thenLoop("animation.mammoth.move");
    private static final RawAnimation MAMMOTH_IDLE = RawAnimation.begin().thenLoop("animation.mammoth.idle");
    private static final RawAnimation MAMMOTH_SWIM = RawAnimation.begin().thenLoop("animation.mammoth.swim");
    private static final RawAnimation MAMMOTH_TRUNK = RawAnimation.begin().thenLoop("animation.mammoth.idle_trunk");
    public EntityMammoth(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        initMammothInventory();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 5D)

                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 8.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new MammothMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new MammothTrunkIdleGoal(this));

    }

    private void initMammothInventory() {
        SimpleContainer animalchest = this.mammothInventory;
        int size = 1;
        this.mammothInventory = new SimpleContainer(size) {
            public boolean stillValid(Player player) {
                return EntityMammoth.this.isAlive() && !EntityMammoth.this.isInsidePortal;
            }
        };
        mammothInventory.addListener(this);
        if (animalchest != null) {
            int i = Math.min(animalchest.getContainerSize(), this.mammothInventory.getContainerSize());
            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = animalchest.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.mammothInventory.setItem(j, itemstack.copy());
                }
            }
        }
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.mammothInventory != null) {
            for (int i = 0; i < mammothInventory.getContainerSize(); i++) {
                this.spawnAtLocation(mammothInventory.getItem(i));
            }
            mammothInventory.clearContent();
            this.setHoldItemStack(ItemStack.EMPTY);
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (pHand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (this.readyForShearing() && pPlayer.getItemBySlot(EquipmentSlot.HEAD).is(UPItems.TYRANTS_CROWN.get()) && itemStack.is(Tags.Items.SHEARS)) {
            shear(SoundSource.PLAYERS);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));

        return temptationItems;
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_TRUNKING, false);
        this.entityData.define(HOLD_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (mammothInventory != null) {
            final ListTag nbttaglist = compound.getList("Items", 10);
            this.initMammothInventory();
            for (int i = 0; i < nbttaglist.size(); ++i) {
                final CompoundTag CompoundNBT = nbttaglist.getCompound(i);
                final int j = CompoundNBT.getByte("Slot") & 255;
                ItemStack itemStack = ItemStack.of(CompoundNBT.getCompound("Item"));
                this.mammothInventory.setItem(j, itemStack);
                this.setHoldItemStack(itemStack);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (mammothInventory != null) {
            final ListTag nbttaglist = new ListTag();
            for (int i = 0; i < this.mammothInventory.getContainerSize(); ++i) {
                final ItemStack itemstack = this.mammothInventory.getItem(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag CompoundNBT = new CompoundTag();
                    CompoundNBT.putByte("Slot", (byte) i);
                    CompoundNBT.put("Item", itemstack.serializeNBT());
                    nbttaglist.add(CompoundNBT);
                }
            }
            compound.put("Items", nbttaglist);
        }
    }

    public boolean isTrunking() {
        return this.entityData.get(IS_TRUNKING);
    }

    public void setTrunking(boolean isPecking) {
        this.entityData.set(IS_TRUNKING, isPecking);
    }

    public ItemStack getHoldItemStack() {
        return this.entityData.get(HOLD_ITEM);
    }

    public void setHoldItemStack(ItemStack itemStack) {
        this.entityData.set(HOLD_ITEM, itemStack);
    }

    public void tick() {
        super.tick();
        if (dirtyCooldown-- < 0 && this.mammothInventory.isEmpty() && !this.level().isClientSide) {
            List<ItemStack> itemStack = getShearLoot(this);
            if (!itemStack.isEmpty()) {
                ItemStack item = itemStack.get(this.random.nextInt(itemStack.size()));
                item.setCount(this.random.nextInt(0, 3));
                this.mammothInventory.addItem(item);
                this.dirtyCooldown = this.random.nextInt(6000, 12000);
                this.setHoldItemStack(item);
            } else {
                this.dirtyCooldown = this.random.nextInt(100);
            }
        }
    }

    @Override
    public void shear(SoundSource category) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, category, 1.0F, 1.0F);
        this.gameEvent(GameEvent.ENTITY_INTERACT);
        if (!this.level().isClientSide() && this.mammothInventory != null && !this.mammothInventory.isEmpty()) {
            List<ItemStack> lootList = Collections.singletonList(this.mammothInventory.getItem(0));
            for (ItemStack stack : lootList) {
                ItemEntity e = this.spawnAtLocation(stack.copy());
                e.hasImpulse = true;
            }
            this.mammothInventory.clearContent();
            this.setHoldItemStack(ItemStack.EMPTY);
        }
    }

    private static List<ItemStack> getShearLoot(EntityMammoth mammoth) {
        if (!mammoth.level().isClientSide) {
            LootTable loottable = mammoth.level().getServer().getLootData().getLootTable(MAMMOTH_LOOT);
            return loottable.getRandomItems((new LootParams.Builder((ServerLevel) mammoth.level()))
                    .withParameter(LootContextParams.THIS_ENTITY, mammoth)
                    .withParameter(LootContextParams.ORIGIN, mammoth.position())
                    .create(LootContextParamSets.CHEST));
        }
        return Collections.emptyList();
    }

    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public boolean isShearable(@javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos) {
        return readyForShearing();
    }

    @Override
    public void containerChanged(Container pContainer) {

    }

    @Override
    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        if (this.mammothInventory != null && !this.mammothInventory.isEmpty() && this.readyForShearing() && player.getItemBySlot(EquipmentSlot.HEAD).is(UPItems.TYRANTS_CROWN.get()) && item.is(Tags.Items.SHEARS)) {
            level.playSound(null, this, SoundEvents.SHEEP_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHEAR, player);
            if (!level.isClientSide) {
                java.util.List<ItemStack> items = new java.util.ArrayList<>();
                items.add(this.mammothInventory.getItem(0));
                this.mammothInventory.clearContent();
                this.setHoldItemStack(ItemStack.EMPTY);
                return items;
            }
        }
        return Collections.emptyList();
    }

    public class MammothTrunkIdleGoal extends Goal {
        private final EntityMammoth mammoth;

        public MammothTrunkIdleGoal(EntityMammoth mammoth) {
            this.mammoth = mammoth;
        }

        @Override
        public boolean canUse() {
            return this.mammoth.onGround() && this.mammoth.getRandom().nextInt(100) == 0;
        }

        @Override
        public void tick() {
            this.mammoth.setTrunking(true);
            if (this.mammoth.getRandom().nextInt(100) <= 25) {
                this.mammoth.setTrunking(false);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void stop() {
            this.mammoth.setTrunking(false);
        }
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return false;
    }

    @Override
    protected boolean hasCustomNavigation() {
        return false;
    }

    @Override
    protected boolean hasMakeStuckInBlock() {
        return false;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return false;
    }

    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return null;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.MAMMOTH_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.MAMMOTH_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.MAMMOTH_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    protected <E extends EntityMammoth> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.setAndContinue(MAMMOTH_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6  && !this.isSwimming()) {
            event.setAndContinue(MAMMOTH_WALK);
            return PlayState.CONTINUE;
        }
        event.setAndContinue(MAMMOTH_IDLE);
        return PlayState.CONTINUE;
    }

    protected <E extends EntityMammoth> PlayState trunkController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isTrunking() && !this.isSwimming()) {
            event.setAndContinue(MAMMOTH_TRUNK);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Trunk", 5, this::trunkController));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

}
