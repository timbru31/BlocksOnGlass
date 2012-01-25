package de.xghostkillerx.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.StepSound;
import net.minecraft.server.World;

public class CustomFlowers extends BlockFlower {

	public CustomFlowers(int i, int j) {
		super(i, j);
	}

	public CustomFlowers setHardness(float f) {
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

	public boolean canPlace(World world, int i, int j, int k) {
		return (world.getTypeId(i, j, k) == 0) && (world.getTypeId(i, j - 1, k) != 0);
	}

//	protected boolean d(int i) {
//		return i == Block.GRASS.id || i == Block.DIRT.id || i == Block.SOIL.id || 
//				i == Block.GLOWSTONE.id || i == Block.GLASS.id || i == Block.LEAVES.id;
//	}



	public boolean f(World world, int i, int j, int k) {
		return d(world.getTypeId(i, j - 1, k));
	}
}
