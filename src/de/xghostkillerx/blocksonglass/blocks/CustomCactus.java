package de.xghostkillerx.blocksonglass.blocks;

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
		if (l == Block.CACTUS.id || l == Block.SAND.id) {
			if (world.getMaterial(i - 1, j, k).isBuildable()) {
				return false;
			} else if (world.getMaterial(i + 1, j, k).isBuildable()) {
				return false;
			} else if (world.getMaterial(i, j, k - 1).isBuildable()) {
				return false;
			} else if (world.getMaterial(i, j, k + 1).isBuildable()) {
				return false;
			}
			else {
				return l == Block.CACTUS.id || l == Block.SAND.id;
			}
		}

		return true;
	}
}
