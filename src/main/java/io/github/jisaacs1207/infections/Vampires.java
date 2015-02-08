package io.github.jisaacs1207.infections;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEntityEvent  event){
	    Player player = event.getPlayer();
	    Entity entity = event.getRightClicked();
	    if (!(entity instanceof Painting))
	        return;
	    int x = Infections.plugin.getConfig().getInt("vampirespawn.x");
	    int y = Infections.plugin.getConfig().getInt("vampirespawn.y");
	    int z = Infections.plugin.getConfig().getInt("vampirespawn.z");
	    World world = Bukkit.getServer().getWorld(Infections.plugin.getConfig().getString("vampirespawn.world"));
	    Location vampireDen = new Location(world, x, y, z);
    	player.teleport(vampireDen);
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
