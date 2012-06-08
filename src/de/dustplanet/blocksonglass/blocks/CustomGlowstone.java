package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockLightStone;
import net.minecraft.server.Material;
import net.minecraft.server.StepSound;

public class CustomGlowstone extends BlockLightStone {

	public CustomGlowstone(int i, int j, Material material) {
		super(i, j, material);
	}
	public CustomGlowstone setHardness(float f) {
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

	public CustomGlowstone setLightValue(float f) {
		lightEmission[this.id] = (int)(15.0F * f);
		return this;
	}
	
    // Render as a full block
    public boolean b() {
        return true;
    } 
}
