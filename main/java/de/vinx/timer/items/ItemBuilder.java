package de.vinx.timer.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemBuilder {

    public static ItemStack createItemStack(Material material, int amount, byte damage) {
        return new ItemStack(material, amount, damage);
    }

    public static ItemStack createItemStack(Material material, int amount, byte damage, String displayname) {
        ItemStack item = createItemStack(material, amount, damage);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItemStack(Material material, int amount, byte damage, String displayname,
                                            ArrayList<String> lore) {
        ItemStack item = createItemStack(material, amount, damage, displayname);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createLeatherArmor(Material material, byte damage, String displayname, Color color) {
        ItemStack item = createItemStack(material, 1, damage, displayname);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createPlayerSkull(int amount, String displayname, String skullowner) {
//		ItemStack item = createItemStack(Material.SKULL, amount, (byte) 3, displayname);
//		SkullMeta meta = (SkullMeta) item.getItemMeta();
//		meta.setOwner(skullowner);
//		item.setItemMeta(meta);
        return null;
    }

    public static void setLore(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void setDisplayname(ItemStack item, String displayname) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);
        item.setItemMeta(meta);
    }

}
