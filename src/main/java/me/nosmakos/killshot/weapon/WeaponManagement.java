package me.nosmakos.killshot.weapon;

import me.nosmakos.killshot.KillShot;
import me.nosmakos.killshot.api.ItemBuilder;
import me.nosmakos.killshot.configuration.WeaponData;
import me.nosmakos.killshot.itemnbt.NBTItem;
import me.nosmakos.killshot.utilities.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WeaponManagement {

    private KillShot plugin;
    public static Set<UUID> reloadControl = new HashSet<>();

    public WeaponManagement(KillShot plugin) {
        this.plugin = plugin;
    }

    public ItemStack getWeaponItem(String gun) {
        WeaponData data = WeaponData.getConfig(plugin, gun);

        return new ItemBuilder(
                XMaterial.valueOf(data.getString("information.itemType").toUpperCase()).pM())
                .setDurability((short) data.getInt("information.itemDurability"))
                .setAmount(1)
                .setName(data.getString("information.itemName") + ChatColor.GRAY + " «" + data.getInt("reload.reloadAmount") + "»")
                .setLore(data.getString("information.itemLore") != null ? data.getString("information.itemLore").split("\\|") : null)
                .buildWeapon(gun);
    }

    public Weapon getWeapon(String weapon) {
        return plugin.weapon().get(weapon.toLowerCase());
    }

    public boolean hasWeapon(Player player) {
        NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());
        return nbtItem.hasNBTData() && nbtItem.hasKey("weaponCategory") && nbtItem.hasKey("randomId");
    }

    public boolean isWeapon(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasNBTData() && nbtItem.hasKey("weaponCategory") && nbtItem.hasKey("randomId");
    }

    public String getCategory(Player player) {
        NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());
        return nbtItem.hasNBTData() && nbtItem.hasKey("weaponCategory") && nbtItem.hasKey("randomId") ? nbtItem.getString("weaponCategory") : null;
    }

    public String getCategory(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasNBTData() && nbtItem.hasKey("weaponCategory") && nbtItem.hasKey("randomId") ? nbtItem.getString("weaponCategory") : null;
    }

    public String getWeaponId(Player player) {
        NBTItem nbtItem = new NBTItem(player.getInventory().getItemInMainHand());
        return nbtItem.hasNBTData() && nbtItem.hasKey("weaponCategory") && nbtItem.hasKey("randomId") ? nbtItem.getString("randomId") : null;
    }

    public ItemStack getAmmoItem(String ammo) {
        FileConfiguration file = plugin.getAmmunitionConfig();

        return new ItemBuilder(XMaterial.valueOf(file.getString("ammunitionTypes." + ammo + ".itemType").toUpperCase()).pM())
                .setDurability((short) file.getInt("ammunitionTypes." + ammo + ".itemDurability"))
                .setName(file.getString("ammunitionTypes." + ammo + ".itemName"))
                .setLore(file.getString("ammunitionTypes." + ammo + ".itemLore") != null ? file.getString("ammunitionTypes." + ammo + ".itemLore").split("\\|") : null)
                .build();
    }

    public ItemStack getAmmoItem(String ammo, int amount) {
        FileConfiguration file = plugin.getAmmunitionConfig();

        return new ItemBuilder(XMaterial.valueOf(file.getString("ammunitionTypes." + ammo + ".itemType").toUpperCase()).pM())
                .setDurability((short) file.getInt("ammunitionTypes." + ammo + ".itemDurability"))
                .setAmount(amount)
                .setName(file.getString("ammunitionTypes." + ammo + ".itemName"))
                .setLore(file.getString("ammunitionTypes." + ammo + ".itemLore") != null ? file.getString("ammunitionTypes." + ammo + ".itemLore").split("\\|") : null)
                .build();
    }

    public int getCurrentAmmo(Player player) {
        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

        if (meta != null) {
            return Integer.parseInt(meta.getDisplayName().split("[«»]")[1]);
        }
        return 0;
    }

    public void removeAmmo(Player player, Weapon weapon) {
        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

        if (meta != null) {
            meta.setDisplayName(weapon.getCustomName() + ChatColor.GRAY + " «" + (getCurrentAmmo(player) - 1) + "»");
        }
        player.getInventory().getItemInMainHand().setItemMeta(meta);
        player.updateInventory();
    }

    public void addAmmo(Player player, Weapon weapon) {
        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

        if (meta != null) {
            meta.setDisplayName(weapon.getCustomName() + ChatColor.GRAY + " «" + (weapon.getReloadAmmoAmount()) + "»");
        }
        player.getInventory().getItemInMainHand().setItemMeta(meta);
        player.updateInventory();
    }

}