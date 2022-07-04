package de.vinx.timer.timer;

import de.vinx.timer.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Timer {

	static int taskId;
	public static boolean isRunning = false;
	public static boolean reverse;

	public static int days;
	public static int hours;
	public static int minutes;
	public static int seconds;

	public static void loadTimer() {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Main.getTimerFile());

		days = cfg.getInt("days");
		hours = cfg.getInt("hours");
		minutes = cfg.getInt("minutes");
		seconds = cfg.getInt("seconds");
		reverse = cfg.getBoolean("reverse");
	}

	@SuppressWarnings("deprecation")
	public static void resumeTimer() {
		if (isRunning) {
			return;
		}

		isRunning = true;
		Main.stopIdle();
		taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				seconds++;

				// Update Minutes
				if (seconds == 60) {
					seconds = 0;
					minutes++;
				}

				// Update Hours
				if (minutes == 60) {
					minutes = 0;
					hours++;
				}

				// Update days
				if (hours == 24) {
					hours = 0;
					days++;
				}

				sendTimer();

			}
		}, 0, 20);

	}

	public static void stopTimer() {
		if (!isRunning) {
			return;
		}
		Main.startIdle();
		Bukkit.getScheduler().cancelTask(taskId);
		isRunning = false;

		saveTimer();
	}

	public static void resetTimer() {

		if (isRunning) {
			stopTimer();
			isRunning = false;
		}

		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Main.getTimerFile());
		days = 0;
		hours = 0;
		minutes = 0;
		seconds = 0;

		cfg.set("days", days);
		cfg.set("hours", hours);
		cfg.set("minutes", minutes);
		cfg.set("seconds", seconds);

		try {
			cfg.save(Main.getTimerFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void saveTimer() {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Main.getTimerFile());
		cfg.set("days", days);
		cfg.set("hours", hours);
		cfg.set("minutes", minutes);
		cfg.set("seconds", seconds);

		try {
			cfg.save(Main.getTimerFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void resumeReverseTimer(int days, int hours, int minutes, int seconds) {
		if (isRunning) {
			return;
		}

		isRunning = true;

		Main.stopIdle();

		formatTime(days, hours, minutes, seconds);

//		sender.sendMessage(Main.getPrefix() + "�aZeit: �6" + Timer.days + " �aTage, �6" + hours + " �aStunden, �6"
//				+ minutes + " �aMinuten, �6" + seconds + " �aSekunden");

		taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {

				if (Timer.seconds <= 1) {
					if (Timer.minutes > -1) {
						Timer.seconds = 60;
						Timer.minutes--;
					}
				}

				if (Timer.minutes == -1) {
					if (Timer.hours > -1) {
						Timer.minutes = 59;
						Timer.hours--;
					}
				}

				if (Timer.hours == -1) {
					if (Timer.days > -1) {
						Timer.hours = 23;
						Timer.days--;
					}
				}

				if (Timer.days < 0) {
					Bukkit.broadcastMessage(Main.getPrefix() + Main.getMessages().get(7));
					resetTimer();
					return;
				} else
					Timer.seconds--;

				sendTimer();

			}
		}, 0, 20);
	}

	public static void sendActionbar(Player player, String text) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
	}

	private static void sendTimer() {
		String daysString = "" + Timer.days;
		String hoursString = "" + Timer.hours;
		String minutesString = "" + Timer.minutes;
		String secondsString = "" + Timer.seconds;

		if (Timer.seconds < 10)
			secondsString = "0" + Timer.seconds;
		if (Timer.minutes < 10)
			minutesString = "0" + Timer.minutes;
		if (Timer.hours < 10)
			hoursString = "0" + Timer.hours;
		if (Timer.days < 10)
			daysString = "0" + Timer.days;

		String timer = Main.getMessages().get(5).replace("%d%", daysString).replace("%h%", hoursString)
				.replace("%m%", minutesString).replace("%s%", secondsString);

		for (Player p : Bukkit.getOnlinePlayers())
			sendActionbar(p, timer);

//		ItemBuilder.setDisplayname(TimerGui.time, timer);
//		TimerGui.timerInv.setItem(4, TimerGui.time);
//		TimerGui.timerInv2.setItem(4, TimerGui.time);
	}

	public static String getTimerAsString() {
//		String daysString = "" + Timer.days;
		String hoursString = "" + Timer.hours;
		String minutesString = "" + Timer.minutes;
		String secondsString = "" + Timer.seconds;

		if (Timer.seconds < 10)
			secondsString = "0" + Timer.seconds;
		if (Timer.minutes < 10)
			minutesString = "0" + Timer.minutes;
		if (Timer.hours < 10)
			hoursString = "0" + Timer.hours;
//		if (Timer.days < 10)
//			daysString = "0" + Timer.days;

		return hoursString + ":" + minutesString + ":" + secondsString;
	}

	public static void formatTime(int days, int hours, int minutes, int seconds) {
		Timer.days = days;
		Timer.hours = hours;
		Timer.minutes = minutes;
		Timer.seconds = seconds;

		// Berechung der Zeit

		if (Timer.seconds / 60 >= 1) {
			Timer.minutes = Timer.seconds / 60;
			Timer.seconds = Timer.seconds % 60;
		}

		if (Timer.minutes / 60 >= 1) {
			Timer.hours = Timer.minutes / 60;
			Timer.minutes = Timer.minutes % 60;
		}

		if (Timer.hours / 24 >= 1) {
			Timer.days = Timer.hours / 24;
			Timer.hours = Timer.hours % 24;
		}
	}

	public static boolean isReverse() {
		return reverse;
	}

	public static void setReverse(boolean reverse) {
		Timer.reverse = reverse;
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Main.getTimerFile());
		cfg.set("reverse", reverse);
		try {
			cfg.save(Main.getTimerFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
