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
package io.github.realyusufismail.realyusufismailcore.data.dimension.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.FlatBuilder;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.InlineNoiseBuilder;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.Reference;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.ReferenceNoiseBuilder;
import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.builder.BiomeSourceBuilder;
import io.github.realyusufismail.realyusufismailcore.data.dimension.util.GeneratorType;
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

    public FlatBuilder flat() {

        if (generatorType != GeneratorType.SUPERFLAT) {
            throw new IllegalStateException("Generator type must be SUPERFLAT");
        }

        return new FlatBuilder(this);
    }

    public ReferenceNoiseBuilder referenceNoise(Reference reference, BiomeSourceBuilder biomeSourceBuilder) {

        if (generatorType != GeneratorType.DEFAULT) {
            throw new IllegalStateException("Generator type must be DEFAULT");
        }

        return new ReferenceNoiseBuilder(this, reference, biomeSourceBuilder);
    }

    public InlineNoiseBuilder inlineNoise() {

        if (generatorType != GeneratorType.DEFAULT) {
            throw new IllegalStateException("Generator type must be DEFAULT");
        }

        return new InlineNoiseBuilder(this);
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", generatorType.getId());

        if (flatBuilder != null) {
            object.add("settings", flatBuilder.toJson());
        } else if (referenceNoiseBuilder != null) {
            object.add("settings", referenceNoiseBuilder.toJson());
        } else if (inlineNoiseBuilder != null) {
            object.add("settings", inlineNoiseBuilder.toJson());
        }

        return object;
    }
}
