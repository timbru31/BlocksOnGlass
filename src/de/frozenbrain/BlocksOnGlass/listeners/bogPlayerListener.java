package de.frozenbrain.BlocksOnGlass.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import de.frozenbrain.BlocksOnGlass.bogPlugin;

public class bogPlayerListener extends PlayerListener {

	private final bogPlugin plugin;
	public bogPlayerListener(final bogPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onPlayerInteract(final PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.isCancelled()) return;
		// Check for the blocks first and item
		if(event.hasBlock() && event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

			Material blockMaterial = event.getClickedBlock().getType();
			Material itemMaterial = event.getItem().getType();

			if(blockMaterial == Material.GLASS) {
				if(plugin.blocks.contains(itemMaterial)) {
					if (bogPlugin.config.getBoolean("permissions") == true) {
						if(!player.hasPermission("bog." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			else if(blockMaterial == Material.ICE) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if(plugin.blocks.contains(itemMaterial)) {
						if(!player.hasPermission("boi." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			else if(blockMaterial == Material.LEAVES) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if(plugin.blocks.contains(itemMaterial)) {
						if(!player.hasPermission("bol." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			else if(blockMaterial == Material.FENCE) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if(plugin.blocks.contains(itemMaterial)) {
						if(!player.hasPermission("bof." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// I added the new nether fences! New permission is bonf.*  -> Block On Nether Fence = bonf
			else if(blockMaterial == Material.NETHER_FENCE) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if(plugin.blocks.contains(itemMaterial)) {
						if(!player.hasPermission("bonf." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// I added glowstone, too! Because Notch changed it back to glass (before it was like stone...)
			// New permission is bogl.*  -> Block On Glowstone = bogl
			else if(blockMaterial == Material.GLOWSTONE) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if(plugin.blocks.contains(itemMaterial)) {
						if(!player.hasPermission("bogl." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}