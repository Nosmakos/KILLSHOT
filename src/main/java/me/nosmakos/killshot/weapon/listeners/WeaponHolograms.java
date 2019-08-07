package me.nosmakos.killshot.weapon.listeners;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class WeaponHolograms implements Listener {

    private KillShot plugin;
    private WeaponManagement weaponManagement;

    public WeaponHolograms(KillShot plugin) {
        this.plugin = plugin;
        this.weaponManagement = plugin.getWeaponManagement();
    }

    @EventHandler
    public void onDropWeaponsHologram(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (!weaponManagement.isWeapon(item)) return;
        Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(item));

        if (!weapon.hasWeaponItemDropHologram()) return;
        weaponManagement.createItemHologram(event.getItemDrop(), item.getItemMeta().getDisplayName());
    }

    @EventHandler
    public void onDeathWeaponsHologram(PlayerDeathEvent event) {
        Iterator<ItemStack> it = event.getDrops().iterator();

        while (it.hasNext()) {
            ItemStack item = it.next();
            if (!weaponManagement.isWeapon(item)) {
                continue;
            }
            Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(item));
            if (!weapon.hasWeaponItemDropHologram()) {
                continue;
            }
            it.remove();

            weaponManagement.dropItemHologram(event.getEntity().getLocation(), item);
        }
    }
}
