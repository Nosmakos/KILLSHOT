package me.nosmakos.killshot.projectile;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.configuration.WeaponData;
import me.nosmakos.killshot.itemnbt.NBTItem;
import me.nosmakos.killshot.utilities.KUtil;
import me.nosmakos.killshot.utilities.Lang;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        if ((event.getEntity() instanceof Arrow) && (event.getEntity().getShooter() instanceof Player)) {
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
        if ((event.getEntity() instanceof LivingEntity) && (event.getDamager() instanceof Arrow)) {
            Arrow s = (Arrow) event.getDamager();

            LivingEntity entity = (LivingEntity) event.getEntity();
            if ((entity instanceof ItemFrame) || (entity instanceof ArmorStand)) return;

            if (!bullets.containsKey(s.getEntityId())) return;
            Weapon weapon = weaponManagement.getWeapon(bullets.get(s.getEntityId()));

            double damage = KUtil.randomInt(1, 10) >= 9 ? weapon.getProjectileDamage() + weapon.getProjectileCriticalDamage() : weapon.getProjectileDamage();
            event.setDamage(weaponManagement.isHeadShot(s.getLocation(), entity.getLocation()) ? damage + weapon.getProjectileHeadShotDamage() : damage);

            Bukkit.getScheduler().runTask(plugin, () -> {
                entity.setMaximumNoDamageTicks(0);
                entity.setNoDamageTicks(0);
            });

            WeaponData data = WeaponData.getConfig(plugin, bullets.get(s.getEntityId()));
            if (data.get("abilities.effectList") != null && event.getEntity() instanceof Player) {
                List<String> effectList = data.getStringList("abilities.effectList");

                effectList.stream().map(potionEffect -> potionEffect.split("-"))
                        .forEach(effect -> ((Player) event.getEntity()).addPotionEffect(new PotionEffect(
                                Objects.requireNonNull(PotionEffectType.getByName(effect[0])),
                                20 * Integer.parseInt(effect[1]),
                                Integer.parseInt(effect[2]))
                        ));
            }
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
        if (event.getEntity() instanceof Arrow && !bullets.containsKey(event.getEntity().getEntityId())) return;

        Bukkit.getScheduler().runTaskLater(plugin, () -> bullets.remove(event.getEntity().getEntityId()), 20);
        event.getEntity().remove();
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