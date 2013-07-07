package com.github.theway2cool1.recursion.protect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.theway2cool1.recursion.protect.cmd.RProtectCommand;
import com.github.theway2cool1.recursion.protect.event.BlockBreakEventListener;
import com.github.theway2cool1.recursion.protect.event.BlockPlaceEventListener;
import com.github.theway2cool1.recursion.protect.event.PlayerInteractEventListener;

public class RecursionProtect extends JavaPlugin{
	private static ArrayList<String> usingWand = new ArrayList<String>();
	private static ArrayList<Selection> selections = new ArrayList<Selection>();
	private static ArrayList<ProtectedRegion> regions = new ArrayList<ProtectedRegion>();
	@Override
	public void onEnable(){
		this.getLogger().info("RecursionProtect v" + this.getDescription().getVersion() + " enabled.");
		this.loadRegions();
		this.getConfig().addDefault("blacklist.enabled", true);
		this.getConfig().addDefault("blacklist.items", Arrays.asList(new String[]{"tnt", "flint_and_steel"}));
		this.getConfig().addDefault("blacklist.spawner_eggs", Arrays.asList(new String[]{"creeper", "ghast"}));
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockBreakEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockPlaceEventListener(this), this);
		this.getCommand("rprotect").setExecutor(new RProtectCommand(this));
	}
	@Override
	public void onDisable(){
		if(!regions.isEmpty()){
			this.getLogger().info("Saving regions...");
			for(ProtectedRegion r : regions){
				r.save();
				this.getLogger().info("Saved region: " + r.getName());
			}
		}
		this.getLogger().info("RecursionProtect v" + this.getDescription().getVersion() + " disabled.");
	}
	
	//Selections start
	public static boolean hasSelectorEnabled(Player p){
		return usingWand.contains(p.getName());
	}
	public static void setSelectorEnabled(Player p, boolean enabled){
		if(enabled){
			if(!usingWand.contains(p.getName())) usingWand.add(p.getName());
			return;
		}
		usingWand.remove(p.getName());
	}
	public static Selection getSelection(Player p){
		for(Selection sel : selections){
			if(sel.getPlayer().equals(p.getName())) return sel;
		}
		return null;
	}
	public static void addSelection(Selection sel){
		selections.add(sel);
	}
	//Selections end
	
	//Regions start
	public static ArrayList<ProtectedRegion> getRegions(){
		return regions;
	}
	public void deleteRegion(ProtectedRegion region){
		for(Object r: regions.toArray()){
			if((ProtectedRegion) r == region){
				File f = new File("plugins" + File.separator + "RecursionProtect" + File.separator + "regions" + File.separator + region.getName() + ".txt");
				if(!f.delete()){
					this.getLogger().warning("Could not delete region \"" + region.getName() + "\". Try deleting it manually and reloading.");
				}
				regions.remove(region);
			}
		}
	}
	public static ProtectedRegion getRegion(String name){
		for(ProtectedRegion r : regions){
			if(r.getName().equalsIgnoreCase(name)) return r;
		}
		return null;
	}
	//Regions end
	
	//Permission checks start
	public int canBreakBlock(Player p, Block b){
		if(this.getConfig().getBoolean("blacklist.enabled")){
			if(this.getConfig().getStringList("blacklist.items").contains(b.getType().toString().toLowerCase()) ||
		    this.getConfig().getStringList("blacklist.items").contains(Integer.toString(b.getType().getId()))){
				if(!p.hasPermission("rprotect.break." + b.getType().toString().toLowerCase()) && !p.hasPermission("rprotect.break.*")) return 1;
			}
		}
		for(ProtectedRegion r : regions){
			if(r.contains(b.getLocation())){
				if(!p.hasPermission("rprotect.region.break." + r.getName()) &&  !p.hasPermission("rprotect.region.break.*") && r.hasModifier(Modifier.ENFORCE_DESTROY_PERMISSIONS.getLabel())) return 2;
			}
		}
		return 0;
	}
	public int canPlaceBlock(Player p, Block b){
		if(this.getConfig().getBoolean("blacklist.enabled")){
			if(this.getConfig().getStringList("blacklist.items").contains(b.getType().toString().toLowerCase()) ||
			this.getConfig().getStringList("blacklist.items").contains(Integer.toString(b.getType().getId()))){
				if(!p.hasPermission("rprotect.place." + b.getType().toString().toLowerCase()) && !p.hasPermission("rprotect.place.*")) return 1;
			}
		}
		for(ProtectedRegion r : regions){
			if(r.contains(b.getLocation())){
				if(!p.hasPermission("rprotect.region.place." + r.getName()) && !p.hasPermission("rprotect.region.place.*") && r.hasModifier(Modifier.ENFORCE_BUILD_PERMISSIONS.getLabel())) return 2;
			}
		}
		return 0;
	}
	public int canUseItem(Player p, Material m, Location l){
		if(this.getConfig().getBoolean("blacklist.enabled")){
			if(this.getConfig().getStringList("blacklist.items").contains(m.toString().toLowerCase()) ||
			this.getConfig().getStringList("blacklist.items").contains(Integer.toString(m.getId()))){
				if(!p.hasPermission("rprotect.use." + m.toString().toLowerCase()) && !p.hasPermission("rprotect.use.*")) return 1;
			}
		}
		for(ProtectedRegion r : regions){
			if(r.contains(l)){
				if(!p.hasPermission("rprotect.region.use." + r.getName()) && !p.hasPermission("rprotect.region.use.*") && r.hasModifier(Modifier.ENFORCE_ITEM_USE_PERMISSIONS.getLabel())) return 2;
			}
		}
		return 0;
	}
	public int canSpawnMob(Player p, EntityType e, Location l){
		if(this.getConfig().getBoolean("blacklist.enabled")){
			if(this.getConfig().getStringList("blacklist.items").contains(e.toString().toLowerCase()) ||
			this.getConfig().getStringList("blacklist.spawner_eggs").contains(e.toString().toLowerCase())){
				if(!p.hasPermission("rprotect.spawn." + e.toString().toLowerCase()) && !p.hasPermission("rprotect.spawn.*")) return 1;
			}
		}
	    for(ProtectedRegion r : regions){
			if(r.contains(l)){
				if(!p.hasPermission("rprotect.region.spawn." + r.getName()) && !p.hasPermission("rprotect.region.spawn.*") && r.hasModifier(Modifier.ENFORCE_ITEM_USE_PERMISSIONS.getLabel())) return 2;
			}
		}
		return 0;		
	}
	//Permission checks end
	
	//Loading start
	private void loadRegions(){
		File f = new File("plugins" + File.separator + "RecursionProtect" + File.separator + "regions");
		BufferedReader in;
		String s = null;
		try{
			if(f.listFiles() != null){
				for(File region : f.listFiles()){
					Selection sel = new Selection(null);
					ProtectedRegion cregion = new ProtectedRegion(new String[Modifier.values().length]);
					in = new BufferedReader(new FileReader(region));
					while((s = in.readLine()) != null){
						switch(s.split(": ")[0]){
						case "name":
							cregion.setName(s.split(": ")[1]);
							break;
						case "world":
							sel.setWorld(Bukkit.getWorld(s.split(": ")[1]));
							break;
						case "minX":
							sel.setMinX(Integer.parseInt(s.split(": ")[1]));
							break;
						case "minY":
							sel.setMinY(Integer.parseInt(s.split(": ")[1]));
							break;
						case "minZ":
							sel.setMinZ(Integer.parseInt(s.split(": ")[1]));
							break;
						case "maxX":
							sel.setMaxX(Integer.parseInt(s.split(": ")[1]));
							break;
						case "maxY":
							sel.setMaxY(Integer.parseInt(s.split(": ")[1]));
							break;
						case "maxZ":
							sel.setMaxZ(Integer.parseInt(s.split(": ")[1]));
							cregion.setSelection(sel);
							break;
						case "modifiers":
							String[] modifiers = new String[Modifier.values().length];
							cregion.setModifiers(modifiers);
							for(int i = 0; i<s.split(": ")[1].split(", ").length; i++){
								cregion.addModifier(s.split(": ")[1].split(", ")[i]);
							}
							break;
						default:
							this.getLogger().info("Unnecessary line in region file " + region.getName() +":\n" + "\"" + s + "\"\n" + "This line will be deleted on next reload.");
						}	
					}
					in.close();
					regions.add(cregion);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//Loading end
	
	//Other
	public void checkEvents(){
		
	}
}
