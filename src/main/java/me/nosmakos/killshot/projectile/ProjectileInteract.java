package me.nosmakos.killshot.projectile;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.itemnbt.NBTItem;
import me.nosmakos.killshot.utilities.KUtil;
import me.nosmakos.killshot.utilities.Lang;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.HashMap;
import java.util.Map;

public class ProjectileInteract implements Listener {

    private KillShot plugin;
    private WeaponManagement weaponManagement;
    private Map<Integer, String> bullets = new HashMap<>();

    public ProjectileInteract(KillShot plugin) {
        this.plugin = plugin;
        this.weaponManagement = plugin.getWeaponManagement();
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if ((event.getEntity() instanceof Snowball) && (event.getEntity().getShooter() instanceof Player)) {
            Player player = (Player) event.getEntity().getShooter();

            if (!weaponManagement.hasWeapon(player)) return;
            NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());
            String weaponName = item.getString("weaponCategory");

            Weapon weapon = weaponManagement.getWeapon(weaponName);
            bullets.put(event.getEntity().getEntityId(), weaponName);

            Bukkit.getScheduler().runTaskLater(plugin, event.getEntity()::remove, 1 + weapon.getProjectileDistance());
        }
    }

    @EventHandler
    public void onProjectileEntityDamage(EntityDamageByEntityEvent event) {
        if ((event.getDamager() != null) && (event.getDamager() instanceof Snowball)) {
            Snowball s = (Snowball) event.getDamager();

            if (!bullets.containsKey(s.getEntityId())) return;

            Weapon weapon = weaponManagement.getWeapon(bullets.get(s.getEntityId()));

            int random = KUtil.randomInt(1, 10);
            event.setDamage(random >= 9 ? weapon.getProjectileDamage() + weapon.getProjectileCriticalDamage() : weapon.getProjectileDamage());

            Bukkit.getScheduler().runTaskLater(plugin, () -> ((LivingEntity) event.getEntity()).setNoDamageTicks(0), 1);
            bullets.remove(event.getEntity().getEntityId());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
            if (!weaponManagement.hasWeapon(event.getEntity().getKiller())) return;

            event.setDeathMessage(Lang.KILLED_PLAYER_DEATH_MESSAGE.get()
                    .replace("%victim%", event.getEntity().getName())
                    .replace("%killer%", event.getEntity().getKiller().getName())
                    .replace("%gun%", "︻デ═一"));
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball && !bullets.containsKey(event.getEntity().getEntityId())) return;
        bullets.remove(event.getEntity().getEntityId());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!WeaponManagement.reloadControl.contains(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        if (!WeaponManagement.reloadControl.contains(event.getWhoClicked().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!WeaponManagement.reloadControl.contains(event.getWhoClicked().getUniqueId())) return;
        event.setCancelled(true);
    }
}