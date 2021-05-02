package pjc21.mods.unikittymod.entities.unikitty.goals;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import pjc21.mods.unikittymod.entities.unikitty.UnikittyEntity;

public class UnikittySitOnBlockGoal extends MoveToBlockGoal {

    private final UnikittyEntity unikitty;

    public UnikittySitOnBlockGoal(UnikittyEntity p_i50330_1_, double p_i50330_2_) {
        super(p_i50330_1_, p_i50330_2_, 8);
        this.unikitty = p_i50330_1_;
    }

    public boolean canUse() {
        return this.unikitty.isTame() && !this.unikitty.isOrderedToSit() && super.canUse();
    }

    public void start() {
        super.start();
        this.unikitty.setInSittingPose(false);
    }

    public void stop() {
        super.stop();
        this.unikitty.setInSittingPose(false);
    }

    public void tick() {
        super.tick();
        this.unikitty.setInSittingPose(this.isReachedTarget());
    }

    protected boolean isValidTarget(IWorldReader p_179488_1_, BlockPos p_179488_2_) {
        if (!p_179488_1_.isEmptyBlock(p_179488_2_.above())) {
            return false;
        } else {
            BlockState blockstate = p_179488_1_.getBlockState(p_179488_2_);
            if (blockstate.is(Blocks.CHEST)) {
                return ChestTileEntity.getOpenCount(p_179488_1_, p_179488_2_) < 1;
            } else {
                return blockstate.is(Blocks.FURNACE) && blockstate.getValue(FurnaceBlock.LIT) || blockstate.is(BlockTags.BEDS, (p_234025_0_) -> p_234025_0_.getOptionalValue(BedBlock.PART).map((p_234026_0_) -> p_234026_0_ != BedPart.HEAD).orElse(true));
            }
        }
    }
}
