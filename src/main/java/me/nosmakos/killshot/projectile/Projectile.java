package me.nosmakos.killshot.projectile;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import me.nosmakos.killshot.utilities.XSound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Projectile extends BukkitRunnable {

    private KillShot plugin;
    private WeaponManagement weaponManagement;
    private Weapon weapon;

    private Player player;
    private int projectileAmount;

    public Projectile(KillShot plugin, Player player, Weapon weapon) {
        this.plugin = plugin;
        this.weaponManagement = plugin.getWeaponManagement();
        this.weapon = weapon;

        this.player = player;
        this.projectileAmount = weapon.getProjectileAmount();
    }

    @Override
    public void run() {
        projectileAmount--;
        if (projectileAmount < 0 || weaponManagement.getCurrentAmmo(player) <= 0) {
            cancel();
            return;
        }
        if (weapon.getProjectileDefaultSound() != null) {
            String[] sound = weapon.getProjectileDefaultSound().split("-");
            player.getWorld().playSound(player.getLocation(), XSound.valueOf(sound[0].toUpperCase()).parseSound(), Integer.parseInt(sound[1]), Integer.parseInt(sound[2]));
        }

        if (weapon.getProjectileCustomSound() != null) {
            player.getWorld().playSound(player.getLocation(), weapon.getProjectileCustomSound(), 1, 1);
        }
        weapon.getProjectileType().interact(plugin, player, weapon);
    }
}