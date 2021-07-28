package me.zhenri.mito;

import me.zhenri.mito.api.MitoAPI;
import me.zhenri.mito.commands.Mito;
import me.zhenri.mito.commands.SetMito;
import me.zhenri.mito.commands.SetMitoNPC;
import me.zhenri.mito.listeners.LegendChatListener;
import me.zhenri.mito.listeners.MitoListener;
import me.zhenri.mito.npcs.MitoNPC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static boolean legendchat = false;
    
    @Override
    public void onEnable() {
        instance = this;
        getCommand("mito").setExecutor(new Mito());
        getCommand("setmitonpc").setExecutor(new SetMitoNPC());
        getCommand("setmito").setExecutor(new SetMito());
        Bukkit.getPluginManager().registerEvents(new MitoListener(), this);

        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        MitoNPC.spawn();

        if (Bukkit.getPluginManager().getPlugin("Legendchat") != null) {
            legendchat = true;
            Bukkit.getPluginManager().registerEvents(new LegendChatListener(), this);
        }

        MitoAPI.checkVersion();
        if (MitoAPI.update) {
            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(" §e[Mito] Uma nova versão do plugin está disponível.");
            Bukkit.getConsoleSender().sendMessage(" §e[Mito] Versão atual: §b" + Main.getInstance().getDescription().getVersion());
            Bukkit.getConsoleSender().sendMessage(" §e[Mito] Nova versão: §b" + MitoAPI.latestversion);
            Bukkit.getConsoleSender().sendMessage(" ");
            Bukkit.getConsoleSender().sendMessage(" §e[Mito] Para baixar a versão mais recente acesse o link abaixo:");
            Bukkit.getConsoleSender().sendMessage(" §e[Mito] §b" + MitoAPI.download);
            Bukkit.getConsoleSender().sendMessage(" ");
        }
    }

    @Override
    public void onDisable() {
        MitoNPC.remove();
    }
}
