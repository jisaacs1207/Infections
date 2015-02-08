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
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

public class Vampires implements Listener {
//	String string = Infections.plugin.getConfig().getString("p");
	public boolean day = true;
	@EventHandler
	// Invisibility on sneak
	public void onSneak(PlayerToggleSneakEvent event) { 
		Player player = event.getPlayer();
		if(!Infections.day){
			for (int i = 0; i < Infections.VampireList.size(); i++) {
				String vampire = Infections.VampireList.get(i);
				if(event.getPlayer().getName().equalsIgnoreCase(vampire)){
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
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEntityEvent  event){
	    Player player = event.getPlayer();
	    Entity entity = event.getRightClicked();
	    if (!(entity instanceof Painting))
	        return;
	    for (int i = 0; i < Infections.VampireList.size(); i++) {
	    	String vampire = Infections.VampireList.get(i);
	    	if(event.getPlayer().getName().equalsIgnoreCase(vampire)){
	    	    int x = Infections.plugin.getConfig().getInt("vampirespawn.x");
	    	    int y = Infections.plugin.getConfig().getInt("vampirespawn.y");
	    	    int z = Infections.plugin.getConfig().getInt("vampirespawn.z");
	    	    World world = Bukkit.getServer().getWorld(Infections.plugin.getConfig().getString("vampirespawn.world"));
	    	    Location vampireDen = new Location(world, x, y, z);
	        	player.teleport(vampireDen);
	    	}
	    }
	}
	
	@EventHandler
	public void onJump(PlayerMoveEvent event) {
		if(!Infections.day){
			for (int i = 0; i < Infections.VampireList.size(); i++) {
				String vampire = Infections.VampireList.get(i);
				if(event.getPlayer().getName().equalsIgnoreCase(vampire)){
					Block block, control;
					Vector dir = event.getPlayer().getVelocity().setY(1.25);
					if(event.getTo().getY() > event.getFrom().getY())
					{
						block = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getTo().getX(), event.getTo().getY()+2, event.getTo().getZ()));
						control = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getTo().getX(), event.getTo().getY()-2, event.getTo().getZ()));
					    if(!(block.getTypeId() != 0 || control.getTypeId() == 0))
						{
							event.getPlayer().setVelocity(dir);
						}
					}
				}
			}
		}
	}
	
    @EventHandler
    public void onPlayerSeep(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
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
                    				Block block2 =Infections.plugin.getServer().getWorld(w).getBlockAt(blockLoc2);
                    				Block block3 =Infections.plugin.getServer().getWorld(w).getBlockAt(blockLoc3);
                    				if(block2.getType().equals(Material.AIR)){
                    					if(!block3.getType().equals(Material.AIR)){
                    						if(!block3.getType().equals(Material.LAVA)){
                    							p.playEffect(EntityEffect.WOLF_SMOKE);
                                                p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 10, 0);
                            					p.teleport(blockLoc);
                            					found=true;
                            					p.sendMessage(ChatColor.GRAY+"You've seeped through the ground below you.");
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
	
	public static void vampNight(){
		for (int i = 0; i < Infections.VampireList.size(); i++) {
			String vampire = Infections.VampireList.get(i);
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(vampire)){
					p.setWalkSpeed((float).4);
				}
			}
		}
	}
	
	public static void vampDay(){
		for (int i = 0; i < Infections.VampireList.size(); i++) {
			String vampire = Infections.VampireList.get(i);
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getName().equalsIgnoreCase(vampire)){
					p.setWalkSpeed((float).2);
				}
			}
		}
	}
}
