package me.nosmakos.killshot.weapon;

import me.nosmakos.killshot.projectile.ProjectileType;
import org.bukkit.inventory.ItemStack;

public class Weapon {

    private String customName, projectileDefaultSound, projectileCustomSound, reloadDefaultSound, reloadCustomSound, scopeDefaultSound;
    private double projectileDamage, projectileCriticalDamage, projectileSpeed;
    private ProjectileType projectileType;
    private int itemDurability, projectileAmount, projectileDistance, projectileCooldown, ammoAmount, ammoCooldown, ammoRemoveAmount, scopeLevel, scopeData;
    private ItemStack ammoType;
    private boolean isScoped, weaponItemDropHologram;

    public Weapon(String customName, int itemDurability, ProjectileType projectileType, double projectileDamage, int projectileAmount, double projectileSpeed, int projectileCooldown, int projectileDistance, String projectileDefaultSound, String projectileCustomSound, int ammoAmount, int ammoCooldown, ItemStack ammoType, int ammoRemoveAmount, String reloadDefaultSound, String reloadCustomSound) {
        this.customName = customName;
        this.itemDurability = itemDurability;
        this.projectileType = projectileType;
        this.projectileDamage = projectileDamage;
        this.projectileAmount = projectileAmount;
        this.projectileSpeed = projectileSpeed;
        this.projectileCooldown = projectileCooldown;
        this.projectileDistance = projectileDistance;
        this.projectileDefaultSound = projectileDefaultSound;
        this.projectileCustomSound = projectileCustomSound;
        this.ammoAmount = ammoAmount;
        this.ammoCooldown = ammoCooldown;
        this.ammoType = ammoType;
        this.ammoRemoveAmount = ammoRemoveAmount;
        this.reloadDefaultSound = reloadDefaultSound;
        this.reloadCustomSound = reloadCustomSound;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileType projectileType) {
        this.projectileType = projectileType;
    }

    public void setProjectileDamage(double d) {
        this.projectileDamage = d;
    }

    public double getProjectileDamage() {
        return this.projectileDamage;
    }

    public void setProjectileDistance(int i) {
        this.projectileDistance = i;
    }

    public int getProjectileDistance() {
        return this.projectileDistance;
    }

    public int getProjectileAmount() {
        return projectileAmount;
    }

    public void setProjectileAmount(int i) {
        this.projectileAmount = i;
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(double projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public String getProjectileDefaultSound() {
        return projectileDefaultSound;
    }

    public void setProjectileDefaultSound(String projectileDefaultSound) {
        this.projectileDefaultSound = projectileDefaultSound;
    }

    public String getProjectileCustomSound() {
        return projectileCustomSound;
    }

    public void setProjectileCustomSound(String projectileCustomSound) {
        this.projectileCustomSound = projectileCustomSound;
    }

    public String getReloadDefaultSound() {
        return reloadDefaultSound;
    }

    public void setReloadDefaultSound(String reloadDefaultSound) {
        this.reloadDefaultSound = reloadDefaultSound;
    }

    public String getReloadCustomSound() {
        return reloadCustomSound;
    }

    public void setReloadCustomSound(String reloadCustomSound) {
        this.reloadCustomSound = reloadCustomSound;
    }

    public int getReloadAmmoAmount() {
        return ammoAmount;
    }

    public void setReloadAmmoAmount(int ammoAmount) {
        this.ammoAmount = ammoAmount;
    }

    public int getReloadAmmoRemoveAmount() {
        return ammoRemoveAmount;
    }

    public void setReloadAmmoRemoveAmount(int ammoRemoveAmount) {
        this.ammoRemoveAmount = ammoRemoveAmount;
    }

    public ItemStack getReloadAmmoType() {
        return ammoType;
    }

    public void setReloadAmmoType(ItemStack ammoType) {
        this.ammoType = ammoType;
    }

    public int getProjectileCooldown() {
        return projectileCooldown;
    }

    public void setProjectileCooldown(int projectileCooldown) {
        this.projectileCooldown = projectileCooldown;
    }

    public int getReloadAmmoCooldown() {
        return ammoCooldown;
    }

    public void setReloadAmmoCooldown(int ammoCooldown) {
        this.ammoCooldown = ammoCooldown;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    public void setScopeLevel(int scopeLevel) {
        this.scopeLevel = scopeLevel;
    }

    public boolean isScoped() {
        return isScoped;
    }

    public void setScoped(boolean scoped) {
        isScoped = scoped;
    }

    public int getScopeData() {
        return scopeData;
    }

    public void setScopeData(int scopeData) {
        this.scopeData = scopeData;
    }

    public String getScopeDefaultSound() {
        return scopeDefaultSound;
    }

    public void setScopeDefaultSound(String scopeDefaultSound) {
        this.scopeDefaultSound = scopeDefaultSound;
    }

    public int getItemDurability() {
        return itemDurability;
    }

    public void setItemDurability(short itemDurability) {
        this.itemDurability = itemDurability;
    }

    public double getProjectileCriticalDamage() {
        return projectileCriticalDamage;
    }

    public void setProjectileCriticalDamage(double projectileCriticalDamage) {
        this.projectileCriticalDamage = projectileCriticalDamage;
    }

    public boolean hasWeaponItemDropHologram() {
        return weaponItemDropHologram;
    }

    public void setWeaponItemDropHologram(boolean weaponItemDropHologram) {
        this.weaponItemDropHologram = weaponItemDropHologram;
    }
}