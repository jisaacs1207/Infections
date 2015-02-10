package io.github.jisaacs1207.infections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Vampires implements Listener {
//	String string = Infections.plugin.getConfig().getString("p");
	
	@EventHandler
	// Invisibility on sneak
	public void onSneak(PlayerToggleSneakEvent event) { 
		Player player = event.getPlayer();
		if(!Infections.day){
			for (int i = 0; i < Infections.UndeadList.size(); i++) {
				String vampire = Infections.UndeadList.get(i);	
				if(event.getPlayer().getName().equalsIgnoreCase(vampire)){
					if(!Infections.day){
						if(event.isSneaking()){
							if(!player.isFlying()){
								for (Player players : Bukkit.getOnlinePlayers())
					            {
									player.playEffect(EntityEffect.WOLF_SMOKE);
									player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 0);
									player.setWalkSpeed((float)0.4);
									if(!players.isOp()){
										players.hidePlayer(player);
									}
					            }
							}	
						}
						else{
							if(!player.isFlying()){
								for (Player players : Bukkit.getOnlinePlayers())
					            {   
									players.showPlayer(player);
					                player.playEffect(EntityEffect.WOLF_SMOKE);
					                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 0);
					                player.setWalkSpeed((float)0.2);
					            }
							}
						}
					}
				}
			}
		}
	}
	// Sends a player to the vampire spawn when they right click a painting.
	// Requirement - 10 Food
	// Cost - 4 Food
	@EventHandler
	public void onPlayerUsePainting(PlayerInteractEntityEvent  event){
	    Player player = event.getPlayer();
	    Entity entity = event.getRightClicked();
	    if (!(entity instanceof Painting))
	        return;
	    for (int i = 0; i < Infections.VampireList.size(); i++) {
	    	String vampire = Infections.VampireList.get(i);
	    	if(event.getPlayer().getName().equalsIgnoreCase(vampire)){
	    		if(player.getFoodLevel()>10){
	    			int x = Infections.plugin.getConfig().getInt("vampirespawn.x");
		    	    int y = Infections.plugin.getConfig().getInt("vampirespawn.y");
		    	    int z = Infections.plugin.getConfig().getInt("vampirespawn.z");
		    	    World world = Bukkit.getServer().getWorld(Infections.plugin.getConfig().getString("vampirespawn.world"));
		    	    Location vampireDen = new Location(world, x, y, z);
		        	player.teleport(vampireDen);
		        	player.setFoodLevel(player.getFoodLevel()-4);
	    	    }
	    	}
	    }
	}

	// Seep when looking straight down and right clicking.
    @EventHandler
    public void onPlayerSeep(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        	if(p.isSneaking()){
        		for (int i = 0; i < Infections.VampireList.size(); i++) {
            		String vampire = Infections.VampireList.get(i);
            		if(p.getName().equalsIgnoreCase(vampire)){
            			if((p.getLocation().getPitch()==90)){
                    		if(p.getItemInHand().getType().equals(Material.AIR)){
                        		int x = p.getLocation().getBlockX();
                        		int y = p.getLocation().getBlockY();
                        		int z = p.getLocation().getBlockZ();
                        		String w = p.getLocation().getWorld().getName();
                        		Boolean found = false;
                        		while(found==false){
                        			Location blockLoc= new Location(Bukkit.getWorld(w),x,y,z);
                        			Block block =Infections.plugin.getServer().getWorld(w).getBlockAt(blockLoc);
                        			if(block.getType().equals(Material.AIR)){
                        				int y2=y-1;
                        				int y3=y-2;
                        				Location blockLoc2=new Location(Bukkit.getWorld(w),x,y2,z);
                        				Location blockLoc3=new Location(Bukkit.getWorld(w),x,y3,z);
                        				Location blockLoc4=new Location(Bukkit.getWorld(w),x,y3,z);
                        				Block block2 =Infections.plugin.getServer().getWorld(w).getBlockAt(blockLoc2);
                        				Block block3 =Infections.plugin.getServer().getWorld(w).getBlockAt(blockLoc3);
                        				Block block4 =Infections.plugin.getServer().getWorld(w).getBlockAt(blockLoc4);
                        				if(block2.getType().equals(Material.AIR)){
                    						if(block3.getType().isSolid()){
                    							if(block4.getType().isSolid()){
                    								if(p.getFoodLevel()>10){
                    									p.setFoodLevel(p.getFoodLevel()-4);
                    									p.playEffect(EntityEffect.WOLF_SMOKE);
                                                        p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 10, 15);
                                    					p.teleport(blockLoc);
                                    					found=true;
                                    					p.sendMessage(ChatColor.GRAY+"You've seeped through the ground below you.");
                    								}
                    								else{
                    									found=true;
                                    					p.sendMessage(ChatColor.GRAY+"You're too hungry to do that.");
                    								}
                    							}
                    						}	
                        				}
                        			}
                        			y--;
                        			if(y<2){
                        				p.sendMessage(ChatColor.RED+"Can't seep here!");
                        				found=true;
                        			}
                        		}
                    		}
                    	}
            		}
            	}
        	}
        }
    }
    // Feeds the vampire when he does damage.
    @EventHandler
    public void onVampAttack(final EntityDamageByEntityEvent event) {
    	Entity damager = event.getDamager();
    	if(damager instanceof Player){
    		Player p = (Player) damager;
    		for (int i = 0; i < Infections.VampireList.size(); i++) {
        		String vampire = Infections.VampireList.get(i);
        		if(p.getName().equalsIgnoreCase(vampire)){
        			int pFoodLevel = p.getFoodLevel();
        			if(pFoodLevel!=20){
        				int pNewFoodLevel = pFoodLevel+1;
        				if (pNewFoodLevel>20) pNewFoodLevel=20;
        				p.setFoodLevel(pNewFoodLevel);
            			p.playSound(p.getLocation(), Sound.EAT, 5, 0);
        			}
        		}
    		}
    	}
    }
    // Negates falling damage. Takes food during day, if have food, else takes life.
    @EventHandler
    public void onFallDmg(final EntityDamageEvent event) {
    	if(event.getCause() == DamageCause.FALL){
    		if(event.getEntity() instanceof Player){
    			Player p = (Player) event.getEntity();
	        	for (int i = 0; i < Infections.UndeadList.size(); i++) {
	        		String vampire = Infections.UndeadList.get(i);
	        		if(p.getName().equalsIgnoreCase(vampire)){
	        			if(Infections.day){
	        				int pFood = p.getFoodLevel();
	        				if(pFood>2){
	        					p.setFoodLevel(pFood-2);
	        					p.playSound(p.getLocation(), Sound.HURT, 10, 0);
	        					event.setCancelled(true);
	        				}
	        				else{
	        					int pLife=p.getHealth();
	        					if(pLife>1){
	        						p.setHealth(pLife-1);
	        						p.playSound(p.getLocation(), Sound.HURT, 10, 0);
	        						event.setCancelled(true);
	        					}
	        				}
	        			}
	        			else if(!Infections.day){
	        				event.setCancelled(true);
	        			}
	        		}
	        	}
    		}
    	}
    }
    // Makes sure players can't munch normal munchables. Raises the dead when munch on rotten meat.
    @EventHandler
    public void onPlayerEat(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if((event.getAction().equals(Action.RIGHT_CLICK_AIR))||(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
        	if(p.getItemInHand().getType().isEdible()){
        		for (int i = 0; i < Infections.VampireList.size(); i++) {
            		String vampire = Infections.VampireList.get(i);
            		if(p.getName().equalsIgnoreCase(vampire)){
            			if(p.getItemInHand().getType().equals(Material.ROTTEN_FLESH)){
            				if(p.getFoodLevel()>10){
            					Location loc = p.getLocation();
                				loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                				if(p.getItemInHand().getAmount()>1){
                					ItemStack is = p.getItemInHand();//If I put the setAmount here it would not return an ItemStack
                    				is.setAmount(is.getAmount() - 1);
                    				p.setItemInHand(is);
                    				int pFood=p.getFoodLevel();
                    				pFood=pFood-2;
                    				p.setFoodLevel(pFood);
                    				p.sendMessage(ChatColor.GRAY+"You raise the dead.");
                    				event.setCancelled(true);
                				}
                				else{
                					int pFood=p.getFoodLevel();
                    				pFood=pFood-2;
                    				p.setFoodLevel(pFood);
                					p.setItemInHand(null);
                					p.sendMessage(ChatColor.GRAY+"You raise the dead.");
                					event.setCancelled(true);
                				}
            				}
            				else{
            					p.sendMessage(ChatColor.GRAY+"You're too hungry to do that.");
            					event.setCancelled(true);
            				}
            			}
            			else{
            				p.sendMessage(ChatColor.GRAY+"You can no longer gain sustenance from that.");
                        	p.sendMessage(ChatColor.GRAY+ "Try a health potion... or something"+ChatColor.RED+" fresher"+ChatColor.GRAY+".");
                        	event.setCancelled(true);
            			}
            		}
            	}
        	}
        }	
    }
    // gives life and food for health potions
    @EventHandler
    public void onPlayerPotion(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if((event.getAction().equals(Action.RIGHT_CLICK_AIR))||(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
        	if(p.getItemInHand().getType().equals(Material.POTION)){
        		if((p.getItemInHand().getDurability() == 8197)||(p.getItemInHand().getDurability() == 16341)||
        				(p.getItemInHand().getDurability() == 8197)||(p.getItemInHand().getDurability() == 16373)){
            		for (int i = 0; i < Infections.VampireList.size(); i++) {
                		String vampire = Infections.VampireList.get(i);
                		if(p.getName().equalsIgnoreCase(vampire)){
                			int pExtraHealth=0;
                			if(p.getItemInHand().getDurability() == 8197){
                				pExtraHealth=4;
                			}
                			else{
                				pExtraHealth=8;
                			}
                			int pHealth=p.getHealth();
                			int pFood=p.getFoodLevel();
                			int pNewHealth=pHealth+pExtraHealth;
                			int pNewFood=pFood+8;
                			if(pNewHealth>20) pNewHealth=20;
                			if(pNewFood>20) pNewFood=20;
                			p.setHealth(pNewHealth);
                			p.setFoodLevel(pNewFood);
                			p.playSound(p.getLocation(), Sound.DRINK, 10, 0);
                			p.getPlayer().getInventory().setItemInHand(new ItemStack(Material.GLASS_BOTTLE,1));
                            event.setCancelled(true);
                		}
            		}	
                }
        	}
        }
    }
    // Cancels undead targeting
    @EventHandler
    public void onUndeadTarget(EntityTargetEvent event) {
    	Entity target = event.getTarget();
    	Entity entity = event.getEntity();
    	if(target instanceof Player){
    		if((entity instanceof Zombie)||(entity instanceof Skeleton)||(entity instanceof PigZombie)){
    			for (int i = 0; i < Infections.UndeadList.size(); i++) {
            		String vampire = Infections.UndeadList.get(i);
            		Player p = (Player) event.getTarget();
            		if(p.getName().equalsIgnoreCase(vampire)){
            			event.setCancelled(true);
            		}
    			}
    		}
    	}
    }
    //Sets player back to normal walking and nightvision on quit.
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent  event){
		event.getPlayer().setWalkSpeed(.2F);
		if(event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)){
			event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
		if(event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)){
			event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
    }
  //Sets player back to normal walking and nightvision on join.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent  event){
    	event.getPlayer().setWalkSpeed(.2F);
		if(event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)){
			event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
		if(event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)){
			event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
    }
    //Sets pig and villager kills to zombies. Pigzombies and zombies to skeletons.
    @EventHandler
    public void onKill(EntityDeathEvent event) {
    	Entity died = event.getEntity();
    	Entity killer = event.getEntity().getKiller();
    	if(killer instanceof Player){
    		Player p = (Player) killer;
    		for (int i = 0; i < Infections.UndeadList.size(); i++) {
    			String vampire = Infections.UndeadList.get(i);
				if(p.getName().equalsIgnoreCase(vampire)){
					Location diedAt = died.getLocation();
					if(died instanceof Pig){
						diedAt.getWorld().spawnEntity(diedAt, EntityType.PIG_ZOMBIE);
						event.setDroppedExp(0);
					}
					if(died instanceof Villager){
						diedAt.getWorld().spawnEntity(diedAt, EntityType.ZOMBIE);
						event.setDroppedExp(0);
					}
					if(died instanceof Zombie){
						diedAt.getWorld().spawnEntity(diedAt, EntityType.SKELETON);
						event.setDroppedExp(0);
					}
				}
    		}
    	}
    }
    
    // Cancels undead harm
    @EventHandler
    public void onUndeadDmg(EntityDamageByEntityEvent event) {
    	Entity damager = event.getDamager();
    	Entity victim = event.getEntity();
    	if(victim instanceof Player){
    		Player p = (Player) victim;
    		if((damager instanceof Zombie)||(damager instanceof Skeleton)||(damager instanceof PigZombie)){
    			for (int i = 0; i < Infections.UndeadList.size(); i++) {
        			String vampire = Infections.UndeadList.get(i);
    				if(p.getName().equalsIgnoreCase(vampire)){
    					event.setCancelled(true);
    				}
    			}
    		}
    	}
    }
    
    // Runs once a second.
    public static void secondCheck(){
    	for (int i = 0; i < Infections.UndeadList.size(); i++) {
			String vampire = Infections.UndeadList.get(i);
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(vampire)){
					for (int t = 0; t < Infections.ThrallList.size(); t++) {
						String thrall = Infections.ThrallList.get(t);
						if(p.getName().equalsIgnoreCase(thrall)){
							Player thrallie= (Player) p;
							String vampFriend = Infections.plugin.getConfig().getString("thralls."+p.getName());
							for(Player friend : Bukkit.getOnlinePlayers()){
								if(friend.getName().equalsIgnoreCase(vampFriend)){
									if(friend.getLocation().distance(p.getLocation())<25){
										if(Infections.randInt(1, 20)==10){
											int friendFood=friend.getFoodLevel()+1;
											int friendHealth=friend.getHealth()+1;
											int thrallFood=thrallie.getFoodLevel()+1;
											int thrallHealth=thrallie.getHealth()+1;
											if(friendFood>20) friendFood=20;
											if(friendHealth>20) friendHealth=20;
											if(thrallFood>20) thrallFood=20;
											if(thrallHealth>20) thrallHealth=20;
											friend.setFoodLevel(friendFood);
											friend.setHealth(friendHealth);
											thrallie.setFoodLevel(thrallFood);
											thrallie.setHealth(thrallHealth);
										}
									}
								}
							}
						}
					}
					if(p.getLocation().getBlock().getLightLevel()<8){
						if(p.getFoodLevel()>10){
							p.removePotionEffect(PotionEffectType.NIGHT_VISION);
							p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,600,1));
						}
						else{
							p.removePotionEffect(PotionEffectType.NIGHT_VISION);
						}
					}
					if(p.getLocation().getBlock().getLightLevel()>7){
						p.removePotionEffect(PotionEffectType.NIGHT_VISION);
					}
				}
			}
    	}
    }
    // Runs once a second from the onEnable. sets walk speed
	public static void vampNight(){
		for (int i = 0; i < Infections.UndeadList.size(); i++) {
			String vampire = Infections.UndeadList.get(i);
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(vampire)){
					if(p.getFoodLevel()>10){
						p.setWalkSpeed((float).4);
					}
					else{
						p.setWalkSpeed((float).2);
					}
				}
				if(p.hasPotionEffect(PotionEffectType.JUMP)){
					p.removePotionEffect(PotionEffectType.JUMP);
				}
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,600,12));
			}
		}
	}
	// Runs once a second from the onEnable schedule during the day. Sets walk speed and removes food if vamp.
	public static void vampDay(){
		boolean pThrall=false;
		for (int i = 0; i < Infections.UndeadList.size(); i++) {
			String vampire = Infections.UndeadList.get(i);
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(vampire)){
					for (int t = 0; t < Infections.ThrallList.size(); t++) {
						String thrall = Infections.ThrallList.get(t);
						if(p.getName().equalsIgnoreCase(thrall)){
							pThrall=true;
						}
					}
					p.setWalkSpeed((float).2);
					if(p.getLocation().getBlock().getLightLevel()==15){
						if(Infections.randInt(1, 18)==10){
							int pFoodLevel=p.getFoodLevel();
							if(!pThrall){
								if(pFoodLevel>2){
									int newFoodLevel = pFoodLevel-1;
									p.setFoodLevel(newFoodLevel);
								}
							}
						}
					}	
				}
			}
		}
	}
}
