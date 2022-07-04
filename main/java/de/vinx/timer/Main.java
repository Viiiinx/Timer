package de.vinx.timer;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.vinx.timer.commands.TimerCommand;
import de.vinx.timer.commands.TimerCommandCompleter;
import de.vinx.timer.gui.TimerGui;
import de.vinx.timer.timer.Timer;

public class Main extends JavaPlugin {

	static int taskId;

	private static Main instance;

	private static File timerFile;

	private static String prefix;

	private static ArrayList<String> messages;

	private String bukkitVersion = "1.8.8-R0.1-SNAPSHOT";
	private String download = "https://cdn.getbukkit.org/craftbukkit/craftbukkit-1.8.8-R0.1-SNAPSHOT-latest.jar";

	@Override
	public void onEnable() {
		//ChatColor.translateAlternateColorCodes('&',"");
		instance = this;
		loadTimer();
		getCommand("timer").setExecutor(new TimerCommand());
		getCommand("timer").setTabCompleter(new TimerCommandCompleter());
		startIdle();
//		TimerGui.loadGuiItems();
		Bukkit.getPluginManager().registerEvents(new TimerGui(), this);
		Bukkit.getConsoleSender()
				.sendMessage( ChatColor.GOLD + getDescription().getName() + " Version " + getDescription().getVersion());
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.RED + "Test");
	}

	@Override
	public void onDisable() {
		Timer.saveTimer();
	}

	private void loadTimer() {
		timerFile = new File(getDataFolder(), "timer.yml");
		if (!timerFile.exists())
			saveResource("timer.yml", true);

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		Timer.loadTimer();
		
		//prefix = getConfig().getString("prefix").replace("§", "�") + " �r";
		prefix = getConfig().getString("prefix").replace("§", " ") + " r";


		messages = new ArrayList<String>();

		messages.add(getConfig().getString("resume").replace("§", " "));        	//0
		messages.add(getConfig().getString("pause").replace("§", " "));         	 	//1
		messages.add(getConfig().getString("reset").replace("§", " "));				//2
		messages.add(getConfig().getString("running").replace("§", " "));			//3
		messages.add(getConfig().getString("not_running").replace("§", " "));		//4
		messages.add(getConfig().getString("actionbar").replace("§", " "));		//5
		messages.add(getConfig().getString("paused").replace("§", " "));			//6
		messages.add(getConfig().getString("over").replace("§", " "));				//7
		messages.add(getConfig().getString("remain_time").replace("§", " "));	//8
		messages.add(getConfig().getString("reverse").replace("§", " "));			//9
		messages.add(getConfig().getString("unreverse").replace("§", " "));		//10
		messages.add(getConfig().getString("set").replace("§", " "));					//11
		messages.add(getConfig().getString("get").replace("§", " "));					//12

	}

	public static String getPrefix() {
		return prefix;
	}

	public static ArrayList<String> getMessages() {
		return messages;
	}

	public static Main getInstance() {
		return instance;
	}

	public static File getTimerFile() {
		return timerFile;
	}

	@SuppressWarnings("deprecation")
	public static void startIdle() {

		taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(instance, new Runnable() {

			@Override
			public void run() {
//				ItemBuilder.setDisplayname(TimerGui.time, messages.get(6));
//				TimerGui.timerInv.setItem(4, TimerGui.time);
//				TimerGui.timerInv2.setItem(4, TimerGui.time);
				for (Player p : Bukkit.getOnlinePlayers())
					Timer.sendActionbar(p, messages.get(6));
			}
		}, 0, 40);

	}

	public static void stopIdle() {
		Bukkit.getScheduler().cancelTask(taskId);
	}

}
