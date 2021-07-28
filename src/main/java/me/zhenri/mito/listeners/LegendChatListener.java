package me.zhenri.mito.listeners;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import me.zhenri.mito.Main;
import me.zhenri.mito.api.MitoAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LegendChatListener implements Listener {

    @EventHandler
    private void onChat(ChatMessageEvent e) {
        if (Main.legendchat && e.getTags().contains("mito")) {
            e.setTagValue("mito", MitoAPI.fixColor(Main.getInstance().getConfig().getString("Tag")));
        }
    }
}
