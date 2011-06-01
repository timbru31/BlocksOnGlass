package de.frozenbrain.BlocksOnGlass;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.Block;
import net.minecraft.server.Material;
import net.minecraft.server.MaterialMapColor;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class bogPlugin extends JavaPlugin {
	
	private final bogPlayerListener playerListener = new bogPlayerListener(this);
	public PermissionHandler Permissions;
	public List<org.bukkit.Material> blocks = new ArrayList<org.bukkit.Material>();
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		
		for(int i=0;i<255;i++) {
			if(Block.byId[i] instanceof net.minecraft.server.BlockGlass) {
				Block.byId[i] = null;
				Block.byId[i] = new bogBlockGlass(20, 49, new Material(MaterialMapColor.b), true).setHardness(0.3F).setSound(Block.j).a("glass");
			}
		}
        
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
        
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
        blocks.add(org.bukkit.Material.SIGN);
        blocks.add(org.bukkit.Material.WOOD_DOOR);
        blocks.add(org.bukkit.Material.IRON_DOOR);
        blocks.add(org.bukkit.Material.REDSTONE);
        blocks.add(org.bukkit.Material.BED);
        blocks.add(org.bukkit.Material.DIODE);
        blocks.add(org.bukkit.Material.TRAP_DOOR);
        
        setupPermissions();
	}
	
	public void onDisable() {
		Block[] blockList = Block.byId;
		
		for(int i=0;i<255;i++) {
			if(blockList[i] instanceof bogBlockGlass) {
				blockList[i] = null;
				blockList[i] = Block.GLASS;
			}
		}
		
		System.out.println("BlocksOnGlass disabled.");
	}
	
	private void setupPermissions() {
	      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
	      PluginDescriptionFile pdfFile = this.getDescription();
	      if (this.Permissions == null) {
	          if (test != null) {
	              this.Permissions = ((Permissions)test).getHandler();
	              System.out.println("[" + pdfFile.getName() + "] Permission system detected.");
	          } else {
	              System.out.println("[" + pdfFile.getName() + "] Permission system not detected. Everybody can place everything on glass.");
	          }
	      }
	  }
}
