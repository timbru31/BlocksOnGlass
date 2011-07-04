package de.frozenbrain.BlocksOnGlass;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFence;
import net.minecraft.server.StepSound;
import net.minecraft.server.World;

public class bogBlockFence extends BlockFence {

    public bogBlockFence(int i, int j) {
        super(i, j);
    }

    @Override
    public boolean a() {
        return true;
    }
    
    @Override
    public boolean b() {
        return true;
    }
    
    @Override
    public AxisAlignedBB e(World world, int i, int j, int k) {
        return AxisAlignedBB.b((double) i, (double) j, (double) k, (double) i, (double) j, (double) k);
    }
    
    @Override
    public boolean canPlace(World world, int i, int j, int k) {
        return world.getTypeId(i, j - 1, k) == this.id ? true : (!world.getMaterial(i, j - 1, k).isBuildable() ? false : super.canPlace(world, i, j, k));
    }
    
    protected bogBlockFence setHardness(float f) {
        this.strength = f;
        if (this.durability < f * 5.0F) {
            this.durability = f * 5.0F;
        }

        return this;
    }
    
    protected Block setSound(StepSound stepsound) {
        this.stepSound = stepsound;
        return this;
    }
    
    public bogBlockFence setResistance(float f) {
    	this.durability = f * 3.0F;
        return this;
    }
}