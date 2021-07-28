package me.zhenri.mito.listeners;

import me.zhenri.mito.Main;
import me.zhenri.mito.api.MitoAPI;
import me.zhenri.mito.commands.Mito;
import me.zhenri.mito.npcs.MitoNPC;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MitoListener implements Listener {

    @EventHandler
    public void RemoveNPC(NPCRemoveEvent e) {
        if (e.getNPC().getId() == 1000) {
            if (MitoNPC.hologram != null) {
                MitoNPC.hologram.delete();
            }
        }
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (MitoAPI.checkMito(p.getName())) {
            MitoAPI.setMito(p.getKiller());
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getName().equalsIgnoreCase("zHenri_")) {
            e.getPlayer().sendMessage("§c[Mito] Este servidor utiliza o seu plugin de Mito! (Versão " + Main.getInstance().getDescription().getVersion() + ")");
        }
        if (e.getPlayer().hasPermission("mito.setmito") || e.getPlayer().hasPermission("mito.setmitonpc")) {
            MitoAPI.checkVersion();
            if (MitoAPI.update) {
                e.getPlayer().sendMessage(" ");
                e.getPlayer().sendMessage(" §e[Mito] Uma nova versão do plugin está disponível.");
                e.getPlayer().sendMessage(" §e[Mito] Versão atual: §b" + Main.getInstance().getDescription().getVersion());
                e.getPlayer().sendMessage(" §e[Mito] Nova versão: §b" + MitoAPI.latestversion);
                e.getPlayer().sendMessage(" ");
                e.getPlayer().sendMessage(" §e[Mito] Para baixar a versão mais recente acesse o link abaixo:");
                e.getPlayer().sendMessage(" §e[Mito] §b" + MitoAPI.download);
                e.getPlayer().sendMessage(" ");
            }
        }
        if (MitoAPI.checkMito(e.getPlayer().getName())) {
            if (Main.getInstance().getConfig().getBoolean("AnunciarEntrada")) {
                for (String anuncio : Main.getInstance().getConfig().getStringList("Mensagens.AnuncioEntrada")) {
                    Bukkit.broadcastMessage(MitoAPI.fixColor(anuncio).replace("%mito%", MitoAPI.getMitoName()));
                }
            }
        }
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent e) {
        if (MitoAPI.checkMito(e.getPlayer().getName())) {
            if (Main.getInstance().getConfig().getBoolean("AnunciarSaida")) {
                for (String anuncio : Main.getInstance().getConfig().getStringList("Mensagens.AnuncioSaida")) {
                    Bukkit.broadcastMessage(MitoAPI.fixColor(anuncio).replace("%mito%", MitoAPI.getMitoName()));
                }
            }
        }
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().getName().equals(MitoAPI.fixColor(Main.getInstance().getConfig().getString("MitoMenu.name")))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void RightClickNPC(NPCRightClickEvent e) {
        if (e.getNPC().getId() == 1000) {
            if (e.getClicker().hasPermission("mito.setmitonpc") && e.getClicker().getItemInHand().getType() == Material.STICK)
                return;
            MitoAPI.openMitoMenu(e.getClicker());
        }
    }

    @EventHandler
    public void LeftClickNPC(NPCLeftClickEvent e) {
        if (e.getNPC().getId() == 1000) {
            MitoAPI.openMitoMenu(e.getClicker());
        }
    }
}
