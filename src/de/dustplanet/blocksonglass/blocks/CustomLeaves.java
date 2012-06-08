package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockLeaves;
import net.minecraft.server.StepSound;

public class CustomLeaves extends BlockLeaves {
	
	public CustomLeaves(int i, int j) {
        super(i, j);
    }
	
	public CustomLeaves setHardness(float f) {
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
}
