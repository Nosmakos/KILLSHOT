package me.nosmakos.killshot.itemnbt;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;

public class NBTReflectionUtil {
    public NBTReflectionUtil() {
    }

    public static Object getNMSEntity(Entity entity) {
        try {
            return ReflectionMethod.CRAFT_ENTITY_GET_HANDLE.run(ClassWrapper.CRAFT_ENTITY.getClazz().cast(entity));
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object readNBTFile(FileInputStream stream) {
        try {
            return ReflectionMethod.NBTFILE_READ.run((Object)null, stream);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object saveNBTFile(Object nbt, FileOutputStream stream) {
        try {
            return ReflectionMethod.NBTFILE_WRITE.run((Object)null, nbt, stream);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Object getItemRootNBTTagCompound(Object nmsitem) {
        Class clazz = nmsitem.getClass();

        try {
            Method method = clazz.getMethod("getTag");
            Object answer = method.invoke(nmsitem);
            return answer;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Object convertNBTCompoundtoNMSItem(NBTCompound nbtcompound) {
        Class clazz = ClassWrapper.NMS_ITEMSTACK.getClazz();

        try {
            if(MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_12_R1.getVersionId()) {
                Constructor<?> constructor = clazz.getConstructor(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
                constructor.setAccessible(true);
                return constructor.newInstance(nbtcompound.getCompound());
            } else {
                Method method = clazz.getMethod("createStack", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
                method.setAccessible(true);
                return method.invoke((Object)null, nbtcompound.getCompound());
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static NBTContainer convertNMSItemtoNBTCompound(Object nmsitem) {
        Class clazz = nmsitem.getClass();

        try {
            Method method = clazz.getMethod("save", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            Object answer = method.invoke(nmsitem, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]));
            return new NBTContainer(answer);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static Object getEntityNBTTagCompound(Object NMSEntity) {
        try {
            Object nbt = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object answer = ReflectionMethod.NMS_ENTITY_GET_NBT.run(NMSEntity, nbt);
            if(answer == null) {
                answer = nbt;
            }

            return answer;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Object setEntityNBTTag(Object NBTTag, Object NMSEntity) {
        try {
            ReflectionMethod.NMS_ENTITY_SET_NBT.run(NMSEntity, NBTTag);
            return NMSEntity;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Object getTileEntityNBTTagCompound(BlockState tile) {
        try {
            Object pos = ObjectCreator.NMS_BLOCKPOSITION.getInstance(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(tile.getWorld());
            Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
            Object o = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(nmsworld, pos);
            Object tag = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object answer = ReflectionMethod.TILEENTITY_GET_NBT.run(o, tag);
            if(answer == null) {
                answer = tag;
            }

            return answer;
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static void setTileEntityNBTTagCompound(BlockState tile, Object comp) {
        try {
            Object pos = ObjectCreator.NMS_BLOCKPOSITION.getInstance(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(tile.getWorld());
            Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
            Object o = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(nmsworld, pos);
            ReflectionMethod.TILEENTITY_SET_NBT.run(o, comp);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static Object getSubNBTTagCompound(Object compound, String name) {
        Class c = compound.getClass();

        try {
            Method method = c.getMethod("getCompound", String.class);
            Object answer = method.invoke(compound, name);
            return answer;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static void addNBTTagCompound(NBTCompound comp, String name) {
        if(name == null) {
            remove(comp, name);
        } else {
            Object nbttag = comp.getCompound();
            if(nbttag == null) {
                nbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
            }

            if(valideCompound(comp)) {
                Object workingtag = gettoCompount(nbttag, comp);

                try {
                    Method method = workingtag.getClass().getMethod("set", String.class, ClassWrapper.NMS_NBTBASE.getClazz());
                    method.invoke(workingtag, name, ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance());
                    comp.setCompound(nbttag);
                } catch (Exception var6) {
                    var6.printStackTrace();
                }
            }
        }
    }

    public static Boolean valideCompound(NBTCompound comp) {
        Object root = comp.getCompound();
        if(root == null) {
            root = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }

        return gettoCompount(root, comp) != null;
    }

    static Object gettoCompount(Object nbttag, NBTCompound comp) {
        Stack structure;
        for(structure = new Stack(); comp.getParent() != null; comp = comp.getParent()) {
            structure.add(comp.getName());
        }

        do {
            if(structure.isEmpty()) {
                return nbttag;
            }

            nbttag = getSubNBTTagCompound(nbttag, (String)structure.pop());
        } while(nbttag != null);

        return null;
    }

    public static void addOtherNBTCompound(NBTCompound comp, NBTCompound nbtcompound) {
        Object rootnbttag = comp.getCompound();
        if(rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }

        if(valideCompound(comp)) {
            Object workingtag = gettoCompount(rootnbttag, comp);

            try {
                ReflectionMethod.COMPOUND_ADD.run(workingtag, nbtcompound.getCompound());
                comp.setCompound(rootnbttag);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

        }
    }

    public static String getContent(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if(rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }

        if(!valideCompound(comp)) {
            return null;
        } else {
            Object workingtag = gettoCompount(rootnbttag, comp);

            try {
                Method method = workingtag.getClass().getMethod("get", String.class);
                return method.invoke(workingtag, new Object[]{key}).toString();
            } catch (Exception var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public static void set(NBTCompound comp, String key, Object val) {
        if(val == null) {
            remove(comp, key);
        } else {
            Object rootnbttag = comp.getCompound();
            if(rootnbttag == null) {
                rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
            }

            if(!valideCompound(comp)) {
                (new Throwable("InvalideCompound")).printStackTrace();
            } else {
                Object workingtag = gettoCompount(rootnbttag, comp);

                try {
                    Method method = workingtag.getClass().getMethod("set", String.class, ClassWrapper.NMS_NBTBASE.getClazz());
                    method.invoke(workingtag, key, val);
                    comp.setCompound(rootnbttag);
                } catch (Exception var7) {
                    var7.printStackTrace();
                }

            }
        }
    }


    public static void setObject(NBTCompound comp, String key, Object value) {
        if(MinecraftVersion.hasGsonSupport()) {
            try {
                String json = GsonWrapper.getString(value);
                setData(comp, ReflectionMethod.COMPOUND_SET_STRING, key, json);
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }
    }

    public static <T> T getObject(NBTCompound comp, String key, Class<T> type) {
        if(!MinecraftVersion.hasGsonSupport()) {
            return null;
        } else {
            String json = (String)getData(comp, ReflectionMethod.COMPOUND_GET_STRING, key);
            return json == null?null:GsonWrapper.deserializeJson(json, type);
        }
    }

    public static void remove(NBTCompound comp, String key) {
        Object rootnbttag = comp.getCompound();
        if(rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }

        if(valideCompound(comp)) {
            Object workingtag = gettoCompount(rootnbttag, comp);
            ReflectionMethod.COMPOUND_REMOVE_KEY.run(workingtag, key);
            comp.setCompound(rootnbttag);
        }
    }

    public static Set getKeys(NBTCompound comp) {
        Object rootnbttag = comp.getCompound();
        if(rootnbttag == null) {
            rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
        }

        if(!valideCompound(comp)) {
            return null;
        } else {
            Object workingtag = gettoCompount(rootnbttag, comp);
            return (Set)ReflectionMethod.COMPOUND_GET_KEYS.run(workingtag);
        }
    }

    public static void setData(NBTCompound comp, ReflectionMethod type, String key, Object data) {
        if(data == null) {
            remove(comp, key);
        } else {
            Object rootnbttag = comp.getCompound();
            if(rootnbttag == null) {
                rootnbttag = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
            }

            if(valideCompound(comp)) {
                Object workingtag = gettoCompount(rootnbttag, comp);
                type.run(workingtag, key, data);
                comp.setCompound(rootnbttag);
            }
        }
    }

    public static Object getData(NBTCompound comp, ReflectionMethod type, String key) {
        Object rootnbttag = comp.getCompound();
        if(rootnbttag == null) {
            return null;
        } else if(!valideCompound(comp)) {
            return null;
        } else {
            Object workingtag = gettoCompount(rootnbttag, comp);
            return type.run(workingtag, key);
        }
    }
}
