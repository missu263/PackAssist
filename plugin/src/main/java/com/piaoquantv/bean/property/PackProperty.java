package com.piaoquantv.bean.property;

/**
 * Create by nieqi on 10/25/21
 */
public class PackProperty {
    public static final String CUSTOM_PROPERTY_FLAG = "custom_pack_property_";

    public static final String ENV = "接口环境";
    public static final String CHAT_NAME = "chatName";

    public static boolean isCustomProperty(String propertyName) {
        if (propertyName.contains(CUSTOM_PROPERTY_FLAG)) {
            return true;
        }

        return false;
    }

    public static PackProperty build(String name, String value) {
        return new PackProperty(name, value);
    }

    public PackProperty() {

    }

    public PackProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String name;
    public String value;

    @Override
    public String toString() {
        return "PackProperty{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
