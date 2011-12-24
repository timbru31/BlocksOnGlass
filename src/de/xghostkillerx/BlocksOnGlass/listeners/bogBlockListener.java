package de.xghostkillerx.BlocksOnGlass.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockListener;

import de.xghostkillerx.BlocksOnGlass.bogPlugin;

public class bogBlockListener extends BlockListener {

	@SuppressWarnings("unused")
	private final bogPlugin plugin;
	
	public bogBlockListener(final bogPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void onBlockForm(BlockFormEvent event) {
		Material formedBlock = event.getNewState().getType();
		if(formedBlock != Material.SNOW) return;
		Material snowOn = event.getBlock().getRelative(BlockFace.DOWN).getType();
		if((snowOn == Material.GLASS) || (snowOn == Material.FENCE) || (snowOn == Material.ICE)) {
			event.setCancelled(true);
		}
	}
	
}