package com.github.theway2cool1.recursion.protect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class ProtectedRegion{
	private String name;
	private Selection sel;
	private String[] modifiers;
	public ProtectedRegion(String[] modifiers){
		this.modifiers=modifiers;
	}
	public ProtectedRegion(String name, Selection sel, String[] modifiers){
		this.name=name;
		this.sel=sel;
		this.modifiers=modifiers;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public Selection getSelection(){
		return sel;
	}
	public void setSelection(Selection sel){
		this.sel=sel;
	}
	public void setModifiers(String[] modifiers){
		this.modifiers=modifiers;
	}
	public void loadModifiers(String[] modifiers){
		this.modifiers = modifiers;
	}
	public boolean areModifiersEmpty(){
		for(int i = 0; i<modifiers.length; i++){
			if(modifiers[i] != null) return false;
		}
		return true;
	}
	public void addModifier(String s){
		if(!this.hasModifier(s)){
			for(int i = 0; i<modifiers.length; i++){
				if(modifiers[i]==null){
					modifiers[i]=s;
					return;
				}
			}
		}
	}
	public void removeModifier(String s){
		for(int i = 0; i<modifiers.length; i++){
			if(modifiers[i].equalsIgnoreCase(s)){
				modifiers[i]=null;
				return;
			}
		}
	}
	public boolean hasModifier(String s){
		for(int i = 0; i<modifiers.length; i++){
			if(modifiers[i] != null){
				if(modifiers[i].equalsIgnoreCase(s)) return true;
			}
		}
		return false;
	}
	public boolean contains(Location l){
		if(l.getWorld() == sel.getWorld()
			&& l.getBlockX() >= sel.getMinX() && l.getBlockX() <= sel.getMaxX()
			&& l.getBlockY() >= sel.getMinY() && l.getBlockY() <= sel.getMaxY()
			&& l.getBlockZ() >= sel.getMinZ() && l.getBlockZ() <= sel.getMaxZ()){
				return true;
			}
		return false;
	}
	public void save(){
		File f = new File("plugins" + File.separator + "RecursionProtect" + File.separator + "regions" + File.separator + name + ".txt");
		f.delete();
		f.getParentFile().mkdirs();
		try{
			f.createNewFile();
			PrintWriter out = new PrintWriter(new FileWriter(f));
			out.println("name: " + name);
			out.println("world: " + sel.getWorld().getName());
			out.println("minX: " + sel.getMinX());
			out.println("minY: " + sel.getMinY());
			out.println("minZ: " + sel.getMinZ());
			out.println("maxX: " + sel.getMaxX());
			out.println("maxY: " + sel.getMaxY());
			out.println("maxZ: " + sel.getMaxZ());
			if(this.modifiersToString() != null){
				out.println("modifiers: " + this.modifiersToString());
			}
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public String modifiersToString(){
		if(!this.areModifiersEmpty()){
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i<modifiers.length; i++){
				if(modifiers[i] != null){
					sb.append(modifiers[i] + ", ");
				}
			}
			if(sb.toString() != null){
				return sb.toString().substring(0, sb.toString().lastIndexOf(","));
			}
			return null;
		}
	    return null;
	}
	public String getInfo(){
		StringBuffer sb = new StringBuffer();
		sb.append(ChatColor.GOLD + "= Region Information: " + ChatColor.BLUE + this.getName() + ChatColor.GOLD + " =\n");
		sb.append(ChatColor.YELLOW + "Min X: " + ChatColor.WHITE + sel.getMinX() + "\n");
		sb.append(ChatColor.YELLOW + "Min Y: " + ChatColor.WHITE + sel.getMinY() + "\n");
		sb.append(ChatColor.YELLOW + "Min Z: " + ChatColor.WHITE + sel.getMinZ() + "\n");
		sb.append(ChatColor.YELLOW + "Max X: " + ChatColor.WHITE + sel.getMaxX() + "\n");
		sb.append(ChatColor.YELLOW + "Max Y: " + ChatColor.WHITE + sel.getMaxY() + "\n");
		sb.append(ChatColor.YELLOW + "Max Z: " + ChatColor.WHITE + sel.getMaxZ() + "\n");
		sb.append(ChatColor.GREEN + "Modifiers: " + ChatColor.WHITE + this.modifiersToString());
		return sb.toString();
	}
}
