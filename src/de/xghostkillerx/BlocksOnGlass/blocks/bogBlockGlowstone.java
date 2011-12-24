package de.xghostkillerx.BlocksOnGlass.blocks;

import net.minecraft.server.BlockLightStone;
import net.minecraft.server.Material;

public class bogBlockGlowstone extends BlockLightStone {

	public bogBlockGlowstone(int i, int j, Material material) {
		super(i, j, material);
	}
	public bogBlockGlowstone setHardness(float f) {
		this.strength = f;
		if (this.durability < f * 5.0F) {
			this.durability = f * 5.0F;
		}
		return this;
	}
}
