package de.xghostkillerx.blocksonglass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import net.minecraft.server.Block;
import net.minecraft.server.Material;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.*;

import de.xghostkillerx.blocksonglass.blocks.CustomCactus;
import de.xghostkillerx.blocksonglass.blocks.CustomFences;
import de.xghostkillerx.blocksonglass.blocks.CustomFlowers;
import de.xghostkillerx.blocksonglass.blocks.CustomGlass;
import de.xghostkillerx.blocksonglass.blocks.CustomGlowstone;
import de.xghostkillerx.blocksonglass.blocks.CustomIce;
import de.xghostkillerx.blocksonglass.blocks.CustomLeaves;
import de.xghostkillerx.blocksonglass.blocks.CustomStairs;
import de.xghostkillerx.blocksonglass.blocks.CustomSteps;
import de.xghostkillerx.blocksonglass.blocks.CustomTNT;
import de.xghostkillerx.blocksonglass.blocks.CustomThinFence;


/**
 * BlocksOnGlass for CraftBukkit/Bukkit
 * Handles some general stuff.
 * Restores and modifies the blocks
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

/*
 * TODO
 * 
 * Stuck when trying to go from Ice/Leaves/etc. to fences
 * No way to go through gap with fence/netherfence
 * spawning mobs on glass?
 * redstone texture is missing (looking up under glass)
 * disable/enable redstone (onBlockPowered!?)
 * Topics: add new blocks!
 */

public class BlocksOnGlass extends JavaPlugin {
	public final Logger log = Logger.getLogger("Minecraft");
	private final BlocksOnGlassPlayerListener playerListener = new BlocksOnGlassPlayerListener(this);
	private final BlocksOnGlassBlockListener blockListener = new BlocksOnGlassBlockListener(this);
	private final BlocksOnGlassEntityListener entityListener = new BlocksOnGlassEntityListener(this);
	public List<org.bukkit.Material> blocks = new ArrayList<org.bukkit.Material>();
	public FileConfiguration config;
	public File configFile;

	// Shutdown
	public void onDisable() {
		restoreBlocks();
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " " + pdfFile.getVersion() + " is disabled!");
	}

	// Start
	public void onEnable() {
		// Events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(blockListener, this);
		pm.registerEvents(playerListener, this);
		pm.registerEvents(entityListener, this);

		// Config
		configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
		}
		config = this.getConfig();
		loadConfig();

		// Blocks modification
		modifyBlocks();
		setupBlockLists();

		// Message
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " " + pdfFile.getVersion() + " is enabled!");

		// Stats
		try {
			Metrics metrics = new Metrics();
			metrics.beginMeasuringPlugin(this);
		}
		catch (IOException e) {}
	}

	private void modifyBlocks() {
		// Glass
		if (config.getBoolean("blocks.glass") == true) {
			// Block GLASS = (new BlockGlass(20, 49, Material.SHATTERABLE, false)).c(0.3F).a(j).a("glass");
			Block.byId[Block.GLASS.id] = null;
			Block.byId[Block.GLASS.id] = new CustomGlass(Block.GLASS.id, 49, Material.SHATTERABLE, true).setHardness(0.3F).setSound(Block.j).a("glass");
			try {
				Field field = Material.SHATTERABLE.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.SHATTERABLE, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the glass block!");
			}
			Block.lightBlock[Block.GLASS.id] = 0;
		}

		// Fences
		if (config.getBoolean("blocks.fence") == true) {
			// Block FENCE = (new BlockFence(85, 4)).c(2.0F).b(5.0F).a(e).a("fence");
			Block.byId[Block.FENCE.id] = null;
			Block.byId[Block.FENCE.id] = new CustomFences(Block.FENCE.id, 4).setHardness(2.0F).setResistance(5.0F).setSound(Block.e).a("fence");
		}

		// Ice
		if (config.getBoolean("blocks.ice") == true) {
			// Block ICE = (new BlockIce(79, 67)).c(0.5F).g(3).a(j).a("ice");
			Block.byId[Block.ICE.id] = null;
			Block.byId[Block.ICE.id] = new CustomIce(Block.ICE.id, 67).setHardness(0.5F).setSound(Block.j).a("ice");
			try {
				Field field = Material.ICE.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.ICE, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the ice block!");
			}
			Block.lightBlock[Block.ICE.id] = 3;
		}

		// Leaves
		if (config.getBoolean("blocks.leaves") == true) {
			// BlockLeaves LEAVES = (BlockLeaves) (new BlockLeaves(18, 52)).c(0.2F).g(1).a(g).a("leaves").i();
			Block.byId[Block.LEAVES.id] = null;
			Block.byId[Block.LEAVES.id] = new CustomLeaves(Block.LEAVES.id, 52).setHardness(0.2F).setSound(Block.g).a("leaves");
			try {
				Field field = Material.LEAVES.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.LEAVES, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the leaves block!");
			}
			Block.lightBlock[Block.LEAVES.id] = 1;
			Block.t[Block.LEAVES.id] = true;
		}

		// NetherFences
		if (config.getBoolean("blocks.netherfence") == true) {
			// Block NETHER_FENCE = (new BlockFence(113, 224, Material.STONE)).c(2.0F).b(10.0F).a(h).a("netherFence");
			Block.byId[Block.NETHER_FENCE.id] = null;
			Block.byId[Block.NETHER_FENCE.id] = new CustomFences(Block.NETHER_FENCE.id, 224, Material.STONE).setHardness(2.0F).setResistance(10.0F).setSound(Block.h).a("netherFence");
		}

		// Glowstone
		if (config.getBoolean("blocks.glowstone") == true) {
			// Block GLOWSTONE = (new BlockLightStone(89, 105, Material.SHATTERABLE)).c(0.3F).a(j).a(1.0F).a("lightgem");
			Block.byId[Block.GLOWSTONE.id] = null;
			Block.byId[Block.GLOWSTONE.id] = new CustomGlowstone(Block.GLOWSTONE.id, 105, Material.SHATTERABLE).setHardness(0.3F).setLightValue(1.0F).setSound(Block.j).a("lightgem");
			try {
				Field field = Material.SHATTERABLE.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.SHATTERABLE, false);
			} catch (Exception e) {
				log.warning("BlocksOnGlass couldn't modify the glowstone block!");
				e.printStackTrace();
			}
		}

		// Different STAIRS (All extending BlockStairs)
		if (config.getBoolean("blocks.stairs.brick") == true) {
			// Block BRICK_STAIRS = (new BlockStairs(108, BRICK)).a("stairsBrick").i();
			Block.byId[Block.BRICK_STAIRS.id] = null;
			Block.byId[Block.BRICK_STAIRS.id] = new CustomStairs(Block.BRICK_STAIRS.id, Block.BRICK).a("stairsBrick");
			Block.t[Block.BRICK_STAIRS.id] = true;
		}
		if (config.getBoolean("blocks.stairs.wood") == true) {
			// Block WOOD_STAIRS = (new BlockStairs(53, WOOD)).a("stairsWood").i();
			Block.byId[Block.WOOD_STAIRS.id] = null;
			Block.byId[Block.WOOD_STAIRS.id] = new CustomStairs(Block.WOOD_STAIRS.id, Block.WOOD).a("stairsWood");
			Block.t[Block.WOOD_STAIRS.id] = true;
		}
		if (config.getBoolean("blocks.stairs.cobblestone") == true) {
			// Block COBBLESTONE_STAIRS = (new BlockStairs(67, COBBLESTONE)).a("stairsStone").i();
			Block.byId[Block.COBBLESTONE_STAIRS.id] = null;
			Block.byId[Block.COBBLESTONE_STAIRS.id] = new CustomStairs(Block.COBBLESTONE_STAIRS.id, Block.COBBLESTONE).a("stairsStone");
			Block.t[Block.COBBLESTONE_STAIRS.id] = true;
		}
		if (config.getBoolean("blocks.stairs.stone") == true) {
			// Block STONE_STAIRS = (new BlockStairs(109, SMOOTH_BRICK)).a("stairsStoneBrickSmooth").i();
			Block.byId[Block.STONE_STAIRS.id] = null;
			Block.byId[Block.STONE_STAIRS.id] = new CustomStairs(Block.STONE_STAIRS.id, Block.SMOOTH_BRICK).a("stairsStoneBrickSmooth");
			Block.t[Block.STONE_STAIRS.id] = true;
		}
		if (config.getBoolean("blocks.stairs.netherbrick") == true) {
			// Block NETHER_BRICK_STAIRS = (new BlockStairs(114, NETHER_BRICK)).a("stairsNetherBrick").i();
			Block.byId[Block.NETHER_BRICK_STAIRS.id] = null;
			Block.byId[Block.NETHER_BRICK_STAIRS.id] = new CustomStairs(Block.NETHER_BRICK_STAIRS.id, Block.NETHER_BRICK).a("stairsNetherBrick");
			Block.t[Block.NETHER_BRICK_STAIRS.id] = true;
		}

		// Steps
		if (config.getBoolean("blocks.steps") == true) {
			// Block STEP = (new BlockStep(44, false)).c(2.0F).b(10.0F).a(h).a("stoneSlab");
			Block.byId[Block.STEP.id] = null;
			Block.byId[Block.STEP.id] = new CustomSteps(Block.STEP.id, false).setHardness(2.0F).setResistance(10.0F).setSound(Block.h).a("stoneSlab");

		}
		// TNT
		if (config.getBoolean("blocks.tnt") == true) {
			// Block TNT = (new BlockTNT(46, 8)).c(0.0F).a(g).a("tnt");
			Block.byId[Block.TNT.id] = null;
			Block.byId[Block.TNT.id] = new CustomTNT(Block.TNT.id, 8).setHardness(0.0F).setSound(Block.g).a("tnt");
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

		// Cactus
		if (config.getBoolean("blocks.cactus") == true) {
			// Block CACTUS = (new BlockCactus(81, 70)).c(0.4F).a(k).a("cactus");
			Block.byId[Block.CACTUS.id] = null;
			Block.byId[Block.CACTUS.id] = new CustomCactus(Block.CACTUS.id, 70).setHardness(0.4F).setSound(Block.k).a("cactus");
			try {
				Field field = Material.CACTUS.getClass().getDeclaredField("H");
				field.setAccessible(true);
				field.setBoolean(Material.CACTUS, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the cactus block!");
			}
		}

		// ThinGlass
		if (config.getBoolean("blocks.thinglass") == true) {
			// Block THIN_GLASS = (new BlockThinFence(102, 49, 148, Material.SHATTERABLE, false)).c(0.3F).a(j).a("thinGlass");
			Block.byId[Block.THIN_GLASS.id] = null;
			Block.byId[Block.THIN_GLASS.id] = new CustomThinFence(Block.THIN_GLASS.id, 49, 148, Material.SHATTERABLE, false).setHardness(0.3F).setSound(Block.j).a("thinGlass");
		}

		// IronFence
		if (config.getBoolean("blocks.ironfence") == true) {
			// Block IRON_FENCE = (new BlockThinFence(101, 85, 85, Material.ORE, true)).c(5.0F).b(10.0F).a(i).a("fenceIron");
			Block.byId[Block.IRON_FENCE.id] = null;
			Block.byId[Block.IRON_FENCE.id] = new CustomThinFence(Block.IRON_FENCE.id, 85, 85, Material.ORE, true).setHardness(5.0F).setResistance(10.0F).setSound(Block.i).a("fenceIron");
		}

		if (config.getBoolean("botanical") == true) {
			// BlockFlower YELLOW_FLOWER = (BlockFlower) (new BlockFlower(37, 13)).c(0.0F).a(g).a("flower");
			Block.byId[Block.YELLOW_FLOWER.id] = null;
			Block.byId[Block.YELLOW_FLOWER.id] = new CustomFlowers(Block.YELLOW_FLOWER.id, 13).setHardness(0.0F).setSound(Block.g).a("flower");

			// BlockFlower RED_ROSE = (BlockFlower) (new BlockFlower(38, 12)).c(0.0F).a(g).a("rose");
			Block.byId[Block.RED_ROSE.id] = null;
			Block.byId[Block.RED_ROSE.id] = new CustomFlowers(Block.RED_ROSE.id, 12).setHardness(0.0F).setSound(Block.g).a("rose");
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
		try {
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("H");
			field.setAccessible(true);
			field.setBoolean(Material.SHATTERABLE, true);
		} catch (Exception e) {
			log.warning("BlocksOnGlass couldn't restore the glowstone block!");
			e.printStackTrace();
		}

		// Stairs
		Block.byId[Block.BRICK_STAIRS.id] = Block.BRICK_STAIRS;
		Block.byId[Block.WOOD_STAIRS.id] = Block.WOOD_STAIRS;
		Block.byId[Block.COBBLESTONE_STAIRS.id] = Block.COBBLESTONE_STAIRS;
		Block.byId[Block.STONE_STAIRS.id] = Block.STONE_STAIRS;
		Block.byId[Block.NETHER_BRICK_STAIRS.id] = Block.NETHER_BRICK_STAIRS;

		// Step
		Block.byId[Block.STEP.id] = Block.STEP;

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

		//Cactus
		Block.byId[Block.CACTUS.id] = Block.CACTUS;
		try {
			Field field = Material.CACTUS.getClass().getDeclaredField("H");
			field.setAccessible(true);
			field.setBoolean(Material.CACTUS, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.warning("BlocksOnGlass couldn't restore the cactus block!");
		}

		// ThinGlass
		Block.byId[Block.THIN_GLASS.id] = Block.THIN_GLASS;

		// IronFence
		Block.byId[Block.IRON_FENCE.id] = Block.IRON_FENCE;

		// Flowers
		Block.byId[Block.YELLOW_FLOWER.id] = Block.YELLOW_FLOWER;
		Block.byId[Block.RED_ROSE.id] = Block.RED_ROSE;
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
		blocks.add(org.bukkit.Material.REDSTONE_TORCH_OFF);
		blocks.add(org.bukkit.Material.REDSTONE_WIRE);
		blocks.add(org.bukkit.Material.STONE_BUTTON);
		blocks.add(org.bukkit.Material.WOODEN_DOOR);
		blocks.add(org.bukkit.Material.WOOD_DOOR);
		blocks.add(org.bukkit.Material.IRON_DOOR_BLOCK);
		blocks.add(org.bukkit.Material.IRON_DOOR);
		blocks.add(org.bukkit.Material.REDSTONE);
		blocks.add(org.bukkit.Material.BED);
		blocks.add(org.bukkit.Material.DIODE);
		blocks.add(org.bukkit.Material.DIODE_BLOCK_OFF);
		blocks.add(org.bukkit.Material.DIODE_BLOCK_ON);
		blocks.add(org.bukkit.Material.TRAP_DOOR);
		blocks.add(org.bukkit.Material.SNOW);
		blocks.add(org.bukkit.Material.THIN_GLASS);
		blocks.add(org.bukkit.Material.IRON_FENCE);
		if (config.getBoolean("botanical") == true) {
			blocks.add(org.bukkit.Material.RED_MUSHROOM);
			blocks.add(org.bukkit.Material.BROWN_MUSHROOM);
			blocks.add(org.bukkit.Material.YELLOW_FLOWER);
			blocks.add(org.bukkit.Material.RED_ROSE);
			blocks.add(org.bukkit.Material.CACTUS);
			blocks.add(org.bukkit.Material.WATER_LILY);
			blocks.add(org.bukkit.Material.DEAD_BUSH);
		}
	}

	public void loadConfig() {
		config.options().header("For help and support visit please: http://bit.ly/bogbukkitdev or http://bit.ly/bogbukkit");
		config.addDefault("permissions", true);
		config.addDefault("botanical", true);
		config.addDefault("tweaks.noMelt", true);
		config.addDefault("blocks.glass", true);
		config.addDefault("blocks.ice", true);
		config.addDefault("blocks.leaves", true);
		config.addDefault("blocks.fence", true);
		config.addDefault("blocks.netherfence", true);
		config.addDefault("blocks.glowstone", true);
		config.addDefault("blocks.cactus", false);
		config.addDefault("blocks.tnt", false);
		config.addDefault("blocks.steps", true);
		config.addDefault("blocks.stairs.wood", true);
		config.addDefault("blocks.stairs.stone", true);
		config.addDefault("blocks.stairs.cobblestone", true);
		config.addDefault("blocks.stairs.brick", true);
		config.addDefault("blocks.stairs.netherbrick", true);
		config.addDefault("blocks.ironfence", true);
		config.addDefault("blocks.thinglass", true);
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
