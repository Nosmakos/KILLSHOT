package me.nosmakos.killshot.projectile;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.projectile.types.Bullet;
import me.nosmakos.killshot.projectile.types.Flare;
import me.nosmakos.killshot.projectile.types.Rocket;
import org.bukkit.entity.Player;

public enum ProjectileType {
    BULLET,
    FLARE,
    ROCKET;

    public void interact(KillShot plugin, Player player, Weapon weapon) {
        switch (this) {
            case BULLET:
                new Bullet(player, weapon);
                break;
            case FLARE:
                new Flare(player, weapon).runTaskTimer(plugin, 0, 1);
                break;
            case ROCKET:
                new Rocket(player, weapon).runTaskTimer(plugin, 0, 1);
                break;
        }
        plugin.getWeaponManagement().removeAmmo(player, weapon);
    }
}
