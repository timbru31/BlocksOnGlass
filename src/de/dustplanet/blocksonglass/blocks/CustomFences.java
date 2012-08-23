package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockFence;
import net.minecraft.server.CreativeModeTab;
import net.minecraft.server.Material;
import net.minecraft.server.StepSound;

public class CustomFences extends BlockFence {
	    
    public CustomFences(int i, int j) {
        super(i, j, Material.WOOD);
        this.a(CreativeModeTab.c);
    }

    public CustomFences(int i, int j, Material material) {
        super(i, j, material);
        this.a(CreativeModeTab.c);
    }
    
    // Render as a full block
    public boolean c() {
        return true;
    }
    
    public CustomFences setHardness(float f) {
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
    
    public CustomFences setResistance(float f) {
    	this.durability = f * 3.0F;
        return this;
    } 
}