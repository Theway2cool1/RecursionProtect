package com.github.theway2cool1.recursion.protect.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.theway2cool1.recursion.protect.RecursionProtect;

public class BlockPlaceEventListener implements Listener{
	private RecursionProtect plugin;
	public BlockPlaceEventListener(RecursionProtect plugin){
		this.plugin=plugin;
	}
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e){
		switch(plugin.canPlaceBlock(e.getPlayer(), e.getBlock())){
		case 0:
			return;
		case 1:
			e.getPlayer().sendMessage(ChatColor.RED + "You aren't allowed to place the block: " + e.getBlock().getType().toString().toLowerCase().replaceAll("[_]", " "));
			e.setCancelled(true);
			break;
		case 2:
			e.getPlayer().sendMessage(ChatColor.RED + "You aren't allowed to place blocks in this area.");
			e.setCancelled(true);
			break;
		}
	}
}
