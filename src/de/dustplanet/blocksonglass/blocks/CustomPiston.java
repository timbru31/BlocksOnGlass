package de.dustplanet.blocksonglass.blocks;

import net.minecraft.server.BlockPiston;
import net.minecraft.server.CreativeModeTab;

public class CustomPiston extends BlockPiston {

	public CustomPiston(int i, int j, boolean flag) {
		super(i, j, flag);
		this.a(CreativeModeTab.d);
	}
	
    // Render as a full block
    public boolean c() {
        return true;
    }
}
