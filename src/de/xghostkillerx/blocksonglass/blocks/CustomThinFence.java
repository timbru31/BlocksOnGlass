package de.xghostkillerx.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockThinFence;
import net.minecraft.server.Material;
import net.minecraft.server.StepSound;

public class CustomThinFence extends BlockThinFence {

	public CustomThinFence(int i, int j, int k, Material material, boolean flag) {
		super(i,j,k,material, flag);
	}
	
    // Render as a full block
    public boolean b() {
        return true;
    }
	
	public CustomThinFence setHardness(float f) {
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
    
    public CustomThinFence setResistance(float f) {
    	this.durability = f * 3.0F;
        return this;
    }
}
