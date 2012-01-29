package de.xghostkillerx.blocksonglass;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class BlocksOnGlassPlayerListener implements Listener {

	public BlocksOnGlass plugin;
	public BlocksOnGlassPlayerListener(BlocksOnGlass instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.isCancelled()) return;
		// Check for the blocks first and item
		if (event.hasBlock() && event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Material blockMaterial = event.getClickedBlock().getType();
			Material itemMaterial = event.getItem().getType();
			// Glass: bog.* -> Blocks On Glass = bog
			if (blockMaterial == Material.GLASS && plugin.config.getBoolean("blocks.glass") == true) {
				if (cancel("bog.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Ice: boi.* -> Blocks On Ice = boi
			else if (blockMaterial == Material.ICE && plugin.config.getBoolean("blocks.ice") == true) {
				if (cancel("boi.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Leaves: bol.* -> Blocks On Leaves = bol
			else if (blockMaterial == Material.LEAVES && plugin.config.getBoolean("blocks.leaves") == true) {
				if (cancel("bol.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Fence: bof.* -> Blocks On Fence = bof
			else if (blockMaterial == Material.FENCE && plugin.config.getBoolean("blocks.fence") == true) {
				// Vanilla like -> possible!!
				if ((itemMaterial == Material.TORCH)
						|| (itemMaterial == Material.REDSTONE_TORCH_ON)
						|| (itemMaterial == Material.WOOD_PLATE)
						|| (itemMaterial == Material.STONE_PLATE)
						|| (itemMaterial == Material.REDSTONE_TORCH_OFF)) {
					event.setCancelled(false);
				}
				if (cancel("bof.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// NetherFence: bonf.*  -> Blocks On Nether Fence = bonf
			else if (blockMaterial == Material.NETHER_FENCE && plugin.config.getBoolean("blocks.netherfence") == true) {
				if (cancel("bonf.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Glowstone: bogl.*  -> Blocks On Glowstone = bogl
			else if (blockMaterial == Material.GLOWSTONE && plugin.config.getBoolean("blocks.glowstone") == true) {
				if (cancel("bogl.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// TNT: bot.* -> Blocks On TNT = bot
			else if (blockMaterial == Material.TNT && plugin.config.getBoolean("blocks.tnt") == true) {
				if (cancel("bot.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Cactus: boc.* -> Blocks On Cactus = boc
			else if (blockMaterial == Material.CACTUS && plugin.config.getBoolean("blocks.cactus") == true) {
				if (cancel("boc.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Steps: bosteps.* -> Blocks On Steps = bosteps
			else if (blockMaterial == Material.STEP && plugin.config.getBoolean("blocks.steps") == true) {
				if (cancel("bosteps.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// IronFence: boif.* -> Blocks On Iron Fence = boif
			else if (blockMaterial == Material.IRON_FENCE && plugin.config.getBoolean("blocks.ironfence") == true) {
				if (cancel("boif.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// ThinGlass: botg.* -> Blocks On Thin Glass = botg
			else if (blockMaterial == Material.THIN_GLASS && plugin.config.getBoolean("blocks.thinglass") == true) {
				if (cancel("botg.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Stairs bostairs.* -> Blocks On Stairs = bostairs
			else if (blockMaterial == Material.WOOD_STAIRS && plugin.config.getBoolean("blocks.stairs.wood") == true) {
				if (cancel("bostairs.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Stairs bostairs.* -> Blocks On Stairs = bostairs
			else if (blockMaterial == Material.COBBLESTONE_STAIRS && plugin.config.getBoolean("blocks.stairs.cobblestone") == true) {
				if (cancel("bostairs.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Stairs bostairs.* -> Blocks On Stairs = bostairs
			else if (blockMaterial == Material.BRICK_STAIRS && plugin.config.getBoolean("blocks.stairs.brick") == true) {
				if (cancel("bostairs.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Stairs bostairs.* -> Blocks On Stairs = bostairs
			else if (blockMaterial == Material.NETHER_BRICK_STAIRS && plugin.config.getBoolean("blocks.stairs.netherbrick") == true) {
				if (cancel("bostairs.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
			// Stairs bostairs.* -> Blocks On Stairs = bostairs
			else if (blockMaterial == Material.SMOOTH_STAIRS && plugin.config.getBoolean("blocks.stairs.stone") == true) {
				if (cancel("bostairs.", itemMaterial, player) == true) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	// Check to see if the player has got the permission
	private boolean cancel (String permission, Material itemMaterial, Player player) {
		if (plugin.config.getBoolean("permissions") == true) {
			if (plugin.blocks.contains(itemMaterial)) {
				if (!player.hasPermission(permission + itemMaterial.name().toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
}