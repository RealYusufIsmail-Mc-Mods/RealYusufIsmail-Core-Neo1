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
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.util.DimensionType;
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
