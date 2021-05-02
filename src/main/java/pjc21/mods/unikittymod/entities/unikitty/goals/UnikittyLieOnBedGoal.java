package pjc21.mods.unikittymod.entities.unikitty.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import pjc21.mods.unikittymod.entities.unikitty.UnikittyEntity;

import java.util.EnumSet;

public class UnikittyLieOnBedGoal extends MoveToBlockGoal {

    private final UnikittyEntity unikitty;

    public UnikittyLieOnBedGoal(UnikittyEntity p_i50331_1_, double p_i50331_2_, int p_i50331_4_) {
        super(p_i50331_1_, p_i50331_2_, p_i50331_4_, 6);
        this.unikitty = p_i50331_1_;
        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    public boolean canUse() {
        return this.unikitty.isTame() && !this.unikitty.isOrderedToSit() && !this.unikitty.isLying() && super.canUse();
    }

    public void start() {
        super.start();
        this.unikitty.setInSittingPose(false);
    }

    @SuppressWarnings("NullableProblems")
    protected int nextStartTick(CreatureEntity p_203109_1_) {
        return 40;
    }

    public void stop() {
        super.stop();
        this.unikitty.setLying(false);
    }

    public void tick() {
        super.tick();
        this.unikitty.setInSittingPose(false);
        if (!this.isReachedTarget()) {
            this.unikitty.setLying(false);
        } else if (!this.unikitty.isLying()) {
            this.unikitty.setLying(true);
        }
    }

    protected boolean isValidTarget(IWorldReader p_179488_1_, BlockPos p_179488_2_) {
        return p_179488_1_.isEmptyBlock(p_179488_2_.above()) && p_179488_1_.getBlockState(p_179488_2_).getBlock().is(BlockTags.BEDS);
    }
}
