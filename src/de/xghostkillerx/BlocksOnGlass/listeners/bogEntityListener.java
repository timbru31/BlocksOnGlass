package de.xghostkillerx.BlocksOnGlass.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

import de.xghostkillerx.BlocksOnGlass.bogPlugin;

public class bogEntityListener extends EntityListener {

	@SuppressWarnings("unused")
	private final bogPlugin plugin;
	public bogEntityListener(final bogPlugin plugin) {
		this.plugin = plugin;
	}

	// Damage event
	public void onEntityDamage(EntityDamageEvent event) {
		// Only if the player gets damage
		event.getEntity();
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