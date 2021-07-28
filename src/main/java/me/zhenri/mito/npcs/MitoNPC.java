package me.zhenri.mito.npcs;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.zhenri.mito.Main;
import me.zhenri.mito.api.MitoAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class MitoNPC {

    public static Hologram hologram;

    public static void spawn() {
        if (CitizensAPI.getNPCRegistry().getById(1000) == null) {
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, UUID.randomUUID(), 1000, "§c"+MitoAPI.getMitoName());
            npc.data().set("player-skin-name", MitoAPI.getMitoName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select 1000");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc gravity");
        }
        if (!CitizensAPI.getNPCRegistry().getById(1000).getName().equals("§b"+MitoAPI.getMitoName())) {
            CitizensAPI.getNPCRegistry().getById(1000).setName("§b"+MitoAPI.getMitoName());
        }
        if (!CitizensAPI.getNPCRegistry().getById(1000).data().get("player-skin-name").equals(MitoAPI.getMitoName())) {
            CitizensAPI.getNPCRegistry().getById(1000).data().set("player-skin-name", MitoAPI.getMitoName());
        }
        if (!check() && getLocation() != null) {
            Location l = getLocation();
            CitizensAPI.getNPCRegistry().getById(1000).spawn(l);
            double addy = 2.59;
            int add = 0;
            for (String line : Main.getInstance().getConfig().getStringList("Hologram")) {
                if (add > 0) {
                    addy = addy+0.25;
                }
                add++;
            }
            hologram = HologramsAPI.createHologram(Main.getInstance(), l.clone().add(0, addy, 0));
            int k = 0;
            for (String line : Main.getInstance().getConfig().getStringList("Hologram")) {
                hologram.insertTextLine(k, MitoAPI.fixColor(line).replace("%mito%", MitoAPI.getMitoName()));
                k++;
            }
        }
    }

    public static void set(Location location) {
        String world = location.getWorld().getName();
        String x = String.valueOf(location.getX());
        String y = String.valueOf(location.getY());
        String z = String.valueOf(location.getZ());
        String yaw = String.valueOf(location.getYaw());
        String pitch = String.valueOf(location.getPitch());
        String string = world+";"+x+";"+y+";"+z+";"+yaw+";"+pitch;
        Main.getInstance().getConfig().set("NPC", string);
        Main.getInstance().saveConfig();
        if (check()) {
            remove();
            spawn();
        }else {
            spawn();
        }
    }

    public static boolean check() {
        if (CitizensAPI.getNPCRegistry().getById(1000) != null && CitizensAPI.getNPCRegistry().getById(1000).isSpawned()) {
            return true;
        }else {
            return false;
        }
    }

    public static void remove() {
        if (check()) {
            CitizensAPI.getNPCRegistry().getById(1000).despawn();
        }
        if (hologram != null) {
            hologram.delete();
        }
    }

    public static Location getLocation() {
        String location = Main.getInstance().getConfig().getString("NPC");
        if (location == null) return null;
        String[] split = location.split(";");
        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static void updateNPC() {
        NPC npc = CitizensAPI.getNPCRegistry().getById(1000);
        if (npc != null && getLocation() != null) {
            if (!npc.getName().equals("§b"+MitoAPI.getMitoName())) {
                npc.despawn();
                npc.setName("§b"+MitoAPI.getMitoName());
                npc.data().set("player-skin-name", MitoAPI.getMitoName());
                npc.spawn(getLocation());
            }
        }
    }
}
