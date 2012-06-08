package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockCactus;
import net.minecraft.server.StepSound;
import net.minecraft.server.World;

public class CustomCactus extends BlockCactus {

	public CustomCactus(int i, int j) {
		super(i, j);
	}

	// Render as a full block
	public boolean b() {
		return true;
	}

	public CustomCactus setHardness(float f) {
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

	public boolean f(World world, int i, int j, int k) {
		int l = world.getTypeId(i, j - 1, k);
		if ((l == Block.CACTUS.id) || (l == Block.SAND.id)) {
			if (world.getMaterial(i - 1, j, k).isBuildable())
				return false;
			if (world.getMaterial(i + 1, j, k).isBuildable())
				return false;
			if (world.getMaterial(i, j, k - 1).isBuildable())
				return false;
			if (world.getMaterial(i, j, k + 1).isBuildable()) {
				return false;
			}

			return (l == Block.CACTUS.id) || (l == Block.SAND.id);
		}

		return (l == Block.GLOWSTONE.id) || (l == Block.GLASS.id) || (l == Block.LEAVES.id) || 
				(l == Block.FENCE.id) || (l == Block.NETHER_FENCE.id) || (l == Block.TNT.id) || 
				(l == Block.THIN_GLASS.id) || (l == Block.IRON_FENCE.id) || (l == Block.STEP.id) || 
				(l == Block.ICE.id) || (l == Block.CACTUS.id) || (l == Block.BRICK_STAIRS.id) || 
				(l == Block.WOOD_STAIRS.id) || (l == Block.NETHER_BRICK_STAIRS.id) || (l == Block.BRICK_STAIRS.id) || 
				(l == Block.COBBLESTONE_STAIRS.id) || (l == Block.PISTON.id) || (l == Block.PISTON_STICKY.id) || 
				(l == Block.PISTON_EXTENSION.id) || (l == Block.PISTON_MOVING.id);
	}
}
