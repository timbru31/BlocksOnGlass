package de.xghostkillerx.BlocksOnGlass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockTNT;

public class bogBlockTNT extends BlockTNT{

	public bogBlockTNT(int i, int j) {
		super(i, j);
	}

	public Block setHardness(float f) {
		this.strength = f;
		return this;
	}

}
