package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockIce;
import net.minecraft.server.CreativeModeTab;
import net.minecraft.server.StepSound;

public class CustomIce extends BlockIce {

	public CustomIce(int i, int j) {
        super(i, j);
        this.frictionFactor = 0.98F;
        this.b(true);
        this.a(CreativeModeTab.b);
    }
    
    public CustomIce setHardness(float f) {
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
    
    // Render as a full block
    public boolean c() {
        return true;
    }
}