package me.zhenri.mito.commands;

import me.zhenri.mito.Main;
import me.zhenri.mito.api.MitoAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetMito implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if (c.getName().equalsIgnoreCase("setmito")) {
            if (!s.hasPermission("mito.setmito")) {
                for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SemPermissao")) {
                    s.sendMessage(MitoAPI.fixColor(message));
                }
            }
            if (args.length == 0) {
                for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SetMitoUsage")) {
                    s.sendMessage(MitoAPI.fixColor(message));
                }
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                for (String message : Main.getInstance().getConfig().getStringList("Mensagens.OfflinePlayer")) {
                    s.sendMessage(MitoAPI.fixColor(message));
                }
                return true;
            }
            if (!MitoAPI.checkMito(target.getName())) {
                MitoAPI.setMito(target);
                for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SetMito")) {
                    s.sendMessage(MitoAPI.fixColor(message));
                }
            } else {
                for (String message : Main.getInstance().getConfig().getStringList("Mensagens.SetMitoJaMito")) {
                    s.sendMessage(MitoAPI.fixColor(message));
                }
            }
        }
        return false;
    }
}
