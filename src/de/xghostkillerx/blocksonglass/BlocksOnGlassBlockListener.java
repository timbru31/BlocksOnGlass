package de.xghostkillerx.blocksonglass;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BlocksOnGlassBlockListener implements Listener {

	public BlocksOnGlass plugin;
	public BlocksOnGlassBlockListener(BlocksOnGlass instance) {
		plugin = instance;
	}

	@EventHandler
	public void onBlockFade(final BlockFadeEvent event) {
		Block block = event.getBlock();
		if (block.getType() == Material.ICE || block.getType() == Material.SNOW || block.getType() == Material.SNOW_BLOCK) {
			if (plugin.config.getBoolean("tweaks.noMelt") == true)
				event.setCancelled(true);
		}
	}
}