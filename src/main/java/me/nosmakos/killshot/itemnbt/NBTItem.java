package me.nosmakos.killshot.itemnbt;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound {
    private ItemStack bukkitItem;

    public NBTItem(ItemStack item) {
        super(null, null);
        if(item == null) {
            throw new NullPointerException("ItemStack can't be null!");
        } else {
            this.bukkitItem = item.clone();
        }
    }

    protected Object getCompound() {
        return NBTReflectionUtil.getItemRootNBTTagCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, this.bukkitItem));
    }

    protected void setCompound(Object compound) {
        Object stack = ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, this.bukkitItem);
        ReflectionMethod.ITEMSTACK_SET_TAG.run(stack, compound);
        this.bukkitItem = (ItemStack)ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null, stack);
    }

    public ItemStack getItem() {
        return this.bukkitItem;
    }

    protected void setItem(ItemStack item) {
        this.bukkitItem = item;
    }

    public boolean hasNBTData() {
        return this.getCompound() != null;
    }

    public static NBTContainer convertItemtoNBT(ItemStack item) {
        return NBTReflectionUtil.convertNMSItemtoNBTCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, item));
    }

    public static ItemStack convertNBTtoItem(NBTCompound comp) {
        return (ItemStack)ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run((Object)null, NBTReflectionUtil.convertNBTCompoundtoNMSItem(comp));
    }
}
