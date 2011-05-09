package de.frozenbrain.BlocksOnGlass;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class bogPlayerListener extends PlayerListener {

	private final bogPlugin plugin;
	
	public bogPlayerListener(final bogPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.hasBlock() && event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType() == Material.GLASS) && (event.getItem().getTypeId() >= 323)) {
			
			if(plugin.Permissions == null) return;
			
			if(!plugin.Permissions.has(event.getPlayer(), "bog." + event.getItem().getType().name().toLowerCase())) event.setCancelled(true);
			
		}
    }
}