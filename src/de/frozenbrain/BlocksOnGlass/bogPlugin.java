package de.frozenbrain.BlocksOnGlass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.Block;
import net.minecraft.server.Material;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.*;

import de.frozenbrain.BlocksOnGlass.blocks.bogBlockFence;
import de.frozenbrain.BlocksOnGlass.blocks.bogBlockGlass;
import de.frozenbrain.BlocksOnGlass.blocks.bogBlockIce;
import de.frozenbrain.BlocksOnGlass.blocks.bogBlockLeaves;
import de.frozenbrain.BlocksOnGlass.listeners.bogBlockListener;
import de.frozenbrain.BlocksOnGlass.listeners.bogEntityListener;
import de.frozenbrain.BlocksOnGlass.listeners.bogPlayerListener;

/**
 * 
 * Hi,
 * I made some changes! I hope it's okay, I'll make a pull request so you can easily add them!
 * 
 * First:
 * I removed the old Permision 3.XX stuff (Or was it even 2.XX?)
 * Because nobody uses this anymore. (bPermisions and PermissionsEX are the future)
 * 
 * I added some new items (flowers etc.)
 * 
 * I updated the config to the new API
 * 
 * I added support for Nether fences! (Permission: bonf.* -> Block On Nether Fences!)
 * I added support for glowstone, too! (Because it's again like glass....) (Permission: bogl.* -> Block On Glowstone!)
 * 
 * Fixed some bugs ;)
 * 
 * Small things like,
 * Version info at onDisable
 *
 */

public class bogPlugin extends JavaPlugin {
	
	private final bogPlayerListener playerListener = new bogPlayerListener(this);
	private final bogBlockListener blockListener = new bogBlockListener(this);
	private final bogEntityListener entityListener = new bogEntityListener(this);
	public List<org.bukkit.Material> blocks = new ArrayList<org.bukkit.Material>();
	public static FileConfiguration config;
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_FORM, blockListener, Priority.Normal, this);
		// Added new event for cancelling the damage under fences...
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);
        
		// Config (updated to new API!)
		config = this.getConfig();
		config.addDefault("botanical", false);
		config.addDefault("fencefix", true);
		config.addDefault("permissions", true);
		config.options().copyDefaults(true);
		saveConfig();
		
		modifyBlocks();
		setupBlockLists();
        
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " by xGhOsTkiLLeRx is enabled!");
	}
	
	public void onDisable() {
		restoreBlocks();
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
	}
	
	private void modifyBlocks() {
		Block.byId[Block.GLASS.id] = null;
		Block.byId[Block.GLASS.id] = new bogBlockGlass(Block.GLASS.id, 49, Material.EARTH, true).setHardness(0.3F).setSound(Block.j).a("glass");
		try {
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("G");
			field.setAccessible(true);
			field.setBoolean(Material.SHATTERABLE, false);
			//System.out.println("Glass field:" +field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.q[Block.GLASS.id] = 0;
		
		Block.byId[Block.FENCE.id] = null;
		Block.byId[Block.FENCE.id] = new bogBlockFence(Block.FENCE.id, 4).setHardness(0.3F).setResistance(5F).setSound(Block.e).a("fence");
		Block.q[Block.FENCE.id] = 0;
		
		Block.byId[Block.ICE.id] = null;
		Block.byId[Block.ICE.id] = new bogBlockIce(Block.ICE.id, 67).setHardness(0.5F).setSound(Block.j).a("ice");
		try {
			Field field = Material.ICE.getClass().getDeclaredField("G");
			field.setAccessible(true);
			field.setBoolean(Material.ICE, true);
			//System.out.println(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.q[Block.ICE.id] = 3;
		
		Block.byId[Block.LEAVES.id] = null;
		Block.byId[Block.LEAVES.id] = new bogBlockLeaves(Block.LEAVES.id, 52).setHardness(0.2F).setSound(Block.g).a("leaves");
		try {
			Field field = Material.LEAVES.getClass().getDeclaredField("G");
			field.setAccessible(true);
			field.setBoolean(Material.LEAVES, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.q[Block.LEAVES.id] = 0; // f(1)
		Block.t[Block.LEAVES.id] = true; // g()
		
		
		// I added NetherFence support
		// Because it's the same class (both are extending BlockFence) I use the old fence class, too!
		
		Block.byId[Block.NETHER_FENCE.id] = null;
		Block.byId[Block.NETHER_FENCE.id] = new bogBlockFence(Block.NETHER_FENCE.id, 4).setHardness(0.3F).setResistance(5F).setSound(Block.e).a("netherFence");
		Block.q[Block.NETHER_FENCE.id] = 0;
	}
	
	private void restoreBlocks() {
		Block.byId[Block.GLASS.id] = Block.GLASS;
		try {
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("G");
			field.setAccessible(true);
			field.setBoolean(Material.SHATTERABLE, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.byId[Block.FENCE.id] = Block.FENCE;
		Block.byId[Block.ICE.id] = Block.ICE;
		try {
			Field field = Material.ICE.getClass().getDeclaredField("G");
			field.setAccessible(true);
			field.setBoolean(Material.ICE, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.byId[Block.LEAVES.id] = Block.LEAVES;
		try {
			Field field = Material.LEAVES.getClass().getDeclaredField("G");
			field.setAccessible(true);
			field.setBoolean(Material.LEAVES, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// I added NetherFence support
		Block.byId[Block.NETHER_FENCE.id] = Block.NETHER_FENCE;
	}
	
	private void setupBlockLists() {
		blocks.add(org.bukkit.Material.POWERED_RAIL);
        blocks.add(org.bukkit.Material.DETECTOR_RAIL);
        blocks.add(org.bukkit.Material.TORCH);
        blocks.add(org.bukkit.Material.LADDER);
        blocks.add(org.bukkit.Material.RAILS);
        blocks.add(org.bukkit.Material.LEVER);
        blocks.add(org.bukkit.Material.STONE_PLATE);
        blocks.add(org.bukkit.Material.WOOD_PLATE);
        blocks.add(org.bukkit.Material.REDSTONE_TORCH_ON);
        blocks.add(org.bukkit.Material.STONE_BUTTON);
        blocks.add(org.bukkit.Material.WOOD_DOOR);
        blocks.add(org.bukkit.Material.IRON_DOOR);
        blocks.add(org.bukkit.Material.REDSTONE);
        blocks.add(org.bukkit.Material.BED);
        blocks.add(org.bukkit.Material.DIODE);
        blocks.add(org.bukkit.Material.TRAP_DOOR);
        // new added but only if the server admin wants the flower stuff
        if (config.getBoolean("botanicle") == true) {
        	blocks.add(org.bukkit.Material.RED_ROSE);
        	blocks.add(org.bukkit.Material.YELLOW_FLOWER);
        	blocks.add(org.bukkit.Material.DEAD_BUSH);
        	blocks.add(org.bukkit.Material.RED_MUSHROOM);
        	blocks.add(org.bukkit.Material.BROWN_MUSHROOM);
        	blocks.add(org.bukkit.Material.SAPLING);
        	blocks.add(org.bukkit.Material.CACTUS);
        	blocks.add(org.bukkit.Material.SUGAR_CANE);
        }
	}
}
