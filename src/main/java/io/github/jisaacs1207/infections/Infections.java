package io.github.jisaacs1207.infections;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Infections extends JavaPlugin implements Listener{
	public static Infections plugin;
	public static Boolean day = true;
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
    				Vampires.secondCheck();
    			}
    			else{
    				day = false;
    				Vampires.vampNight();
    				Vampires.secondCheck();
    			}
            }
        }, 0L, 20L);
        VampireList=Infections.plugin.getConfig().getStringList("vampires");
        
	}

	@Override
	public void onDisable() {
		getLogger().info("Unloaded infections.");
		plugin = null;
		VampireList=null;
		day=null;
	}

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    public static boolean day() {
	    Server server = Infections.plugin.getServer();
	    long time = server.getWorld("world").getTime();
	 
	    if(time > 0 && time < 12300) {
	        return true;
	    } else {
	        return false;
	    }
	}
}
