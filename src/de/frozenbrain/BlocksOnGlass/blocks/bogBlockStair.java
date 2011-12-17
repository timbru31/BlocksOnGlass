package de.frozenbrain.BlocksOnGlass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockStairs;

public class bogBlockStair extends BlockStairs{
	
    public bogBlockStair(int i, Block block) {
		super(i, block);
    }
	public bogBlockStair setHardness(float f) {
        this.strength = f;
        if (this.durability < f * 5.0F) {
            this.durability = f * 5.0F;
        }
        return this;
    }
}
