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
    	if (world.getTypeId(i, j - 1, k) == org.bukkit.Material.AIR.getId()) return false;
        return true;
    }
    
    public boolean f(World world, int i, int j, int k) {
        return world.isChunkLoaded(i, j, k);
    }
}
