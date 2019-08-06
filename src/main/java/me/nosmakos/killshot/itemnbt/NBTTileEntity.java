package me.nosmakos.killshot.itemnbt;

import org.bukkit.block.BlockState;

public class NBTTileEntity extends NBTCompound {

    private final BlockState tile;

    /**
     * @param tile BlockState from any TileEntity
     */
    public NBTTileEntity(BlockState tile) {
        super(null, null);
        this.tile = tile;
    }

    @Override
    public Object getCompound() {
        return NBTReflectionUtil.getTileEntityNBTTagCompound(tile);
    }

    @Override
    protected void setCompound(Object compound) {
        NBTReflectionUtil.setTileEntityNBTTagCompound(tile, compound);
    }

}