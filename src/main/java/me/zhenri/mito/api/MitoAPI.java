package me.zhenri.mito.api;

import me.zhenri.mito.Main;
import me.zhenri.mito.npcs.MitoNPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MitoAPI {

    public static void reloadPlugin() {

        if (!new File(Main.getInstance().getDataFolder(), "config.yml").exists()) {
            Main.getInstance().saveDefaultConfig();
        }

        Main.getInstance().reloadConfig();
        MitoNPC.remove();
        MitoNPC.spawn();
    }

    public static void setMito(Player player) {
        if (player != null) {
            String playername = player.getName();
            if (Main.getInstance().getConfig().getBoolean("AnunciarMito")) {
                for (String anuncio : Main.getInstance().getConfig().getStringList("Mensagens.NovoMito")) {
                    Bukkit.broadcastMessage(MitoAPI.fixColor(anuncio).replace("%mito%", playername));
                }
            }
            if (Main.getInstance().getConfig().getBoolean("Raio")) {
                player.getWorld().strikeLightning(player.getLocation());
            }
            Main.getInstance().getConfig().set("Mito", playername);
            Main.getInstance().saveConfig();
            MitoNPC.updateNPC();
        }
    }

    public static void openMitoMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, Main.getInstance().getConfig().getInt("MitoMenu.rows")*9, MitoAPI.fixColor(Main.getInstance().getConfig().getString("MitoMenu.name")));
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(MitoAPI.fixColor(Main.getInstance().getConfig().getString("MitoMenu.item.name")).replace("%mito%", MitoAPI.getMitoName()));
        List<String> lore = new ArrayList<>();
        for (String loreconfig : Main.getInstance().getConfig().getStringList("MitoMenu.item.lore")) {
            lore.add(MitoAPI.fixColor(loreconfig).replace("%mito%", MitoAPI.getMitoName()));
        }
        meta.setLore(lore);
        head.setItemMeta(meta);
        SkullMeta skullmeta = (SkullMeta) head.getItemMeta();
        skullmeta.setOwner(Main.getInstance().getConfig().getString("Mito"));
        head.setItemMeta(skullmeta);
        inventory.setItem(Main.getInstance().getConfig().getInt("MitoMenu.item.slot"), head);
        player.openInventory(inventory);
    }

    public static boolean checkMito(String player) {
        if (player.equals(getMitoName())) {
            return true;
        }else {
            return false;
        }
    }

    public static String fixColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getMitoName() {
        return Main.getInstance().getConfig().getString("Mito");
    }
}
