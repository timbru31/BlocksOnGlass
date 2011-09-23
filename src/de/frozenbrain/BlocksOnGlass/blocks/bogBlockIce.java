package de.frozenbrain.BlocksOnGlass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockIce;
import net.minecraft.server.StepSound;

public class bogBlockIce extends BlockIce {

	public bogBlockIce(int i, int j) {
        super(i, j);
    }
	
    @Override
    public boolean a() {
        return true;
    }
    
    public bogBlockIce setHardness(float f) {
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