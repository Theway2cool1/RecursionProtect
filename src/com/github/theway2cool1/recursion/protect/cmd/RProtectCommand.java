package com.github.theway2cool1.recursion.protect.cmd;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.theway2cool1.recursion.protect.Modifier;
import com.github.theway2cool1.recursion.protect.ProtectedRegion;
import com.github.theway2cool1.recursion.protect.RecursionProtect;

public class RProtectCommand implements CommandExecutor{
	
	private RecursionProtect plugin;
	public RProtectCommand(RecursionProtect plugin){
		this.plugin=plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length >= 1){
			if(args[0].equalsIgnoreCase("selector")){
				if(sender instanceof Player){
					if(sender.hasPermission("rprotect.useselector")){
						Player player = (Player) sender;
						if(args.length == 1){
							RecursionProtect.setSelectorEnabled(player, !RecursionProtect.hasSelectorEnabled(player));
							if(RecursionProtect.hasSelectorEnabled(player)){
								player.sendMessage(ChatColor.GREEN + "Selector enabled. Use a blaze rod to make selections.");
								if(!player.getInventory().contains(Material.BLAZE_ROD)){
									player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));
								}
								return true;
							}
							player.sendMessage(ChatColor.RED + "Selector disabled.");
							return true;
						}else if(args.length == 2){
							if(args[1].equalsIgnoreCase("on")){
								RecursionProtect.setSelectorEnabled(player, true);
								player.sendMessage(ChatColor.GREEN + "Selector enabled. Use a blaze rod to make selections.");
								if(!player.getInventory().contains(Material.BLAZE_ROD)){
									player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));
								}
								return true;
							}else if(args[1].equalsIgnoreCase("off")){
								RecursionProtect.setSelectorEnabled(player, false);
								player.sendMessage(ChatColor.RED + "Selector disabled.");
								return true;
							}
							sender.sendMessage(ChatColor.RED + "Invalid command usage. Type '/rprotect help' for help.");
							return true;
						}
						sender.sendMessage(ChatColor.RED + "Invalid command usage. Type '/rprotect help' for help.");
						return true;
					}
					sender.sendMessage(ChatColor.RED + "You're not allowed to use the selector.");
					return true;
				}
				sender.sendMessage("Only players can use the selector.");
				return true;
			}else if(args[0].equalsIgnoreCase("createregion")){
				if(sender instanceof Player){
					if(sender.hasPermission("rprotect.regions.create")){
						Player player = (Player) sender;
						if(args.length != 2){
							sender.sendMessage(ChatColor.RED + "Invalid command usage. Type '/rprotect help' for help.");
							return true;
						}
						if(RecursionProtect.getSelection(player) == null){
							player.sendMessage(ChatColor.RED + "You haven't made a selection.");
							return true;
						}
						if(!RecursionProtect.getSelection(player).isComplete()){
							player.sendMessage(ChatColor.RED + "Your selection is incomplete.");
							return true;
						}
						if(RecursionProtect.getRegion(args[1]) != null){
							player.sendMessage(ChatColor.RED + "That region already exists.");
							return true;
						}
						ProtectedRegion region = new ProtectedRegion(args[1], RecursionProtect.getSelection(player), new String[Modifier.values().length]);
						RecursionProtect.getRegions().add(region);
						player.sendMessage(ChatColor.GREEN + "Region created. Changes will take effect immediately, it will be saved to file on next reload or restart.");
						return true;
					}
					sender.sendMessage(ChatColor.RED + "You're not allowed to create regions.");
					return true;
				}
				sender.sendMessage("Only players can create regions.");
				return true;
			}else if(args[0].equalsIgnoreCase("deleteregion")){
				if(sender.hasPermission("rprotect.regions.delete")){
					if(args.length != 2){
						sender.sendMessage(ChatColor.RED + "Invalid command usage. Type '/rprotect help' for help.");
						return true;
					}
					if(RecursionProtect.getRegion(args[1]) == null){
						sender.sendMessage(ChatColor.RED + "That region does not exist.");
						return true;
					}
					plugin.deleteRegion(RecursionProtect.getRegion(args[1]));
					sender.sendMessage(ChatColor.GREEN + "Region deleted.");
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to delete regions.");
				return true;
			}else if(args[0].equalsIgnoreCase("addmod")){
				if(sender.hasPermission("rprotect.regions.addmod")){
					if(args.length != 3){
						sender.sendMessage(ChatColor.RED + "Invalid command usage. Type '/rprotect help' for help.");
						return true;
					}
					if(RecursionProtect.getRegion(args[1]) == null){
						sender.sendMessage(ChatColor.RED + "That region does not exist.");
						return true;
					}
					if(!Modifier.isValid(args[2])){
						sender.sendMessage(ChatColor.RED + "Invalid modifier. Type '/rprotect modifiers' for a list of valid modifiers.");
						return true;
					}
					RecursionProtect.getRegion(args[1]).addModifier(args[2]);
					sender.sendMessage(ChatColor.GREEN + "Modifier added. Changes will take effect immediately, will be saved on next reload or restart.");
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to add modifiers to regions.");
				return true;
			}else if(args[0].equalsIgnoreCase("listregions")){
				if(sender.hasPermission("rprotect.regions.list")){
					if(!RecursionProtect.getRegions().isEmpty()){
						sender.sendMessage(ChatColor.DARK_RED + "Recursion" + ChatColor.DARK_GRAY + "Protect" + ChatColor.GRAY + " regions:");
						for(ProtectedRegion r: RecursionProtect.getRegions()){
							sender.sendMessage(ChatColor.YELLOW + r.getName());
						}
						return true;
					}
					sender.sendMessage(ChatColor.RED + "There are currently no loaded regions.");
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to list regions.");
				return true;
			}else if(args[0].equalsIgnoreCase("rmvmod")){
				if(sender.hasPermission("rprotect.region.removemod")){
					if(args.length != 3){
						sender.sendMessage(ChatColor.RED + "Invalid command usage. Type '/rprotect help' for help.");
						return true;
					}
					if(RecursionProtect.getRegion(args[1]) == null){
						sender.sendMessage(ChatColor.RED + "That region does not exist.");
						return true;
					}
					if(!Modifier.isValid(args[2])){
						sender.sendMessage(ChatColor.RED + "Invalid modifier. Type '/rprotect modifiers' for a list of valid modifiers.");
						return true;
					}
					RecursionProtect.getRegion(args[1]).removeModifier(args[2]);
					sender.sendMessage(ChatColor.GREEN + "Modifier removed. Changes will take effect immediately, will be saved on next reload or restart.");
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to remove modifiers from regions.");
				return true;
			}else if(args[0].equalsIgnoreCase("info")){
				if(sender.hasPermission("rprotect.region.info")){
					if(args.length != 2){
						sender.sendMessage(ChatColor.RED + "Invalid command usage. Type '/rprotect help' for help.");
						return true;
					}
					if(RecursionProtect.getRegion(args[1]) == null){
						sender.sendMessage(ChatColor.RED + "That region does not exist.");
						return true;
					}
					sender.sendMessage(RecursionProtect.getRegion(args[1]).getInfo());
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to view region info.");
				return true;
			}else if(args[0].equalsIgnoreCase("modifiers")){
				if(sender.hasPermission("rprotect.seemods")){
					sender.sendMessage(ChatColor.DARK_RED + "Recursion" + ChatColor.DARK_GRAY + "Protect" + ChatColor.GRAY + " modifiers:");
					for(Modifier m : Modifier.values()){
						sender.sendMessage(ChatColor.GREEN + m.getLabel() + ": " + ChatColor.GRAY + m.getDescription() + "\n");
					}
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to see the RecursionProtect modifiers.");
			}else if(args[0].equalsIgnoreCase("help")){
				if(sender.hasPermission("rprotect.seehelp")){
					sender.sendMessage(ChatColor.DARK_RED + "Recursion" + ChatColor.DARK_GRAY + "Protect" + ChatColor.GRAY + " help:");
					
					sender.sendMessage(ChatColor.GRAY + "Brackets ([]) mark optional command arguments. Arrows (<>) mark required arguments.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect selector [on/off]: " + ChatColor.GRAY + "Toggles the RecursionProtect selector. It is used to select " +
					"regions which can be protected using /rprotect createregion.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect createregion <name>: " + ChatColor.GRAY + "Creates a protected region within your selected area.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect deleteregion <name>: " + ChatColor.GRAY + "Deletes the region with the specified name.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect listregions: " + ChatColor.GRAY + "Lists all protected regions currently loaded.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect addmod <region name> <label>: " + ChatColor.GRAY + "Adds the specified modifier to the specified region. For a list of" +
						"modifiers, type " + ChatColor.GREEN + "/rprotect modifiers.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect rmvmod <region name> <label>: " + ChatColor.GRAY + "Removes the specified modifier from the specified region. For a list of"
					+ "modifiers, type " + ChatColor.GREEN + "/rprotect modifiers.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect info <region name>: " + ChatColor.GRAY + "Shows information about the specified region.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect modifiers: " + ChatColor.GRAY + "Lists all modifiers that can be applied to regions.\n");
					
					sender.sendMessage(ChatColor.GREEN + "/rprotect reload: " + ChatColor.GRAY + "Saves all regions and reloads the plugin's config.");
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to view RecursionProtect help.");
				return true;
			}else if(args[0].equalsIgnoreCase("reload")){
				if(sender.hasPermission("rprotect.reload")){
					if(!RecursionProtect.getRegions().isEmpty()){
						plugin.getLogger().info("Saving regions...");
						for(ProtectedRegion r : RecursionProtect.getRegions()){
							r.save();
							plugin.getLogger().info("Saved region: " + r.getName());
						}
					}
					plugin.reloadConfig();
					sender.sendMessage(ChatColor.GRAY + "Reloaded " + ChatColor.DARK_RED + "Recursion" + ChatColor.DARK_GRAY + "Protect" + ChatColor.GRAY
					+ " v" + plugin.getDescription().getVersion());
					return true;
				}
				sender.sendMessage(ChatColor.RED + "You're not allowed to reload this plugin.");
				return true;
			}
		}
		sender.sendMessage(ChatColor.DARK_RED + "Recursion" + ChatColor.DARK_GRAY + "Protect" + ChatColor.GRAY + " v" + plugin.getDescription().getVersion() + ". Type '/rprotect help' for help.");
		return true;
	}

}
