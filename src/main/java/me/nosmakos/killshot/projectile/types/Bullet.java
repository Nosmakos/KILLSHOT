package me.nosmakos.killshot.projectile.types;


import me.nosmakos.killshot.weapon.Weapon;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

public class Bullet {

    public Bullet(Player player, Weapon weapon) {
        Snowball bullet = player.launchProjectile(Snowball.class);

        bullet.setVelocity(player.getLocation().getDirection().multiply(weapon.getProjectileSpeed()));
        bullet.setSilent(true);
        bullet.setGravity(false);

        Location loc = player.getEyeLocation();
        Vector dir = loc.getDirection();
        player.getWorld().spawnParticle(Particle.CRIT, loc.getX(), loc.getY(), loc.getZ(), 0, (float) dir.getX(), (float) dir.getY(), (float) dir.getZ(), 4.5D, null);
    }
}
