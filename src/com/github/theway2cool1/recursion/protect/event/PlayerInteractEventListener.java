package com.github.theway2cool1.recursion.protect.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.theway2cool1.recursion.protect.RecursionProtect;
import com.github.theway2cool1.recursion.protect.Selection;

public class PlayerInteractEventListener implements Listener{
	private RecursionProtect plugin;
	public PlayerInteractEventListener(RecursionProtect plugin){
		this.plugin=plugin;
	}
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.LEFT_CLICK_BLOCK){
			if(RecursionProtect.hasSelectorEnabled(p) && p.getItemInHand().getType() == Material.BLAZE_ROD){
				e.setCancelled(true);
				Selection sel;
				if(RecursionProtect.getSelection(p) == null){
					sel = new Selection(p.getName());
					sel.setWorld(p.getWorld());
					sel.setLoc1(e.getClickedBlock().getLocation());
					RecursionProtect.addSelection(sel);
				}else{
					RecursionProtect.getSelection(p).setWorld(p.getWorld());
					RecursionProtect.getSelection(p).setLoc1(e.getClickedBlock().getLocation());
				}
				p.sendMessage(ChatColor.GREEN + "Location 1 set.");
				return;
			}
		}
		else if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(RecursionProtect.hasSelectorEnabled(p) && p.getItemInHand().getType() == Material.BLAZE_ROD){
				e.setCancelled(true);
				if(RecursionProtect.getSelection(p) == null){
					Selection sel = new Selection(p.getName());
					sel.setWorld(p.getWorld());
					sel.setLoc2(e.getClickedBlock().getLocation());
					RecursionProtect.addSelection(sel);
				}else{
					RecursionProtect.getSelection(p).setWorld(p.getWorld());
					RecursionProtect.getSelection(p).setLoc2(e.getClickedBlock().getLocation());
				}
				p.sendMessage(ChatColor.GREEN + "Location 2 set.");
				return;
			}
			if(e.getPlayer().getItemInHand().getType().getId() != 383){
				switch(plugin.canUseItem(e.getPlayer(), e.getPlayer().getItemInHand().getType(), e.getClickedBlock().getLocation())){
				case 0:
					return;
				case 1:
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to use the item: " + e.getPlayer().getItemInHand().getType().toString().toLowerCase().replaceAll("[_]", " "));
					return;
				case 2:
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to use items in this area.");
					return;
				}
			}else{
				switch(plugin.canSpawnMob(e.getPlayer(), EntityType.fromId(e.getPlayer().getItemInHand().getData().getData()), e.getClickedBlock().getLocation())){
				case 0:
					return;
				case 1:
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to spawn the mob: " + EntityType.fromId(e.getPlayer().getItemInHand().getData().getData()).toString().toLowerCase().replaceAll("[_]", " "));
					return;
				case 2:
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to spawn mobs in this area.");
					return;
				}
			}
		}else if(e.getAction() == Action.RIGHT_CLICK_AIR){
			switch(plugin.canUseItem(e.getPlayer(), e.getPlayer().getItemInHand().getType(), e.getPlayer().getLocation())){
			case 0:
				return;
			case 1:
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to use the item: " + e.getPlayer().getItemInHand().getType().toString().toLowerCase().replaceAll("[_]", " "));
				return;
			case 2:
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to use items in this area.");
				return;
			}
		}
	}
}
