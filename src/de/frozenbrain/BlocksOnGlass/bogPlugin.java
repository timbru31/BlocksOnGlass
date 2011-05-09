package de.frozenbrain.BlocksOnGlass;

import net.minecraft.server.Block;
import net.minecraft.server.Material;

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
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		
		Block[] blockList = Block.byId;
		
		for(int i=0;i<255;i++) {
			if(blockList[i] instanceof net.minecraft.server.BlockGlass) {
				blockList[i] = null;
				blockList[i] = (new bogBlockGlass(20, 49, Material.SHATTERABLE, true)).setHardness(0.3F).setSound(Block.j).a("glass");
				Block.q[i] = 0;
			}
		}
        
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
        
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
