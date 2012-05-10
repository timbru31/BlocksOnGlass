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
    
    public boolean canPlace(World world, int i, int j, int k) {
    	if (world.getTypeId(i, j - 1, k) == org.bukkit.Material.AIR.getId()) return false;
        return true;
    }

    public boolean f(World world, int i, int j, int k) {
        if (j >= 0 && j < 256) {
            int l = world.getTypeId(i, j - 1, k);
            if (l == Block.MYCEL.id) return l == Block.MYCEL.id || world.m(i, j, k) < 13 && this.d(l);
            else return true;
        } else {
            return false;
        }
    }
}
