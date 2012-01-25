package de.xghostkillerx.blocksonglass.blocks;

import net.minecraft.server.Block;
import net.minecraft.server.BlockStairs;

public class CustomStairs extends BlockStairs {
	
    public boolean a() {
        return false;
    }
    
    @Override
    public boolean b() {
        return true;
    }
	
    public CustomStairs(int i, Block block) {
		super(i, block);
    }
}
