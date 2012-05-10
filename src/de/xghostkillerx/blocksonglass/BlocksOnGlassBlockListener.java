package de.xghostkillerx.blocksonglass;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

/**
 * BlocksOnGlass for CraftBukkit/Bukkit
 * Handles the melt tweak
 * 
 * Refer to the forum thread:
 * http://bit.ly/bogbukkit
 * Refer to the dev.bukkit.org page:
 * http://bit.ly/bogbukkitdev
 *
 * @author xGhOsTkiLLeRx
 * @thanks to FrozenBrain for the original BlocksOnGlass plugin!
 * @thanks to mooman219 for help with bug fixing and NMS code!
 * 
 */

public class BlocksOnGlassBlockListener implements Listener {

	public BlocksOnGlass plugin;
	public BlocksOnGlassBlockListener(BlocksOnGlass instance) {
		plugin = instance;
	}

	@EventHandler
	public void onBlockFade(BlockFadeEvent event) {
		Block block = event.getBlock();
		if (block.getType() == Material.ICE || block.getType() == Material.SNOW || block.getType() == Material.SNOW_BLOCK) {
			if (plugin.config.getBoolean("tweaks.noMelt") == true)
				event.setCancelled(true);
		}
	}
}