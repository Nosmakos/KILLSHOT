package me.nosmakos.killshot.configuration;

import me.nosmakos.killshot.KillShot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WeaponData extends YamlConfiguration {

    private static final Map<String, WeaponData> configs = new HashMap<>();

    public static WeaponData getConfig(KillShot plugin, String weapon) {
        synchronized (configs) {
            return configs.computeIfAbsent(weapon, k -> new WeaponData(plugin, weapon));
        }
    }

    public static void remove(String gun) {
        configs.remove(gun);
    }

    public static void removeAll() {
        synchronized (configs) {
            configs.clear();
        }
    }

    private final File file;
    private final Object saveLock = new Object();
    private final String weapon;

    private WeaponData(KillShot plugin, String weapon) {
        super();
        this.weapon = weapon;
        this.file = new File(plugin.getDataFolder(),
                "weapons" + File.separator + weapon.toLowerCase() + ".yml");
        reload();
    }

    public void reload() {
        synchronized (saveLock) {
            try {
                load(file);
            } catch (Exception ignore) {
            }
        }
    }

    public void save() {
        synchronized (saveLock) {
            try {
                save(file);
            } catch (Exception ignore) {
            }
        }
    }
}