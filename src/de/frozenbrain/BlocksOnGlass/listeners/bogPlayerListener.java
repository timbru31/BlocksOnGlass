package de.frozenbrain.BlocksOnGlass.listeners;

import org.bukkit.Material;
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
		if(plugin.Permissions == null) return;
		if(event.isCancelled()) return;
		
		if(event.hasBlock() && event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			
			Material blockMaterial = event.getClickedBlock().getType();
			Material itemMaterial = event.getItem().getType();
			
			if(blockMaterial == Material.GLASS) {
				if(plugin.blocks.contains(itemMaterial)) {
					if(!plugin.Permissions.has(event.getPlayer(), "bog." + itemMaterial.name().toLowerCase())) {
						event.setCancelled(true);
					}
				}
			} else if(blockMaterial == Material.ICE) {
				if(plugin.blocks.contains(itemMaterial)) {
					if(!plugin.Permissions.has(event.getPlayer(), "boi." + itemMaterial.name().toLowerCase())) {
						event.setCancelled(true);
					}
				}
			} else if(blockMaterial == Material.LEAVES) {
				if(plugin.blocks.contains(itemMaterial)) {
					if(!plugin.Permissions.has(event.getPlayer(), "bol." + itemMaterial.name().toLowerCase())) {
						event.setCancelled(true);
					}
				}
			} else if(blockMaterial == Material.FENCE) {
				if(plugin.blocks.contains(itemMaterial) && !plugin.fenceWhitelist.contains(itemMaterial)) {
					if(!plugin.Permissions.has(event.getPlayer(), "bof." + itemMaterial.name().toLowerCase())) {
						event.setCancelled(true);
					}
				}
			}
		}
    }
}