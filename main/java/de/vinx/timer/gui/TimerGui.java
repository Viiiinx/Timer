package de.vinx.timer.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.vinx.timer.Main;
import de.vinx.timer.timer.Timer;
import de.vinx.timer.items.ItemBuilder;

public class TimerGui implements Listener {

    static ItemStack placeholder;

    public static Inventory timerInv;

    public static ItemStack time;
    public static ItemStack resume;
    public static ItemStack resume1;
    public static ItemStack stop;
    public static ItemStack stop1;
    public static ItemStack reset;
    public static ItemStack openInv2;

    public static Inventory timerInv2;

    public static ItemStack openInv1;
    public static ItemStack enableReverse;
    public static ItemStack disableReverse;

    public static void loadGuiItems() {

//		placeholder = ItemBuilder.createItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15, "�7 ");
//
//		// Inventar 1
//
//		timerInv = Bukkit.createInventory(null, 36, Main.getPrefix() + "�avorw�rts");
//
//		time = ItemBuilder.createItemStack(Material.WATCH, 1, (byte) 0);
//		resume = ItemBuilder.createItemStack(Material.INK_SACK, 1, (byte) 10, "�aTimer fortsetzen");
//		resume1 = ItemBuilder.createItemStack(Material.INK_SACK, 1, (byte) 8, "�cDer Timer l�uft bereits");
//		stop = ItemBuilder.createItemStack(Material.INK_SACK, 1, (byte) 10, "�aTimer stoppen");
//		stop1 = ItemBuilder.createItemStack(Material.INK_SACK, 1, (byte) 8, "�cDer Timer l�uft nicht");
//		reset = ItemBuilder.createItemStack(Material.INK_SACK, 1, (byte) 9, "�aTimer zur�cksetzen");
//		openInv2 = ItemBuilder.createItemStack(Material.ARROW, 1, (byte) 0, "�7�oN�chste Seite");

        // Inventar 2

        timerInv2 = Bukkit.createInventory(null, 36, Main.getPrefix() + "�ar�ckw�rts");

        openInv1 = ItemBuilder.createItemStack(Material.ARROW, 1, (byte) 0, "�7�ozur�ck");
//		enableReverse = ItemBuilder.createItemStack(Material.STAINED_GLASS, 1, (byte) 5,
//				"�6Der Timer l�uft �ar�ckwr�rts");
//		disableReverse = ItemBuilder.createItemStack(Material.STAINED_GLASS, 1, (byte) 14,
//				"�6Der Timer l�uft �avorw�rts");

        for (int i = 0; i < 36; i++) {
            timerInv.setItem(i, placeholder);
            timerInv2.setItem(i, placeholder);
        }

        timerInv.setItem(24, reset);
        timerInv.setItem(35, openInv2);

        timerInv2.setItem(27, openInv1);
    }

    public static void openGui(Player p) {

        setItems();

        p.openInventory(timerInv);
    }

    public void openGui2(Player p) {

    }

    private static void setItems() {
        if (Timer.isRunning) {
            timerInv.setItem(20, resume1);
            timerInv.setItem(22, stop);
        } else {
            timerInv.setItem(20, resume);
            timerInv.setItem(22, stop1);
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {
            if (e.getWhoClicked() instanceof Player) {

                Player p = (Player) e.getWhoClicked();

                if (e.getInventory().equals(timerInv)) {
                    e.setCancelled(true);


                    if (e.getCurrentItem().equals(resume)) {
                        Timer.resumeTimer();
                        setItems();
//						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                    }
                    if (e.getCurrentItem().equals(stop)) {
                        Timer.stopTimer();
                        setItems();
//						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                    }
                    if (e.getCurrentItem().equals(reset)) {
                        Timer.resetTimer();
                        setItems();
//						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
                    }
                    if(e.getCurrentItem().equals(openInv2)) {
                        p.openInventory(timerInv2);
                    }

                }

                if (e.getInventory().equals(timerInv2)) {
                    e.setCancelled(true);

                    if(e.getCurrentItem().equals(openInv1)) {
                        p.openInventory(timerInv);
                    }
                }
            }

        } catch (Exception e1) {
            return;
        }
    }
}
