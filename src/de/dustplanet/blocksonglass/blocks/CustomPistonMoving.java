package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.BlockPistonMoving;

public class CustomPistonMoving extends BlockPistonMoving{

	public CustomPistonMoving(int i) {
		super(i);
	}

    // Render as a full block
    public boolean c() {
        return true;
    }
}
