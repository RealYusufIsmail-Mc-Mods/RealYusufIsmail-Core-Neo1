/*
 * Copyright 2023 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.GeneratorBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.builder.LayerBuilder;
import java.util.List;
import lombok.Getter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

@Getter
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

    public FlatBuilder setBiome(ResourceKey<Biome> biome) {
        this.biome = biome;
        return this;
    }

    public FlatBuilder setHasLakes(boolean hasLakes) {
        this.hasLakes = hasLakes;
        return this;
    }

    public FlatBuilder setHasFeatures(boolean hasFeatures) {
        this.hasFeatures = hasFeatures;
        return this;
    }

    public FlatBuilder setLayers(List<LayerBuilder> layers) {
        this.layers = layers;
        return this;
    }

    public FlatBuilder setStructure(ResourceKey<Structure> structure) {
        this.structure = structure;
        return this;
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
