package de.dustplanet.blocksonglass;

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
import de.dustplanet.blocksonglass.blocks.CustomCactus;
import de.dustplanet.blocksonglass.blocks.CustomDeadBush;
import de.dustplanet.blocksonglass.blocks.CustomFences;
import de.dustplanet.blocksonglass.blocks.CustomFlowers;
import de.dustplanet.blocksonglass.blocks.CustomGlass;
import de.dustplanet.blocksonglass.blocks.CustomGlowstone;
import de.dustplanet.blocksonglass.blocks.CustomIce;
import de.dustplanet.blocksonglass.blocks.CustomLeaves;
import de.dustplanet.blocksonglass.blocks.CustomMushroom;
import de.dustplanet.blocksonglass.blocks.CustomPiston;
import de.dustplanet.blocksonglass.blocks.CustomPistonExtension;
import de.dustplanet.blocksonglass.blocks.CustomPistonMoving;
import de.dustplanet.blocksonglass.blocks.CustomReed;
//import de.dustplanet.blocksonglass.blocks.CustomStairs;
import de.dustplanet.blocksonglass.blocks.CustomSteps;
import de.dustplanet.blocksonglass.blocks.CustomTNT;
import de.dustplanet.blocksonglass.blocks.CustomThinFence;
import de.dustplanet.blocksonglass.blocks.CustomWaterLily;

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
	private List<String> stats = new ArrayList<String>();
	public FileConfiguration config;
	public File configFile;

	// Shutdown
	public void onDisable() {
		restoreBlocks();
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " " + pdfFile.getVersion() + " is disabled!");
		stats.clear();
		blocks.clear();
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
			Metrics metrics = new Metrics(this);
			// Custom plotter for each item
			for (int i = 0; i < stats.size(); i++) {
				final String name = stats.get(i);
				metrics.addCustomData(new Metrics.Plotter() {
					@Override
					public String getColumnName() {
						return name;
					}

					@Override
					public int getValue() {
						return 1;
					}
				});
			}
			metrics.start();
		}
		catch (IOException e) {}
	}

	private void modifyBlocks() {
		// Glass
		if (config.getBoolean("blocks.glass")) {
			// Block GLASS = (new BlockGlass(20, 49, Material.SHATTERABLE, false)).c(0.3F).a(j).b("glass");
			Block.byId[Block.GLASS.id] = null;
			Block.byId[Block.GLASS.id] = new CustomGlass(Block.GLASS.id, 49, Material.SHATTERABLE, false).setHardness(0.3F).setSound(Block.j).b("glass");
			try {
				Field field = Material.SHATTERABLE.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.SHATTERABLE, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the glass block!");
			}
			stats.add("Glass");
		}

		// Fences
		if (config.getBoolean("blocks.fence")) {
			// Block FENCE = (new BlockFence(85, 4)).c(2.0F).b(5.0F).a(e).b("fence");
			Block.byId[Block.FENCE.id] = null;
			Block.byId[Block.FENCE.id] = new CustomFences(Block.FENCE.id, 4).setHardness(2.0F).setResistance(5.0F).setSound(Block.e).b("fence");
			stats.add("Fence");
		}

		// Ice
		if (config.getBoolean("blocks.ice")) {
			// Block ICE = (new BlockIce(79, 67)).c(0.5F).h(3).a(j).b("ice");
			Block.byId[Block.ICE.id] = null;
			Block.byId[Block.ICE.id] = new CustomIce(Block.ICE.id, 67).setHardness(0.5F).setSound(Block.j).b("ice");
			try {
				Field field = Material.ICE.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.ICE, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the ice block!");
			}
			Block.lightBlock[Block.ICE.id] = 3;
			stats.add("Ice");
		}

		// Leaves
		if (config.getBoolean("blocks.leaves")) {
			// BlockLeaves LEAVES = (BlockLeaves) (new BlockLeaves(18, 52)).c(0.2F).h(1).a(g).b("leaves").p();
			Block.byId[Block.LEAVES.id] = null;
			Block.byId[Block.LEAVES.id] = new CustomLeaves(Block.LEAVES.id, 52).setHardness(0.2F).setSound(Block.g).b("leaves");
			try {
				Field field = Material.LEAVES.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.LEAVES, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the leaves block!");
			}
			Block.lightBlock[Block.LEAVES.id] = 1;
			Block.r[Block.LEAVES.id] = true;
			stats.add("Leaves");
		}

		// NetherFences
		if (config.getBoolean("blocks.netherfence")) {
			// Block NETHER_FENCE = (new BlockFence(113, 224, Material.STONE)).c(2.0F).b(10.0F).a(h).b("netherFence");
			Block.byId[Block.NETHER_FENCE.id] = null;
			Block.byId[Block.NETHER_FENCE.id] = new CustomFences(Block.NETHER_FENCE.id, 224, Material.STONE).setHardness(2.0F).setResistance(10.0F).setSound(Block.h).b("netherFence");
			stats.add("NetherFence");
		}

		// Glowstone
		if (config.getBoolean("blocks.glowstone")) {
			// Block GLOWSTONE = (new BlockLightStone(89, 105, Material.SHATTERABLE)).c(0.3F).a(j).a(1.0F).b("lightgem");
			Block.byId[Block.GLOWSTONE.id] = null;
			Block.byId[Block.GLOWSTONE.id] = new CustomGlowstone(Block.GLOWSTONE.id, 105, Material.SHATTERABLE).setHardness(0.3F).setLightValue(1.0F).setSound(Block.j).b("lightgem");
			try {
				Field field = Material.SHATTERABLE.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.SHATTERABLE, false);
			}
			catch (Exception e) {
				log.warning("BlocksOnGlass couldn't modify the glowstone block!");
				e.printStackTrace();
			}
			stats.add("Glowstone");
		}

		// Different STAIRS (All extending BlockStairs)
//		if (config.getBoolean("blocks.stairs.brick")) {
//			// Block BRICK_STAIRS = (new BlockStairs(108, BRICK)).a("stairsBrick").j();
//			// Block BRICK_STAIRS = (new BlockStairs(108, BRICK, 0)).b("stairsBrick").p();
//			Block.byId[Block.BRICK_STAIRS.id] = null;
//			Block.byId[Block.BRICK_STAIRS.id] = new CustomStairs(Block.BRICK_STAIRS.id, Block.BRICK).b("stairsBrick");
//			Block.r[Block.BRICK_STAIRS.id] = true;
//			stats.add("BrickStairs");
//		}
//		if (config.getBoolean("blocks.stairs.wood")) {
//			// Block WOOD_STAIRS = (new BlockStairs(53, WOOD)).a("stairsWood").j();
//			// Block WOOD_STAIRS = (new BlockStairs(53, WOOD, 0)).b("stairsWood").p();
//			Block.byId[Block.WOOD_STAIRS.id] = null;
//			Block.byId[Block.WOOD_STAIRS.id] = new CustomStairs(Block.WOOD_STAIRS.id, Block.WOOD).b("stairsWood");
//			Block.r[Block.WOOD_STAIRS.id] = true;
//			stats.add("WoodStairs");
//		}
//		if (config.getBoolean("blocks.stairs.cobblestone")) {
//			// Block COBBLESTONE_STAIRS = (new BlockStairs(67, COBBLESTONE)).a("stairsStone").j();
//			// Block COBBLESTONE_STAIRS = (new BlockStairs(67, COBBLESTONE, 0)).b("stairsStone").p();
//			Block.byId[Block.COBBLESTONE_STAIRS.id] = null;
//			Block.byId[Block.COBBLESTONE_STAIRS.id] = new CustomStairs(Block.COBBLESTONE_STAIRS.id, Block.COBBLESTONE).b("stairsStone");
//			Block.r[Block.COBBLESTONE_STAIRS.id] = true;
//			stats.add("CobblestoneStairs");
//		}
//		if (config.getBoolean("blocks.stairs.stone")) {
//			// Block STONE_STAIRS = (new BlockStairs(109, SMOOTH_BRICK)).a("stairsStoneBrickSmooth").j();
//			// Block SMOOTH_BRICK = (new BlockSmoothBrick(98)).c(1.5F).b(10.0F).a(h).b("stonebricksmooth");
//			Block.byId[Block.STONE_STAIRS.id] = null;
//			Block.byId[Block.STONE_STAIRS.id] = new CustomStairs(Block.STONE_STAIRS.id, Block.SMOOTH_BRICK).b("stairsStoneBrickSmooth");
//			Block.r[Block.STONE_STAIRS.id] = true;
//			stats.add("StoneStairs");
//		}
//		if (config.getBoolean("blocks.stairs.netherbrick")) {
//			// Block NETHER_BRICK_STAIRS = (new BlockStairs(114, NETHER_BRICK)).a("stairsNetherBrick").j();
//			// Block NETHER_BRICK_STAIRS = (new BlockStairs(114, NETHER_BRICK, 0)).b("stairsNetherBrick").p();
//			Block.byId[Block.NETHER_BRICK_STAIRS.id] = null;
//			Block.byId[Block.NETHER_BRICK_STAIRS.id] = new CustomStairs(Block.NETHER_BRICK_STAIRS.id, Block.NETHER_BRICK).b("stairsNetherBrick");
//			Block.r[Block.NETHER_BRICK_STAIRS.id] = true;
//			stats.add("NetherBrickStairs");
//		}
		
		// TODO NEW wooden stairs

		// Steps
		if (config.getBoolean("blocks.steps")) {
			// BlockStepAbstract STEP = (BlockStepAbstract) (new BlockStep(44, false)).c(2.0F).b(10.0F).a(h).b("stoneSlab");
			Block.byId[Block.STEP.id] = null;
			Block.byId[Block.STEP.id] = new CustomSteps(Block.STEP.id, false).setHardness(2.0F).setResistance(10.0F).setSound(Block.h).b("stoneSlab");
			stats.add("Steps");
		}
		// TNT
		if (config.getBoolean("blocks.tnt")) {
			// Block TNT = (new BlockTNT(46, 8)).c(0.0F).a(g).b("tnt");
			Block.byId[Block.TNT.id] = null;
			Block.byId[Block.TNT.id] = new CustomTNT(Block.TNT.id, 8).setHardness(0.0F).setSound(Block.g).b("tnt");
			try {
				Field field = Material.TNT.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.TNT, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the TNT block!");
			}
			stats.add("TNT");
		}

		// Cactus
		if (config.getBoolean("blocks.cactus")) {
			// Block CACTUS = (new BlockCactus(81, 70)).c(0.4F).a(k).b("cactus");
			Block.byId[Block.CACTUS.id] = null;
			Block.byId[Block.CACTUS.id] = new CustomCactus(Block.CACTUS.id, 70).setHardness(0.4F).setSound(Block.k).b("cactus");
			try {
				Field field = Material.CACTUS.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.CACTUS, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the cactus block!");
			}
			stats.add("Cactus");
		}

		// ThinGlass
		if (config.getBoolean("blocks.thinglass")) {
			// Block THIN_GLASS = (new BlockThinFence(102, 49, 148, Material.SHATTERABLE, false)).c(0.3F).a(j).b("thinGlass");
			Block.byId[Block.THIN_GLASS.id] = null;
			Block.byId[Block.THIN_GLASS.id] = new CustomThinFence(Block.THIN_GLASS.id, 49, 148, Material.SHATTERABLE, false).setHardness(0.3F).setSound(Block.j).b("thinGlass");
			stats.add("ThinGlass");
		}

		// IronFence
		if (config.getBoolean("blocks.ironfence")) {
			// Block IRON_FENCE = (new BlockThinFence(101, 85, 85, Material.ORE, true)).c(5.0F).b(10.0F).a(i).b("fenceIron");
			Block.byId[Block.IRON_FENCE.id] = null;
			Block.byId[Block.IRON_FENCE.id] = new CustomThinFence(Block.IRON_FENCE.id, 85, 85, Material.ORE, true).setHardness(5.0F).setResistance(10.0F).setSound(Block.i).b("fenceIron");
			stats.add("IronFence");
		}
		
		// Pistons!
		if (config.getBoolean("blocks.pistons.normal")) {
			// Block PISTON = (new BlockPiston(33, 107, false)).b("pistonBase").p();
			Block.byId[Block.PISTON.id] = null;
			Block.byId[Block.PISTON.id] = new CustomPiston(Block.PISTON.id, 107, false).b("pistonBase");
			try {
				Field field = Material.PISTON.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.PISTON, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the piston block!");
			}
			Block.r[Block.PISTON.id] = true;
			
			// BlockPistonExtension PISTON_EXTENSION = (BlockPistonExtension) (new BlockPistonExtension(34, 107)).p();
			Block.byId[Block.PISTON_EXTENSION.id] = null;
			Block.byId[Block.PISTON_EXTENSION.id] = new CustomPistonExtension(Block.PISTON_EXTENSION.id, 107);
			Block.r[Block.PISTON_EXTENSION.id] = true;
			
			// BlockPistonMoving PISTON_MOVING = new BlockPistonMoving(36);
			Block.byId[Block.PISTON_MOVING.id] = null;
			Block.byId[Block.PISTON_MOVING.id] = new CustomPistonMoving(36);
			stats.add("Piston");
		}
		if (config.getBoolean("blocks.pistons.sticky")) {
			// Block PISTON_STICKY = (new BlockPiston(29, 106, true)).b("pistonStickyBase").p();
			Block.byId[Block.PISTON_STICKY.id] = null;
			Block.byId[Block.PISTON_STICKY.id] = new CustomPiston(Block.PISTON_STICKY.id, 106, true).b("pistonStickyBase");
			try {
				Field field = Material.PISTON.getClass().getDeclaredField("I");
				field.setAccessible(true);
				field.setBoolean(Material.PISTON, false);
			}
			catch (Exception e) {
				e.printStackTrace();
				log.warning("BlocksOnGlass couldn't modify the piston block!");
			}
			Block.r[Block.PISTON_STICKY.id] = true;
			
			// BlockPistonExtension PISTON_EXTENSION = (BlockPistonExtension) (new BlockPistonExtension(34, 107)).p();
			Block.byId[Block.PISTON_EXTENSION.id] = null;
			Block.byId[Block.PISTON_EXTENSION.id] = new CustomPistonExtension(Block.PISTON_EXTENSION.id, 107);
			Block.r[Block.PISTON_EXTENSION.id] = true;
			
			// BlockPistonMoving PISTON_MOVING = new BlockPistonMoving(36);
			Block.byId[Block.PISTON_MOVING.id] = null;
			Block.byId[Block.PISTON_MOVING.id] = new CustomPistonMoving(36);
			stats.add("StickyPiston");
		}

		if (config.getBoolean("botanical")) {
			// BlockFlower YELLOW_FLOWER = (BlockFlower) (new BlockFlower(37, 13)).c(0.0F).a(g).b("flower");
			Block.byId[Block.YELLOW_FLOWER.id] = null;
			Block.byId[Block.YELLOW_FLOWER.id] = new CustomFlowers(Block.YELLOW_FLOWER.id, 13).setHardness(0.0F).setSound(Block.g).b("flower");

			// BlockFlower RED_ROSE = (BlockFlower) (new BlockFlower(38, 12)).c(0.0F).a(g).b("rose");
			Block.byId[Block.RED_ROSE.id] = null;
			Block.byId[Block.RED_ROSE.id] = new CustomFlowers(Block.RED_ROSE.id, 12).setHardness(0.0F).setSound(Block.g).b("rose");
			
			// Block WATER_LILY = (new BlockWaterLily(111, 76)).c(0.0F).a(g).b("waterlily");
			Block.byId[Block.WATER_LILY.id] = null;
			Block.byId[Block.WATER_LILY.id] = new CustomWaterLily(Block.WATER_LILY.id, 76).setHardness(0.0F).setSound(Block.g).b("waterlily");
			
			// BlockDeadBush DEAD_BUSH = (BlockDeadBush) (new BlockDeadBush(32, 55)).c(0.0F).a(g).b("deadbush");
			Block.byId[Block.DEAD_BUSH.id] = null;
			Block.byId[Block.DEAD_BUSH.id] = new CustomDeadBush(Block.DEAD_BUSH.id, 55).setHardness(0.0F).setSound(Block.g).b("deadbush");
			
			// BlockFlower BROWN_MUSHROOM = (BlockFlower) (new BlockMushroom(39, 29)).c(0.0F).a(g).a(0.125F).b("mushroom");
			Block.byId[Block.BROWN_MUSHROOM.id] = null;
			Block.byId[Block.BROWN_MUSHROOM.id] = new CustomMushroom(Block.BROWN_MUSHROOM.id, 29).setHardness(0.0F).setLightValue(0.125F).setSound(Block.g).b("mushroom");
			
		    // BlockFlower RED_MUSHROOM = (BlockFlower) (new BlockMushroom(40, 28)).c(0.0F).a(g).b("mushroom");
			Block.byId[Block.RED_MUSHROOM.id] = null;
			Block.byId[Block.RED_MUSHROOM.id] = new CustomMushroom(Block.RED_MUSHROOM.id, 28).setHardness(0.0F).setSound(Block.g).b("mushroom");
			
			// Block SUGAR_CANE_BLOCK = (new BlockReed(83, 73)).c(0.0F).a(g).b("reeds").v();
			Block.byId[Block.SUGAR_CANE_BLOCK.id] = null;
			Block.byId[Block.SUGAR_CANE_BLOCK.id] = new CustomReed(Block.SUGAR_CANE_BLOCK.id, 73).setHardness(0.0F).setSound(Block.g).b("reeds");
			Block.s[Block.SUGAR_CANE_BLOCK.id] = false;
			
			stats.add("Botanical");
		}
		
		// Chests
		if (config.getBoolean("blocks.chest")) stats.add("Chest");
		
		// Workbench
		if (config.getBoolean("blocks.workbench")) stats.add("Chest");
		
		// Furnace
		if (config.getBoolean("blocks.furnace")) stats.add("Furnace");
		
		// Brewing Stand
		if (config.getBoolean("blocks.brewing_stand")) stats.add("Brewing Stand");
		
		// Dispenser
		if (config.getBoolean("blocks.dispenser")) stats.add("Dispenser");
		
		// Cauldron
		if (config.getBoolean("blocks.cauldron")) stats.add("Cauldron");
	}

	private void restoreBlocks() {
		// Glass
		Block.byId[Block.GLASS.id] = Block.GLASS;
		try {
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("I");
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
			Field field = Material.ICE.getClass().getDeclaredField("I");
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
			Field field = Material.LEAVES.getClass().getDeclaredField("I");
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
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("I");
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
			Field field = Material.TNT.getClass().getDeclaredField("I");
			field.setAccessible(true);
			field.setBoolean(Material.TNT, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.warning("BlocksOnGlass couldn't restore the TNT block!");
		}

		// Cactus
		Block.byId[Block.CACTUS.id] = Block.CACTUS;
		try {
			Field field = Material.CACTUS.getClass().getDeclaredField("I");
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
		
		// Pistons
		Block.byId[Block.PISTON.id] = Block.PISTON;
		Block.byId[Block.PISTON_STICKY.id] = Block.PISTON_STICKY;
		Block.byId[Block.PISTON_EXTENSION.id] = Block.PISTON_EXTENSION;
		Block.byId[Block.PISTON_MOVING.id] = Block.PISTON_MOVING;
		try {
			Field field = Material.PISTON.getClass().getDeclaredField("I");
			field.setAccessible(true);
			field.setBoolean(Material.PISTON, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.warning("BlocksOnGlass couldn't modify the piston block!");
		}

		// Flowers
		Block.byId[Block.YELLOW_FLOWER.id] = Block.YELLOW_FLOWER;
		Block.byId[Block.RED_ROSE.id] = Block.RED_ROSE;
		
		// Waterlily
		Block.byId[Block.WATER_LILY.id] = Block.WATER_LILY;
		
		// Dead Bush
		Block.byId[Block.DEAD_BUSH.id] = Block.DEAD_BUSH;
		
		// Mushrooms
		Block.byId[Block.BROWN_MUSHROOM.id] = Block.BROWN_MUSHROOM;
		Block.byId[Block.RED_MUSHROOM.id] = Block.RED_MUSHROOM;
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
		if (config.getBoolean("botanical")) {
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
		config.addDefault("botanical", false);
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
		config.addDefault("blocks.pistons.normal", true);
		config.addDefault("blocks.pistons.sticky", true);
		config.addDefault("blocks.chest", false);
		config.addDefault("blocks.workbench", false);
		config.addDefault("blocks.furnace", false);
		config.addDefault("blocks.brewing_stand", false);
		config.addDefault("blocks.cauldron", false);
		config.addDefault("blocks.dispenser", false);
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
