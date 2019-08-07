package me.nosmakos.killshot.projectile.types;

import me.nosmakos.killshot.utilities.XSound;
import me.nosmakos.killshot.weapon.Weapon;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Rocket extends BukkitRunnable {

    private Player player;
    private Weapon weapon;

    private Location loc;
    private Vector dir;
    private int distance;


    public Rocket(Player player, Weapon weapon) {
        this.player = player;
        this.weapon = weapon;

        this.distance = weapon.getProjectileDistance();
        this.loc = player.getLocation().clone().add(0, 1.5D, 0);
        this.dir = player.getLocation().getDirection().normalize().clone().multiply(weapon.getProjectileSpeed());
    }

    @Override
    public void run() {
        this.distance -= 1;
        loc.add(dir);

        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 0);
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 2);

        boolean entityNear = false;
        List e = new ArrayList(loc.getWorld().getNearbyEntities(loc, 1.0D, 1.0D, 1.0D));
        if ((!e.isEmpty()) && (!e.contains(player))) {
            entityNear = true;
        }
        if (loc.getBlock().getType().isOccluding() || entityNear || this.distance < 0) {
            loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 0);
            player.getWorld().playSound(loc, XSound.ENTITY_GENERIC_EXPLODE.parseSound(), 8.0F, 0.7F);

            loc.getWorld().getNearbyEntities(loc, 7.0D, 7.0D, 7.0D).stream().filter(entity -> (entity instanceof LivingEntity))
                    .forEach(entity -> ((LivingEntity) entity).damage(weapon.getProjectileDamage(), player)
            );
            cancel();
        }
    }
}