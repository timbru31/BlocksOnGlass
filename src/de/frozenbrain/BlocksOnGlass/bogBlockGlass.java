package de.frozenbrain.BlocksOnGlass;

import java.util.Random;

import net.minecraft.server.Block;
import net.minecraft.server.BlockBreakable;
import net.minecraft.server.Material;
import net.minecraft.server.StepSound;

public class bogBlockGlass extends BlockBreakable {

    public bogBlockGlass(int i, int j, Material material, boolean flag) {
        super(i, j, material, flag);
    }

    public boolean a() {
        return true;
    }
    
    public int a(Random random) {
        return 0;
    }
    
    protected bogBlockGlass setHardness(float f) {
        this.strength = f;
        if (this.durability < f * 5.0F) {
            this.durability = f * 5.0F;
        }

        return this;
    }
    
    protected Block setSound(StepSound stepsound) {
        this.stepSound = stepsound;
        return this;
    }
}