package de.frozenbrain.BlocksOnGlass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockLeaves;
import net.minecraft.server.StepSound;

public class bogBlockLeaves extends BlockLeaves {
	
	public bogBlockLeaves(int i, int j) {
        super(i, j);
        this.b = true; // n()
    }
	
	@Override
	public boolean a() {
		return true;
	}
	
	public bogBlockLeaves setHardness(float f) {
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
