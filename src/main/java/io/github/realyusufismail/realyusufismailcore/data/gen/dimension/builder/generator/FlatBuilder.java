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

    /**
     * Sets the biome for the flat builder. Use {@link Biomes} to get the biome
     *
     * @param biome the resource key of the biome to set
     * @return the updated FlatBuilder instance
     */
    public FlatBuilder setBiome(ResourceKey<Biome> biome) {
        this.biome = biome;
        return this;
    }

    /**
     * Sets whether the generated flat world has lakes.
     *
     * @param hasLakes true if the flat world should have lakes, false otherwise
     * @return the FlatBuilder object with the updated hasLakes value
     */
    public FlatBuilder setHasLakes(boolean hasLakes) {
        this.hasLakes = hasLakes;
        return this;
    }

    /**
     * Sets whether the flat has features.
     *
     * @param hasFeatures whether the flat has features
     * @return the FlatBuilder object
     */
    public FlatBuilder setHasFeatures(boolean hasFeatures) {
        this.hasFeatures = hasFeatures;
        return this;
    }

    /**
     * Sets the layers for this FlatBuilder.
     *
     * @param layers the list of LayerBuilder objects to be set as the layers
     * @return the FlatBuilder object with the specified layers set
     */
    public FlatBuilder setLayers(List<LayerBuilder> layers) {
        this.layers = layers;
        return this;
    }

    /**
     * Sets the structure of the flat. Use {@link BuiltinStructures} to get the structure
     *
     * @param structure the resource key representing the structure of the flat
     * @return the updated FlatBuilder object
     */
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
}
