package me.nosmakos.killshot.itemnbt;

import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public enum ReflectionMethod {
    COMPOUND_SET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Float.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setFloat")}),
    COMPOUND_SET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setString")}),
    COMPOUND_SET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Integer.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setInt")}),
    COMPOUND_SET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, byte[].class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setByteArray")}),
    COMPOUND_SET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, int[].class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setIntArray")}),
    COMPOUND_SET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Long.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setLong")}),
    COMPOUND_SET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Short.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setShort")}),
    COMPOUND_SET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Byte.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setByte")}),
    COMPOUND_SET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Double.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setDouble")}),
    COMPOUND_SET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Boolean.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setBoolean")}),
    COMPOUND_ADD(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "a")}),
    COMPOUND_GET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getFloat")}),
    COMPOUND_GET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getString")}),
    COMPOUND_GET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getInt")}),
    COMPOUND_GET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getByteArray")}),
    COMPOUND_GET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getIntArray")}),
    COMPOUND_GET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getLong")}),
    COMPOUND_GET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getShort")}),
    COMPOUND_GET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getByte")}),
    COMPOUND_GET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getDouble")}),
    COMPOUND_GET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getBoolean")}),
    COMPOUND_REMOVE_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "remove")}),
    COMPOUND_HAS_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "hasKey")}),
    COMPOUND_GET_TYPE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_8_R3, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_8_R3, "b"), new ReflectionMethod.Since(MinecraftVersion.MC1_9_R1, "d")}),
    COMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[0], MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "c"), new ReflectionMethod.Since(MinecraftVersion.MC1_13_R1, "getKeys")}),
    LISTCOMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[0], MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "c"), new ReflectionMethod.Since(MinecraftVersion.MC1_13_R1, "getKeys")}),
    LIST_REMOVE_KEY(ClassWrapper.NMS_NBTTAGLIST.getClazz(), new Class[]{Integer.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "a"), new ReflectionMethod.Since(MinecraftVersion.MC1_9_R1, "remove")}),
    LIST_SIZE(ClassWrapper.NMS_NBTTAGLIST.getClazz(), new Class[0], MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "size")}),
    LIST_SET(ClassWrapper.NMS_NBTTAGLIST.getClazz(), new Class[]{Integer.TYPE, ClassWrapper.NMS_NBTBASE.getClazz()}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "a"), new ReflectionMethod.Since(MinecraftVersion.MC1_13_R1, "set")}),
    LEGACY_LIST_ADD(ClassWrapper.NMS_NBTTAGLIST.getClazz(), new Class[]{ClassWrapper.NMS_NBTBASE.getClazz()}, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_13_R2, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "add")}),
    LIST_ADD(ClassWrapper.NMS_NBTTAGLIST.getClazz(), new Class[]{Integer.TYPE, ClassWrapper.NMS_NBTBASE.getClazz()}, MinecraftVersion.MC1_14_R1, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_14_R1, "add")}),
    LIST_GET_STRING(ClassWrapper.NMS_NBTTAGLIST.getClazz(), new Class[]{Integer.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getString")}),
    LIST_GET(ClassWrapper.NMS_NBTTAGLIST.getClazz(), new Class[]{Integer.TYPE}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "get")}),
    ITEMSTACK_SET_TAG(ClassWrapper.NMS_ITEMSTACK.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "setTag")}),
    ITEMSTACK_NMSCOPY(ClassWrapper.CRAFT_ITEMSTACK.getClazz(), new Class[]{ItemStack.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "asNMSCopy")}),
    ITEMSTACK_BUKKITMIRROR(ClassWrapper.CRAFT_ITEMSTACK.getClazz(), new Class[]{ClassWrapper.NMS_ITEMSTACK.getClazz()}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "asCraftMirror")}),
    CRAFT_WORLD_GET_HANDLE(ClassWrapper.CRAFT_WORLD.getClazz(), new Class[0], MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getHandle")}),
    NMS_WORLD_GET_TILEENTITY(ClassWrapper.NMS_WORLD.getClazz(), new Class[]{ClassWrapper.NMS_BLOCKPOSITION.getClazz()}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getTileEntity")}),
    TILEENTITY_GET_NBT(ClassWrapper.NMS_TILEENTITY.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_8_R3, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_8_R3, "b"), new ReflectionMethod.Since(MinecraftVersion.MC1_9_R1, "save")}),
    TILEENTITY_SET_NBT(ClassWrapper.NMS_TILEENTITY.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_8_R3, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_8_R3, "a"), new ReflectionMethod.Since(MinecraftVersion.MC1_12_R1, "load")}),
    CRAFT_ENTITY_GET_HANDLE(ClassWrapper.CRAFT_ENTITY.getClazz(), new Class[0], MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "getHandle")}),
    NMS_ENTITY_SET_NBT(ClassWrapper.NMS_ENTITY.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_8_R3, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_8_R3, "f")}),
    NMS_ENTITY_GET_NBT(ClassWrapper.NMS_ENTITY.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_8_R3, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_8_R3, "e"), new ReflectionMethod.Since(MinecraftVersion.MC1_12_R1, "save")}),
    NBTFILE_READ(ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS.getClazz(), new Class[]{InputStream.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "a")}),
    NBTFILE_WRITE(ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), OutputStream.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "a")}),
    PARSE_NBT(ClassWrapper.NMS_MOJANGSONPARSER.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new ReflectionMethod.Since[]{new ReflectionMethod.Since(MinecraftVersion.MC1_7_R4, "parse")});

    private MinecraftVersion removedAfter;
    private ReflectionMethod.Since targetVersion;
    private Method method;
    private boolean loaded;
    private boolean compatible;

    private ReflectionMethod(Class<?> targetClass, Class<?>[] args, MinecraftVersion addedSince, MinecraftVersion removedAfter, ReflectionMethod.Since[] methodnames) {
        this.loaded = false;
        this.compatible = false;
        this.removedAfter = removedAfter;
        MinecraftVersion server = MinecraftVersion.getVersion();
        if(server.compareTo(addedSince) >= 0 && (this.removedAfter == null || server.getVersionId() <= this.removedAfter.getVersionId())) {
            this.compatible = true;
            ReflectionMethod.Since target = methodnames[0];
            ReflectionMethod.Since[] var10 = methodnames;
            int var11 = methodnames.length;

            for(int var12 = 0; var12 < var11; ++var12) {
                ReflectionMethod.Since s = var10[var12];
                if(s.version.getVersionId() <= server.getVersionId() && target.version.getVersionId() < s.version.getVersionId()) {
                    target = s;
                }
            }

            this.targetVersion = target;

            try {
                this.method = targetClass.getMethod(this.targetVersion.name, args);
                this.method.setAccessible(true);
                this.loaded = true;
            } catch (NoSuchMethodException | SecurityException | NullPointerException var14) {
                var14.printStackTrace();
            }

        }
    }

    private ReflectionMethod(Class<?> targetClass, Class<?>[] args, MinecraftVersion addedSince, ReflectionMethod.Since[] methodnames) {
        this(targetClass, args, addedSince, (MinecraftVersion)null, methodnames);
    }

    public Object run(Object target, Object... args) {
        try {
            return this.method.invoke(target, args);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public boolean isCompatible() {
        return this.compatible;
    }

    public static class Since {
        public final MinecraftVersion version;
        public final String name;

        public Since(MinecraftVersion version, String name) {
            this.version = version;
            this.name = name;
        }
    }
}
