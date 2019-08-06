package me.nosmakos.killshot.reload;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.configuration.WeaponData;
import me.nosmakos.killshot.utilities.XSound;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ReloadAmmunition extends BukkitRunnable {

    private KillShot plugin;
    private WeaponManagement weaponManagement;
    private Weapon weapon;

    private Player player;
    private String id;

    public ReloadAmmunition(KillShot plugin, Player player, Weapon weapon, String id) {
        this.plugin = plugin;
        this.weaponManagement = plugin.getWeaponManagement();
        this.weapon = weapon;

        this.player = player;
        this.id = id;

        if (weapon.getReloadDefaultSound() != null) {
            String[] sound = weapon.getReloadDefaultSound().split("-");
            player.getWorld().playSound(player.getLocation(), XSound.valueOf(sound[0].toUpperCase()).parseSound(), Integer.parseInt(sound[1]), Integer.parseInt(sound[2]));
        }

        if (weapon.getReloadCustomSound() != null) {
            player.getWorld().playSound(player.getLocation(), weapon.getReloadCustomSound(), 1, 1);
        }
    }

    @Override
    public void run() {
        String cId = weaponManagement.getWeaponId(player);
        if (cId == null || !cId.equals(id) || !hasAmmo()) {
            WeaponManagement.reloadControl.remove(player.getUniqueId());
            cancel();
            return;
        }
        WeaponManagement.reloadControl.remove(player.getUniqueId());

        WeaponData data = WeaponData.getConfig(plugin, weaponManagement.getCategory(player));
        String ammo = Objects.requireNonNull(data.getString("reload.reloadAmmoType")).toLowerCase();

        player.getInventory().removeItem(weaponManagement.getAmmoItem(ammo, weapon.getReloadAmmoRemoveAmount()));
        weaponManagement.addAmmo(player, weapon);
    }

    private boolean hasAmmo() {
        return player.getInventory().containsAtLeast(weapon.getReloadAmmoType(), weapon.getReloadAmmoRemoveAmount());
    }
}