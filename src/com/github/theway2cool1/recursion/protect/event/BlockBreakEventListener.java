package com.github.theway2cool1.recursion.protect.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.github.theway2cool1.recursion.protect.RecursionProtect;

public class BlockBreakEventListener implements Listener{
	private RecursionProtect plugin;
	public BlockBreakEventListener(RecursionProtect plugin){
		this.plugin=plugin;
	}
	@EventHandler(priority=EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e){
		switch(plugin.canBreakBlock(e.getPlayer(), e.getBlock())){
		case 0:
			return;
		case 1:
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "You aren't allowed to break the block: " + e.getBlock().getType().toString().toLowerCase().replaceAll("[_]", " "));
			break;
		case 2:
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "You aren't allowed to break blocks in this area.");
			break;
		}
	}
}
