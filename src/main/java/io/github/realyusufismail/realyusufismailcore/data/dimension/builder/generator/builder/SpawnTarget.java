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
package io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SpawnTarget {
    private int temperature;
    private int humidity;
    private int continentalness;
    private int erosion;
    private int weirdness;
    private int depth;
    private int offset;

    public SpawnTarget setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public SpawnTarget setHumidity(int humidity) {
        this.humidity = humidity;
        return this;
    }

    public SpawnTarget setContinentalness(int continentalness) {
        this.continentalness = continentalness;
        return this;
    }

    public SpawnTarget setErosion(int erosion) {
        this.erosion = erosion;
        return this;
    }

    public SpawnTarget setWeirdness(int weirdness) {
        this.weirdness = weirdness;
        return this;
    }

    public SpawnTarget setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public SpawnTarget setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("temperature", temperature);
        object.addProperty("humidity", humidity);
        object.addProperty("continentalness", continentalness);
        object.addProperty("erosion", erosion);
        object.addProperty("weirdness", weirdness);
        object.addProperty("depth", depth);
        object.addProperty("offset", offset);
        return object;
    }
}
