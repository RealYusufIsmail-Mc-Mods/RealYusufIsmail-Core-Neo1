package io.github.realyusufismail.realyusufismailcore.data.support.dimension.util;

public enum GeneratorType {
    DEBUG_WORLD("minecraft:debug"),
    SUPERFLAT("minecraft:flat"),
    DEFAULT("minecraft:noise");

    private final String id;

    GeneratorType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static GeneratorType fromId(String id) {
        for (GeneratorType type : values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
}
