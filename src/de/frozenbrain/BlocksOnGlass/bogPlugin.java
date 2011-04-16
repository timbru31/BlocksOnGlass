package de.frozenbrain.BlocksOnGlass;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class bogPlugin extends JavaPlugin {
	
	private final bogPlayerListener playerListener = new bogPlayerListener(this);
	private final bogBlockListener blockListener = new bogBlockListener(this);
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Priority.Normal, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
	
	public void onDisable() {
		System.out.println("BlocksOnGlass disabled.");
	}
	
}
