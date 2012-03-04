package de.xghostkillerx.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockMushroom;
import net.minecraft.server.StepSound;
import net.minecraft.server.World;

public class CustomMushroom extends BlockMushroom {

	public CustomMushroom(int i, int j) {
		super(i, j);
	}

	public CustomMushroom setHardness(float f) {
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
	
	public CustomMushroom setLightValue(float f) {
		lightEmission[this.id] = (int)(15.0F * f);
		return this;
	}
	
    protected boolean d(int i) {
        return Block.n[i] ||
        		i == Block.GLOWSTONE.id || i == Block.GLASS.id || i == Block.LEAVES.id ||
        		i == Block.FENCE.id || i == Block.NETHER_FENCE.id || i == Block.TNT.id ||
        		i == Block.THIN_GLASS.id || i == Block.IRON_FENCE.id || i == Block.STEP.id ||
        		i == Block.ICE.id || i == Block.CACTUS.id || i == Block.BRICK_STAIRS.id ||
        		i == Block.WOOD_STAIRS.id || i == Block.NETHER_BRICK_STAIRS.id || i == Block.BRICK_STAIRS.id ||
        		i == Block.COBBLESTONE_STAIRS.id || i == Block.PISTON.id || i == Block.PISTON_STICKY.id ||
        		i == Block.PISTON_EXTENSION.id || i == Block.PISTON_MOVING.id;
    }
    
    public boolean f(World world, int i, int j, int k) {
        if (j >= 0 && j < 256) {
            int l = world.getTypeId(i, j - 1, k);

            return l == Block.MYCEL.id || world.m(i, j, k) < 13 && this.d(l) ||
            		l == Block.GLOWSTONE.id || l == Block.GLASS.id || l == Block.LEAVES.id ||
    				l == Block.FENCE.id || l == Block.NETHER_FENCE.id || l == Block.TNT.id ||
    				l == Block.THIN_GLASS.id || l == Block.IRON_FENCE.id || l == Block.STEP.id ||
    				l == Block.ICE.id || l == Block.CACTUS.id || l == Block.BRICK_STAIRS.id ||
    				l == Block.WOOD_STAIRS.id || l == Block.NETHER_BRICK_STAIRS.id || l == Block.BRICK_STAIRS.id ||
    				l == Block.COBBLESTONE_STAIRS.id || l == Block.PISTON.id || l == Block.PISTON_STICKY.id ||
    				l == Block.PISTON_EXTENSION.id || l == Block.PISTON_MOVING.id;
        } else {
            return false;
        }
    }
}
