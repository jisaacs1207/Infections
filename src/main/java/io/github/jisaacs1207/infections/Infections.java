package io.github.jisaacs1207.infections;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Infections extends JavaPlugin implements Listener{
	public static Infections plugin;
	public static boolean day = true;
	public static List<String> VampireList;
	@Override
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		getLogger().info("Infections now loaded.");
		registerEvents(this, new Vampires(), new Werewolves(), new Avatars(), new Commands());
		getCommand("infect").setExecutor(new Commands());
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	if(day()){
    				day = true;
    				Vampires.vampDay();
    			}
    			else{
    				day = false;
    				Vampires.vampNight();
    			}
            }
        }, 0L, 200L);
        VampireList=Infections.plugin.getConfig().getStringList("vampires");
        
	}

	@Override
	public void onDisable() {
		getLogger().info("Unloaded infections.");
		plugin = null;
	}

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    
    public boolean day() {
	    Server server = Infections.plugin.getServer();
	    long time = server.getWorld("world").getTime();
	 
	    if(time > 0 && time < 12300) {
	        return true;
	    } else {
	        return false;
	    }
	}
}
