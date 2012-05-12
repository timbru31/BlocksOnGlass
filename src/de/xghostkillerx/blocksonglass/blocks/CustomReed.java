package de.xghostkillerx.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockReed;
import net.minecraft.server.StepSound;

public class CustomReed extends BlockReed {

    public CustomReed(int i, int j) {
		super(i, j);
	}

	public CustomReed setHardness(float f) {
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
