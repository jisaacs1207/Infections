package io.github.jisaacs1207.infections;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

class Commands implements CommandExecutor, Listener{

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] args) {
		String cmd = cmnd.getName();
		if ((cmd.equalsIgnoreCase("infect"))||(string.equalsIgnoreCase("inf"))||(string.equalsIgnoreCase("infection"))||
		(string.equalsIgnoreCase("infections"))){
			if (args.length == 0) {
				sender.sendMessage(ChatColor.YELLOW + "Infections by jisaacs1207");
				sender.sendMessage(ChatColor.YELLOW + "v.1 2/5/2015");
				sender.sendMessage(ChatColor.YELLOW + "http://fivekingdoms.net");
				sender.sendMessage(ChatColor.YELLOW + "Get infected and become a vampire, werewolf, or avatar!");
			}
			else if (args.length == 1) {
				if(args[0].equalsIgnoreCase("help")){
					sender.sendMessage(ChatColor.GOLD+"        -------------/"+ChatColor.RED+" Infections "+ChatColor.GOLD+"\\--------------");
					sender.sendMessage(ChatColor.RED+"Infections "+ChatColor.WHITE+"have sprung up all over the lands as of late.");
					sender.sendMessage("There is "+ChatColor.GRAY+"Porphyria "+ChatColor.WHITE+"("+ChatColor.DARK_RED+"vampires"+ChatColor.WHITE+"), "
					        +ChatColor.GREEN+"Lycanthropy "+ChatColor.WHITE+"("+ChatColor.DARK_GREEN+"werewolves"+ChatColor.WHITE+"),");
					sender.sendMessage("and even "+ChatColor.YELLOW+"Angeltoxicoma "+ChatColor.WHITE+"("+ChatColor.GOLD+"avatars"+ChatColor.WHITE+")."
							+ " Each infection carries");
					sender.sendMessage("benefits as well as drawbacks for the host. Very little is");
					sender.sendMessage("known about each other than once contracted, they can be");
					sender.sendMessage(""+ChatColor.RED+"exceedingly difficult"+ChatColor.WHITE+" to cure.");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.DARK_PURPLE + "To learn more, '"+ChatColor.LIGHT_PURPLE+"infect help porphyria/lycanthropy"
					        +ChatColor.DARK_PURPLE+"'");
					sender.sendMessage(ChatColor.DARK_PURPLE + "or, '"+ChatColor.LIGHT_PURPLE+"infect help angeltoxicoma/vampire/werewolf/avatar"
					        +ChatColor.DARK_PURPLE+"'");
				}
				else if(args[0].equalsIgnoreCase("enthrall")){
					sender.sendMessage("");
					sender.sendMessage(ChatColor.GRAY + "Be careful about this. You can only have one thrall at a time.");
					sender.sendMessage(ChatColor.GRAY + "Usage is '"+ChatColor.LIGHT_PURPLE+"/infect enthrall <player>"+ChatColor.GRAY+"'.");
					sender.sendMessage("");
				}
			}
			else if (args.length == 2) {
				if(args[0].equalsIgnoreCase("admin")){
					if(sender.isOp()){
						sender.sendMessage(ChatColor.RED+"You're missing something.");
						sender.sendMessage(ChatColor.RED+"Check 'infect help admin'");
					}
					else{
						sender.sendMessage("Unknown command. Type "+ '"' +"help"+'"'+" for help.");
					}
				}
				else if(args[0].equalsIgnoreCase("enthrall")){
					sender.sendMessage("");
					sender.sendMessage(ChatColor.GRAY +"Are you sure you want to make " + args[1] + " your thrall?");
					sender.sendMessage(ChatColor.RED + "If they complain, staff will undo it. You will lose your chance.");
					sender.sendMessage(ChatColor.GRAY + "To confirm, type '"+ChatColor.LIGHT_PURPLE+"/infect enthrall "+args[1]+" confirm"+ChatColor.GRAY+"'");
					sender.sendMessage("");
				}
			}
			else if (args.length == 3) {
				if(args[0].equalsIgnoreCase("admin")){
					if(args[0].equalsIgnoreCase("admin")){
						if(sender.isOp()){
							if(args[1].equalsIgnoreCase("vampire")){
								if(args[2].equalsIgnoreCase("setspawn")){
									Player player = (Player) sender;
									sender.sendMessage("Vampire spawn set.");
									int x = player.getLocation().getBlockX();
									int y = player.getLocation().getBlockY();
									int z = player.getLocation().getBlockZ();
									String world = player.getLocation().getWorld().getName();
									Infections.plugin.getConfig().set("vampirespawn.x", x);
									Infections.plugin.getConfig().set("vampirespawn.y", y);
									Infections.plugin.getConfig().set("vampirespawn.z", z);
									Infections.plugin.getConfig().set("vampirespawn.world", world);
									Infections.plugin.saveConfig();
								}
							}
						}
						else{
							sender.sendMessage("Unknown command. Type "+ '"' +"help"+'"'+" for help.");
						}
					}
				}
				else if(args[0].equalsIgnoreCase("enthrall")){
					if(args[2].equalsIgnoreCase("confirm")){
						sender.sendMessage("");
						sender.sendMessage(ChatColor.RED+ args[1] + " "+ChatColor.GRAY+"is now enthralled to you.");
						sender.sendMessage("");
					}
				}
			}
        }
		/*else if (cmd.equalsIgnoreCase("enthrall")){
			sender.sendMessage("test");
			Player player = (Player) sender;
			List<String> vList = Infections.plugin.getConfig().getStringList("vampires");
			Boolean isVamp = false;
			for (int i = 0; i < vList.size(); i++) {
        		String vampire = Infections.VampireList.get(i);
        		if(player.getName().equalsIgnoreCase(vampire)){
        			isVamp = true;
        		}
			}
			if(isVamp){
				if (args.length == 0) {
					player.sendMessage(ChatColor.GRAY + "Be very careful about this. You can only have one thrall at a time.");
					player.sendMessage(ChatColor.GRAY + "Usage is '"+ChatColor.RED+"/thrall <player>"+ChatColor.GRAY+"'.");
				}
				if (args.length == 1) {
					player.sendMessage(ChatColor.GRAY +"Are you sure you want to make " + args[1] + " your thrall?");
					player.sendMessage(ChatColor.RED + "If they complain, staff will simply undo it and you will lose your chance.");
					player.sendMessage(ChatColor.GRAY + "To confirm, type '"+ChatColor.RED+"/enthrall "+args[1]+" confirm"+ChatColor.GRAY+"'");
				}
				if (args.length == 2) {
					player.sendMessage(ChatColor.GRAY+ args[1] + " is now enthralled to you.");
				}
			}
			else{
				sender.sendMessage("Unknown command. Type "+ '"' +"help"+'"'+" for help.");
			}
		}*/
        return false;
    }
	
}
