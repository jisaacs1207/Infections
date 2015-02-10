package io.github.jisaacs1207.infections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

class Commands implements CommandExecutor, Listener{

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] args) {
		String cmd = cmnd.getName();
		if (cmd.equalsIgnoreCase("dethrall")){
			Player player = (Player) sender;
			Boolean isVamp = false;
			Boolean hasThrall=false;
			String thrall=null;
			for (int i = 0; i < Infections.VampireList.size(); i++) {
        		String vampire = Infections.VampireList.get(i);
        		if(player.getName().equalsIgnoreCase(vampire)){
        			isVamp = true;
        		}
			}
			for(String thrallCheck:Infections.plugin.getConfig().getConfigurationSection("thralls").getKeys(false)){
				String thraller = Infections.plugin.getConfig().getString("thralls."+thrallCheck);
				if(thraller.equalsIgnoreCase(sender.getName())){
					thrall = thrallCheck;
					hasThrall=true;
				}
			}
			if(isVamp){
				if(hasThrall){
					if (args.length == 0) {
						sender.sendMessage("");
						sender.sendMessage(ChatColor.GRAY + "You will release your thrall with this command.");
						sender.sendMessage(ChatColor.GRAY + "To confirm, type '"+ChatColor.LIGHT_PURPLE+"dethrall confirm"+ChatColor.GRAY+"'");
						sender.sendMessage("");
					}
					if (args.length == 1) {
						sender.sendMessage(ChatColor.GRAY + "You've released your thrall from servitude.");
						Infections.plugin.getConfig().set("thralls."+thrall.toLowerCase(), null);
						Infections.plugin.saveConfig();
						Infections.ThrallList.clear();
						Infections.UndeadList.clear();
						for(String thrallListing:Infections.plugin.getConfig().getConfigurationSection("thralls").getKeys(false)){
				        	Infections.ThrallList.add(thrallListing);
				        }
				        Infections.UndeadList.addAll(Infections.ThrallList);
				        Infections.UndeadList.addAll(Infections.VampireList);
				        for(Player players : Infections.plugin.getServer().getOnlinePlayers()) {
							if(thrall.equalsIgnoreCase(players.getName())){
								players.sendMessage(ChatColor.GRAY+"You feel more alive, yet less close to "+sender.getName()+".");
							}
				        }
					}
				}
				else{
					sender.sendMessage(ChatColor.GRAY + "You don't currently have a thrall.");
				}
			}
			else{
				sender.sendMessage("Unknown command. Type "+ '"' +"help"+'"'+" for help.");
			}
		}
		else if (cmd.equalsIgnoreCase("enthrall")){
			Player player = (Player) sender;
			Boolean isVamp = false;
			for (int i = 0; i < Infections.VampireList.size(); i++) {
        		String vampire = Infections.VampireList.get(i);
        		if(player.getName().equalsIgnoreCase(vampire)){
        			isVamp = true;
        		}
			}
			if(isVamp==true){
				if (args.length == 0) {
					sender.sendMessage("");
					sender.sendMessage(ChatColor.GRAY + "Be careful about this. You can only have one thrall at a time.");
					sender.sendMessage(ChatColor.GRAY + "Usage is '"+ChatColor.LIGHT_PURPLE+"/enthrall <player>"+ChatColor.GRAY+"'.");
					sender.sendMessage("");
				}
				else if (args.length == 1) {
					sender.sendMessage("");
					sender.sendMessage(ChatColor.GRAY +"Are you sure you want to make " + args[0] + " your thrall?");
					sender.sendMessage(ChatColor.RED + "If they complain, staff will undo it. You will lose your chance.");
					sender.sendMessage(ChatColor.GRAY + "To confirm, type '"+ChatColor.LIGHT_PURPLE+"enthrall "+args[0]+" confirm"+ChatColor.GRAY+"'");
					sender.sendMessage("");
				}
				else if (args.length == 2) {
					if(args[1].equalsIgnoreCase("confirm")){
						Boolean isOnline = false;
						Boolean alreadyUndead = false;
						Boolean alreadyHaveThrall=false;
						Player thrall = null;
						for(Player players : Infections.plugin.getServer().getOnlinePlayers()) {
							if(args[0].equalsIgnoreCase(players.getName())){
								isOnline = true;
								thrall = players.getPlayer();
								for (int i = 0; i < Infections.UndeadList.size(); i++) {
									String vampire = Infections.UndeadList.get(i);
									if(players.getName().equalsIgnoreCase(vampire)){
										alreadyUndead= true;
										sender.sendMessage(ChatColor.GRAY+"That player is already among the unliving.");
									}
								}
							}
						}
						if(isOnline){
							for(String thrallCountCheck:Infections.plugin.getConfig().getConfigurationSection("thralls").getKeys(false)){
								String thraller = Infections.plugin.getConfig().getString("thralls."+thrallCountCheck);
								if(thraller.equalsIgnoreCase(sender.getName())){
									sender.sendMessage(ChatColor.GRAY+"You've already got a thrall named "+thrallCountCheck+".");
									sender.sendMessage(ChatColor.GRAY+"You must type '"+ChatColor.LIGHT_PURPLE+"/dethrall"+ChatColor.GRAY+
											"' to continue!");
									alreadyHaveThrall=true;
								}
							}
							if((!alreadyUndead)&&(!alreadyHaveThrall)){
								if(thrall.getLocation().distance(player.getLocation())<5){
									sender.sendMessage("");
									sender.sendMessage(ChatColor.GRAY+"You draw "+ChatColor.RED+"blood"+ChatColor.GRAY+" and force it on " +thrall.getName() + ".");
									sender.sendMessage(ChatColor.RED+ args[0] + " "+ChatColor.GRAY+"is now enthralled to you.");
									Infections.plugin.getConfig().set("thralls."+thrall.getName().toLowerCase(), player.getName().toLowerCase());
									Infections.plugin.saveConfig();
									Infections.ThrallList.add(thrall.getName().toLowerCase());
									Infections.UndeadList.add(thrall.getName().toLowerCase());
									sender.sendMessage("");	
									thrall.sendMessage(ChatColor.GRAY+sender.getName()+" forces some of their "+ChatColor.RED+"blood"+ChatColor.GRAY+" in to your mouth.");
									thrall.sendMessage(ChatColor.GRAY+"You feel changed. It is not an overly unpleasant feeling.");
								}
								else{
									sender.sendMessage(ChatColor.GRAY+"You need to be closer to them to share your "+ChatColor.RED+"blood"+ChatColor.GRAY+".");
								}
							}
						}
						else{
							sender.sendMessage(ChatColor.GRAY+"That player is not online for you to enthrall!");
						}
					}
				}
			}
			else{
				sender.sendMessage("Unknown command. Type "+ '"' +"help"+'"'+" for help.");
			}
		}
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
			else if (args.length == 4) {
				if(args[0].equalsIgnoreCase("admin")){
					if(sender.isOp()){
						if(args[1].equalsIgnoreCase("vampire")){
							if(args[2].equalsIgnoreCase("strip")){
								Boolean isVamp=false;
								for(String nameTry:Infections.VampireList){
									if(nameTry.equalsIgnoreCase(args[3])){
										isVamp=true;
									}
								}
								if(isVamp){
									Boolean hasThrall=false;
									String thrall=null;
									Infections.plugin.getConfig().set("vampires."+args[3].toLowerCase(), null);
									Infections.plugin.saveConfig();
									Infections.VampireList.clear();
									Infections.VampireList=Infections.plugin.getConfig().getStringList("vampires");
									Infections.UndeadList.clear();
							        Infections.UndeadList.addAll(Infections.ThrallList);
							        Infections.UndeadList.addAll(Infections.VampireList);
									sender.sendMessage("You've swatted the mosquito.");
									for(String thrallCheck:Infections.plugin.getConfig().getConfigurationSection("thralls").getKeys(false)){
										String thraller = Infections.plugin.getConfig().getString("thralls."+thrallCheck);
										if(thraller.equalsIgnoreCase(args[3].toLowerCase())){
											thrall = thrallCheck.toLowerCase();
											hasThrall=true;
										}
									}
									if(hasThrall){
										Infections.plugin.getConfig().set("thralls."+thrall.toLowerCase(), null);
										Infections.plugin.saveConfig();
										Infections.ThrallList.clear();
										Infections.UndeadList.clear();
										for(String thrallListing:Infections.plugin.getConfig().getConfigurationSection("thralls").getKeys(false)){
								        	Infections.ThrallList.add(thrallListing);
								        }
								        Infections.UndeadList.addAll(Infections.ThrallList);
								        Infections.UndeadList.addAll(Infections.VampireList);
								        for(Player players : Infections.plugin.getServer().getOnlinePlayers()) {
											if(thrall.equalsIgnoreCase(players.getName())){
												players.sendMessage(ChatColor.GRAY+"You feel your bond to undeath cease to exist.");
											}
								        }
									}
									for(Player p:Bukkit.getOnlinePlayers()){
										if(p.getName().equalsIgnoreCase(args[3])){
											p.sendMessage(ChatColor.GRAY+"Your skin grows warmer. You've been cured!");
											p.setWalkSpeed(.2F);
											if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
												p.removePotionEffect(PotionEffectType.NIGHT_VISION);
											}
										}
									}
								}
								else{
									sender.sendMessage("They aren't a vampire or you typed the name in wrong.");
								}
							}
							if(args[2].equalsIgnoreCase("grant")){
								boolean online=false;
								boolean isThrall=false;
								boolean isVamp=false;
								String thrall=null;
								String thraller=null;
								for(Player p : Bukkit.getOnlinePlayers()){
									if(args[3].equalsIgnoreCase(p.getName())){
										online=true;
										for (int i = 0; i < Infections.VampireList.size(); i++) {
							        		String vampire = Infections.VampireList.get(i);
							        		if(p.getName().equalsIgnoreCase(vampire)){
							        			sender.sendMessage("They are already a vampire.");
							        			isVamp=true;
							        		}
										}
										for(String thrallCheck:Infections.plugin.getConfig().getConfigurationSection("thralls").getKeys(false)){
											if(thrallCheck.equalsIgnoreCase(p.getName())){
												thraller = Infections.plugin.getConfig().getString("thralls."+thrallCheck);
												thrall = thrallCheck;
												isThrall=true;
											}
										}
										if(!isVamp){
											p.sendMessage("");
											p.sendMessage(ChatColor.RED+sender.getName()+ChatColor.GRAY+" appears and nicks your neck with a scalpel.");
											p.sendMessage(ChatColor.GRAY+"Losing blood, you drink a health potion they offer you!");
											p.sendMessage(ChatColor.GRAY+"Instantly, your wounds close. You feel cold and powerful.");
											p.sendMessage(ChatColor.GRAY+"You have contracted "+ChatColor.RED+"Porphyria"+ChatColor.GRAY+"!");
											sender.sendMessage("Oh good... You've created a new mosquito.");
											if(isThrall){
												Infections.plugin.getConfig().set("thralls."+thrall, null);
												Infections.ThrallList.clear();
												for(String thrallListing:Infections.plugin.getConfig().getConfigurationSection("thralls").getKeys(false)){
										        	Infections.ThrallList.add(thrallListing);
										        }
												p.sendMessage(ChatColor.GRAY+"You feel more powerful but less close to "+thraller+".");
											}
											p.sendMessage(ChatColor.GRAY+"Consult '"+ChatColor.LIGHT_PURPLE+"/help infect"+ChatColor.GRAY+"' for more details.");
											p.sendMessage("");
											Infections.VampireList.add(p.getName().toLowerCase());
											Infections.plugin.getConfig().set("vampires", Infections.VampireList);
											Infections.plugin.saveConfig();
											Infections.UndeadList.clear();
									        Infections.UndeadList.addAll(Infections.ThrallList);
									        Infections.UndeadList.addAll(Infections.VampireList);
										}
									}	
								}
								if(!online){
									sender.sendMessage("They are not online right now.");
								}
							}
						}
					}
					else{
						sender.sendMessage("Unknown command. Type "+ '"' +"help"+'"'+" for help.");
					}
				}
			}
        }
        return false;
    }
	
}
