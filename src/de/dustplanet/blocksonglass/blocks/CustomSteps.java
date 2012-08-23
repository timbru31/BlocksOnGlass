package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockStep;
import net.minecraft.server.CreativeModeTab;
import net.minecraft.server.StepSound;

public class CustomSteps extends BlockStep {

	public CustomSteps(int i, boolean flag) {
		super(i, flag);
		this.a(CreativeModeTab.b);
	}

	// Render as a full block
	public boolean c() {
		return true;
	}

	public CustomSteps setHardness(float f) {
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

	public CustomSteps setResistance(float f) {
		this.durability = f * 3.0F;
		return this;
	}
}
