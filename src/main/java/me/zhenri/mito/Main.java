package me.zhenri.mito;

import me.zhenri.mito.commands.Mito;
import me.zhenri.mito.commands.SetMito;
import me.zhenri.mito.commands.SetMitoNPC;
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
    public static boolean update = false;
    public static String latestversion,download;

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

        try {
            URL url = new URL("https://api.github.com/repos/zHenri-dev/Mito/releases/latest");
            URLConnection connection = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.lines().collect(Collectors.joining("\n"));
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
            latestversion = (String) jsonObject.get("tag_name");
            download = (String) jsonObject.get("html_url");
            if (!getDescription().getVersion().equals(latestversion)) {
                update = true;
                Bukkit.getConsoleSender().sendMessage("§e[Mito] Uma nova versão do plugin está disponível.");
                Bukkit.getConsoleSender().sendMessage("§e[Mito] Versão atual: §b"+getDescription().getVersion());
                Bukkit.getConsoleSender().sendMessage("§e[Mito] Nova versão: §b"+latestversion);
                Bukkit.getConsoleSender().sendMessage(" ");
                Bukkit.getConsoleSender().sendMessage("§e[Mito] Para baixar a versão mais recente acesse o link abaixo:");
                Bukkit.getConsoleSender().sendMessage("§e[Mito] §b"+download);
            }
        }catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§c[Mito] Ocorreu um erro ao tentar verificar as atualizações.");
        }
    }

    @Override
    public void onDisable() {
        MitoNPC.remove();
    }
}
