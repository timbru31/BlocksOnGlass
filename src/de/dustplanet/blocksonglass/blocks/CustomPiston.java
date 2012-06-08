package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.BlockPiston;

public class CustomPiston extends BlockPiston {

	public CustomPiston(int i, int j, boolean flag) {
		super(i, j, flag);
	}
	
    // Render as a full block
    public boolean b() {
        return true;
    }
}
