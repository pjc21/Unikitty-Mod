package pjc21.mod.entity.unikitty;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.DyeUtils;
import pjc21.mod.entity.unikitty.ai.EntityAIUniKittySit;
import pjc21.mod.init.ItemInit;
import pjc21.mod.util.handlers.LootTableHandler;
import pjc21.mod.util.handlers.SoundsHandler;


public class EntityUniKitty extends EntityTameable
{
	private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.<Byte>createKey(EntityUniKitty.class, DataSerializers.BYTE);

	private EntityAITempt aiTempt;
	public int timeUntilNextHairBall;

	/**
     * Internal crafting inventory used to check the result of mixing dyes corresponding to the fleece color when
     * breeding sheep.
     */

    private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container()
    {
        /**
         * Determines whether supplied player can use this container
         */
        public boolean canInteractWith(EntityPlayer playerIn)
        {
            return false;
        }
    }, 2, 1);
    
    /** Map from EnumDyeColor to RGB values for passage to GlStateManager.color() */
    private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);

    private static float[] createUniKittyColor(EnumDyeColor p_192020_0_)
    {
        float[] afloat = p_192020_0_.getColorComponentValues();
        float f = 0.75F;
        return new float[] {afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
    }

    @SideOnly(Side.CLIENT)
    public static float[] getDyeRgb(EnumDyeColor dyeColor)
    {
        return DYE_TO_RGB.get(dyeColor);
    }

    public EntityUniKitty(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 0.7F);
        this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.DYE));
        this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.DYE));
        this.timeUntilNextHairBall = this.rand.nextInt(6000) + 6000;
    }

    @Override
    protected void initEntityAI()
    {
    	this.aiSit = new EntityAISit(this);
        this.aiTempt = new EntityAITempt(this, 0.6D, ItemInit.FISH_GOLDEN, false);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt);
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.tasks.addTask(6, new EntityAIUniKittySit(this, 0.8D));
        this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(8, new EntityAIOcelotAttack(this));
        this.tasks.addTask(9, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(10, new EntityAIWanderAvoidWater(this, 0.8D, 1.0000001E-5F));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(12, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(13, new EntityAIPanic(this, 2.0D));
        this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, (Predicate)null));
    }

    @Override
    protected void updateAITasks()
    {
        //super.updateAITasks();
        if (this.getMoveHelper().isUpdating())
        {
            double d0 = this.getMoveHelper().getSpeed();

            if (d0 == 0.6D)
            {
                this.setSneaking(true);
                this.setSprinting(false);
            }
            else if (d0 == 1.33D)
            {
                this.setSneaking(false);
                this.setSprinting(true);
            }
            else
            {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        }
        else
        {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.world.isRemote && !this.isChild() && --this.timeUntilNextHairBall <= 0)
        {
            this.playSound(SoundsHandler.ENTITY_UNIKITTY_HAIRBALL, 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

            switch (this.getFurColor())
            {
	            case BLACK:
	            {
	            	this.dropItem(ItemInit.STRING_BLACK, 1);
	            	break;
	            }
	            case BLUE:
                {
                	this.dropItem(ItemInit.STRING_BLUE, 1);
                	break;
                }
	            case BROWN:
                {
                	this.dropItem(ItemInit.STRING_BROWN, 1);
                	break;
                }
	            case CYAN:
                {
                	this.dropItem(ItemInit.STRING_CYAN, 1);
                	break;
                }
	            case GRAY:
                {
                	this.dropItem(ItemInit.STRING_GRAY, 1);
                	break;
                }
	            case GREEN:
                {
                	this.dropItem(ItemInit.STRING_GREEN, 1);
                	break;
                }
	            case LIGHT_BLUE:
                {
                	this.dropItem(ItemInit.STRING_LIGHT_BLUE, 1);
                	break;
                }
	            case SILVER:
                {
                	this.dropItem(ItemInit.STRING_LIGHT_GRAY, 1);
                	break;
                }
	            case LIME:
                {
                	this.dropItem(ItemInit.STRING_LIME, 1);
                	break;
                }
	            case MAGENTA:
                {
                	this.dropItem(ItemInit.STRING_MAGENTA, 1);
                	break;
                }
	            case ORANGE:
                {
                	this.dropItem(ItemInit.STRING_ORANGE, 1);
                	break;
                }
	            case PINK:
                {
                	this.dropItem(ItemInit.STRING_PINK, 1);
                	break;
                }
	            case PURPLE:
                {
                	this.dropItem(ItemInit.STRING_PURPLE, 1);
                	break;
                }
	            case RED:
                {
                	this.dropItem(ItemInit.STRING_RED, 1);
                	break;
                }
	            case WHITE:
                default:
                {
                	this.dropItem(Items.STRING, 1);
                	break;
                }
                case YELLOW:
                {
                	this.dropItem(ItemInit.STRING_YELLOW, 1);
                	break;
                }
            }
            this.timeUntilNextHairBall = this.rand.nextInt(6000) + 6000;
        }
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
    protected boolean canDespawn()
    {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
    	
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(DYE_COLOR, Byte.valueOf((byte)0));
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
    	switch (this.getFurColor())
        {
	        case BLACK:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_BLACK;
	        }
	        case BLUE:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_BLUE;
	        }
	        case BROWN:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_BROWN;
	        }
	        case CYAN:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_CYAN;
	        }
	        case GRAY:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_GRAY;
	        }
	        case GREEN:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_GREEN;
	        }
	        case LIGHT_BLUE:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_LIGHT_BLUE;
	        }
	        case SILVER:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_LIGHT_GRAY;
	        }
	        case LIME:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_LIME;
	        }
	        case MAGENTA:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_MAGENTA;
	        }
	        case ORANGE:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_ORANGE;
	        }
	        case PINK:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_PINK;
	        }
	        case PURPLE:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_PURPLE;
	        }
	        case RED:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_RED;
	        }
	        case WHITE:
	        default:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_WHITE;
	        }
	        case YELLOW:
	        {
	        	return LootTableHandler.ENTITY_UNIKITTY_YELLOW;
	        }
        }
    }

	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed())
        {
            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack))
            {
                this.aiSit.setSitting(!this.isSitting());
            }
            else if (!this.isOwner(player))
            {
            	this.playSound(SoundEvents.ENTITY_CAT_HISS, 1.0F, 1.0F);
            }
        }
        else if ((this.aiTempt == null || this.aiTempt.isRunning()) && itemstack.getItem() == ItemInit.FISH_GOLDEN && player.getDistanceSq(this) < 9.0D)
        {
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote)
            {
                if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
                {
                    this.setTamedBy(player);
                    this.playTameEffect(true);
                    this.aiSit.setSitting(true);
                    this.world.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }
        
        if (this.isTamed())
        {
        	if (this.isOwner(player) && !this.world.isRemote && itemstack.getItem() == Items.DYE)
        	{
        		Optional<EnumDyeColor> color = DyeUtils.colorFromStack(itemstack);
            	if(color.isPresent()) 
                {
                    this.setFurColor(color.get());
                    if (!player.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }
                }
        	}
        }
        else if (!(this.isTamed()))
        {
        	if (!this.world.isRemote && itemstack.getItem() == Items.DYE)
        	{
        		Optional<EnumDyeColor> color = DyeUtils.colorFromStack(itemstack);
            	if(color.isPresent()) 
                {
                    this.setFurColor(color.get());
                    if (!player.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);
                    }
                }
        	}
        }
        
        return super.processInteract(player, hand);
    }

	public static void registerFixesUniKitty(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityUniKitty.class);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setByte("Color", (byte)this.getFurColor().getMetadata());
        compound.setInteger("HairBallTime", this.timeUntilNextHairBall);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setFurColor(EnumDyeColor.byMetadata(compound.getByte("Color")));
        
        if (compound.hasKey("HairBallTime"))
        {
            this.timeUntilNextHairBall = compound.getInteger("HairBallTime");
        }
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
    	if (this.isTamed())
        {
            if (this.isInLove())
            {
                return SoundEvents.ENTITY_CAT_PURR;
            }
            else
            {
                return this.rand.nextInt(4) == 0 ? SoundEvents.ENTITY_CAT_PURREOW : SoundEvents.ENTITY_CAT_AMBIENT;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_CAT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            if (this.aiSit != null)
            {
                this.aiSit.setSitting(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }
    
    /**
     * Gets the wool color of this sheep.
     */
    public EnumDyeColor getFurColor()
    {
    	return EnumDyeColor.byMetadata(((Byte)this.dataManager.get(DYE_COLOR)).byteValue() & 15);
    }

    /**
     * Sets the fur color of this unikitty
     */
    public void setFurColor(EnumDyeColor color)
    {
        byte b0 = ((Byte)this.dataManager.get(DYE_COLOR)).byteValue();
        this.dataManager.set(DYE_COLOR, Byte.valueOf((byte)(b0 & 240 | color.getMetadata() & 15)));
    }

    /**
     * Chooses a color based on the provided random.
     */
    public static EnumDyeColor getRandomUniKittyColor(Random random)
    {
        int i = random.nextInt(100);

        if (i < 20)
        {
            return EnumDyeColor.WHITE;
        }
        else if (i < 60)
        {
        	return EnumDyeColor.ORANGE;
        }
        else
        {
            return EnumDyeColor.BLACK;
        }
    }

    @Override
    public EntityUniKitty createChild(EntityAgeable ageable)
    {
    	EntityUniKitty entityunikitty = (EntityUniKitty)ageable;
    	EntityUniKitty entityunikitty1 = new EntityUniKitty(this.world);
    	
    	if (this.isTamed())
    	{
    		entityunikitty.setOwnerId(this.getOwnerId());
    		entityunikitty.setTamed(true);
    	}
    	
    	entityunikitty1.setFurColor(this.getDyeColorMixFromParents(this, entityunikitty));
        
    	return entityunikitty1;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isBreedingItem(@Nonnull ItemStack stack)
    {
        return !stack.isEmpty() && stack.getItem() == Items.FISH;
    }
    
    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if (otherAnimal == this)
        {
            return false;
        }
        /*
        else if (!this.isTamed())
        {
            return false;
        }
        */
        else if (!(otherAnimal instanceof EntityUniKitty))
        {
            return false;
        }
        else
        {
        	EntityUniKitty entityunikitty = (EntityUniKitty)otherAnimal;

        	return this.isInLove() && entityunikitty.isInLove();
        	
        	/*
            if (!entityunikitty.isTamed())
            {
                return false;
            }
            else
            {
                return this.isInLove() && entityunikitty.isInLove();
            }
            */
        	
        }
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.rand.nextInt(3) != 0;
    }
    
    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    @Override
    public boolean isNotColliding()
    {
        if (this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox()))
        {
            BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

            if (blockpos.getY() < this.world.getSeaLevel())
            {
                return false;
            }

            IBlockState iblockstate = this.world.getBlockState(blockpos.down());
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS || block.isLeaves(iblockstate, this.world, blockpos.down()))
            {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Get the name of this object. For players this returns their username
     */
    @Override
    public String getName()
    {
        if (this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
            return this.isTamed() ? I18n.translateToLocal("Pet UniKitty") : super.getName();
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setFurColor(getRandomUniKittyColor(this.world.rand));
        return livingdata;
    }

    /**
     * Attempts to mix both parent unikitty to come up with a mixed dye color.
     */
    private EnumDyeColor getDyeColorMixFromParents(EntityAnimal father, EntityAnimal mother)
    {
        int i = ((EntityUniKitty)father).getFurColor().getDyeDamage();
        int j = ((EntityUniKitty)mother).getFurColor().getDyeDamage();
        this.inventoryCrafting.getStackInSlot(0).setItemDamage(i);
        this.inventoryCrafting.getStackInSlot(1).setItemDamage(j);
        ItemStack itemstack = CraftingManager.findMatchingResult(this.inventoryCrafting, ((EntityUniKitty)father).world);
        int k;

        if (itemstack.getItem() == Items.DYE)
        {
            k = itemstack.getMetadata();
        }
        else
        {
            k = this.world.rand.nextBoolean() ? i : j;
        }

        return EnumDyeColor.byDyeDamage(k);
    }

    static
    {
        for (EnumDyeColor enumdyecolor : EnumDyeColor.values())
        {
            DYE_TO_RGB.put(enumdyecolor, createUniKittyColor(enumdyecolor));
        }

        DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] {0.9019608F, 0.9019608F, 0.9019608F});
    }
}
