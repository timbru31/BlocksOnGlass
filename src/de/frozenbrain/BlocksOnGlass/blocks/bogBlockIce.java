package de.frozenbrain.BlocksOnGlass.blocks;

import net.minecraft.server.Material;
import net.minecraft.server.MaterialMapColor;

public class bogBlockIce extends bogBlockGlass {

	public bogBlockIce(int i, int j) {
        super(i, j, new Material(MaterialMapColor.g), true);
        this.frictionFactor = 0.98F;
        this.a(true);
    }

}