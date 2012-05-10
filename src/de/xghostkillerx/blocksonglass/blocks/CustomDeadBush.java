package de.xghostkillerx.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockDeadBush;
import net.minecraft.server.StepSound;

public class CustomDeadBush extends BlockDeadBush {

	public CustomDeadBush(int i, int j) {
		super(i, j);
	}

	protected boolean d(int i) {
		return true;
	}
	
	public CustomDeadBush setHardness(float f) {
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
