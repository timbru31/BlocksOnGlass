package de.frozenbrain.BlocksOnGlass;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class bogPlugin extends JavaPlugin {
	
	private final bogPlayerListener playerListener = new bogPlayerListener(this);
	private final bogBlockListener blockListener = new bogBlockListener(this);
	public boolean canPlaceTorch;
	public boolean canPlaceRSTorch;
	public boolean canPlaceRedstone;
	public boolean canPlaceRails;
	public boolean canPlaceLadders;
	public boolean canPlaceDoors;
	
	public void onEnable() {
		reloadConfig();
		
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
	
	public void reloadConfig() {
		Configuration config = getConfiguration();
		config.load();
		canPlaceTorch = config.getBoolean("allowTorch", true);
		canPlaceRSTorch = config.getBoolean("allowRedstoneTorch", true);
		canPlaceRedstone = config.getBoolean("allowRedstone", true);
		canPlaceRails = config.getBoolean("allowRail", true);
		canPlaceLadders = config.getBoolean("allowLadder", true);
		canPlaceDoors = config.getBoolean("allowDoor", true);
		config.save();
	}
	
	public boolean hasBit(byte checkData, byte checkBit) {
    	if((checkData & checkBit) == checkBit) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
}
