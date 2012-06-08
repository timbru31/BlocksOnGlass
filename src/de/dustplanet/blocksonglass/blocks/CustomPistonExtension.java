package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.BlockPistonExtension;

public class CustomPistonExtension extends BlockPistonExtension {

	public CustomPistonExtension(int i, int j) {
		super(i, j);
	}

    // Render as a full block
    public boolean b() {
        return true;
    }
}
