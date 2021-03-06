package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockTNT;
import net.minecraft.server.CreativeModeTab;
import net.minecraft.server.StepSound;

public class CustomTNT extends BlockTNT{

	public CustomTNT(int i, int j) {
		super(i, j);
		this.a(CreativeModeTab.d);
	}

	public CustomTNT setHardness(float f) {
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
