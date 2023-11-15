package io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.support.dimension.util.DimensionType;
import lombok.Getter;

@Getter
public class DimensionBuilder {
    private final String name;
    private final GeneratorBuilder generatorBuilder;
    private DimensionType dimensionType = DimensionType.OVERWORLD;

    public DimensionBuilder(String name, GeneratorBuilder generatorBuilder) {
        this.name = name;
        this.generatorBuilder = generatorBuilder;
    }

    public DimensionBuilder setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
        return this;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", dimensionType.getId());
        object.add("generator", generatorBuilder.toJson());
        return object;
    }
}
