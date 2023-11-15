package io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.GeneratorBuilder;
import io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.generator.builder.LayerBuilder;
import lombok.Setter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.List;

@Setter
public class FlatBuilder {

    private final GeneratorBuilder generatorBuilder;

    private ResourceKey<Biome> biome = Biomes.PLAINS;
    private boolean hasLakes = true;
    private boolean hasFeatures = true;
    private List<LayerBuilder> layers;
    private ResourceKey<Structure> structure = BuiltinStructures.ANCIENT_CITY;

    public FlatBuilder(GeneratorBuilder generatorBuilder) {
        this.generatorBuilder = generatorBuilder;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();

        object.addProperty("biome", biome.location().toString());
        object.addProperty("lakes", hasLakes);
        object.addProperty("features", hasFeatures);
        object.addProperty("structures", structure.location().toString());

        for (LayerBuilder layer : layers) {
            object.add("layers", layer.toJson());
        }

        return object;
    }

    public void build() {
        generatorBuilder.setFlatBuilder(this);
    }
}
