package me.zhenri.mito.commands;

import me.zhenri.mito.Main;
import me.zhenri.mito.api.MitoAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mito implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (c.getName().equalsIgnoreCase("mito")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (args.length == 0) {
                    MitoAPI.openMitoMenu(p);
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!p.hasPermission("mito.reload")) {
                        for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SemPermissao")) {
                            p.sendMessage(MitoAPI.fixColor(message));
                        }
                        return true;
                    }
                    MitoAPI.reloadPlugin();
                    p.sendMessage("§aReload concluído com sucesso.");
                }
            } else {
                if (args.length == 0) {
                    s.sendMessage("§c" + MitoAPI.getMitoName() + " é atual Mito do PvP.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!s.hasPermission("mito.reload")) {
                        for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SemPermissao")) {
                            s.sendMessage(MitoAPI.fixColor(message));
                        }
                        return true;
                    }
                    MitoAPI.reloadPlugin();
                    s.sendMessage("§aReload concluído com sucesso.");
                }
            }
        }
        return false;
    }
}
