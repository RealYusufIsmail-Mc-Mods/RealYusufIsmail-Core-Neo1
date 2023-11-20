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
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.FlatBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.InlineNoiseBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.ReferenceNoiseBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.builder.BiomeSourceBuilder;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.builder.Reference;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.util.GeneratorType;
import lombok.Setter;

@Setter
public class GeneratorBuilder {

    private final GeneratorType generatorType;

    private FlatBuilder flatBuilder;

    private ReferenceNoiseBuilder referenceNoiseBuilder;

    private InlineNoiseBuilder inlineNoiseBuilder;

    public GeneratorBuilder(GeneratorType generatorType) {
        this.generatorType = generatorType;
    }

    /**
     * Returns a new instance of FlatBuilder using the current object as the parent.
     *
     * @return a new instance of FlatBuilder
     * @throws IllegalStateException if the generator type is not SUPERFLAT
     */
    public FlatBuilder flat() {

        if (generatorType != GeneratorType.SUPERFLAT) {
            throw new IllegalStateException("Generator type must be SUPERFLAT");
        }

        return new FlatBuilder(this);
    }

    /**
     * Sets the reference noise for the generator.
     * Only applicable when the generator type is DEFAULT.
     *
     * @param reference           the reference noise to set
     * @param biomeSourceBuilder  the biome source builder to use
     * @return a new instance of ReferenceNoiseBuilder
     * @throws IllegalStateException  if the generator type is not DEFAULT
     */
    public ReferenceNoiseBuilder referenceNoise(Reference reference, BiomeSourceBuilder biomeSourceBuilder) {

        if (generatorType != GeneratorType.DEFAULT) {
            throw new IllegalStateException("Generator type must be DEFAULT");
        }

        return new ReferenceNoiseBuilder(this, reference, biomeSourceBuilder);
    }

    /**
     * Returns an InlineNoiseBuilder object based on the given BiomeSource.
     *
     * @param biomeSourceBuilder the BiomeSourceBuilder object used for generating inline noise
     * @throws IllegalStateException if the generatorType is not set to DEFAULT
     * @return an InlineNoiseBuilder object
     */
    public InlineNoiseBuilder inlineNoise(BiomeSourceBuilder biomeSourceBuilder) {

        if (generatorType != GeneratorType.DEFAULT) {
            throw new IllegalStateException("Generator type must be DEFAULT");
        }

        return new InlineNoiseBuilder(this, biomeSourceBuilder);
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", generatorType.getId());

        if (flatBuilder != null) {
            object.add("settings", flatBuilder.toJson());
        } else if (referenceNoiseBuilder != null) {
            object.add("settings", referenceNoiseBuilder.toJson());
            object.add(
                    "biome_source",
                    referenceNoiseBuilder.getBiomeSourceBuilder().toJson());
        } else if (inlineNoiseBuilder != null) {
            object.add("settings", inlineNoiseBuilder.toJson());
            object.add(
                    "biome_source", inlineNoiseBuilder.getBiomeSourceBuilder().toJson());
        }

        return object;
    }
}
