package pjc21.mod.entity.unikitty.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pjc21.mod.entity.unikitty.EntityUniKitty;

public class EntityAIUniKittySit extends EntityAIMoveToBlock
{
	private final EntityUniKitty unikitty;

    public EntityAIUniKittySit(EntityUniKitty unikittyIn, double p_i45315_2_)
    {
        super(unikittyIn, p_i45315_2_, 8);
        this.unikitty = unikittyIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.unikitty.isTamed() && !this.unikitty.isSitting() && super.shouldExecute();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.unikitty.getAISit().setSitting(false);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
        this.unikitty.setSitting(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
        this.unikitty.getAISit().setSitting(false);

        if (!this.getIsAboveDestination())
        {
            this.unikitty.setSitting(false);
        }
        else if (!this.unikitty.isSitting())
        {
            this.unikitty.setSitting(true);
        }
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        if (!worldIn.isAirBlock(pos.up()))
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.CHEST)
            {
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).numPlayersUsing < 1)
                {
                    return true;
                }
            }
            else
            {
                if (block == Blocks.LIT_FURNACE)
                {
                    return true;
                }

                if (block == Blocks.BED && iblockstate.getValue(BlockBed.PART) != BlockBed.EnumPartType.HEAD)
                {
                    return true;
                }
            }

            return false;
        }
    }
}
