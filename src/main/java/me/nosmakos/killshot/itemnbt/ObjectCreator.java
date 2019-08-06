package me.nosmakos.killshot.itemnbt;



import java.lang.reflect.Constructor;

public enum ObjectCreator {
    NMS_NBTTAGCOMPOUND(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[0]),
    NMS_BLOCKPOSITION(ClassWrapper.NMS_BLOCKPOSITION.getClazz(), new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});

    private Constructor<?> construct;

    ObjectCreator(Class<?> clazz, Class<?>[] args) {
        try {
            this.construct = clazz.getConstructor(args);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public Object getInstance(Object... args) {
        try {
            return this.construct.newInstance(args);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
