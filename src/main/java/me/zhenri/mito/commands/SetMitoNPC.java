package me.zhenri.mito.commands;

import me.zhenri.mito.Main;
import me.zhenri.mito.api.MitoAPI;
import me.zhenri.mito.npcs.MitoNPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetMitoNPC implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (c.getName().equalsIgnoreCase("setmitonpc")) {
            if (!(s instanceof Player)) {
                s.sendMessage("§cComando disponível apenas para jogadores.");
                return true;
            }
            Player p = (Player) s;
            if (!p.hasPermission("mito.setmitonpc")) {
                for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SemPermissao")) {
                    p.sendMessage(MitoAPI.fixColor(message));
                }
                return true;
            }
            MitoNPC.set(p.getLocation());
            for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SetMitoNPC")) {
                p.sendMessage(MitoAPI.fixColor(message));
            }
        }
        return false;
    }
}
