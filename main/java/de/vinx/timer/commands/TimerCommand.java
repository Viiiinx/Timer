package de.vinx.timer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.vinx.timer.Main;
import de.vinx.timer.timer.Timer;

public class TimerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		//String syntax = Main.getPrefix() + "�cBitte nutze /timer <resume/pause/reset/set/get/reverse>";
		String syntax = Main.getPrefix() + ChatColor.RED +  "Bitte nutze /timer <resume/pause/reset/set/get/reverse>";

		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(Main.getPrefix()+ ChatColor.RED + "Beispieltext");
			} else sender.sendMessage(syntax);

			return true;
		}

		if (args[0].equalsIgnoreCase("resume")) {
			if (Timer.isRunning) {
				sender.sendMessage(Main.getPrefix() + Main.getMessages().get(3));
				return true;
			}
			if (!Timer.isReverse()) {
				Timer.resumeTimer();
			} else {
				Timer.resumeReverseTimer(Timer.days, Timer.hours, Timer.minutes, Timer.seconds);
			}
			sender.sendMessage(Main.getPrefix() + Main.getMessages().get(0));
			return true;
		}

		if (args[0].equalsIgnoreCase("pause")) {
			if (!Timer.isRunning) {
				sender.sendMessage(Main.getPrefix() + Main.getMessages().get(4));
				return true;
			}
			Timer.stopTimer();
			sender.sendMessage(Main.getPrefix() + Main.getMessages().get(1));
			return true;
		}

		if (args[0].equalsIgnoreCase("reset")) {
			Timer.resetTimer();
			sender.sendMessage(Main.getPrefix() + Main.getMessages().get(2));
			return true;
		}

		if (args[0].equalsIgnoreCase("reverse")) {
			if (Timer.isReverse()) {
				sender.sendMessage(Main.getPrefix() + Main.getMessages().get(10));
				if (Timer.isRunning) {
					Timer.stopTimer();
					Timer.resumeTimer();
				}
				Timer.setReverse(false);
			} else {
				sender.sendMessage(Main.getPrefix() + Main.getMessages().get(9));
				Timer.setReverse(true);
				if (Timer.isRunning) {
					Timer.stopTimer();
					Timer.resumeReverseTimer(Timer.days, Timer.hours, Timer.minutes, Timer.seconds);
				}
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("set")) {
			if (args.length == 1) {
				sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Bitte nutze /timer set <Tage> <Stunden> <Minuten> <Sekunden>");
				return true;
			}

			int days;
			int hours;
			int minutes;
			int seconds;

			try {

				days = Integer.parseInt(args[1]);
				hours = Integer.parseInt(args[2]);
				minutes = Integer.parseInt(args[3]);
				seconds = Integer.parseInt(args[4]);

			} catch (Exception e) {
				sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Bitte gebe gültige Zahlen ein!");
				return true;
			}

			Timer.days = days;
			Timer.hours = hours;
			Timer.minutes = minutes;
			Timer.seconds = seconds;
			Timer.formatTime(days, hours, minutes, seconds);
			Timer.saveTimer();

			sender.sendMessage(Main.getPrefix() + Main.getMessages().get(11).replace("%t%", Timer.getTimerAsString()));
			return true;

		}

		if (args[0].equalsIgnoreCase("get")) {
			sender.sendMessage(Main.getPrefix() + Main.getMessages().get(12).replace("%t%", Timer.getTimerAsString()));
			return true;
		}

		if (args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "/timer resume" + ChatColor.GREEN
					+ "�aTimer fortsetzen");
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "/timer pause" + ChatColor.GREEN
					+ "Timer pausieren");
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "/timer reset"+ ChatColor.GREEN
					+ "Timer zurücksetzen");
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "/timer set <Zeit>" + ChatColor.GREEN
					+ "Zeit des Timers einstellen");
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "timer get" + ChatColor.GREEN
					+ "Gibt die Zeit des Timers aus");
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "/timer reverse" + ChatColor.GREEN
					+ "Timer umkehren");
			return true;
		}

		sender.sendMessage(syntax);

		return true;
	}

}
