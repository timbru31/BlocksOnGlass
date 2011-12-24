package de.xghostkillerx.BlocksOnGlass.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

import de.xghostkillerx.BlocksOnGlass.bogPlugin;

public class bogPlayerListener extends PlayerListener {

	private final bogPlugin plugin;
	public bogPlayerListener(final bogPlugin plugin) {
		this.plugin = plugin;
	}

	public void onPlayerInteract(final PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.isCancelled()) return;
		// Check for the blocks first and item
		if (event.hasBlock() && event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Material blockMaterial = event.getClickedBlock().getType();
			Material itemMaterial = event.getItem().getType();
			// Glass
			if (blockMaterial == Material.GLASS && bogPlugin.config.getBoolean("blocks.glass") == true) {
				if (plugin.blocks.contains(itemMaterial)) {
					if (bogPlugin.config.getBoolean("permissions") == true) {
						if (!player.hasPermission("bog." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// Ice
			else if (blockMaterial == Material.ICE && bogPlugin.config.getBoolean("blocks.ice") == true) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if (plugin.blocks.contains(itemMaterial)) {
						if (!player.hasPermission("boi." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// Leaves
			else if (blockMaterial == Material.LEAVES && bogPlugin.config.getBoolean("blocks.leaves") == true) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if (plugin.blocks.contains(itemMaterial)) {
						if (!player.hasPermission("bol." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// Fence
			else if (blockMaterial == Material.FENCE && bogPlugin.config.getBoolean("blocks.fence") == true) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if (plugin.blocks.contains(itemMaterial)) {
						if (!player.hasPermission("bof." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// New permission is bonf.*  -> Block On Nether Fence = bonf
			else if (blockMaterial == Material.NETHER_FENCE && bogPlugin.config.getBoolean("blocks.netherfence") == true) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if (plugin.blocks.contains(itemMaterial)) {
						if (!player.hasPermission("bonf." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// New permission is bogl.*  -> Blocks On Glowstone = bogl
			else if (blockMaterial == Material.GLOWSTONE && bogPlugin.config.getBoolean("blocks.glowstone") == true) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if (plugin.blocks.contains(itemMaterial)) {
						if (!player.hasPermission("bogl." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
			// New permissions is bot.* -> Blocks On TNT = bot
			else if (blockMaterial == Material.TNT && bogPlugin.config.getBoolean("blocks.tnt") == true) {
				if (bogPlugin.config.getBoolean("permissions") == true) {
					if (plugin.blocks.contains(itemMaterial)) {
						if (!player.hasPermission("bot." + itemMaterial.name().toLowerCase())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}