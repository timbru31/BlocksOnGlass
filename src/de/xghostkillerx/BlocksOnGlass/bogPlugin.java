package de.xghostkillerx.BlocksOnGlass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.server.Block;
import net.minecraft.server.Material;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.*;

import de.xghostkillerx.BlocksOnGlass.blocks.bogBlockFence;
import de.xghostkillerx.BlocksOnGlass.blocks.bogBlockGlass;
import de.xghostkillerx.BlocksOnGlass.blocks.bogBlockGlowstone;
import de.xghostkillerx.BlocksOnGlass.blocks.bogBlockIce;
import de.xghostkillerx.BlocksOnGlass.blocks.bogBlockLeaves;
import de.xghostkillerx.BlocksOnGlass.blocks.bogBlockStair;
import de.xghostkillerx.BlocksOnGlass.blocks.bogBlockTNT;
import de.xghostkillerx.BlocksOnGlass.listeners.bogBlockListener;
import de.xghostkillerx.BlocksOnGlass.listeners.bogEntityListener;
import de.xghostkillerx.BlocksOnGlass.listeners.bogPlayerListener;

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
	public static final Logger log = Logger.getLogger("Minecraft");
	private final bogPlayerListener playerListener = new bogPlayerListener(this);
	private final bogBlockListener blockListener = new bogBlockListener(this);
	private final bogEntityListener entityListener = new bogEntityListener(this);
	public List<org.bukkit.Material> blocks = new ArrayList<org.bukkit.Material>();
	public static FileConfiguration config;
	public File configFile;

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_FORM, blockListener, Priority.Normal, this);
		// Added new event for canceling the damage under fences...
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Normal, this);

		// Config (updated to new API!)
		configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
		}
		config = this.getConfig();
		config = this.getConfig();
		loadConfig();

		modifyBlocks();
		setupBlockLists();

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	public void onDisable() {
		restoreBlocks();
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
	}

	private void modifyBlocks() {
		// Glass
		if (config.getBoolean("blocks.glass") == true) {
			Block.byId[Block.GLASS.id] = null;
			Block.byId[Block.GLASS.id] = new bogBlockGlass(Block.GLASS.id, 49, Material.SHATTERABLE, true).setHardness(0.3F).a("glass");
			try {
				Field field = Material.SHATTERABLE.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.SHATTERABLE, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the glass block!");
			}
		}
		// Fences
		if (config.getBoolean("blocks.fence") == true) {
			Block.byId[Block.FENCE.id] = null;
			Block.byId[Block.FENCE.id] = new bogBlockFence(Block.FENCE.id, 4).setHardness(0.3F).setResistance(5F).a("fence");
		}
		// Ice
		if (config.getBoolean("blocks.ice") == true) {
			Block.byId[Block.ICE.id] = null;
			Block.byId[Block.ICE.id] = new bogBlockIce(Block.ICE.id, 67).setHardness(0.5F).a("ice");
			try {
				Field field = Material.ICE.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.ICE, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the ice block!");
			}
		}
		// Leaves
		if (config.getBoolean("blocks.leaves") == true) {
			Block.byId[Block.LEAVES.id] = null;
			Block.byId[Block.LEAVES.id] = new bogBlockLeaves(Block.LEAVES.id, 52).setHardness(0.2F).a("leaves");
			try {
				Field field = Material.LEAVES.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.LEAVES, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the leaves block!");
			}
		}
		// NetherFences
		// Because it's the same class (both are extending BlockFence) I use the old fence class, too!
		if (config.getBoolean("blocks.netherfence") == true) {
			Block.byId[Block.NETHER_FENCE.id] = null;
			Block.byId[Block.NETHER_FENCE.id] = new bogBlockFence(Block.NETHER_FENCE.id, 4).setHardness(0.3F).setResistance(5F).a("netherFence");
		}
		// Glowstone
		// New class! (LightStone = Glowstone)
		if (config.getBoolean("blocks.glowstone") == true) {
			Block.byId[Block.GLOWSTONE.id] = null;
			Block.byId[Block.GLOWSTONE.id] = new bogBlockGlowstone(Block.GLOWSTONE.id, 89, Material.SHATTERABLE).setHardness(0.3F).a("glowstone");
		}

		Block.byId[Block.BRICK_STAIRS.id] = null;
		Block.byId[Block.BRICK_STAIRS.id] = new bogBlockStair(Block.BRICK_STAIRS.id, Block.BRICK).setHardness(0.3F);

		// TNT
		if (config.getBoolean("blocks.tnt") == true) {
			Block.byId[Block.TNT.id] = null;
			Block.byId[Block.TNT.id] = new bogBlockTNT(Block.TNT.id, 46).setHardness(0.0F);
			try {
				Field field = Material.TNT.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.TNT, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the TNT block!");
			}
		}
	}

	private void restoreBlocks() {
		// Glass
		Block.byId[Block.GLASS.id] = Block.GLASS;
		try {
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("H");
			field.setAccessible(true);
			field.setBoolean(Material.SHATTERABLE, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.warning("BlocksOnGlass couldn't restore the glass block!");
		}
		// Fences
		Block.byId[Block.FENCE.id] = Block.FENCE;
		// Ice
		Block.byId[Block.ICE.id] = Block.ICE;
		try {
			Field field = Material.ICE.getClass().getDeclaredField("H");
			field.setAccessible(true);
			field.setBoolean(Material.ICE, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.warning("BlocksOnGlass couldn't restore the ice block!");
		}
		// Leaves
		Block.byId[Block.LEAVES.id] = Block.LEAVES;
		try {
			Field field = Material.LEAVES.getClass().getDeclaredField("H");
			field.setAccessible(true);
			field.setBoolean(Material.LEAVES, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.warning("BlocksOnGlass couldn't restore the leaves block!");
		}
		// NetherFences
		Block.byId[Block.NETHER_FENCE.id] = Block.NETHER_FENCE;
		// Glowstone
		Block.byId[Block.GLOWSTONE.id] = Block.GLOWSTONE;
		// TNT
		Block.byId[Block.TNT.id] = Block.TNT;
		try {
			Field field = Material.TNT.getClass().getDeclaredField("H");
			field.setAccessible(true);
			field.setBoolean(Material.TNT, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.warning("BlocksOnGlass couldn't restore the TNT block!");
		}
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
		blocks.add(org.bukkit.Material.RED_MUSHROOM);
		blocks.add(org.bukkit.Material.BROWN_MUSHROOM);
	}

	private void loadConfig() {
		config.addDefault("tweaks.fencefix", true);
		config.addDefault("permissions", true);
		config.addDefault("blocks.glass", true);
		config.addDefault("blocks.ice", true);
		config.addDefault("blocks.leaves", true);
		config.addDefault("blocks.fence", true);
		config.addDefault("blocks.netherfence", true);
		config.addDefault("blocks.glowstone", true);
		config.addDefault("blocks.tnt", false);
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
