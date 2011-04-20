package de.frozenbrain.BlocksOnGlass;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
		
		if(event.hasBlock()) {
			
			Block block = event.getClickedBlock();
			
			if(	(block.getType() == Material.GLASS) &&
				(event.getAction() == Action.RIGHT_CLICK_BLOCK) &&
				(block.getFace(event.getBlockFace()).getType() == Material.AIR)) {
				
				if((event.getPlayer().getItemInHand().getType() == Material.TORCH) && !(event.getBlockFace() == BlockFace.DOWN) && plugin.canPlaceTorch) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.TORCH);
					fixTorch(block.getFace(event.getBlockFace()), event.getBlockFace());
					event.getClickedBlock().setType(Material.GLASS);
					takeItem(event);
					
				} else if((event.getPlayer().getItemInHand().getType() == Material.REDSTONE_TORCH_ON) && !(event.getBlockFace() == BlockFace.DOWN) && plugin.canPlaceRSTorch) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.REDSTONE_TORCH_ON);
					fixTorch(block.getFace(event.getBlockFace()), event.getBlockFace());
					event.getClickedBlock().setType(Material.GLASS);
					takeItem(event);
					
				} else if((event.getPlayer().getItemInHand().getType() == Material.LADDER) && !(event.getBlockFace() == BlockFace.DOWN) && !(event.getBlockFace() == BlockFace.UP) && plugin.canPlaceLadders) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.LADDER);
					fixLadder(block.getFace(event.getBlockFace()), event.getBlockFace());
					event.getClickedBlock().setType(Material.GLASS);
					takeItem(event);
					
				} else if((event.getPlayer().getItemInHand().getType() == Material.RAILS) && (event.getBlockFace() == BlockFace.UP) && plugin.canPlaceRails) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.RAILS);
					event.getClickedBlock().setType(Material.GLASS);
					takeItem(event);
					
				} else if((event.getPlayer().getItemInHand().getType() == Material.REDSTONE) && (event.getBlockFace() == BlockFace.UP) && plugin.canPlaceRedstone) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.REDSTONE_WIRE);
					event.getClickedBlock().setType(Material.GLASS);
					takeItem(event);
					
				} else if((event.getPlayer().getItemInHand().getType() == Material.WOOD_DOOR) && (event.getBlockFace() == BlockFace.UP) && (block.getFace(event.getBlockFace(), 2).getType() == Material.AIR) && plugin.canPlaceDoors) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.WOODEN_DOOR);
					block.getFace(event.getBlockFace(), 2).setType(Material.WOODEN_DOOR);
					block.getFace(event.getBlockFace(), 2).setData((byte) 0x8);
					event.getClickedBlock().setType(Material.GLASS);
					fixDoor(block.getFace(event.getBlockFace()), event.getPlayer().getLocation());
					takeItem(event);
					
				} else if((event.getPlayer().getItemInHand().getType() == Material.IRON_DOOR) && (event.getBlockFace() == BlockFace.UP) && (block.getFace(event.getBlockFace(), 2).getType() == Material.AIR) && plugin.canPlaceDoors) {
					block.setType(Material.DIRT);
					block.getFace(event.getBlockFace()).setType(Material.IRON_DOOR_BLOCK);
					block.getFace(event.getBlockFace(), 2).setType(Material.IRON_DOOR_BLOCK);
					block.getFace(event.getBlockFace(), 2).setData((byte) 0x8);
					event.getClickedBlock().setType(Material.GLASS);
					fixDoor(block.getFace(event.getBlockFace()), event.getPlayer().getLocation());
					takeItem(event);
					
				} else if((event.getPlayer().getItemInHand().getType() == Material.BED) && (event.getBlockFace() == BlockFace.UP) && plugin.canPlaceBeds) {
					int orientation = floor_double((double)((event.getPlayer().getLocation().getYaw() * 4F) / 360F) + 0.5D) & 3;
					BlockFace face = BlockFace.UP;
					switch(orientation) {
						case 0:
							face = BlockFace.WEST;
							break;
						case 1:
							face = BlockFace.NORTH;
							break;
						case 2:
							face = BlockFace.EAST;
							break;
						case 3:
							face = BlockFace.SOUTH;
							break;
					}
					if((face != BlockFace.UP) && (block.getFace(face).getType() != Material.AIR) && (block.getFace(BlockFace.UP).getFace(face).getType() == Material.AIR)) {
						block.setType(Material.DIRT);
						block.getFace(BlockFace.UP).setType(Material.BED_BLOCK);
						block.getFace(BlockFace.UP).setData((byte) orientation);
						block.getFace(BlockFace.UP).getFace(face).setType(Material.BED_BLOCK);
						block.getFace(BlockFace.UP).getFace(face).setData((byte)((byte) orientation + (byte) 0x8));
						event.getClickedBlock().setType(Material.GLASS);
						takeItem(event);
					}
				}
			}
		}
	}
	
	private void takeItem(PlayerInteractEvent event) {
		if(event.getItem().getAmount() == 1) {
			event.getPlayer().getInventory().setItemInHand(null);
		} else {
			event.getItem().setAmount(event.getItem().getAmount() - 1);
		}
	}
	
	private void fixDoor(Block block, Location loc) {
		int i1 = floor_double((double)(((loc.getYaw() + 180F) * 4F) / 360F) - 0.5D) & 3;
		block.setData((byte) i1);
		if(isDoorLeft(block)) {
			i1 = i1 - 1;
			if(i1 == -1) {
				i1 = 3;
			}
			i1 = i1 + 4;
		}
        block.setData((byte) i1);
        block.getFace(BlockFace.UP).setData((byte)((byte) i1 + (byte) 0x8));
	}
	
	private int floor_double(double d)
    {
        int i = (int)d;
        return d >= (double)i ? i : i - 1;
    }
	
	private void fixLadder(Block block, BlockFace face) {
		switch(face) {
			case SOUTH:
				block.setData((byte) 0x5);
				break;
			case WEST:
				block.setData((byte) 0x3);
				break;
			case NORTH:
				block.setData((byte) 0x4);
				break;
			case EAST:
				block.setData((byte) 0x2);
				break;
		}
	}
	
	private void fixTorch(Block block, BlockFace face) {
		switch(face) {
		case NORTH:
			block.setData((byte) 0x2);
			break;
		case EAST:
			block.setData((byte) 0x4);
			break;
		case SOUTH:
			block.setData((byte) 0x1);
			break;
		case WEST:
			block.setData((byte) 0x3);
			break;
		case UP:
			block.setData((byte) 0x5);
			break;
		}
	}
	
	private boolean isDoorLeft(Block checkDoor) {
    	byte data = checkDoor.getData();
    	BlockFace checkDouble = null;
    	BlockFace checkTurned = null;
    	if(plugin.hasBit(data, (byte) 0x1) && !plugin.hasBit(data, (byte) 0x3)) {
    		checkTurned = BlockFace.NORTH;
    		checkDouble = BlockFace.SOUTH;
    	} else if(plugin.hasBit(data, (byte) 0x2) && !plugin.hasBit(data, (byte) 0x3)) {
    		checkTurned = BlockFace.EAST;
    		checkDouble = BlockFace.WEST;
    	} else if(plugin.hasBit(data, (byte) 0x3)) {
    		checkTurned = BlockFace.SOUTH;
    		checkDouble = BlockFace.NORTH;
    	} else {
    		checkTurned = BlockFace.WEST;
    		checkDouble = BlockFace.EAST;
    	}
    	
    	if((checkDoor.getFace(checkDouble).getType() == Material.WOODEN_DOOR) || (checkDoor.getFace(checkDouble).getType() == Material.IRON_DOOR_BLOCK)) {
    		return true;
    	} else if(checkDoor.getFace(checkDouble).getType() == Material.AIR) {
    		if((checkDoor.getFace(checkTurned).getType() != Material.AIR)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
}
