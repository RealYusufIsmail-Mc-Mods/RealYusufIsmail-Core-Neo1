package io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.generator.FlatBuilder;
import io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.generator.NoiseBuilder;
import io.github.realyusufismail.realyusufismailcore.data.support.dimension.util.GeneratorType;
import lombok.Setter;

@Setter
public class GeneratorBuilder {

    private final GeneratorType generatorType;

    private FlatBuilder flatBuilder;

    private NoiseBuilder noiseBuilder;

    public GeneratorBuilder(GeneratorType generatorType) {
        this.generatorType = generatorType;
    }

    public FlatBuilder flat() {

        if (generatorType != GeneratorType.SUPERFLAT) {
            throw new IllegalStateException("Generator type must be SUPERFLAT");
        }

        return new FlatBuilder(this);
    }

    public NoiseBuilder noise() {

        if (generatorType != GeneratorType.DEFAULT) {
            throw new IllegalStateException("Generator type must be DEFAULT");
        }

        return new NoiseBuilder(this);
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", generatorType.getId());

        if (flatBuilder != null) {
            object.add("settings", flatBuilder.toJson());
        } else if (noiseBuilder != null) {
            object.add("settings", noiseBuilder.toJson());
        }

        return object;
    }
}
