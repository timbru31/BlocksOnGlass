package de.frozenbrain.BlocksOnGlass;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

public class bogBlockListener extends BlockListener {
	
	@SuppressWarnings("unused")
	private final bogPlugin plugin;
	
	public bogBlockListener(bogPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onBlockPhysics(BlockPhysicsEvent event) {
		if(event.getBlock().getType() == Material.TORCH) {
			if(getAttachedBlock(event.getBlock()) == Material.GLASS) { 
				event.setCancelled(true);
			}
		}
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType() == Material.GLASS) {
			Block north = block.getFace(BlockFace.NORTH);
			Block east = block.getFace(BlockFace.EAST);
			Block south = block.getFace(BlockFace.SOUTH);
			Block west = block.getFace(BlockFace.WEST);
			Block up = block.getFace(BlockFace.UP);
			
			ItemStack dropTorch = new ItemStack(Material.TORCH, 1);
			
			if(north.getType() == Material.TORCH) {
				if(north.getData() == 0x2) {
					north.setType(Material.AIR);
					north.getWorld().dropItemNaturally(north.getLocation(), dropTorch);
				}
			}
			if(east.getType() == Material.TORCH) {
				if(east.getData() == 0x4) {
					east.setType(Material.AIR);
					east.getWorld().dropItemNaturally(east.getLocation(), dropTorch);
				}
			}
			if(south.getType() == Material.TORCH) {
				if(south.getData() == 0x1) {
					south.setType(Material.AIR);
					south.getWorld().dropItemNaturally(south.getLocation(), dropTorch);
				}
			}
			if(west.getType() == Material.TORCH) {
				if(west.getData() == 0x3) {
					west.setType(Material.AIR);
					west.getWorld().dropItemNaturally(west.getLocation(), dropTorch);
				}
			}
			if(up.getType() == Material.TORCH) {
				if(up.getData() == 0x5) {
					up.setType(Material.AIR);
					up.getWorld().dropItemNaturally(up.getLocation(), dropTorch);
				}
			}
		}
	}
	
	private Material getAttachedBlock(Block block) {
		byte data = block.getData();
		switch (data) {
			case 0x1:
				return block.getFace(BlockFace.NORTH).getType();
			case 0x2:
				return block.getFace(BlockFace.SOUTH).getType();
			case 0x3:
				return block.getFace(BlockFace.EAST).getType();
			case 0x4:
				return block.getFace(BlockFace.WEST).getType();
			case 0x5:
				return block.getFace(BlockFace.DOWN).getType();
			default:
				return Material.AIR;
		}
	}
	
}
