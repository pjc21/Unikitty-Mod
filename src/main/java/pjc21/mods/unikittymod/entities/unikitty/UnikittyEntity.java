package pjc21.mods.unikittymod.entities.unikitty;

import com.google.common.collect.Maps;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pjc21.mods.unikittymod.config.UnikittyConfig;
import pjc21.mods.unikittymod.entities.unikitty.goals.UnikittyLieOnBedGoal;
import pjc21.mods.unikittymod.entities.unikitty.goals.UnikittySitOnBlockGoal;
import pjc21.mods.unikittymod.init.ModEntities;
import pjc21.mods.unikittymod.init.ModItems;
import pjc21.mods.unikittymod.init.ModLootTables;
import pjc21.mods.unikittymod.init.ModSounds;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("NullableProblems")
public class UnikittyEntity extends TameableEntity {

    private static final Ingredient TEMPT_INGREDIENT = Ingredient.of(ModItems.FISH_GOLDEN.get());
    private static final DataParameter<Byte> DATA_HAIR_COLOR = EntityDataManager.defineId(UnikittyEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(UnikittyEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> IS_LYING = EntityDataManager.defineId(UnikittyEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RELAX_STATE_ONE = EntityDataManager.defineId(UnikittyEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DATA_COLLAR_COLOR = EntityDataManager.defineId(UnikittyEntity.class, DataSerializers.INT);

    private net.minecraft.entity.ai.goal.TemptGoal temptGoal;
    private float lieDownAmount;
    private float lieDownAmountO;
    private float lieDownAmountTail;
    private float lieDownAmountOTail;
    private float relaxStateOneAmount;
    private float relaxStateOneAmountO;
    private int hairBallTime = this.random.nextInt(6000) + 6000;

    private static final Map<DyeColor, float[]> COLORARRAY_BY_COLOR = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((DyeColor dyeColor) -> dyeColor, UnikittyEntity::createUnikittyColor)));

    public UnikittyEntity(EntityType<? extends UnikittyEntity> entityType, World world) {
        super(entityType, world);
    }

    private static float[] createUnikittyColor(DyeColor dyeColor) {
        if (dyeColor == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = dyeColor.getTextureDiffuseColors();
            float f = 0.75F;
            return new float[]{afloat[0] * f, afloat[1] * f, afloat[2] * f};
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static float[] getColorArray(DyeColor dyeColor) {
        return COLORARRAY_BY_COLOR.get(dyeColor);
    }

    protected void registerGoals() {
        this.temptGoal = new UnikittyEntity.TemptGoal(this, 0.6D, TEMPT_INGREDIENT, true);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new UnikittyEntity.MorningGiftGoal(this));
        this.goalSelector.addGoal(3, this.temptGoal);
        this.goalSelector.addGoal(5, new UnikittyLieOnBedGoal(this, 1.1D, 8));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(7, new UnikittySitOnBlockGoal(this, 0.8D));
        this.goalSelector.addGoal(8, new LeapAtTargetGoal(this, 0.3F));
        this.goalSelector.addGoal(9, new OcelotAttackGoal(this));
        this.goalSelector.addGoal(10, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(11, new WaterAvoidingRandomWalkingGoal(this, 0.8D, 1.0000001E-5F));
        this.goalSelector.addGoal(12, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_HAIR_COLOR) & 15);
    }

    public void setColor(DyeColor dyeColor) {
        byte b0 = this.entityData.get(DATA_HAIR_COLOR);
        this.entityData.set(DATA_HAIR_COLOR, (byte)(b0 & 240 | dyeColor.getId() & 15));
    }

    public static DyeColor getRandomUnikittyColor(Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.BLACK;
        } else if (i < 10) {
            return DyeColor.ORANGE;
        } else if (i < 15) {
            return DyeColor.YELLOW;
        } else if (i < 18) {
            return DyeColor.BROWN;
        } else {
            return random.nextInt(500) == 0 ? DyeColor.PINK : DyeColor.WHITE;
        }
    }

    public void setLying(boolean lying) {
        this.entityData.set(IS_LYING, lying);
    }

    public boolean isLying() {
        return this.entityData.get(IS_LYING);
    }

    public void setRelaxStateOne(boolean relaxStateOne) {
        this.entityData.set(RELAX_STATE_ONE, relaxStateOne);
    }

    public boolean isRelaxStateOne() {
        return this.entityData.get(RELAX_STATE_ONE);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor dyeColor) {
        this.entityData.set(DATA_COLLAR_COLOR, dyeColor.getId());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HAIR_COLOR, (byte)0);
        this.entityData.define(DATA_TYPE_ID, 1);
        this.entityData.define(IS_LYING, false);
        this.entityData.define(RELAX_STATE_ONE, false);
        this.entityData.define(DATA_COLLAR_COLOR, DyeColor.RED.getId());
    }

    public void addAdditionalSaveData(CompoundNBT addCompoundNBT) {
        super.addAdditionalSaveData(addCompoundNBT);
        addCompoundNBT.putByte("CollarColor", (byte)this.getCollarColor().getId());
        addCompoundNBT.putByte("Color", (byte)this.getColor().getId());
        addCompoundNBT.putInt("HairBallTime", this.hairBallTime);
    }

    public void readAdditionalSaveData(CompoundNBT readCompoundNBT) {
        super.readAdditionalSaveData(readCompoundNBT);
        this.setColor(DyeColor.byId(readCompoundNBT.getByte("Color")));
        if (readCompoundNBT.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(readCompoundNBT.getInt("CollarColor")));
        }
        if (readCompoundNBT.contains("HairBallTime")) {
            this.hairBallTime = readCompoundNBT.getInt("HairBallTime");
        }
    }

    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            if (d0 == 0.6D) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(false);
            } else if (d0 == 1.33D) {
                this.setPose(Pose.STANDING);
                this.setSprinting(true);
            } else {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (UnikittyConfig.entitySettings.unikittyEntity.unikittyHairball.get() && !this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.hairBallTime <= 0) {
            this.playSound(ModSounds.UNIKITTY_HAIRBALL.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            Arrays.stream(DyeColor.values()).filter(dyeColor -> this.getColor() == dyeColor).forEach(dyeColor -> this.spawnAtLocation(ModItems.COLORED_STRING.get(dyeColor).get(), 1));
            this.hairBallTime = this.random.nextInt(6000) + 6000;
        }
    }

    @Override
    protected boolean shouldDropExperience() {
        return (UnikittyConfig.entitySettings.unikittyEntity.unikittyDropExp.get()) && !this.isBaby();
    }

    @Override
    public ResourceLocation getDefaultLootTable() {
        switch(this.getColor()) {
            case WHITE:
            default:
                return ModLootTables.UNIKITTY_WHITE;
            case ORANGE:
                return ModLootTables.UNIKITTY_ORANGE;
            case MAGENTA:
                return ModLootTables.UNIKITTY_MAGENTA;
            case LIGHT_BLUE:
                return ModLootTables.UNIKITTY_LIGHT_BLUE;
            case YELLOW:
                return ModLootTables.UNIKITTY_YELLOW;
            case LIME:
                return ModLootTables.UNIKITTY_LIME;
            case PINK:
                return ModLootTables.UNIKITTY_PINK;
            case GRAY:
                return ModLootTables.UNIKITTY_GRAY;
            case LIGHT_GRAY:
                return ModLootTables.UNIKITTY_LIGHT_GRAY;
            case CYAN:
                return ModLootTables.UNIKITTY_CYAN;
            case PURPLE:
                return ModLootTables.UNIKITTY_PURPLE;
            case BLUE:
                return ModLootTables.UNIKITTY_BLUE;
            case BROWN:
                return ModLootTables.UNIKITTY_BROWN;
            case GREEN:
                return ModLootTables.UNIKITTY_GREEN;
            case RED:
                return ModLootTables.UNIKITTY_RED;
            case BLACK:
                return ModLootTables.UNIKITTY_BLACK;
        }
    }

    public static boolean checkUnikittySpawnRules(EntityType<? extends UnikittyEntity> unikitty, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        Block blockBelow = world.getBlockState(pos.below()).getBlock();
        return UnikittyConfig.entitySettings.unikittyEntity.unikittySpawnBlocks.get().contains(blockBelow.getRegistryName().toString()) && blockBelow.getRegistryName() != null && world.getRawBrightness(pos, 0) > 8;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isTame()) {
            if (this.isInLove()) {
                return SoundEvents.CAT_PURR;
            } else {
                return this.random.nextInt(4) == 0 ? SoundEvents.CAT_PURREOW : SoundEvents.CAT_AMBIENT;
            }
        } else {
            return SoundEvents.CAT_STRAY_AMBIENT;
        }
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    public void hiss() {
        this.playSound(SoundEvents.CAT_HISS, this.getSoundVolume(), this.getVoicePitch());
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.CAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    protected void usePlayerItem(PlayerEntity playerIn, ItemStack stack) {
        if (this.isFood(stack)) {
            this.playSound(SoundEvents.CAT_EAT, 1.0F, 1.0F);
        }

        super.usePlayerItem(playerIn, stack);
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public boolean doHurtTarget(Entity entityIn) {
        return entityIn.hurt(DamageSource.mobAttack(this), this.getAttackDamage());
    }

    public void tick() {
        super.tick();
        if (this.temptGoal != null && this.temptGoal.isRunning() && !this.isTame() && this.tickCount % 100 == 0) {
            this.playSound(SoundEvents.CAT_BEG_FOR_FOOD, 1.0F, 1.0F);
        }

        this.handleLieDown();
    }

    private void handleLieDown() {
        if ((this.isLying() || this.isRelaxStateOne()) && this.tickCount % 5 == 0) {
            this.playSound(SoundEvents.CAT_PURR, 0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), 1.0F);
        }

        this.updateLieDownAmount();
        this.updateRelaxStateOneAmount();
    }

    private void updateLieDownAmount() {
        this.lieDownAmountO = this.lieDownAmount;
        this.lieDownAmountOTail = this.lieDownAmountTail;
        if (this.isLying()) {
            this.lieDownAmount = Math.min(1.0F, this.lieDownAmount + 0.15F);
            this.lieDownAmountTail = Math.min(1.0F, this.lieDownAmountTail + 0.08F);
        } else {
            this.lieDownAmount = Math.max(0.0F, this.lieDownAmount - 0.22F);
            this.lieDownAmountTail = Math.max(0.0F, this.lieDownAmountTail - 0.13F);
        }
    }

    private void updateRelaxStateOneAmount() {
        this.relaxStateOneAmountO = this.relaxStateOneAmount;
        if (this.isRelaxStateOne()) {
            this.relaxStateOneAmount = Math.min(1.0F, this.relaxStateOneAmount + 0.1F);
        } else {
            this.relaxStateOneAmount = Math.max(0.0F, this.relaxStateOneAmount - 0.13F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getLieDownAmount(float p_213408_1_) {
        return MathHelper.lerp(p_213408_1_, this.lieDownAmountO, this.lieDownAmount);
    }

    @OnlyIn(Dist.CLIENT)
    public float getLieDownAmountTail(float p_213421_1_) {
        return MathHelper.lerp(p_213421_1_, this.lieDownAmountOTail, this.lieDownAmountTail);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRelaxStateOneAmount(float p_213424_1_) {
        return MathHelper.lerp(p_213424_1_, this.relaxStateOneAmountO, this.relaxStateOneAmount);
    }

    private DyeColor getOffspringColor(AnimalEntity femaleIn, AnimalEntity maleIn) {
        DyeColor dyecolor = ((UnikittyEntity)femaleIn).getColor();
        DyeColor dyecolor1 = ((UnikittyEntity)maleIn).getColor();
        CraftingInventory craftinginventory = makeContainer(dyecolor, dyecolor1);
        return this.level.getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, craftinginventory, this.level).map((p_213614_1_) -> p_213614_1_.assemble(craftinginventory)).map(ItemStack::getItem).filter(DyeItem.class::isInstance).map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> this.level.random.nextBoolean() ? dyecolor : dyecolor1);
    }

    private static CraftingInventory makeContainer(DyeColor p_213611_0_, DyeColor p_213611_1_) {
        CraftingInventory craftinginventory = new CraftingInventory(new Container((ContainerType)null, -1) {
            public boolean stillValid(PlayerEntity p_75145_1_) {
                return false;
            }
        }, 2, 1);
        craftinginventory.setItem(0, new ItemStack(DyeItem.byColor(p_213611_0_)));
        craftinginventory.setItem(1, new ItemStack(DyeItem.byColor(p_213611_1_)));
        return craftinginventory;
    }


    public UnikittyEntity getBreedOffspring(ServerWorld world, AgeableEntity parentIn) {
        UnikittyEntity unikittyentity = (UnikittyEntity)parentIn;
        UnikittyEntity unikittyentity1 = ModEntities.UNIKITTYENTITY.get().create(world);
        unikittyentity1.setColor(this.getOffspringColor(this, unikittyentity));

        if (parentIn instanceof UnikittyEntity) {
            if (this.isTame()) {
                unikittyentity1.setOwnerUUID(this.getOwnerUUID());
                unikittyentity1.setTame(true);
                if (this.random.nextBoolean()) {
                    unikittyentity1.setCollarColor(this.getCollarColor());
                } else {
                    unikittyentity1.setCollarColor(((UnikittyEntity)parentIn).getCollarColor());
                }
            }
        }

        return unikittyentity1;
    }

    public boolean canMate(AnimalEntity p_70878_1_) {
        if (!this.isTame()) {
            return false;
        } else if (!(p_70878_1_ instanceof UnikittyEntity)) {
            return false;
        } else {
            UnikittyEntity unikittyentity = (UnikittyEntity)p_70878_1_;
            return unikittyentity.isTame() && super.canMate(p_70878_1_);
        }
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        p_213386_4_ = super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
        this.setColor(getRandomUnikittyColor(p_213386_1_.getRandom()));

        World world = p_213386_1_.getLevel();
        if (world instanceof ServerWorld && ((ServerWorld)world).structureFeatureManager().getStructureAt(this.blockPosition(), true, Structure.SWAMP_HUT).isValid()) {
            this.setPersistenceRequired();
        }

        return p_213386_4_;
    }

    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            if (this.isTame() && this.isOwnedBy(p_230254_1_)) {
                return ActionResultType.SUCCESS;
            } else {
                return !this.isFood(itemstack) || !(this.getHealth() < this.getMaxHealth()) && this.isTame() ? ActionResultType.PASS : ActionResultType.SUCCESS;
            }
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(p_230254_1_)) {
                    if (!(item instanceof DyeItem)) {
                        if (item.isEdible() && this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                            this.usePlayerItem(p_230254_1_, itemstack);
                            this.heal((float)item.getFoodProperties().getNutrition());
                            return ActionResultType.CONSUME;
                        }

                        ActionResultType actionresulttype = super.mobInteract(p_230254_1_, p_230254_2_);
                        if (!actionresulttype.consumesAction() || this.isBaby()) {
                            this.setOrderedToSit(!this.isOrderedToSit());
                        }

                        return actionresulttype;
                    }

                    DyeColor dyecolor = ((DyeItem)item).getDyeColor();

                    if ((p_230254_1_.isCrouching()) && (dyecolor != this.getColor())) {
                        this.setColor(dyecolor);
                        if (!p_230254_1_.abilities.instabuild) {
                            itemstack.shrink(1);
                        }

                        this.setPersistenceRequired();
                        return ActionResultType.CONSUME;
                    }

                    if (!(p_230254_1_.isCrouching()) && (dyecolor != this.getCollarColor())) {
                        this.setCollarColor(dyecolor);
                        if (!p_230254_1_.abilities.instabuild) {
                            itemstack.shrink(1);
                        }

                        this.setPersistenceRequired();
                        return ActionResultType.CONSUME;
                    }
                }
                else {
                    if (p_230254_1_.isCrouching()){
                        hiss();
                    }
                }
            } else if (this.isFood(itemstack)) {
                this.usePlayerItem(p_230254_1_, itemstack);
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
                    this.tame(p_230254_1_);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                this.setPersistenceRequired();
                return ActionResultType.CONSUME;
            }

            ActionResultType actionresulttype1 = super.mobInteract(p_230254_1_, p_230254_2_);
            if (actionresulttype1.consumesAction()) {
                this.setPersistenceRequired();
            }

            return actionresulttype1;
        }
    }

    public boolean isFood(ItemStack p_70877_1_) {
        return TEMPT_INGREDIENT.test(p_70877_1_);
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.5F;
    }

    public boolean removeWhenFarAway(double p_213397_1_) {
        return !this.isTame() && this.tickCount > 2400;
    }

    static class MorningGiftGoal extends Goal {
        private final UnikittyEntity unikitty;
        private PlayerEntity ownerPlayer;
        private BlockPos goalPos;
        private int onBedTicks;

        public MorningGiftGoal(UnikittyEntity p_i50439_1_) {
            this.unikitty = p_i50439_1_;
        }

        public boolean canUse() {
            if (!this.unikitty.isTame()) {
                return false;
            } else if (this.unikitty.isOrderedToSit()) {
                return false;
            } else {
                LivingEntity livingentity = this.unikitty.getOwner();
                if (livingentity instanceof PlayerEntity) {
                    this.ownerPlayer = (PlayerEntity)livingentity;
                    if (!livingentity.isSleeping()) {
                        return false;
                    }

                    if (this.unikitty.distanceToSqr(this.ownerPlayer) > 100.0D) {
                        return false;
                    }

                    BlockPos blockpos = this.ownerPlayer.blockPosition();
                    BlockState blockstate = this.unikitty.level.getBlockState(blockpos);
                    if (blockstate.getBlock().is(BlockTags.BEDS)) {
                        this.goalPos = blockstate.getOptionalValue(BedBlock.FACING).map((p_234186_1_) -> blockpos.relative(p_234186_1_.getOpposite())).orElseGet(() -> new BlockPos(blockpos));
                        return !this.spaceIsOccupied();
                    }
                }

                return false;
            }
        }

        private boolean spaceIsOccupied() {
            for(UnikittyEntity unikittyentity : this.unikitty.level.getEntitiesOfClass(UnikittyEntity.class, (new AxisAlignedBB(this.goalPos)).inflate(2.0D))) {
                if (unikittyentity != this.unikitty && (unikittyentity.isLying() || unikittyentity.isRelaxStateOne())) {
                    return true;
                }
            }

            return false;
        }

        public boolean canContinueToUse() {
            return this.unikitty.isTame() && !this.unikitty.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !this.spaceIsOccupied();
        }

        public void start() {
            if (this.goalPos != null) {
                this.unikitty.setInSittingPose(false);
                this.unikitty.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1F);
            }
        }

        public void stop() {
            this.unikitty.setLying(false);
            float f = this.unikitty.level.getTimeOfDay(1.0F);
            if (this.ownerPlayer.getSleepTimer() >= 100 && (double)f > 0.77D && (double)f < 0.8D && (double)this.unikitty.level.getRandom().nextFloat() < 0.7D) {
                this.giveMorningGift();
            }

            this.onBedTicks = 0;
            this.unikitty.setRelaxStateOne(false);
            this.unikitty.getNavigation().stop();
        }

        private void giveMorningGift() {
            Random random = this.unikitty.getRandom();
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
            blockpos$mutable.set(this.unikitty.blockPosition());
            this.unikitty.randomTeleport((double)(blockpos$mutable.getX() + random.nextInt(11) - 5), (double)(blockpos$mutable.getY() + random.nextInt(5) - 2), (double)(blockpos$mutable.getZ() + random.nextInt(11) - 5), false);
            blockpos$mutable.set(this.unikitty.blockPosition());
            LootTable loottable = this.unikitty.level.getServer().getLootTables().get(LootTables.CAT_MORNING_GIFT);
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)this.unikitty.level)).withParameter(LootParameters.ORIGIN, this.unikitty.position()).withParameter(LootParameters.THIS_ENTITY, this.unikitty).withRandom(random);

            for(ItemStack itemstack : loottable.getRandomItems(lootcontext$builder.create(LootParameterSets.GIFT))) {
                this.unikitty.level.addFreshEntity(new ItemEntity(this.unikitty.level, (double)blockpos$mutable.getX() - (double)MathHelper.sin(this.unikitty.yBodyRot * ((float)Math.PI / 180F)), (double)blockpos$mutable.getY(), (double)blockpos$mutable.getZ() + (double)MathHelper.cos(this.unikitty.yBodyRot * ((float)Math.PI / 180F)), itemstack));
            }
        }

        public void tick() {
            if (this.ownerPlayer != null && this.goalPos != null) {
                this.unikitty.setInSittingPose(false);
                this.unikitty.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), (double)1.1F);
                if (this.unikitty.distanceToSqr(this.ownerPlayer) < 2.5D) {
                    ++this.onBedTicks;
                    if (this.onBedTicks > 16) {
                        this.unikitty.setLying(true);
                        this.unikitty.setRelaxStateOne(false);
                    } else {
                        this.unikitty.lookAt(this.ownerPlayer, 45.0F, 45.0F);
                        this.unikitty.setRelaxStateOne(true);
                    }
                } else {
                    this.unikitty.setLying(false);
                }
            }
        }
    }

    static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {
        @Nullable
        private PlayerEntity selectedPlayer;
        private final UnikittyEntity unikitty;

        public TemptGoal(UnikittyEntity p_i50438_1_, double p_i50438_2_, Ingredient p_i50438_4_, boolean p_i50438_5_) {
            super(p_i50438_1_, p_i50438_2_, p_i50438_4_, p_i50438_5_);
            this.unikitty = p_i50438_1_;
        }

        public void tick() {
            super.tick();
            if (this.selectedPlayer == null && this.mob.getRandom().nextInt(600) == 0) {
                this.selectedPlayer = this.player;
            } else if (this.mob.getRandom().nextInt(500) == 0) {
                this.selectedPlayer = null;
            }
        }

        protected boolean canScare() {
            return this.selectedPlayer != null && this.selectedPlayer.equals(this.player) ? false : super.canScare();
        }

        public boolean canUse() {
            return super.canUse() && !this.unikitty.isTame();
        }
    }
}
