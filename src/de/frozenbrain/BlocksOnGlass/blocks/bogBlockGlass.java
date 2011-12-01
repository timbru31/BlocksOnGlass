package de.frozenbrain.BlocksOnGlass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockGlass;
import net.minecraft.server.Material;
import net.minecraft.server.StepSound;

public class bogBlockGlass extends BlockGlass {

    public bogBlockGlass(int i, int j, Material material, boolean flag) {
        super(i, j, material, flag);
    }

    @Override
    public boolean a() {
        return true;
    }
    
    public bogBlockGlass setHardness(float f) {
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
}
