package com.github.theway2cool1.recursion.protect;
import org.bukkit.Location;
import org.bukkit.World;

public class Selection{
	private String player;
	private World w;
	private Location loc1 = null;
	private Location loc2 = null;
	private int minX = -1;
	private int maxX = -1;
	private int minY = -1;
	private int maxY = -1;
	private int minZ = -1;
	private int maxZ = -1;
	public Selection(String player){
		this.player = player;
	}
	public Selection(String player, World w, int minX, int minY, int minZ, int maxX, int maxY, int maxZ){
		this.player = player;
		this.w = w;
		this.setMinX(minX);
		this.setMaxX(maxX);
		this.setMinY(minY);
		this.setMaxY(maxY);
		this.setMinZ(minZ);
		this.setMaxZ(maxZ);
	}
	public boolean isComplete(){
		if(loc1 == null || loc2 == null){
			return false;
		}
		return true;
	}
	public void setLoc1(Location l){
		loc1 = l;
		if(loc2 == null){
			minX = loc1.getBlockX();
			minY = loc1.getBlockY();
			minZ = loc1.getBlockZ();
		}else{
			if(loc1.getBlockX() > loc2.getBlockX()){
				maxX = loc1.getBlockX();
				minX = loc2.getBlockX();
			}else{
				minX = loc1.getBlockX();
				maxX = loc2.getBlockX();
			}
			if(loc1.getBlockY() > loc2.getBlockY()){
				maxY = loc1.getBlockY();
				minY = loc2.getBlockY();
			}else{
				minY = loc1.getBlockY();
				maxY = loc2.getBlockY();
			}
			if(loc1.getBlockZ() > loc2.getBlockZ()){
				maxZ = loc1.getBlockZ();
				minZ = loc2.getBlockZ();
			}else{
				minZ = loc1.getBlockZ();
				maxZ = loc2.getBlockZ();
			}
		}
	}
	public void setLoc2(Location l){
		loc2 = l;
		if(loc1 == null){
			minX = loc2.getBlockX();
			minY = loc2.getBlockY();
			minZ = loc2.getBlockZ();
		}else{
			if(loc1.getBlockX() > loc2.getBlockX()){
				maxX = loc1.getBlockX();
				minX = loc2.getBlockX();
			}else{
				minX = loc1.getBlockX();
				maxX = loc2.getBlockX();
			}
			if(loc1.getBlockY() > loc2.getBlockY()){
				maxY = loc1.getBlockY();
				minY = loc2.getBlockY();
			}else{
				minY = loc1.getBlockY();
				maxY = loc2.getBlockY();
			}
			if(loc1.getBlockZ() > loc2.getBlockZ()){
				maxZ = loc1.getBlockZ();
				minZ = loc2.getBlockZ();
			}else{
				minZ = loc1.getBlockZ();
				maxZ = loc2.getBlockZ();
			}
		}
	}
	public String getPlayer(){
		return player;
	}
	public World getWorld(){
		return w;
	}
	public void setWorld(World world){
		this.w = world;
	}
	public int getMinX() {
		return minX;
	}
	public void setMinX(int minX) {
		this.minX = minX;
	}
	public int getMaxX() {
		return maxX;
	}
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	public int getMinY() {
		return minY;
	}
	public void setMinY(int minY) {
		this.minY = minY;
	}
	public int getMaxY() {
		return maxY;
	}
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	public int getMinZ() {
		return minZ;
	}
	public void setMinZ(int minZ) {
		this.minZ = minZ;
	}
	public int getMaxZ() {
		return maxZ;
	}
	public void setMaxZ(int maxZ) {
		this.maxZ = maxZ;
	}
}
