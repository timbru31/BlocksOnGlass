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
    
    public boolean canPlace(World world, int i, int j, int k)
    {
        if(!super.canPlace(world, i, j, k))
        {
            return false;
        } else
        {
            return canBlockStay(world, i, j, k);
        }
    }


    public boolean canBlockStay(World world, int i, int j, int k)
    {
        if(world.getMaterial(i - 1, j, k).isSolid())
        {
            return false;
        }
        if(world.getMaterial(i + 1, j, k).isSolid())
        {
            return false;
        }
        if(world.getMaterial(i, j, k - 1).isSolid())
        {
            return false;
        }
        if(world.getMaterial(i, j, k + 1).isSolid())
        {
            return false;
        } else
        {
            int l = world.getTypeId(i, j - 1, k);
            return l == Block.CACTUS.id || l == Block.SAND.id;
        }
    }
}
