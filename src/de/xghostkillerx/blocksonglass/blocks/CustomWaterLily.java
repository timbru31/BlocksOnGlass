package de.xghostkillerx.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockWaterLily;
import net.minecraft.server.StepSound;
import net.minecraft.server.World;

public class CustomWaterLily extends BlockWaterLily {

	public CustomWaterLily(int i, int j) {
		super(i, j);
	}

	public CustomWaterLily setHardness(float f) {
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

	protected boolean d(int i) {
		return (i == Block.STATIONARY_WATER.id) || 
				(i == Block.GLOWSTONE.id) || (i == Block.GLASS.id) || (i == Block.LEAVES.id) || 
				(i == Block.FENCE.id) || (i == Block.NETHER_FENCE.id) || (i == Block.TNT.id) || 
				(i == Block.THIN_GLASS.id) || (i == Block.IRON_FENCE.id) || (i == Block.STEP.id) || 
				(i == Block.ICE.id) || (i == Block.CACTUS.id) || (i == Block.BRICK_STAIRS.id) || 
				(i == Block.WOOD_STAIRS.id) || (i == Block.NETHER_BRICK_STAIRS.id) || (i == Block.BRICK_STAIRS.id) || 
				(i == Block.COBBLESTONE_STAIRS.id) || (i == Block.PISTON.id) || (i == Block.PISTON_STICKY.id) || 
				(i == Block.PISTON_EXTENSION.id) || (i == Block.PISTON_MOVING.id);
	}

	public boolean f(World world, int i, int j, int k) {
		if (j >= 0 && j < 256) {
			return world.getData(i, j - 1, k) == 0;
		}
		else return false;
	}
}
