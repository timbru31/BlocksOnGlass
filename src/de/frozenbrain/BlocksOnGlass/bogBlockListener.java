package de.frozenbrain.BlocksOnGlass;

import org.bukkit.Material;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SnowFormEvent;

public class bogBlockListener extends BlockListener {

	@SuppressWarnings("unused")
	private final bogPlugin plugin;
	
	public bogBlockListener(final bogPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void onSnowForm(SnowFormEvent event) {
		if((event.getMaterial() == Material.GLASS) || (event.getMaterial() == Material.FENCE)) {
			event.setCancelled(true);
		}
	}
	
}