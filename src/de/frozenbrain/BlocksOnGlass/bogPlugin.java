package de.frozenbrain.BlocksOnGlass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.Block;
import net.minecraft.server.Material;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import de.frozenbrain.BlocksOnGlass.blocks.bogBlockFence;
import de.frozenbrain.BlocksOnGlass.blocks.bogBlockGlass;
import de.frozenbrain.BlocksOnGlass.blocks.bogBlockIce;
import de.frozenbrain.BlocksOnGlass.blocks.bogBlockLeaves;
import de.frozenbrain.BlocksOnGlass.listeners.bogBlockListener;
import de.frozenbrain.BlocksOnGlass.listeners.bogPlayerListener;

public class bogPlugin extends JavaPlugin {
	
	private final bogPlayerListener playerListener = new bogPlayerListener(this);
	private final bogBlockListener blockListener = new bogBlockListener(this);
	public PermissionHandler Permissions;
	public List<org.bukkit.Material> blocks = new ArrayList<org.bukkit.Material>();
	public List<org.bukkit.Material> fenceWhitelist = new ArrayList<org.bukkit.Material>();
	public static boolean fenceFix;
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.SNOW_FORM, blockListener, Priority.Normal, this);
		
		modifyBlocks();
		setupBlockLists();
        
        Configuration config = getConfiguration();
        fenceFix = config.getBoolean("fencefix", true);
        config.save();
        
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

        setupPermissions();
	}
	
	public void onDisable() {
		restoreBlocks();
		System.out.println("BlocksOnGlass disabled.");
	}
	
	private void modifyBlocks() {
		Block.byId[Block.GLASS.id] = null;
		Block.byId[Block.GLASS.id] = new bogBlockGlass(20, 49, Material.SHATTERABLE, true).setHardness(0.3F).setSound(Block.j).a("glass");
		try {
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("F");
			field.setAccessible(true);
			field.setBoolean(Material.SHATTERABLE, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.q[Block.GLASS.id] = 0;
		
		Block.byId[Block.FENCE.id] = null;
		Block.byId[Block.FENCE.id] = new bogBlockFence(85, 4).setHardness(0.3F).setResistance(5F).setSound(Block.e).a("fence");
		Block.q[Block.FENCE.id] = 0;
		
		Block.byId[Block.ICE.id] = null;
		Block.byId[Block.ICE.id] = new bogBlockIce(79, 67).setHardness(0.5F).setSound(Block.j).a("ice");
		Block.q[Block.ICE.id] = 3;
		
		Block.byId[Block.LEAVES.id] = null;
		Block.byId[Block.LEAVES.id] = new bogBlockLeaves(18, 52).setHardness(0.2F).setSound(Block.g).a("leaves");
		try {
			Field field = Material.LEAVES.getClass().getDeclaredField("F");
			field.setAccessible(true);
			field.setBoolean(Material.LEAVES, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.q[Block.LEAVES.id] = 1; // f(1)
		Block.t[Block.LEAVES.id] = true; // g()
	}
	
	private void restoreBlocks() {
		Block.byId[Block.GLASS.id] = Block.GLASS;
		try {
			Field field = Material.SHATTERABLE.getClass().getDeclaredField("F");
			field.setAccessible(true);
			field.setBoolean(Material.SHATTERABLE, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Block.byId[Block.FENCE.id] = Block.FENCE;
		Block.byId[Block.ICE.id] = Block.ICE;
		Block.byId[Block.LEAVES.id] = Block.LEAVES;
		try {
			Field field = Material.LEAVES.getClass().getDeclaredField("F");
			field.setAccessible(true);
			field.setBoolean(Material.LEAVES, true);
		} catch (Exception e) {
			e.printStackTrace();
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
        
        fenceWhitelist.add(org.bukkit.Material.TORCH);
        fenceWhitelist.add(org.bukkit.Material.REDSTONE_TORCH_ON);
	}
	
	private void setupPermissions() {
	      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
	      PluginDescriptionFile pdfFile = this.getDescription();
	      if (this.Permissions == null) {
	          if (test != null) {
	              this.Permissions = ((Permissions)test).getHandler();
	              System.out.println("[" + pdfFile.getName() + "] Permission system detected.");
	          } else {
	              System.out.println("[" + pdfFile.getName() + "] Permission system not detected.");
	          }
	      }
	  }
}
