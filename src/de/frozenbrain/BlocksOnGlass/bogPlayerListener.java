package de.frozenbrain.BlocksOnGlass;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class bogPlayerListener extends PlayerListener {

	@SuppressWarnings("unused")
	private final bogPlugin plugin;
	
	public bogPlayerListener(final bogPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.hasBlock()) {
			
			Block block = event.getClickedBlock();
			
			if(	(block.getType() == Material.GLASS) &&
				(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				
				if((event.getPlayer().getItemInHand().getType() == Material.TORCH) && (block.getFace(event.getBlockFace()).getType() != Material.TORCH)) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.TORCH);
					event.getClickedBlock().setType(Material.GLASS);
					if(getAttachedBlock(block.getFace(event.getBlockFace())).getType() != Material.GLASS) {
						fixTorch(block.getFace(event.getBlockFace()));
					}
					if(event.getItem().getAmount() == 1) {
						event.getPlayer().getInventory().remove(event.getItem());
					} else {
						event.getItem().setAmount(event.getItem().getAmount() - 1);
					}
				} else if((event.getPlayer().getItemInHand().getType() == Material.REDSTONE_TORCH_ON) && (block.getFace(event.getBlockFace()).getType() != Material.REDSTONE_TORCH_ON)) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.REDSTONE_TORCH_ON);
					event.getClickedBlock().setType(Material.GLASS);
					if(getAttachedBlock(block.getFace(event.getBlockFace())).getType() != Material.GLASS) {
						fixTorch(block.getFace(event.getBlockFace()));
					}
					if(event.getItem().getAmount() == 1) {
						event.getPlayer().getInventory().remove(event.getItem());
					} else {
						event.getItem().setAmount(event.getItem().getAmount() - 1);
					}
				} else if((event.getPlayer().getItemInHand().getType() == Material.RAILS) && (block.getFace(event.getBlockFace()).getType() != Material.RAILS)) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.RAILS);
					event.getClickedBlock().setType(Material.GLASS);
					if(event.getItem().getAmount() == 1) {
						event.getPlayer().getInventory().remove(event.getItem());
					} else {
						event.getItem().setAmount(event.getItem().getAmount() - 1);
					}
				} else if((event.getPlayer().getItemInHand().getType() == Material.REDSTONE) && (block.getFace(event.getBlockFace()).getType() != Material.REDSTONE_WIRE)) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.REDSTONE_WIRE);
					event.getClickedBlock().setType(Material.GLASS);
					if(event.getItem().getAmount() == 1) {
						event.getPlayer().getInventory().remove(event.getItem());
					} else {
						event.getItem().setAmount(event.getItem().getAmount() - 1);
					}
				} else if((event.getPlayer().getItemInHand().getType() == Material.LADDER) && (block.getFace(event.getBlockFace()).getType() != Material.LADDER)) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.LADDER);
					event.getClickedBlock().setType(Material.GLASS);
					switch(event.getBlockFace()) {
						case SOUTH:
							block.getFace(event.getBlockFace()).setData((byte) 0x5);
							break;
						case WEST:
							block.getFace(event.getBlockFace()).setData((byte) 0x3);
							break;
						case NORTH:
							block.getFace(event.getBlockFace()).setData((byte) 0x4);
							break;
						case EAST:
							block.getFace(event.getBlockFace()).setData((byte) 0x2);
							break;
					}
					if(event.getItem().getAmount() == 1) {
						event.getPlayer().getInventory().remove(event.getItem());
					} else {
						event.getItem().setAmount(event.getItem().getAmount() - 1);
					}
				} 
				
			}
		}
	}
	
	private void fixTorch(Block block) {
		Block north = block.getFace(BlockFace.NORTH);
		Block east = block.getFace(BlockFace.EAST);
		Block south = block.getFace(BlockFace.SOUTH);
		Block west = block.getFace(BlockFace.WEST);
		Block down = block.getFace(BlockFace.DOWN);
		
		if(north.getType() == Material.GLASS) {
			block.setData((byte) 0x1);
		}
		if(east.getType() == Material.GLASS) {
			block.setData((byte) 0x3);
		}
		if(south.getType() == Material.GLASS) {
			block.setData((byte) 0x2);
		}
		if(west.getType() == Material.GLASS) {
			block.setData((byte) 0x4);
		}
		if(down.getType() == Material.GLASS) {
			block.setData((byte) 0x5);
		}
	}
	
	private Block getAttachedBlock(Block block) {
		byte data = block.getData();
		switch (data) {
			case 0x1:
				return block.getFace(BlockFace.NORTH);
			case 0x2:
				return block.getFace(BlockFace.SOUTH);
			case 0x3:
				return block.getFace(BlockFace.EAST);
			case 0x4:
				return block.getFace(BlockFace.WEST);
			case 0x5:
				return block.getFace(BlockFace.DOWN);
			default:
				return null;
		}
	}
}
