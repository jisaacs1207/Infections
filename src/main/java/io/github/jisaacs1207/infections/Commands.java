package io.github.jisaacs1207.infections;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

class Commands implements CommandExecutor, Listener{

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] args) {
		if (string.equalsIgnoreCase("infect")) {
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
			}
        }
        return false;
    }
	
}
