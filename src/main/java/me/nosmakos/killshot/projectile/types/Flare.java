package me.nosmakos.killshot.projectile.types;

import me.nosmakos.killshot.utilities.XMaterial;
import me.nosmakos.killshot.weapon.Weapon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Flare extends BukkitRunnable {

    private Player player;
    private Weapon weapon;

    private Location loc;
    private Vector dir;
    private int distance;

    public Flare(Player player, Weapon weapon) {
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

        player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 5);

        if (XMaterial.isNewVersion()) {
            org.bukkit.block.data.BlockData redstoneData = Material.REDSTONE_BLOCK.createBlockData();
            player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 20, redstoneData);
        } else {
            player.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 20, new org.bukkit.material.MaterialData(Material.REDSTONE_BLOCK));
        }

        boolean entityNear = false;
        List e = new ArrayList(loc.getWorld().getNearbyEntities(loc, 0.5D, 0.5D, 0.5D));
        if ((!e.isEmpty()) && (!e.contains(player))) {
            entityNear = true;
        }
        if (loc.getBlock().getType().isOccluding() || entityNear || this.distance < 0) {

            loc.getWorld().getNearbyEntities(loc, 0.5D, 0.5D, 0.5D).stream().filter(entity -> (entity instanceof LivingEntity))
                    .forEach(entity -> {
                        ((LivingEntity) entity).damage(weapon.getProjectileDamage(), player);
                        entity.setFireTicks(100);
                    });
            cancel();
        }
    }
}