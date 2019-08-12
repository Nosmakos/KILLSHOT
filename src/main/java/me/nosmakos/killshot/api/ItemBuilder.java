package me.nosmakos.killshot.api;

import me.nosmakos.killshot.itemnbt.NBTItem;
import me.nosmakos.killshot.utilities.KUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private Material mat;
    private int amount;
    private short durability;
    private String name;
    private List<String> lore;

    public ItemBuilder() {
    }

    public ItemBuilder(Material mat) {
        this.mat = mat;
    }

    public Material getMat() {
        return this.mat;
    }

    public ItemBuilder setMat(Material mat) {
        this.mat = mat;
        return this;
    }

    public int getAmount() {
        return this.amount;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public short getDurability() {
        return this.durability;
    }

    public ItemBuilder setDurability(short durability) {
        this.durability = durability;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ItemBuilder setName(String name) {
        this.name = KUtil.colorize(name);
        return this;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ItemBuilder setLore(String[] lore) {
        this.lore = new ArrayList();
        for (String l : lore)
            this.lore.add(KUtil.colorize(l));
        return this;
    }

    public ItemBuilder addLineToLore(String[] lore) {
        for (String l : lore)
            this.lore.add(KUtil.colorize(l));
        return this;
    }

    public ItemBuilder removeLineFromLore(int index) {
        this.lore.remove(index);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStack buildWeapon(String category) {
        ItemStack item = new ItemStack(this.mat, this.amount, this.durability);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName(this.name);
            meta.setLore(this.lore);
        }

        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("randomId", UUID.randomUUID().toString());
        nbtItem.setString("weaponCategory", category);

        return nbtItem.getItem();
    }

    @SuppressWarnings("deprecation")
    public ItemStack build() {
        ItemStack item = new ItemStack(this.mat, this.amount, this.durability);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName(this.name);
            meta.setLore(this.lore);
        }
        item.setItemMeta(meta);
        return item;
    }

}