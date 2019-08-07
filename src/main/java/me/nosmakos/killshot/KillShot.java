package me.nosmakos.killshot;

import com.google.common.base.Charsets;
import me.nosmakos.killshot.abilities.ProjectileBreakBlocks;
import me.nosmakos.killshot.bstats.Metrics;
import me.nosmakos.killshot.commands.CommandHandler;
import me.nosmakos.killshot.configuration.WeaponData;
import me.nosmakos.killshot.projectile.ProjectileInteract;
import me.nosmakos.killshot.projectile.ProjectileType;
import me.nosmakos.killshot.utilities.KUtil;
import me.nosmakos.killshot.utilities.Update;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import me.nosmakos.killshot.weapon.listeners.WeaponHolograms;
import me.nosmakos.killshot.weapon.listeners.WeaponInteract;
import me.nosmakos.killshot.weapon.listeners.WeaponScopeInteract;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KillShot extends JavaPlugin {

    /*
       Snowballs are highly recommended for ordinary projectiles.
    */
    private File langFile, ammunitionFile;
    private FileConfiguration langConfig, ammunitionConfig;

    private WeaponManagement weaponManagement;

    private Map<String, Weapon> weapons = new HashMap<>();

    @Override
    public void onEnable() {
        new Metrics(this);
        this.weaponManagement = new WeaponManagement(this);
        /* Configuration Files */
        createFiles();
        updateFiles();

        /* Events Registration */
        PluginManager pM = Bukkit.getServer().getPluginManager();
        pM.registerEvents(new ProjectileInteract(this), this);
        pM.registerEvents(new WeaponInteract(this), this);
        pM.registerEvents(new WeaponScopeInteract(this), this);
        pM.registerEvents(new WeaponHolograms(this), this);

        pM.registerEvents(new ProjectileBreakBlocks(this), this);

        /* Commands Registration */
        getCommand("killshot").setExecutor(new CommandHandler(this));

        checkVersionUpdates();
        /* Weapons Registration */
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                this,
                this::registerWeaponCategories,
                20
        );
    }

    @Override
    public void onDisable() {
        WeaponData.removeAll();
        weapons.clear();
    }

    public FileConfiguration getLanguageConfig() {
        return this.langConfig;
    }

    public void reloadLanguageConfig() {
        langConfig = YamlConfiguration.loadConfiguration(langFile);
        InputStream defItemsConfigStream = this.getResource("lang.yml");
        if (defItemsConfigStream != null) {
            langConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defItemsConfigStream, Charsets.UTF_8)));
        }
    }

    private void createFiles() {
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            saveResource("lang.yml", false);
            getLogger().info("Creating lang.yml configuration file...");
        }

        langConfig = new YamlConfiguration();
        try {
            langConfig.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().warning("Cannot load lang.yml configuration file.");
        }

        ammunitionFile = new File(getDataFolder(), "ammunition.yml");
        if (!ammunitionFile.exists()) {
            ammunitionFile.getParentFile().mkdirs();
            saveResource("ammunition.yml", false);
            getLogger().info("Creating ammunition.yml configuration file...");
        }

        ammunitionConfig = new YamlConfiguration();
        try {
            ammunitionConfig.load(ammunitionFile);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().warning("Cannot load ammunition.yml configuration file.");
        }
    }

    public FileConfiguration getAmmunitionConfig() {
        return this.ammunitionConfig;
    }

    public void reloadAmmunitionConfig() {
        ammunitionConfig = YamlConfiguration.loadConfiguration(ammunitionFile);
        InputStream defItemsConfigStream = this.getResource("ammunition.yml");
        if (defItemsConfigStream != null) {
            ammunitionConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defItemsConfigStream, Charsets.UTF_8)));
        }
    }

    private void checkVersionUpdates() {
        Update update = new Update(this, "70103");
        Update.UpdateResults result = update.checkUpdates();
        switch (result.getResult()) {
            case NO_UPDATE:
                break;
            case FAIL:
                getLogger().warning("Could not check for a version updates due to no internet connection.");
                break;
            case UPDATE_AVAILABLE:
                getLogger().info("Stable Version: " + update.newVersion() + " is out! You are still running version: " + update.currentVersion());
                getLogger().info("Update at: https://spigotmc.org/resources/70103");
                break;
        }
    }

    private void registerWeaponCategories() {
        weapons.clear();

        File[] files = new File(getDataFolder(), "weapons").listFiles();

        if (files != null && files.length != 0) {
            for (File list : files) {
                String gun = list.getName().toLowerCase().replace(".yml", "");

                WeaponData data = WeaponData.getConfig(this, gun);
                data.reload();

                String ammo = Objects.requireNonNull(data.getString("reload.reloadAmmoType")).toLowerCase();

                Weapon createdGun = new Weapon(
                        KUtil.colorize(data.getString("information.itemName")),
                        data.getInt("information.itemDurability"),
                        ProjectileType.valueOf(data.getString("shooting.projectileType")),
                        data.getDouble("shooting.projectileDamage"),
                        data.getInt("shooting.projectileAmount"),
                        data.getInt("shooting.projectileSpeed"),
                        data.getInt("shooting.projectileCooldown"),
                        data.getInt("shooting.projectileDistance"),
                        data.getString("shooting.projectileDefaultSound"),
                        data.getString("shooting.projectileCustomSound"),
                        data.getInt("reload.reloadAmount"),
                        data.getInt("reload.reloadCooldown"),
                        weaponManagement.getAmmoItem(ammo),
                        data.getInt("reload.reloadRemoveAmount"),
                        data.getString("reload.reloadDefaultSound"),
                        data.getString("reload.reloadCustomSound")
                );
                createdGun.setProjectileCriticalDamage(data.getDouble("abilities.projectileCriticalDamage"));
                createdGun.setWeaponItemDropHologram(data.getBoolean("information.itemDropHologram"));

                if (data.getString("scope") != null) {
                    createdGun.setScopeLevel(data.getInt("scope.weaponScopeLevel"));
                    createdGun.setScopeData(data.getInt("scope.weaponScopeData"));
                    createdGun.setScopeDefaultSound(data.getString("scope.weaponScopeDefaultSound"));
                }
                weapons.put(gun, createdGun);
            }

            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[KILLSHOT] " + ChatColor.GOLD + "Loaded - " + ChatColor.RED + "(" + weapons.size() + ")" + ChatColor.GOLD +
                    " Weapon" + KUtil.s(weapons.size())
                    + " " + ChatColor.RED + Arrays.toString(weapons.keySet().toArray()).toUpperCase()
            );
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[KILLSHOT] Could not load any weapons. Weapons-Data is empty or doesn't exist - Ignoring...");
        }
    }

    private void updateFiles() {
        boolean update = false;

        if (!getLanguageConfig().isSet("config-version") || !getLanguageConfig().getString("config-version").equals("1.0")) {
            saveResource("lang.yml", true);
            update = true;
        }

        if (update) getLogger().info("Updating configuration files ...");
    }

    public Map<String, Weapon> weapon() {
        return this.weapons;
    }

    public WeaponManagement getWeaponManagement() {
        return weaponManagement;
    }
}
