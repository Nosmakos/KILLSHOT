package me.nosmakos.killshot.weapon.listeners;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.projectile.Projectile;
import me.nosmakos.killshot.reload.ReloadAmmunition;
import me.nosmakos.killshot.utilities.Lang;
import me.nosmakos.killshot.utilities.Permission;
import me.nosmakos.killshot.utilities.XSound;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WeaponInteract implements Listener {

    private KillShot plugin;
    private WeaponManagement weaponManagement;
    private Set<UUID> projectileCooldown = new HashSet<>();

    public WeaponInteract(KillShot plugin) {
        this.plugin = plugin;
        this.weaponManagement = plugin.getWeaponManagement();
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        if (!weaponManagement.hasWeapon(event.getPlayer())) return;
        event.setCancelled(true);

        Player player = event.getPlayer();
        if ((event.getHand() != EquipmentSlot.OFF_HAND) && (event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(player));
            if (player.hasPermission("killshot.weapons." + weapon) || player.hasPermission(Permission.WEAPONS_OP.get()) || player.hasPermission(Permission.OP.get())) {
                if (weaponManagement.getCurrentAmmo(player) <= 0) {
                    if (!player.getInventory().containsAtLeast(weapon.getReloadAmmoType(), weapon.getReloadAmmoRemoveAmount())) {
                        player.getWorld().playSound(player.getLocation(), XSound.BLOCK_NOTE_BLOCK_SNARE.parseSound(), 2, 3);
                        return;
                    }
                    if (WeaponManagement.reloadControl.contains(player.getUniqueId())) return;
                    WeaponManagement.reloadControl.add(player.getUniqueId());

                    new ReloadAmmunition(plugin, player, weapon, weaponManagement.getWeaponId(player))
                            .runTaskLater(plugin, 40 + weapon.getReloadAmmoCooldown()
                            );
                    return;
                }
                if (projectileCooldown.contains(player.getUniqueId())) return;
                projectileCooldown.add(player.getUniqueId());

                new Projectile(plugin, player, weapon).runTaskTimer(plugin, 0L, 1L);
                Bukkit.getScheduler().runTaskLater(plugin,
                        () -> projectileCooldown.remove(player.getUniqueId()),
                        weapon.getProjectileCooldown()
                );
            } else {
                player.sendMessage(Lang.NO_PERMISSION.get());
            }
        }
    }

    @EventHandler
    public void onBlockPlacement(BlockPlaceEvent event) {
        if (!weaponManagement.hasWeapon(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!weaponManagement.hasWeapon(event.getPlayer())) return;
        event.setCancelled(true);
    }
}
