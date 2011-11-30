package de.frozenbrain.BlocksOnGlass.blocks;

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
    public boolean canPlace(World world, int i, int j, int k) {
     if (de.frozenbrain.BlocksOnGlass.bogPlugin.config.getBoolean("fencefix") == true) {
     return true;
     }
        return world.getTypeId(i, j - 1, k) == this.id ? true : (!world.getMaterial(i, j - 1, k).isBuildable() ? false : super.canPlace(world, i, j, k));
    }
    
    public bogBlockFence setHardness(float f) {
        this.strength = f;
        if (this.durability < f * 5.0F) {
            this.durability = f * 5.0F;
        }

        return this;
    }
    
    public Block setSound(StepSound stepsound) {
        this.stepSound = stepsound;
        return this;
    }
    
    public bogBlockFence setResistance(float f) {
    	this.durability = f * 3.0F;
        return this;
    }
}