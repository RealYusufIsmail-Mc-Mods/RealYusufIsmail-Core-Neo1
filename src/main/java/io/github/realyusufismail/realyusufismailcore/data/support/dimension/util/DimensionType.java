package io.github.realyusufismail.realyusufismailcore.data.support.dimension.util;

public enum DimensionType {
    OVERWORLD("minecraft:overworld"),
    OVERWORLD_CAVES("minecraft:overworld_caves"),
    NETHER("minecraft:the_nether"),
    END("minecraft:the_end");

    private final String id;

    DimensionType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static DimensionType fromId(String id) {
        for (DimensionType type : values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
}
