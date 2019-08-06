package me.nosmakos.killshot.weapon.listeners;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.utilities.XSound;
import me.nosmakos.killshot.weapon.Weapon;
import me.nosmakos.killshot.weapon.WeaponManagement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WeaponScopeInteract implements Listener {

    private WeaponManagement weaponManagement;

    public WeaponScopeInteract(KillShot plugin) {
        this.weaponManagement = plugin.getWeaponManagement();
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        if (!weaponManagement.hasWeapon(event.getPlayer())) return;
        event.setCancelled(true);

        Player player = event.getPlayer();
        if ((event.getHand() != EquipmentSlot.OFF_HAND) && (event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(player));

            if (weapon.getScopeLevel() == 0) return;

            if (weapon.getScopeDefaultSound() != null) {
                String[] sound = weapon.getScopeDefaultSound().split("-");
                player.getWorld().playSound(player.getLocation(), XSound.valueOf(sound[0].toUpperCase()).parseSound(), Integer.parseInt(sound[1]), Integer.parseInt(sound[2]));
            }

            if (!weapon.isScoped()) {
                weapon.setScoped(true);

                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, weapon.getScopeLevel()));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 250));

                player.getInventory().getItemInMainHand().setDurability((short) weapon.getScopeData());

                return;
            }
            weapon.setScoped(false);

            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.JUMP);

            player.getInventory().getItemInMainHand().setDurability((short) weapon.getItemDurability());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!weaponManagement.hasWeapon((Player) event.getWhoClicked())) return;

        Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory((Player) event.getWhoClicked()));
        if (weapon.isScoped()) event.setCancelled(true);
    }

    @EventHandler
    public void onWeaponHeld(PlayerItemHeldEvent event) {
        if (!weaponManagement.hasWeapon(event.getPlayer())) return;

        Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(event.getPlayer()));
        if (!weapon.isScoped()) return;
        Player player = event.getPlayer();

        weapon.setScoped(false);

        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.JUMP);

        player.getInventory().getItemInMainHand().setDurability((short) weapon.getItemDurability());
    }

    @EventHandler
    public void onWeaponDrop(PlayerDropItemEvent event) {
        if (!weaponManagement.isWeapon(event.getItemDrop().getItemStack())) return;
        ItemStack itemStack = event.getItemDrop().getItemStack();

        Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(itemStack));
        if (!weapon.isScoped()) return;
        Player player = event.getPlayer();

        weapon.setScoped(false);

        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.JUMP);

        itemStack.setDurability((short) weapon.getItemDurability());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!weaponManagement.hasWeapon(event.getPlayer())) return;

        Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(event.getPlayer()));
        if (!weapon.isScoped()) return;
        Player player = event.getPlayer();

        weapon.setScoped(false);

        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.JUMP);

        player.getInventory().getItemInMainHand().setDurability((short) weapon.getItemDurability());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        for (ItemStack item : event.getDrops()) {
            if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                continue;
            }
            if (!weaponManagement.isWeapon(item)) continue;
            Weapon weapon = weaponManagement.getWeapon(weaponManagement.getCategory(item));
            weapon.setScoped(false);

            item.setDurability((short) weapon.getItemDurability());
        }
    }
}