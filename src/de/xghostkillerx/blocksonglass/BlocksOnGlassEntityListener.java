package de.xghostkillerx.blocksonglass;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * BlocksOnGlass for CraftBukkit/Bukkit
 * Handles the damage events
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

public class BlocksOnGlassEntityListener implements Listener {

	public BlocksOnGlass plugin;
	public BlocksOnGlassEntityListener(BlocksOnGlass instance) {
		plugin = instance;
	}

	// Damage event
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		// If it's suffocation
		if (event.getCause() == DamageCause.SUFFOCATION) {
			// And caused by air or or the fences
			if (event.getEntity().getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.FENCE
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.NETHER_FENCE
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.SELF).getType() == Material.FENCE
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.SELF).getType() == Material.NETHER_FENCE
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.FENCE
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.NETHER_FENCE
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.SELF).getType() == Material.AIR
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.SELF).getType() != Material.NETHER_FENCE
					|| event.getEntity().getLocation().getBlock().getRelative(BlockFace.SELF).getType() != Material.FENCE) {
				// cancel it!
				event.setCancelled(true);
			}
		}
	}
}
