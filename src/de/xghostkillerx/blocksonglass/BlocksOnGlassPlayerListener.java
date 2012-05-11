package de.xghostkillerx.blocksonglass;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * BlocksOnGlass for CraftBukkit/Bukkit
 * Handles the permission check for each block/item
 * 
 * Refer to the forum thread:
 * http://bit.ly/bogbukkit
 * Refer to the dev.bukkit.org page:
 * http://bit.ly/bogbukkitdev
 *
 * @author xGhOsTkiLLeRx
 * @thanks to FrozenBrain for the original BlocksOnGlass plugin!
 * @thanks to mooman219 for help with bug fixing and NMS code!
 * 
 */

public class BlocksOnGlassPlayerListener implements Listener {

	public BlocksOnGlass plugin;
	public BlocksOnGlassPlayerListener(BlocksOnGlass instance) {
		plugin = instance;
	}
	private Material[] stairs = {Material.WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.NETHER_BRICK_STAIRS, Material.SMOOTH_STAIRS};
	private String[] configStairs = {"blocks.stairs.wood", "blocks.stairs.cobblestone", "blocks.stairs.brick", "blocks.stairs.netherbrick", "blocks.stairs.stone"};
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.isCancelled()) return;
		// Check for the blocks first and item
		if (event.hasBlock() && event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Material blockMaterial = event.getClickedBlock().getType();
			Material itemMaterial = event.getItem().getType();
			// Workbench
			if (blockMaterial == Material.WORKBENCH && plugin.config.getBoolean("blocks.workbench") == true) {
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setUseItemInHand(Event.Result.ALLOW);
			}
			// Chest
			if (blockMaterial == Material.CHEST && plugin.config.getBoolean("blocks.chest") == true) {
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setUseItemInHand(Event.Result.ALLOW);
			}
			// Furnace
			if ((blockMaterial == Material.FURNACE || blockMaterial == Material.BURNING_FURNACE) && plugin.config.getBoolean("blocks.furnace") == true) {
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setUseItemInHand(Event.Result.ALLOW);
			}
			// Cauldron
			if (blockMaterial == Material.CAULDRON && plugin.config.getBoolean("blocks.cauldron") == true) {
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setUseItemInHand(Event.Result.ALLOW);
			}
			// Dispenser
			if (blockMaterial == Material.DISPENSER && plugin.config.getBoolean("blocks.dispenser") == true) {
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setUseItemInHand(Event.Result.ALLOW);
			}
			// Brewing stand
			if (blockMaterial == Material.BREWING_STAND && plugin.config.getBoolean("blocks.brewing_stand") == true) {
				event.setUseInteractedBlock(Event.Result.DENY);
				event.setUseItemInHand(Event.Result.ALLOW);
			}
			// Glass: bog.* -> Blocks On Glass = bog
			if (blockMaterial == Material.GLASS && plugin.config.getBoolean("blocks.glass") == true) {
				cancel("bog.", itemMaterial, player, event);
			}
			// Ice: boi.* -> Blocks On Ice = boi
			else if (blockMaterial == Material.ICE && plugin.config.getBoolean("blocks.ice") == true) {
				cancel("boi.", itemMaterial, player, event);
			}
			// Leaves: bol.* -> Blocks On Leaves = bol
			else if (blockMaterial == Material.LEAVES && plugin.config.getBoolean("blocks.leaves") == true) {
				cancel("bol.", itemMaterial, player, event);
			}
			// Fence: bof.* -> Blocks On Fence = bof
			else if (blockMaterial == Material.FENCE && plugin.config.getBoolean("blocks.fence") == true) {
				// Vanilla like -> possible!!
				if ((itemMaterial == Material.TORCH)
						|| (itemMaterial == Material.REDSTONE_TORCH_ON)
						|| (itemMaterial == Material.WOOD_PLATE)
						|| (itemMaterial == Material.STONE_PLATE)
						|| (itemMaterial == Material.REDSTONE_TORCH_OFF)) {
					return;
				}
				cancel("bof.", itemMaterial, player, event);
			}
			// NetherFence: bonf.*  -> Blocks On Nether Fence = bonf
			else if (blockMaterial == Material.NETHER_FENCE && plugin.config.getBoolean("blocks.netherfence") == true) {
				cancel("bonf.", itemMaterial, player, event);
			}
			// Glowstone: bogl.*  -> Blocks On Glowstone = bogl
			else if (blockMaterial == Material.GLOWSTONE && plugin.config.getBoolean("blocks.glowstone") == true) {
				cancel("bogl.", itemMaterial, player, event);
			}
			// TNT: bot.* -> Blocks On TNT = bot
			else if (blockMaterial == Material.TNT && plugin.config.getBoolean("blocks.tnt") == true) {
				cancel("bot.", itemMaterial, player, event);
			}
			// Cactus: boc.* -> Blocks On Cactus = boc
			else if (blockMaterial == Material.CACTUS && plugin.config.getBoolean("blocks.cactus") == true) {
				cancel("boc.", itemMaterial, player, event);
			}
			// Steps: bosteps.* -> Blocks On Steps = bosteps
			else if (blockMaterial == Material.STEP && plugin.config.getBoolean("blocks.steps") == true) {
				cancel("bosteps.", itemMaterial, player, event);
			}
			// IronFence: boif.* -> Blocks On Iron Fence = boif
			else if (blockMaterial == Material.IRON_FENCE && plugin.config.getBoolean("blocks.ironfence") == true) {
				if ((itemMaterial == Material.IRON_FENCE)
						|| itemMaterial == Material.THIN_GLASS)  {
					return;
				}
				cancel("boif.", itemMaterial, player, event);
			}
			// ThinGlass: botg.* -> Blocks On Thin Glass = botg
			else if (blockMaterial == Material.THIN_GLASS && plugin.config.getBoolean("blocks.thinglass") == true) {
				if ((itemMaterial == Material.IRON_FENCE)
						|| itemMaterial == Material.THIN_GLASS)  {
					return;
				}
				cancel("botg.", itemMaterial, player, event);
			}
			// Piston: bop.* -> Blocks On Piston = bop
			else if ((blockMaterial == Material.PISTON_BASE || blockMaterial == Material.PISTON_EXTENSION || blockMaterial == Material.PISTON_MOVING_PIECE) && plugin.config.getBoolean("blocks.pistons.normal") == true) {
				cancel("bop.", itemMaterial, player, event);
			}
			// Sticky Piston: bosp.* -> Blocks On Sticky Piston = bosp
			else if ((blockMaterial == Material.PISTON_STICKY_BASE || blockMaterial == Material.PISTON_EXTENSION || blockMaterial == Material.PISTON_MOVING_PIECE) && plugin.config.getBoolean("blocks.pistons.sticky") == true) {
				cancel("bosp.", itemMaterial, player, event);
			}
			// Check all stairs
			for (int i = 0; i < stairs.length; i++) {
				if (blockMaterial == stairs[i] && plugin.config.getBoolean(configStairs[i]) == true) {
					cancel("bostairs.", itemMaterial, player, event);
				}
			}
		}
	}

	// Check to see if the player has got the permission
	private void cancel (String permission, Material itemMaterial, Player player, PlayerInteractEvent event) {
		if (plugin.config.getBoolean("permissions") == true) {
			if (plugin.blocks.contains(itemMaterial)) {
				if (!player.hasPermission(permission + itemMaterial.name().toLowerCase())) {
					event.setCancelled(true);
				}
			}
		}
		return;
	}
}